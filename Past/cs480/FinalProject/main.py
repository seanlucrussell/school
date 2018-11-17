import os
import random
import soundfile as sf
from sklearn.neural_network import MLPClassifier
from numpy.fft import rfft,rfftfreq
import numpy as np
import matplotlib.pyplot as plt

classToInt = {}
intToClass = {}


def SplitSamples(samples, widthOfSlice = 2000):
    return np.reshape(samples[:len(samples)-len(samples)%widthOfSlice],(-1,widthOfSlice))

def DeleteSamplesLowAmplitude(samples):
    threshold = np.mean(samples) * np.std(samples)
    return np.delete(samples,np.where(np.mean(samples,axis=1) < threshold),axis=0)

def FourierTransform(samples):
    return np.abs(rfft(samples))

def FileToData(filename):
    samples,samplerate = sf.read(filename)
    samples = SplitSamples(samples)
    samples = DeleteSamplesLowAmplitude(samples)
    samples = FourierTransform(samples)
    return samples

def GenerateDataFromLabel(label, classNumber):
    global classToInt,intToClass
    classToInt[label] = classNumber
    intToClass[classNumber] = label
    train = []
    test = []
    path = 'samples/' + label + '/'
    for file in os.listdir(path):
        if(file.endswith('.wav')):
            if(random.choice([True,False])):
               train += FileToData(path + file).tolist()
            else:
               test += FileToData(path + file).tolist()
    train = np.array(train)
    test = np.array(test)
    train = np.insert(train,0,classToInt[label],axis=1)
    test = np.insert(test,0,classToInt[label],axis=1)
    return train.tolist(),test.tolist()

def GenerateData():
    classes = next(os.walk('samples/'))[1]
    train = []
    test = []
    classNumber = 0
    for c in classes:
        cTrain,cTest = GenerateDataFromLabel(c,classNumber)
        train += cTrain
        test += cTest
        classNumber += 1
    train = np.array(train)
    test = np.array(test)
    traindata = train[:,1:]
    testdata = test[:,1:]
    traintargets = train[:,0].astype(int)
    testtargets = test[:,0].astype(int)
    return traindata,traintargets,testdata,testtargets

def CreateModel():
    print('Generating data...')
    trainingData,trainingTargets,testingData,testingTargets = GenerateData()
    print('Finished generating data. Training model...')
    classifier = MLPClassifier(solver='lbfgs',hidden_layer_sizes=(100))
    classifier.fit(trainingData,trainingTargets)
    print('Finished training model. Gathering statistics...')
    predicted = classifier.predict(testingData)
    totalNumberCorrect = np.sum(predicted == testingTargets)
    print('total % correct on testing data:',
          "%.2f" % (100 * totalNumberCorrect / len(testingTargets)))
    predictedVsTargets = np.vstack((predicted,testingTargets)).T
    for c in intToClass:
        subset = predictedVsTargets[predictedVsTargets[:,0] == c]
        print('\t' + intToClass[c].upper())
        print(intToClass[c],'% correct:',
              "%.2f" % (100*len(subset[subset[:,0]==subset[:,1]]) / len(subset)))
        for g in classToInt:
            print('% guessed ' + g +':',
                  "%.2f" % (100*len(subset[subset[:,1]==classToInt[g]])/len(subset)))
    return classifier

def ClassifyAudioFile(classifier,filename):
    vectors = FileToData(filename)
    if len(vectors) < 1:
        return -1
    predicted = classifier.predict(vectors)
    mostPredicted = np.argmax(np.bincount(predicted))
    return intToClass[mostPredicted]
