import numpy as np
from random import *
import copy
import itertools

#
#
# Globals
#
#

OUT_OF_FIELD = -1
WHITE = 0 #white is empty
RED = 1
GREEN = 2
BLUE = 3
YELLOW = 4
BLACK = 9 #black is drone
COLORS = [RED, GREEN, BLUE, YELLOW] #doesn't include empty (white) blocks, done for numGen()

MAX_X = 50
MIN_X = -50
MAX_Z = 50
MIN_Z = -50
MAX_Y = 50
MIN_Y = 0

droneState = ['unattached', [50,15,50]]

#
#
# End globals
#
#
#

#
# List actions
#

def removeDuplicatesFromListOfLists(listOfLists):
    listOfLists.sort()
    return list(listOfLists for listOfLists,_ in itertools.groupby(listOfLists))

def removeListFromListOfLists(listOfLists, removeSubList):
    '''
    ([[1,2,3],[2,3,4]], [2,3,4]) will return [[1,2,3]]
    '''
    newListOfLists = []
    for iter in listOfLists:
        if (iter == list(removeSubList)):
            continue
        newListOfLists.append(iter)
    return newListOfLists

#
# General
#

def genNum(): #at 10% liklihood per color
    x = randint(0,99)
    if (x > len(COLORS) -1):
        return 0
    else:
        return x

def initiateField():
    '''
    Create numpy field with randomly distributed blocks as predicated by genNum()
    '''
    randomField = [[[genNum() for i in range(MAX_Z-MIN_Z+1)] for j in range(MAX_Y-MIN_Y+1)] for k in range(MAX_X-MIN_X+1)]
    randomField = np.array((randomField))
    return randomField

def getBlocks():
    '''
    Iterate over a numpy field and add each nonWhite element to list 'blocks'; return blocks
    '''
    blocks = []
    for x, xPos in enumerate(field):
        for y, yPos in enumerate(xPos):
            for z, zPos in enumerate(yPos):
                if (field[x, y, z] == WHITE):
                    continue
                blocks.append([field[x, y, z], [x + MIN_X, y + MIN_Y, z + MIN_Z]])
    return blocks

def sortBlocks(blocks):
    '''
    Takes a list of blocks (where the first element is their color & the second element is a list of their positions)
    and orders the list by y then x then z to aid in searching by layer
    '''
    blocks.sort(key=lambda x: x[1][1]) #by y (layer)
    blocks.sort(key=lambda x: x[1][0]) #by x
    blocks.sort(key=lambda x: x[1][2]) #by z
    return blocks

def isAllBlocksUnique():
    '''
    relies on sorted (y->x->z) blocks
    '''
    for iter in range(1, len(blocks)):
        if (blocks[iter][1][0] == blocks[iter-1][1][0] and
            blocks[iter][1][1] == blocks[iter-1][1][1] and
            blocks[iter][1][2] == blocks[iter-1][1][2]):
            return False
    return True

def isAnyFloatingBlock():
    '''
    tests all blocks in a list
    relies on sorted (y->x->z) blocks
    '''
    for enum, block in enumerate(blocks[1:]):
        if (block[1][1] > 0):
            if (blocks[enum][1][0] != block[1][0] or
               blocks[enum][1][1] != block[1][1] - 1 or
               blocks[enum][1][2] != block[1][2]):
                return True
    return False

def isFloatingBlock(blockPos):
    '''
    tests single block
    doesn't rely on sorted blocks
    '''
    stack = getBlocksInStack(blocks, blockPos[0], blockPos[2])
    for iter in stack:
        if (iter[0][1][0] == blockPos[0] and 
            iter[0][1][1] == blockPos[1] - 1 and 
            iter[0][1][2] == blockPos[2]):
            return False
    return True

def getBlocksInStack(xPos, zPos):
    '''
    Given a list of blocks and a desired xPos, zPos this returns all blocks in the column on xPos, zPos
    That is, returns a list of all blocks on any layer of the xz pos along with their location within the original list
    '''
    stack = []
    for enum, block in enumerate(blocks):
        if (block[1][0] == xPos and block[1][2] == zPos):
            stack.append([block, enum])
    return stack

def applyGravity(blocks):
    '''
    Relies on sorted (y->x->z) blocks
    '''
    blocks = sortBlocks(blocks)
    blocks[0][1][1] = MIN_Y #place the first block on the floor
    iterBlocks = copy.deepcopy(blocks)

    for enum, iter in enumerate(iterBlocks):
        if (iter[1][1] > MIN_Y): #not on the floor
            if (blocks[enum -1][1][0] == iter[1][0] and 
                blocks[enum -1][1][2] == iter[1][2]):
                blocks[enum][1][1] = blocks[enum -1][1][1] + 1
            else:
                blocks[enum][1][1] = MIN_Y #floor
    return blocks

def createFieldFromBlocks():
    field = np.zeros((MAX_X-MIN_X+1,MAX_Y-MIN_Y+1,MAX_Z-MIN_Z+1)).astype(int)
    for iter in blocks:    
        field[iter[1][0] + abs(MIN_X)][iter[1][1] + abs(MIN_Y)][iter[1][2] + abs(MIN_Z)] = iter[0]
    return field
#
#
#  USING FIELD, NOT BLOCKS
#  
#
def isPosInField(pos):
    if (pos[0] < 0 or pos[0] >= MAX_X-MIN_X+1 or
        pos[1] < 0 or pos[1] >= MAX_Y-MIN_Y+1 or
        pos[2] < 0 or pos[2] >= MAX_Z-MIN_Z+1):
        return False
    return True

def getPosType(pos):
    if (not isPosInField(pos)):
        return -1
    return (field[pos[0], pos[1], pos[2]])

def getHighestLandingPad(xPos, zPos):
    '''
    Given a list of blocks and a desired xPos, zPos this returns all blocks in the column on xPos, zPos
    Done for release()
    '''
    arr = np.where(field[xPos, :, zPos] == 0)
    if (len(arr[0]) == 0):
        return [xPos, 0, zPos]
    return [xPos, arr[0][0], zPos]

def getViablePickups(colorNum):
    '''
    returns positions above uncovered blocks of a given colorNum, ie landing pads
    '''
    pickups = []
    if (colorNum == WHITE or colorNum == OUT_OF_FIELD):
        return []
    for xPos in range(MAX_X+abs(MIN_X)+1):
        for zPos in range(MAX_Z+abs(MIN_Z)+1):
            highestLandingPad = getHighestLandingPad(xPos, zPos)
            if (getPosType([highestLandingPad[0], highestLandingPad[1]-1,highestLandingPad[2]]) == colorNum):
                if (getPosType(highestLandingPad) == WHITE):
                    pickups.append(highestLandingPad)
    return pickups

def getViableDropColumns(colorNum):
    '''
    returns columns above top blocks of a given colorNum, ie dropoff areas
    '''
    dropColumns = []
    if (colorNum == WHITE or colorNum == OUT_OF_FIELD):
        return []
    for xPos in range(MAX_X+abs(MIN_X)+1):
        for yPos in range(MAX_Y+abs(MIN_Y)):
            for zPos in range(MAX_Z+abs(MIN_Z)+1):
                if (field[xPos][yPos][zPos] == colorNum):
                    if (getPosType([xPos, yPos+1, zPos]) == WHITE):
                        for col in range(yPos+1,MAX_Y):
                            dropColumns.append([xPos, col, zPos])
    return dropColumns

def getViableNonDiagMoves(pos): 
    '''
    returns all empty and inField positions surrounding 
    position [x, y z] or list of positions [[x1,y1,z1],[x2,y2,z2]...]
    maximum of 6
    position given can be out of the field
    '''
    aura = []
    #for scenarios with where pos can be a list of pos's
    groupElements = []
    if (type(pos[0]) == list):
        for iter in pos:
            groupElements.append(iter)
    else:
        groupElements.append(pos)
    
    #get all moves around    
    for iter in groupElements:
        aura.append([iter[0]+1, iter[1], iter[2]])
        aura.append([iter[0]-1, iter[1], iter[2]])
        aura.append([iter[0], iter[1]+1, iter[2]])
        aura.append([iter[0], iter[1]-1, iter[2]])
        aura.append([iter[0], iter[1], iter[2]+1])
        aura.append([iter[0], iter[1], iter[2]-1])
    #remove duplicates
    aura = removeDuplicatesFromListOfLists(aura)
    #remove actual pos's given from aura
    for iter in groupElements:
        aura = removeListFromListOfLists(aura, iter)
    #remove moves outside of the field or without an empty position present
    temp = copy.deepcopy(aura)
    for iter in temp:
        x = getPosType(iter)
        if (x != WHITE):
            aura = removeListFromListOfLists(aura, iter)
    return aura

def getNearestNeighborPos(startPos, targetAuras, MAX_EFFORT=100):
    '''
    Shallowest first
    Expanding cube finds nearest neighbor; returns numMoves and final pos; pos == -1 if no neighbor found
    '''
    if (type(targetAuras[0]) != list):
        targetAuras = [targetAuras]
    frontier = [startPos]
    secondTier = []
    continueRunning = True
    effort = 0
    nearestNeighbor = -1
    while (continueRunning):
        effort += 1
        if (effort == MAX_EFFORT):
            continueRunning = False
            break
        newFrontier = getViableNonDiagMoves(frontier)
        #remove second tier to prevent regression
        for iter in secondTier:
            newFrontier = removeListFromListOfLists(newFrontier, iter)
        #fully expanded, cease efforts
        if (len(newFrontier) == 0):
            continueRunning = False
            break
        for iter in newFrontier:
            #test neighbor found condition
            if (iter in targetAuras):
                nearestNeighbor = iter
                continueRunning = False
                break
        secondTier = frontier
        frontier = newFrontier
    return effort, nearestNeighbor

#
#
# Intialize random field
#
#
field = initiateField()
blocks = getBlocks()
blocks = applyGravity(blocks)
field = createFieldFromBlocks()

print('Num of blocks: ', len(blocks))
print()

print('Demo: find all landing zones of red blocks, that is, any red block on top of a stack')
redPickups = getViablePickups(RED)
print('There are: ', len(redPickups), ' red blocks capable of being picked up')
print()

print('Demo: find location and unobstructed number of moves to nearest')
moves, nearestNeighborPosition = getNearestNeighborPos(droneState[1], redPickups)
print('Moves: ', moves, ' from ', droneState[1])
print('Location: ', nearestNeighborPosition)
print()