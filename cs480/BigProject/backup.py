import wave as wav
import struct
import numpy as np
import matplotlib.pyplot as plt
from numpy.fft import fft,fftfreq
from numpy.fft import rfft,rfftfreq
import os
import soundfile as sf
from sklearn.neural_network import MLPClassifier

DEFAULT_AMPLITUDE = 10000
DEFAULT_FRAMERATE = 32000 #frames per second
DEFAULT_SAMPLEWIDTH = 2
DEFAULT_NCHANNELS = 1

class Sound:
    """a class for storing audio samples and periphiral data"""
    samples = []
    nsamples = 0
    nchannels = DEFAULT_NCHANNELS
    framerate = DEFAULT_FRAMERATE
    samplewidth = DEFAULT_SAMPLEWIDTH
    
    def __init__(self,audioFile=0):
        if (audioFile!=0):
            self.open(audioFile)

    def open(self,audioFile):
        """opens .wav file indicated by audioFile string as list of ints"""
        w = wav.open(audioFile,"r")
        self.nsamples = w.getnframes()
        self.framerate = w.getframerate()
        self.samplewidth = w.getsampwidth()
        self.nchannels = w.getnchannels()
        for i in range(0,self.nsamples):
            waveData = w.readframes(1)
            self.samples.append (struct.unpack("<h", waveData)[0])

    def write(self,audioFile):
        w = wav.open(audioFile,"w")
        self.nsamples = len(self.samples)
        params = (self.nchannels, self.samplewidth, self.framerate,
                  self.nsamples, 'NONE', 'not compressed')
        w.setparams(params)
        w.writeframes(struct.pack("h"*self.nsamples,*self.samples))
        w.close()

    def plot(self,t=.01):
        """plots waveform made up of samples"""
        #need to plot this with respect to time
        plt.ion()
        times = np.arange(0,t,1/self.framerate)
        plt.plot(times,self.samples[:len(times)])
        plt.show()

    def play(self):
        """plays audio file indicated by soundPath"""
        filepath = ".TEMPWAVFILE.wav"
        self.write(filepath)
        os.system("play -q "+filepath)
        os.system("rm "+filepath)

#maybe rename to avoide collisions with .wav package?
class Wave:
    """a class to store wave information"""
    phase = 0 #in radians
    amplitude = 1 #in range -32768 to 32767
    frequency = 1 # in hz
    
    def __init__(self, phase = 0, amplitude = DEFAULT_AMPLITUDE, frequency = 1):
        self.phase = phase
        self.amplitude = amplitude
        self.frequency = frequency

    def sample(self,time):
        return self.amplitude * np.cos((self.phase + time)*self.frequency*2*np.pi)

    def samples(self,t):
        """returns a list of samples up to time t"""
        samps = gettimes(t)
        #samps = [int(self.sample(x)) for x in samps]
        samps = self.samples(samps)
        return samps

    def plot(self,t=.01):
        """plots wave with respect to time interval"""
        #need to plot this with respect to time
        plt.ion()
        samps = self.samples(t)
        times = gettimes(t)
        plt.plot(times,samps)
        plt.xlabel("Time")
        plt.show()

class Waveform:
    waves = []
    
    def __init__(self,waves=[]):
        self.waves = waves

    def samples(self,t=1):
        '''this doesn't have to be so slow, rework this'''
        total = np.zeros(int(t * DEFAULT_FRAMERATE))
        for w in self.waves:
            total += w.samples(t)
        total *= DEFAULT_AMPLITUDE/max(total)
        return total.astype(int)

    def add(self,wave):
        self.waves.append(wave)

    def plot(self,t=.01):
        """plots wave with respect to time interval"""
        #need to plot this with respect to time
        plt.ion()
        samps = self.samples(t)
        times = gettimes(t)
        plt.plot(times,samps)
        plt.xlabel("Time")
        plt.show()

def gettimes(t):
    return np.arange(0,t,1/DEFAULT_FRAMERATE)

def samplesToWaves(samples, framerate = DEFAULT_FRAMERATE):
    waves = []
    fourier = 2 * rfft(samples) / len(samples)
    frequencies = rfftfreq(len(samples), 1/DEFAULT_FRAMERATE)
    plt.ion()
    plt.plot(frequencies,np.abs(fourier))
    plt.show()
    for complex,frequency in zip(fourier,frequencies):
        amp = np.abs(complex)
        freq = frequency
        phase = np.arctan2(complex.imag,complex.real)
        waves.append(Wave(amplitude = amp, frequency = freq, phase = phase))
    return waves
'''
plt.close()

b2Path = "violin/b2.wav"
tonepath = "tones/440Hz_44100Hz_16bit_05sec.wav"

sound = Sound(b2Path)
#samplesToWaves(sound.samples)
#sound.play()
#sound.samples = samples
#sound.write("newsound.wav")
#print (fftTopFreqs(sound.samples,10))
#fftplot(samples)
#fftplot(sound.samples)
sound.play()

soundWave = Waveform()
soundSamples = sound.samples
for w in samplesToWaves(soundSamples):
    if w.amplitude > 50:
        soundWave.add(w)
testSound.samples = soundWave.samples()
#testSound.write('generated.wav')
testSound.play()
'''

classes = {'trumpet':0,'violin':1,'guitar':2,'piano':3,'saxaphone':4,
           0:'trumpet',1:'violin',2:'guitar',3:'piano',4:'saxaphone'}


# MESS WITH PREPROCESSING IN THESE METHODS
def GenerateData():
    trumpetData = GenerateDataWithLabels('trumpet')
    violinData = GenerateDataWithLabels('violin')
    guitarData = GenerateDataWithLabels('guitar')
    pianoData = GenerateDataWithLabels('piano')
    saxaphoneData = GenerateDataWithLabels('saxaphone')
    alldata = np.vstack((trumpetData,violinData,guitarData,pianoData,saxaphoneData))
    np.random.shuffle(alldata)
    data = alldata[:,1:]
    splitindex = int(len(data) / 2)
    traindata = data[:splitindex,:]
    testdata = data[splitindex:,:]
    targets = alldata[:,0].astype(int)
    traintargets = targets[:splitindex]
    testtargets = targets[splitindex:]
    return traindata,traintargets,testdata,testtargets

def GenerateDataWithLabels(label):
    data = GenerateDataFromDirectory('samples-train/'+label)
    data = np.insert(data,0,classes[label],axis=1)
    return data

def GenerateDataFromDirectory(directory):
    data = []
    for file in os.listdir(directory):
        if(file.endswith('.wav')):
            data += FileToVectors(directory + '/' + file).tolist()
    return np.array(data)

def FileToVectors(filename):
    samples,samplerate = sf.read(filename)
    split = ProcessSamples(samples)
    return split

def ProcessSamples(samples):
    samples = SplitSamples(samples)
    samples = DeleteSamplesLowAmplitude(samples)
    samples = FourierTransform(samples)
    return samples

def DeleteSamplesLowAmplitude(samples):
    mean = 0.0001
    return np.delete(samples,np.where(np.mean(samples,axis=1) < mean),axis=0)

def FourierTransform(samples):
    return np.abs(rfft(samples))

def SplitSamples(samples):
    '''splits samples into a bunch of vectors of a specified width and cuts off any excess'''
    widthOfVector = 2000
    return np.reshape(samples[:len(samples)-len(samples)%widthOfVector],(-1,widthOfVector))

def PlotFourier(samples):
    fourier = 2 * rfft(samples) / len(samples)
    frequencies = rfftfreq(len(samples), 1/DEFAULT_FRAMERATE)
    plt.ion()
    plt.plot(frequencies,np.abs(fourier))
    plt.show()

    
# MESS WITH ALGORITHMS IN THESE METHODS

def Train(data, targets):
    classifier = MLPClassifier(solver='lbfgs',hidden_layer_sizes=(50,50))
    classifier.fit(data,targets)
    return classifier

def Use(classifier,filename):
    vectors = FileToVectors(filename)
    if len(vectors) < 1:
        return -1
    predicted = classifier.predict(vectors)
    mostPredicted = np.argmax(np.bincount(predicted))
    return mostPredicted

def TestDirectory(classifier,classname,directory='test'):
    values = []
    for file in os.listdir('samples-' + directory + '/' + classname + '/'):
        if (file.endswith('.wav')):
            values.append(Use(classifier,'samples-' + directory + '/' + classname + '/' + file))
    return values

def PercentCorrect(classifier,classname):
    test = TestDirectory(classifier,classname)
    unique, counts = np.unique(test, return_counts=True)
    count = dict(zip(unique, counts))[classes[classname]]
    return count / len(test)


def CreateModel():
    '''
    this function will be the wrapper around everything. it will
    generate the training and testing data, fit a model to the training data,
    do the testing, and provide useful information after doing so. On completion,
    it will return the created model for further testing and experimentation if
    need be.
    '''
    trainingData,trainingTargets,testingData,testingTargets = GenerateData()
    classifier = Train(trainingData,trainingTargets)
    predicted = classifier.predict(testingData)
    totalNumberCorrect = np.sum(predicted == testingTargets)
    print('total % correct on testing data:',totalNumberCorrect / len(testingTargets))
    

    predictedVsTargets = np.vstack((predicted,testingTargets)).T
    for c in range(5):
        subset = predictedVsTargets[predictedVsTargets[:,0] == c]
        print('\t' + classes[c].upper())
        print(classes[c],'% correct:',
              "%.2f" % (100*len(subset[subset[:,0]==subset[:,1]]) / len(subset)))
        print(classes[c],'test directory accuracy:',
              "%.2f" % (100*PercentCorrect(classifier,classes[c])))
        print('% guessed piano:',
              "%.2f" % (100*len(subset[subset[:,1]==classes['piano']])/len(subset)))
        print('% guessed violin:',
              "%.2f" % (100*len(subset[subset[:,1]==classes['violin']])/len(subset)))
        print('% guessed trumpet:',
              "%.2f" % (100*len(subset[subset[:,1]==classes['trumpet']])/len(subset)))
        print('% guessed guitar:',
              "%.2f" % (100*len(subset[subset[:,1]==classes['guitar']])/len(subset)))
        print('% guessed saxaphone:',
              "%.2f" % (100*len(subset[subset[:,1]==classes['saxaphone']])/len(subset)))

    return classifier

# things to try
# - include samplerate in the feature vectors
# - feature vectors are made up of waves
#   - can try a variety of filtering methods
# - feature vectors are made up of small slices of the audio file
#   - can try diferent lengths of slices
#   - can try ommiting certain slices if they are boring (amplitude too low)
# - feature vectors made up of slices turned into waves
#   - can experiment with both things as above

# all variables that can be altered:
# - width of slices of audio file
# - shape of neural network
# - number of training files 

# want one function
# creates the model and dumps some information regarding how effective the model is at classification by printing various useful statistics
# it will return the model or store it in some useful place. then the model can be used seperately to classify individual audio files

# need to partition data so that entire files are kept in either the training or testing data
# also only want to be dealing with files in one directory. Screw having seperate train and validate folders
