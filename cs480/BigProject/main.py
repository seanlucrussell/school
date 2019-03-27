import wave as wav
import struct
import numpy as np
import matplotlib.pyplot as plt
from numpy.fft import fft,fftfreq
from numpy.fft import rfft,rfftfreq
import os
import soundfile as sf
from sklearn.neural_network import MLPClassifier
import random

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
           0:'trumpet',1:'violin',2:'guitar',3:'piano',4:'saxaphone',
           -1:'unknown','unknown':-1}


# MESS WITH PREPROCESSING IN THESE METHODS
def GenerateData():
    trumpetTrain,trumpetTest = GenerateDataWithLabels('trumpet')
    violinTrain,violinTest = GenerateDataWithLabels('violin')
    guitarTrain,guitarTest = GenerateDataWithLabels('guitar')
    pianoTrain,pianoTest = GenerateDataWithLabels('piano')
    saxaphoneTrain,saxaphoneTest = GenerateDataWithLabels('saxaphone')
    train = np.vstack((trumpetTrain,violinTrain,guitarTrain,pianoTrain,saxaphoneTrain))
    test = np.vstack((trumpetTest,violinTest,guitarTest,pianoTest,saxaphoneTest))
    traindata = train[:,1:]
    testdata = test[:,1:]
    traintargets = train[:,0].astype(int)
    testtargets = test[:,0].astype(int)
    return traindata,traintargets,testdata,testtargets

def GenerateDataWithLabels(label):
    train,test = GenerateDataFromDirectory('samples-train/'+label)
    train = np.insert(train,0,classes[label],axis=1)
    test = np.insert(test,0,classes[label],axis=1)
    return train,test

def GenerateDataFromDirectory(directory):
    ''' need to split data up into train and dest partitions'''
    train = []
    test = []
    for file in os.listdir(directory):
        if(file.endswith('.wav')):
            if(random.choice([True,False])):
               train += FileToVectors(directory + '/' + file).tolist()
            else:
               test += FileToVectors(directory + '/' + file).tolist()
    return np.array(train),np.array(test)

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
    mean = 0.00001
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
    classifier = MLPClassifier(solver='lbfgs',hidden_layer_sizes=(100,25))
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
        #print(classes[c],'test directory accuracy:',
        #      "%.2f" % (100*PercentCorrect(classifier,classes[c])))
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
        print('% unknown:',
              "%.2f" % (100*len(subset[subset[:,1]==classes['unknown']])/len(subset)))

    return classifier
