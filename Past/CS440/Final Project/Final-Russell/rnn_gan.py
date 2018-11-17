# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# ==============================================================================

"""
The hyperparameters used in the model:
- learning_rate - the initial value of the learning rate
- max_grad_norm - the maximum permissible norm of the gradient
- num_layers - the number of LSTM layers
- songlength - the number of unrolled steps of LSTM
- hidden_size - the number of LSTM units
- epochs_before_decay - the number of epochs trained with the initial learning rate
- max_epoch - the total number of epochs for training
- keep_prob - the probability of keeping weights in the dropout layer
- lr_decay - the decay of the learning rate for each epoch after "epochs_before_decay"
- batch_size - the batch size

The hyperparameters that could be used in the model:
- init_scale - the initial scale of the weights

To run:

$ python rnn_gan.py --model small|medium|large --datadir simple-examples/data/ --traindir dir-for-checkpoints-and-plots --select_validation_percentage 0-40 --select_test_percentage 0-40
"""

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import time, datetime, os, sys
import cPickle as pkl
from subprocess import call, Popen

import numpy as np
import tensorflow as tf
from tensorflow.python.client import timeline

import music_data_utils
from midi_statistics import get_all_stats

flags = tf.flags
logging = tf.logging

flags.DEFINE_string("datadir", None, "Directory to save and load midi music files.")
flags.DEFINE_string("traindir", None, "Directory to save checkpoints and gnuplot files.")
flags.DEFINE_integer("epochs_per_checkpoint", 2, "How many training epochs to do per checkpoint.")
flags.DEFINE_boolean("log_device_placement", False, "Outputs info on device placement.")
flags.DEFINE_integer("exit_after", 1440, "exit after this many minutes")
flags.DEFINE_integer("select_validation_percentage", None, "Select random percentage of data as validation set.")
flags.DEFINE_integer("select_test_percentage", None, "Select random percentage of data as test set.")
flags.DEFINE_boolean("sample", False, "Sample output from the model. Assume training was already done. Save sample output to file.")
flags.DEFINE_integer("works_per_composer", 1, "Limit number of works per composer that is loaded.")
flags.DEFINE_boolean("disable_feed_previous", False, "Feed output from previous cell to the input of the next. In the generator.")
flags.DEFINE_float("init_scale", 0.05, "the initial scale of the weights")
flags.DEFINE_float("learning_rate", 0.1, "Learning rate")
flags.DEFINE_float("d_lr_factor", 0.5, "Learning rate decay")
flags.DEFINE_float("max_grad_norm", 5.0, "the maximum permissible norm of the gradient")
flags.DEFINE_float("keep_prob", 0.5, "Keep probability. 1.0 disables dropout.")
flags.DEFINE_float("lr_decay", 1.0, "Learning rate decay after each epoch after epochs_before_decay")
flags.DEFINE_integer("num_layers_g", 2, "Number of stacked recurrent cells in G.")
flags.DEFINE_integer("num_layers_d", 2, "Number of stacked recurrent cells in D.")
flags.DEFINE_integer("songlength", 20, "Limit song inputs to this number of events.")
flags.DEFINE_integer("meta_layer_size", 200, "Size of hidden layer for meta information module.")
flags.DEFINE_integer("hidden_size_g", 350, "Hidden size for recurrent part of G.")
flags.DEFINE_integer("hidden_size_d", 350, "Hidden size for recurrent part of D. Default: same as for G.")
flags.DEFINE_integer("epochs_before_decay", 60, "Number of epochs before starting to decay.")
flags.DEFINE_integer("max_epoch", 50, "Number of epochs before stopping training.")
flags.DEFINE_integer("batch_size", 20, "Batch size.")
flags.DEFINE_integer("biscale_slow_layer_ticks", 8, "Biscale slow layer ticks. Not implemented yet.")
flags.DEFINE_boolean("multiscale", False, "Multiscale RNN. Not implemented.")
flags.DEFINE_integer("pretraining_epochs", 6, "Number of epochs to run lang-model style pretraining.")
flags.DEFINE_boolean("pretraining_d", False, "Train D during pretraining.")
flags.DEFINE_boolean("initialize_d", False, "Initialize variables for D, no matter if there are trained versions in checkpoint.")
flags.DEFINE_boolean("ignore_saved_args", False, "Tells the program to ignore saved arguments, and instead use the ones provided as CLI arguments.")
flags.DEFINE_boolean("pace_events", False, "When parsing input data, insert one dummy event at each quarter note if there is no tone.")
flags.DEFINE_boolean("minibatch_d", False, "Adding kernel features for minibatch diversity.")
flags.DEFINE_boolean("unidirectional_d", True, "Unidirectional RNN instead of bidirectional RNN for D.")
flags.DEFINE_boolean("profiling", False, "Profiling. Writing a timeline.json file in plots dir.")
flags.DEFINE_boolean("float16", False, "Use floa16 data type. Otherwise, use float32.")
flags.DEFINE_boolean("adam", False, "Use Adam optimizer.")
flags.DEFINE_boolean("feature_matching", False, "Feature matching objective for G.")
flags.DEFINE_boolean("disable_l2_regularizer", False, "L2 regularization on weights.")
flags.DEFINE_float("reg_scale", 1.0, "L2 regularization scale.")
flags.DEFINE_boolean("synthetic_chords", False, "Train on synthetically generated chords (three tones per event).")
flags.DEFINE_integer("tones_per_cell", 1, "Maximum number of tones to output per RNN cell.")
flags.DEFINE_string("composer", None, "Specify exactly one composer, and train model only on this.")
flags.DEFINE_float("random_input_scale", 1.0, "Scale of random inputs (1.0=same size as generated features).")
flags.DEFINE_boolean("end_classification", False, "Classify only in ends of D. Otherwise, does classification at every timestep and mean reduce.")

FLAGS = flags.FLAGS

model_layout_flags = ['num_layers_g', 'num_layers_d', 'meta_layer_size', 'hidden_size_g', 'hidden_size_d', 'biscale_slow_layer_ticks', 'multiscale', 'multiscale', 'disable_feed_previous', 'pace_events', 'minibatch_d', 'unidirectional_d', 'feature_matching', 'composer']

def restore_flags(save_if_none_found=True):
  if FLAGS.traindir:
    saved_args_dir = os.path.join(FLAGS.traindir, 'saved_args')
    if save_if_none_found:
      try: os.makedirs(saved_args_dir)
      except: pass
    for arg in FLAGS.__flags:
      if arg not in model_layout_flags:
        continue
      if FLAGS.ignore_saved_args and os.path.exists(os.path.join(saved_args_dir, arg+'.pkl')):
        print('{:%Y-%m-%d %H:%M:%S}: saved_args: Found {} setting from saved state, but using CLI args ({}) and saving (--ignore_saved_args).'.format(datetime.datetime.today(), arg, getattr(FLAGS, arg)))
      elif os.path.exists(os.path.join(saved_args_dir, arg+'.pkl')):
        with open(os.path.join(saved_args_dir, arg+'.pkl'), 'r') as f:
          setattr(FLAGS, arg, pkl.load(f))
          print('{:%Y-%m-%d %H:%M:%S}: saved_args: {} from saved state ({}), ignoring CLI args.'.format(datetime.datetime.today(), arg, getattr(FLAGS, arg)))
      elif save_if_none_found:
        print('{:%Y-%m-%d %H:%M:%S}: saved_args: Found no {} setting from saved state, using CLI args ({}) and saving.'.format(datetime.datetime.today(), arg, getattr(FLAGS, arg)))
        with open(os.path.join(saved_args_dir, arg+'.pkl'), 'w') as f:
          pkl.dump(getattr(FLAGS, arg), f)
      else:
        print('{:%Y-%m-%d %H:%M:%S}: saved_args: Found no {} setting from saved state, using CLI args ({}) but not saving.'.format(datetime.datetime.today(), arg, getattr(FLAGS, arg)))

def data_type():
  return tf.float16 if FLAGS.float16 else tf.float32
  #return tf.float16

def my_reduce_mean(what_to_take_mean_over):
  return tf.reshape(what_to_take_mean_over, shape=[-1])[0]
  denom = 1.0
  for d in what_to_take_mean_over.get_shape():
    if type(d) == tf.Dimension:
      denom = denom*d.value
    else:
      denom = denom*d
  return tf.reduce_sum(what_to_take_mean_over)/denom

def linear(inp, output_dim, scope=None, stddev=1.0, reuse_scope=False):
  norm = tf.random_normal_initializer(stddev=stddev, dtype=data_type())
  const = tf.constant_initializer(0.0, dtype=data_type())
  with tf.variable_scope(scope or 'linear') as scope:
    scope.set_regularizer(tf.contrib.layers.l2_regularizer(scale=FLAGS.reg_scale))
    if reuse_scope:
      scope.reuse_variables()
    w = tf.get_variable('w', [inp.get_shape()[1], output_dim], initializer=norm, dtype=data_type())
    b = tf.get_variable('b', [output_dim], initializer=const, dtype=data_type())
  return tf.matmul(inp, w) + b

def minibatch(inp, num_kernels=25, kernel_dim=10, scope=None, msg='', reuse_scope=False):
  """
   Borrowed from http://blog.aylien.com/introduction-generative-adversarial-networks-code-tensorflow/
  """
  with tf.variable_scope(scope or 'minibatch_d') as scope:
    scope.set_regularizer(tf.contrib.layers.l2_regularizer(scale=FLAGS.reg_scale))
    if reuse_scope:
      scope.reuse_variables()
    x = tf.sigmoid(linear(inp, num_kernels * kernel_dim, scope))
    activation = tf.reshape(x, (-1, num_kernels, kernel_dim))
    diffs = tf.expand_dims(activation, 3) - \
                tf.expand_dims(tf.transpose(activation, [1, 2, 0]), 0)
    abs_diffs = tf.reduce_sum(tf.abs(diffs), 2)
    minibatch_features = tf.reduce_sum(tf.exp(-abs_diffs), 2)
  return tf.concat(axis=1, values=[inp, minibatch_features])

class RNNGAN(object):
  """The RNNGAN model."""
  def __init__(self, is_training, num_song_features=None, num_meta_features=None):
    self.batch_size = batch_size = FLAGS.batch_size
    self.songlength = songlength = FLAGS.songlength
    self._input_songdata = tf.placeholder(shape=[batch_size, songlength, num_song_features], dtype=data_type())
    self._input_metadata = tf.placeholder(shape=[batch_size, num_meta_features], dtype=data_type())
    songdata_inputs = [tf.squeeze(input_, [1]) for input_ in tf.split(self._input_songdata, songlength, axis=1)]
    with tf.variable_scope('G') as scope:
      scope.set_regularizer(tf.contrib.layers.l2_regularizer(scale=FLAGS.reg_scale))
      lstm_cell = tf.nn.rnn_cell.BasicLSTMCell(FLAGS.hidden_size_g, forget_bias=1.0, state_is_tuple=True)
      if is_training and FLAGS.keep_prob < 1:
        lstm_cell = tf.nn.rnn_cell.DropoutWrapper(
            lstm_cell, output_keep_prob=FLAGS.keep_prob)
      cell = tf.nn.rnn_cell.MultiRNNCell([lstm_cell] * FLAGS.num_layers_g, state_is_tuple=True)
      self._initial_state = cell.zero_state(batch_size, data_type())
      random_rnninputs = tf.random_uniform(shape=[batch_size, songlength, int(FLAGS.random_input_scale*num_song_features)], minval=0.0, maxval=1.0, dtype=data_type())
      # Make list of tensors. One per step in recurrence.
      # Each tensor is batchsize*numfeatures.
      random_rnninputs = [tf.squeeze(input_, [1]) for input_ in tf.split(random_rnninputs, songlength, 1)]
      # REAL GENERATOR:
      state = self._initial_state
      # as we feed the output as the input to the next, we 'invent' the initial 'output'.
      generated_point = tf.random_uniform(shape=[batch_size, num_song_features], minval=0.0, maxval=1.0, dtype=data_type())
      outputs = []
      self._generated_features = []
      for i,input_ in enumerate(random_rnninputs):
        if i > 0:
          scope.reuse_variables()
        concat_values = [input_]
        if not FLAGS.disable_feed_previous:
          concat_values.append(generated_point)
        if len(concat_values):
          input_ = tf.concat(axis=1, values=concat_values)
        input_ = tf.nn.relu(linear(input_, FLAGS.hidden_size_g, scope='input_layer', reuse_scope=(i!=0)))
        output, state = cell(input_, state)
        outputs.append(output)
        generated_point = linear(output, num_song_features, scope='output_layer', reuse_scope=(i!=0))
        self._generated_features.append(generated_point)
      # PRETRAINING GENERATOR, will feed inputs, not generated outputs:
      scope.reuse_variables()
      # as we feed the output as the input to the next, we 'invent' the initial 'output'.
      prev_target = tf.random_uniform(shape=[batch_size, num_song_features], minval=0.0, maxval=1.0, dtype=data_type())
      outputs = []
      self._generated_features_pretraining = []
      for i,input_ in enumerate(random_rnninputs):
        concat_values = [input_]
        if not FLAGS.disable_feed_previous:
          concat_values.append(prev_target)
        if len(concat_values):
          input_ = tf.concat(axis=1, values=concat_values)
        input_ = tf.nn.relu(linear(input_, FLAGS.hidden_size_g, scope='input_layer', reuse_scope=(i!=0)))
        output, state = cell(input_, state)
        outputs.append(output)
        generated_point = linear(output, num_song_features, scope='output_layer', reuse_scope=(i!=0))
        self._generated_features_pretraining.append(generated_point)
        prev_target = songdata_inputs[i]
    self._final_state = state
    # These are used both for pretraining and for D/G training further down.
    self._lr = tf.Variable(FLAGS.learning_rate, trainable=False, dtype=data_type())
    self.g_params = [v for v in tf.trainable_variables() if v.name.startswith('model/G/')]
    g_optimizer = tf.train.GradientDescentOptimizer(self._lr)
    reg_losses = tf.get_collection(tf.GraphKeys.REGULARIZATION_LOSSES)
    reg_constant = 0.1  # Choose an appropriate one.
    reg_loss = reg_constant * sum(reg_losses)
    # ---BEGIN, PRETRAINING. ---
    self.rnn_pretraining_loss = tf.reduce_mean(tf.squared_difference(x=tf.transpose(tf.stack(self._generated_features_pretraining), perm=[1, 0, 2]), y=self._input_songdata))
    if not FLAGS.disable_l2_regularizer:
      self.rnn_pretraining_loss = self.rnn_pretraining_loss+reg_loss
    pretraining_grads, _ = tf.clip_by_global_norm(tf.gradients(self.rnn_pretraining_loss, self.g_params), FLAGS.max_grad_norm)
    self.opt_pretraining = g_optimizer.apply_gradients(zip(pretraining_grads, self.g_params))
    # ---END, PRETRAINING---
    # The discriminator tries to tell the difference between samples from the true data distribution (self.x) and the generated samples (self.z).
    # Here we create two copies of the discriminator network (that share parameters), as you cannot use the same network with different inputs in TensorFlow.
    with tf.variable_scope('D') as scope:
      scope.set_regularizer(tf.contrib.layers.l2_regularizer(scale=FLAGS.reg_scale))
      # Make list of tensors. One per step in recurrence. Each tensor is batchsize*numfeatures.
      self.real_d,self.real_d_features = self.discriminator(songdata_inputs, is_training, msg='real')
      scope.reuse_variables()
      generated_data = self._generated_features
      self.generated_d,self.generated_d_features = self.discriminator(generated_data, is_training, msg='generated')
    # Define the loss for discriminator and generator networks (see the original paper for details), and create optimizers for both
    self.d_loss = tf.reduce_mean(-tf.log(tf.clip_by_value(self.real_d, 1e-1000000, 1.0)) -tf.log(1 - tf.clip_by_value(self.generated_d, 0.0, 1.0-1e-1000000)))
    self.g_loss_feature_matching = tf.reduce_sum(tf.squared_difference(self.real_d_features, self.generated_d_features))
    self.g_loss = tf.reduce_mean(-tf.log(tf.clip_by_value(self.generated_d, 1e-1000000, 1.0)))
    if not FLAGS.disable_l2_regularizer:
      self.d_loss = self.d_loss+reg_loss
      self.g_loss_feature_matching = self.g_loss_feature_matching+reg_loss
      self.g_loss = self.g_loss+reg_loss
    self.d_params = [v for v in tf.trainable_variables() if v.name.startswith('model/D/')]
    if not is_training:
      return
    d_optimizer = tf.train.GradientDescentOptimizer(self._lr*FLAGS.d_lr_factor)
    d_grads, _ = tf.clip_by_global_norm(tf.gradients(self.d_loss, self.d_params), FLAGS.max_grad_norm)
    self.opt_d = d_optimizer.apply_gradients(zip(d_grads, self.d_params))
    if FLAGS.feature_matching:
      g_grads, _ = tf.clip_by_global_norm(tf.gradients(self.g_loss_feature_matching, self.g_params), FLAGS.max_grad_norm)
    else:
      g_grads, _ = tf.clip_by_global_norm(tf.gradients(self.g_loss, self.g_params), FLAGS.max_grad_norm)
    self.opt_g = g_optimizer.apply_gradients(zip(g_grads, self.g_params))
    self._new_lr = tf.placeholder(shape=[], name="new_learning_rate", dtype=data_type())
    self._lr_update = tf.assign(self._lr, self._new_lr)

  def discriminator(self, inputs, is_training, msg=''):
    lstm_cell = tf.nn.rnn_cell.BasicLSTMCell(FLAGS.hidden_size_d, forget_bias=1.0)
    if is_training and FLAGS.keep_prob < 1:
      inputs = [tf.nn.dropout(inp, FLAGS.keep_prob) for inp in inputs]
      lstm_cell = tf.nn.rnn_cell.DropoutWrapper(lstm_cell, output_keep_prob=FLAGS.keep_prob)
    cell_fw = tf.nn.rnn_cell.MultiRNNCell([lstm_cell] * FLAGS.num_layers_d)
    initial_state_fw = lstm_cell.zero_state(self.batch_size, data_type())
    outputs, state = tf.nn.static_rnn(lstm_cell, inputs, initial_state=initial_state_fw)
    if FLAGS.minibatch_d:
      outputs = [minibatch(tf.reshape(outp, shape=[FLAGS.batch_size, -1]), msg=msg, reuse_scope=(i!=0)) for i,outp in enumerate(outputs)]
    if FLAGS.end_classification:
      decisions = tf.transpose(tf.stack([tf.sigmoid(linear(output, 1, 'decision', reuse_scope=(i!=0))) for i,output in enumerate([outputs[0], outputs[-1]])]), perm=[1,0,2])
    else:
      decisions = tf.transpose(tf.stack([tf.sigmoid(linear(output, 1, 'decision', reuse_scope=(i!=0))) for i,output in enumerate(outputs)]), perm=[1,0,2])
    decision = tf.reduce_mean(decisions, reduction_indices=[1,2])
    return (decision,tf.transpose(tf.stack(outputs), perm=[1,0,2]))
  
  def assign_lr(self, session, lr_value):
    session.run(self._lr_update, feed_dict={self._new_lr: lr_value})

  @property
  def generated_features(self):
    return self._generated_features

  @property
  def input_songdata(self):
    return self._input_songdata

  @property
  def input_metadata(self):
    return self._input_metadata

  @property
  def targets(self):
    return self._targets

  @property
  def initial_state(self):
    return self._initial_state

  @property
  def cost(self):
    return self._cost

  @property
  def final_state(self):
    return self._final_state

  @property
  def lr(self):
    return self._lr

  @property
  def train_op(self):
    return self._train_op

def run_epoch(session, model, loader, datasetlabel, eval_op_g, pretraining=False, verbose=False):
  """Runs the model on the given data."""
  g_loss, d_loss = 10.0, 10.0
  g_losses, d_losses = 0.0, 0.0
  iters = 0
  loader.rewind(part=datasetlabel)
  [batch_meta, batch_song] = loader.get_batch(model.batch_size, model.songlength, part=datasetlabel)
  run_options = tf.RunOptions(trace_level=tf.RunOptions.FULL_TRACE)
  while batch_meta is not None and batch_song is not None:
    op_g = eval_op_g
    if datasetlabel == 'train' and not pretraining:
      elif d_loss == 0.0:
        op_g = tf.no_op()
      elif g_loss < 2.0 or d_loss < 2.0:
        if g_loss*.7 > d_loss:
          op_g = tf.no_op()
    if pretraining:
      fetches = [model.rnn_pretraining_loss, tf.no_op(), op_g, tf.no_op()]
    else:
      fetches = [model.g_loss, model.d_loss, op_g, tf.no_op()]
    feed_dict = {}
    feed_dict[model.input_songdata.name] = batch_song
    feed_dict[model.input_metadata.name] = batch_meta
    g_loss, d_loss, _, _ = session.run(fetches, feed_dict)
    g_losses += g_loss
    if not pretraining:
      print ('NOT PRETRAINING')
      d_losses += d_loss
    iters += 1
    [batch_meta, batch_song] = loader.get_batch(model.batch_size, model.songlength, part=datasetlabel)
  if iters == 0:
    return (None,None)
  g_mean_loss = g_losses/iters
  if pretraining:
    d_mean_loss = None
  else:
    d_mean_loss = d_losses/iters
  return (g_mean_loss, d_mean_loss)

def sample(session, model, batch=False):
  """Samples from the generative model."""
  fetches = [model.generated_features]
  feed_dict = {}
  generated_features, = session.run(fetches, feed_dict)
  returnable = []
  if batch:
    for batchno in xrange(generated_features[0].shape[0]):
      returnable.append([x[batchno,:] for x in generated_features])
  else:
    returnable = [x[0,:] for x in generated_features]
  return returnable

def main(_):
  if not FLAGS.datadir:
    raise ValueError("Must set --datadir to midi music dir.")
  if not FLAGS.traindir:
    raise ValueError("Must set --traindir to dir where I can save model and plots.")
  restore_flags()
  generated_data_dir = os.path.join(FLAGS.traindir, 'generated_data')
  try:
    os.makedirs(FLAGS.traindir)
  except:
    pass
  try:
    os.makedirs(generated_data_dir)
  except:
    pass
  directorynames = FLAGS.traindir.split('/')
  songfeatures_filename = os.path.join(FLAGS.traindir, 'num_song_features.pkl')
  metafeatures_filename = os.path.join(FLAGS.traindir, 'num_meta_features.pkl')
  loader = music_data_utils.MusicDataLoader(FLAGS.datadir, FLAGS.select_validation_percentage, FLAGS.select_test_percentage, FLAGS.works_per_composer, FLAGS.pace_events, synthetic=None, tones_per_cell=FLAGS.tones_per_cell, single_composer=FLAGS.composer)
  num_song_features = loader.get_num_song_features()
  num_meta_features = loader.get_num_meta_features()
  songlength_ceiling = FLAGS.songlength
  songlength = 0
  with tf.Graph().as_default(), tf.Session() as session:
    with tf.variable_scope("model", reuse=None) as scope:
      scope.set_regularizer(tf.contrib.layers.l2_regularizer(scale=FLAGS.reg_scale))
      m = RNNGAN(is_training=True, num_song_features=num_song_features, num_meta_features=num_meta_features)
    print("Created model with fresh parameters.")
    session.run(tf.global_variables_initializer())
    for i in range(FLAGS.max_epoch):
      if songlength < songlength_ceiling:
        songlength += 4
        print('Changing songlength, now training on {} events from songs.'.format(songlength))
        FLAGS.songlength = songlength
        with tf.variable_scope("model", reuse=True) as scope:
          scope.set_regularizer(tf.contrib.layers.l2_regularizer(scale=FLAGS.reg_scale))
          m = RNNGAN(is_training=True, num_song_features=num_song_features, num_meta_features=num_meta_features)
      m.assign_lr(session, FLAGS.learning_rate * FLAGS.lr_decay ** max(i - FLAGS.epochs_before_decay, 0.0))
      print("Epoch: {} Learning rate: {:.3f}".format(i, session.run(m.lr)))
      run_epoch(session, m, loader, 'train', m.opt_pretraining, pretraining = True, verbose=True)
      song_data = sample(session, m, batch=True)
      midi_patterns = []
      midi_time = time.time()
      for d in song_data:
        midi_patterns.append(loader.get_midi_pattern(d))
      print('done. time: {}'.format(time.time()-midi_time))
      filename = os.path.join(generated_data_dir, 'out-{}-{}.mid'.format(i, datetime.datetime.today().strftime('%Y-%m-%d-%H-%M-%S')))
      loader.save_midi_pattern(filename, midi_patterns[0])
      sys.stdout.flush()
    run_epoch(session, m, loader, 'test', tf.no_op())
    song_data = sample(session, m)
    filename = os.path.join(generated_data_dir, 'out-{}-{}.mid'.format(i, datetime.datetime.today().strftime('%Y-%m-%d-%H-%M-%S')))
    loader.save_data(filename, song_data)
    print('Saved {}.'.format(filename))

if __name__ == "__main__":
  tf.app.run()


