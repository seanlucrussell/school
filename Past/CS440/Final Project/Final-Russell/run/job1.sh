rm train/20161208-everything_and_end_classification/ -r

source run/settings1.sh

python2 rnn_gan.py --model medium --datadir datasets/midis --traindir $TRAIN_DIR $HYPERPARAMS

