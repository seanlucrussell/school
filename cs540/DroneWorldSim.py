import numpy as np
import itertools

#
#
# Globals at the moment
#
#
OUT_OF_BOUNDS = -1 #needed for attach()
WHITE = 0 #white is empty
RED = 1
GREEN = 2
BLUE = 3
YELLOW = 4
BLACK = 9 #black is drone
COLORS = [RED, GREEN, BLUE, YELLOW] #doesn't include empty (white) blocks, done for numGen() and state() creation

MAX_X = 50
MIN_X = -50
MAX_Z = 50
MIN_Z = -50
MAX_Y = 50
MIN_Y = 0
#Total number of spaces == 101*101*51 == 520,251

droneState = ['unattached', [50,50,50]]

#
#
# End globals, etc
#
#

#
#
# Initialize & State
#
#

#the 'public' initialize
def initialize(blocks):
    '''
    Meant to open a txt file?, for now simply passing a blocks object
    '''
    blocks = sortBlocks(blocks)
    if (not isAllBlocksUnique(blocks)):
        print('Not all blocks are unique')
        return -1
    if (isAnyFloatingBlock(blocks)):
        print('Blocks are floating')
        return -1
    field = createFieldFromBlocks(blocks)
    return field

def sortBlocks(blocks):
    '''
    Takes a list of blocks (where the first element is their color & the second element is a list of their positions)
    and orders the list by y then x then z to aid in searching by layer
    '''
    blocks.sort(key=lambda x: x[1][1]) #by y (layer)
    blocks.sort(key=lambda x: x[1][0]) #by x
    blocks.sort(key=lambda x: x[1][2]) #by z
    return blocks

def isAllBlocksUnique(blocks):
    '''
    relies on sorted (y->x->z) blocks
    '''
    for iter in range(1, len(blocks)):
        if (blocks[iter][1][0] == blocks[iter-1][1][0] and
            blocks[iter][1][1] == blocks[iter-1][1][1] and
            blocks[iter][1][2] == blocks[iter-1][1][2]):
            return False
    return True

def isAnyFloatingBlock(blocks):
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

def createFieldFromBlocks(blocks):
    field = np.zeros((MAX_X-MIN_X+1,MAX_Y-MIN_Y+1,MAX_Z-MIN_Z+1)).astype(int)
    for iter in blocks:    
        field[iter[1][0] + abs(MIN_X)][iter[1][1] + abs(MIN_Y)][iter[1][2] + abs(MIN_Z)] = iter[0]
    return field

#the 'public' state()
def state():
    '''
    Iterate over a numpy field and add each nonWhite element to list 'state'; return state along with drone
    '''
    state = []
    for x, xPos in enumerate(field):
        for y, yPos in enumerate(xPos):
            for z, zPos in enumerate(yPos):
                if (field[x, y, z] == WHITE):
                    continue
                state.append([COLORS[field[x, y, z]], [x + MIN_X, y + MIN_Y, z + MIN_Z]])
    state.append(['drone', droneState])
    return state

#
#
# Movement
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

def moveDrone(dx, dy, dz):
    droneXPos = droneState[1][0]
    droneYPos = droneState[1][1]
    droneZPos = droneState[1][2]
    droneAtt = droneState[0]
    #move nowhere
    if (dx == 0 and dy == 0 and dz == 0):
        return [[droneXPos, droneYPos, droneZPos]]
    if (droneXPos + dx > MAX_X or droneXPos + dx < MIN_X or
        droneYPos + dy > MAX_Y or droneYPos + dy < MIN_Y or
        (droneYPos -1 + dy < MIN_Y and droneAtt == 'attached') or
        droneZPos + dz > MAX_Z or droneZPos + dz < MIN_Z):
        return -1
    
    moves = getValidMove(dx, dy, dz)
    if (moves != -1):
        moves = removeListFromListOfLists(moves, [droneState[1][0], droneState[1][1], droneState[1][2]])
        if (droneAtt == 'attached'):
            moveBlock(dx, dy, dz)
        droneState[1][0] += dx
        droneState[1][1] += dy
        droneState[1][2] += dz
        return removeDuplicatesFromListOfLists(moves)
    else:
        return -1
    
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

def moveBlock(dx, dy, dz):
    blockType = getPosType([droneState[1][0], droneState[1][1] -1, droneState[1][2]])
    field[droneState[1][0]][droneState[1][1] -1][droneState[1][2]] = WHITE
    field[droneState[1][0] + dx][droneState[1][1] -1 + dy][droneState[1][2] + dz] = blockType
    
def getValidMove(dx, dy, dz):
    dronePos = droneState[1]
    if (droneState[0] == 'unattached'):
        moves = getValidMoveUnattached(dx, dy, dz)
    elif (droneState[0] == 'attached'):
        moves = getValidMoveAttached(dx, dy, dz)
    else:
        print('Drone state is not attached/unattached')
        return -1

    if (len(moves) == 0):
        print('Not a valid move')
        return -1
    else:
        return moves
    
def getValidMoveUnattached(dx, dy, dz):
    '''
    Tests the six possible paths and returns the first valid one found as a list of lists [[move1],[move2],[move3]]
    Applies if drone  IS NOT carrying a block
    '''
    moves = []
    dronePos = droneState[1]
    if (getPosType([dronePos[0] + dx, dronePos[1] + dy, dronePos[2] + dz]) == WHITE):
        if (getPosType([dronePos[0] + dx, dronePos[1], dronePos[2]]) == WHITE and #x then y then z
            getPosType([dronePos[0] + dx, dronePos[1] + dy, dronePos[2]]) == WHITE):
            moves.append([dronePos[0] + dx, dronePos[1], dronePos[2]])
            moves.append([dronePos[0] + dx, dronePos[1] + dy, dronePos[2]])
        elif (getPosType([dronePos[0] + dx, dronePos[1], dronePos[2]]) == WHITE and #x then z then y
            getPosType([dronePos[0] + dx, dronePos[1], dronePos[2] + dz]) == WHITE):
            moves.append([dronePos[0] + dx, dronePos[1], dronePos[2]])
            moves.append([dronePos[0] + dx, dronePos[1], dronePos[2] + dz])
        elif (getPosType([dronePos[0], dronePos[1] + dy, dronePos[2]]) == WHITE and #y then x then z
            getPosType([dronePos[0] + dx, dronePos[1] + dy, dronePos[2]]) == WHITE):
            moves.append([dronePos[0], dronePos[1] + dy, dronePos[2]])
            moves.append([dronePos[0] + dx, dronePos[1] + dy, dronePos[2]])
        elif (getPosType([dronePos[0], dronePos[1] + dy, dronePos[2]]) == WHITE and #y then z then x
            getPosType([dronePos[0], dronePos[1] + dy, dronePos[2] + dz]) == WHITE):
            moves.append([dronePos[0], dronePos[1] + dy, dronePos[2]])
            moves.append([dronePos[0], dronePos[1] + dy, dronePos[2] + dz])
        elif (getPosType([dronePos[0], dronePos[1], dronePos[2] + dz]) == WHITE and #z then x then y
            getPosType([dronePos[0] + dx, dronePos[1], dronePos[2] + dz]) == WHITE):
            moves.append([dronePos[0], dronePos[1], dronePos[2] + dz])
            moves.append([dronePos[0] + dx, dronePos[1], dronePos[2] + dz])
        elif (getPosType([dronePos[0], dronePos[1], dronePos[2] + dz]) == WHITE and #z then y then x
            getPosType([dronePos[0], dronePos[1] + dy, dronePos[2] + dz]) == WHITE):
            moves.append([dronePos[0], dronePos[1], dronePos[2] + dz])
            moves.append([dronePos[0], dronePos[1] + dy, dronePos[2] + dz])
    if (len(moves) > 0):
        moves.append([dronePos[0] + dx, dronePos[1] + dy, dronePos[2] + dz])
    return moves

def getValidMoveAttached(dx, dy, dz):
    '''
    Tests the six possible paths and returns the first viable one found as a list of lists [[move1],[move2],[move3]]
    Applies if drone IS carrying a block
    '''
    moves = []
    dronePos = droneState[1]
    if (dx == 0 and dz == 0):
        if ((dy > 0 and getPosType([dronePos[0], dronePos[1] + dy, dronePos[2]]) == WHITE) or
        (dy < 0 and getPosType([dronePos[0], dronePos[1] + dy -1, dronePos[2]]) == WHITE)):
            moves.append([dronePos[0], dronePos[1] + dy, dronePos[2]])
    elif (getPosType([dronePos[0] + dx, dronePos[1] + dy, dronePos[2] + dz]) == WHITE and
       getPosType([dronePos[0] + dx, dronePos[1] + dy - 1, dronePos[2] + dz]) == WHITE):
        if (getPosType([dronePos[0], dronePos[1], dronePos[2] + dz]) == WHITE and #z then x then y
            getPosType([dronePos[0] + dx, dronePos[1], dronePos[2] + dz]) == WHITE and
            getPosType([dronePos[0], dronePos[1] - 1, dronePos[2] + dz]) == WHITE and
            getPosType([dronePos[0] + dx, dronePos[1] - 1, dronePos[2] + dz]) == WHITE):
            moves.append([dronePos[0], dronePos[1], dronePos[2] + dz])
            moves.append([dronePos[0] + dx, dronePos[1], dronePos[2] + dz])
        elif (getPosType([dronePos[0] + dx, dronePos[1], dronePos[2]]) == WHITE and #x then z then y
            getPosType([dronePos[0] + dx, dronePos[1], dronePos[2] + dz]) == WHITE and
            getPosType([dronePos[0] + dx, dronePos[1] - 1, dronePos[2]]) == WHITE and
            getPosType([dronePos[0] + dx, dronePos[1] - 1, dronePos[2] + dz]) == WHITE):
            moves.append([dronePos[0] + dx, dronePos[1], dronePos[2]])
            moves.append([dronePos[0] + dx, dronePos[1], dronePos[2] + dz])
        elif (dy >= 0):
            if (getPosType([dronePos[0] + dx, dronePos[1], dronePos[2]]) == WHITE and #x then y then z
                getPosType([dronePos[0] + dx, dronePos[1] - 1, dronePos[2]]) == WHITE and
                getPosType([dronePos[0] + dx, dronePos[1] + dy, dronePos[2]]) == WHITE):
                moves.append([dronePos[0] + dx, dronePos[1], dronePos[2]])
                moves.append([dronePos[0] + dx, dronePos[1] + dy, dronePos[2]])
            elif (getPosType([dronePos[0], dronePos[1] + dy, dronePos[2]]) == WHITE and #y then x then z
                getPosType([dronePos[0] + dx, dronePos[1] + dy, dronePos[2]]) == WHITE and
                getPosType([dronePos[0] + dx, dronePos[1] + dy - 1, dronePos[2]]) == WHITE):
                moves.append([dronePos[0], dronePos[1] + dy, dronePos[2]])
                moves.append([dronePos[0] + dx, dronePos[1] + dy, dronePos[2]])
            elif (getPosType([dronePos[0], dronePos[1] + dy, dronePos[2]]) == WHITE and #y then z then x
                getPosType([dronePos[0], dronePos[1] + dy, dronePos[2] + dz]) == WHITE and
                getPosType([dronePos[0], dronePos[1] + dy - 1, dronePos[2] + dz]) == WHITE):
                moves.append([dronePos[0], dronePos[1] + dy, dronePos[2]])
                moves.append([dronePos[0], dronePos[1] + dy, dronePos[2] + dz])
            elif (getPosType([dronePos[0], dronePos[1], dronePos[2] + dz]) == WHITE and #z then y then x
                getPosType([dronePos[0], dronePos[1] - 1, dronePos[2] + dz]) == WHITE and
                getPosType([dronePos[0], dronePos[1] + dy, dronePos[2] + dz]) == WHITE):
                moves.append([dronePos[0], dronePos[1], dronePos[2] + dz])
                moves.append([dronePos[0], dronePos[1] + dy, dronePos[2] + dz])
        elif (dy < 0):
            if (getPosType([dronePos[0] + dx, dronePos[1], dronePos[2]]) == WHITE and #x then y then z
                getPosType([dronePos[0] + dx, dronePos[1] - 1, dronePos[2]]) == WHITE and
                getPosType([dronePos[0] + dx, dronePos[1] + dy - 1, dronePos[2]]) == WHITE):
                moves.append([dronePos[0] + dx, dronePos[1], dronePos[2]])
                moves.append([dronePos[0] + dx, dronePos[1] + dy, dronePos[2]])
            elif (getPosType([dronePos[0], dronePos[1] + dy - 1, dronePos[2]]) == WHITE and #y then x then z
                getPosType([dronePos[0] + dx, dronePos[1] + dy, dronePos[2]]) == WHITE and
                getPosType([dronePos[0] + dx, dronePos[1] + dy - 1, dronePos[2]]) == WHITE):
                moves.append([dronePos[0], dronePos[1] + dy, dronePos[2]])
                moves.append([dronePos[0] + dx, dronePos[1] + dy, dronePos[2]])
            elif (getPosType([dronePos[0], dronePos[1] + dy - 1, dronePos[2]]) == WHITE and #y then z then x
                getPosType([dronePos[0], dronePos[1] + dy, dronePos[2] + dz]) == WHITE and
                getPosType([dronePos[0], dronePos[1] + dy - 1, dronePos[2] + dz]) == WHITE):
                moves.append([dronePos[0], dronePos[1] + dy, dronePos[2]])
                moves.append([dronePos[0], dronePos[1] + dy, dronePos[2] + dz])
            elif (getPosType([dronePos[0], dronePos[1], dronePos[2] + dz]) == WHITE and #z then y then x
                getPosType([dronePos[0], dronePos[1] - 1, dronePos[2] + dz]) == WHITE and
                getPosType([dronePos[0], dronePos[1] + dy - 1, dronePos[2] + dz]) == WHITE):
                moves.append([dronePos[0], dronePos[1], dronePos[2] + dz])
                moves.append([dronePos[0], dronePos[1] + dy, dronePos[2] + dz])
    if (len(moves) > 0):
        moves.append([dronePos[0] + dx, dronePos[1] + dy, dronePos[2] + dz])
    return moves

#
#
# Attach
#
#

def attach():
    global droneState
    if (getPosType([droneState[1][0], droneState[1][1] -1, droneState[1][2]]) == WHITE or
        getPosType([droneState[1][0], droneState[1][1] -1, droneState[1][2]]) == OUT_OF_BOUNDS):
        print('No block to attach')
        return -1
    droneState = ['attached', droneState[1]]
    return 0

#
#
# Release
#
#

def getHighestLandingPad(xPos, zPos):
    '''
    Given a list of blocks and a desired xPos, zPos this returns all blocks in the column on xPos, zPos
    Done for release()
    '''
    arr = np.where(field[xPos, :, zPos] == 0)
    if (len(arr[0]) == 0):
        return [xPos, 0, zPos]
    return [xPos, arr[0][0], zPos]

def release():
    global droneState
    if (droneState[0] == 'unattached'):
        print('No block to release')
        return -1
    attachedBlockType = getPosType([droneState[1][0], droneState[1][1] -1, droneState[1][2]])
    attachedBlockStopPos = getHighestLandingPad(droneState[1][0], droneState[1][2])
    droneState = ['unattached', droneState[1]]
    field[droneState[1][0], droneState[1][1] -1, droneState[1][2]] = WHITE
    field[attachedBlockStopPos[0], attachedBlockStopPos[1], attachedBlockStopPos[2]] = attachedBlockType
    print(attachedBlockStopPos)
    return 0

#
#
#
# For testing; included atm to generate random blocks
#
#
#

from random import *
import copy

def genNum(): #at 10% liklihood per color if (0,9), 1% if (0,99), etc
    x = randint(0,9)
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


field = initiateField()
blocks = getBlocks()
blocks = applyGravity(blocks)

#
#
#
# End random block generation methods & globals
#
#
#

#
#
#
# Demo
#
#

field = initialize(blocks)
currentState = state()

print(currentState[-1])
print()

print('First 10 in state: ')
for iter in currentState[:10]:
    print(iter)
print()

print('Demo stack')
print(field[50,:,50])
print()

print('Demo top of stack')
print(getHighestLandingPad(50,50))
print()

print('Demo lower drone until invalid')
while (True):
    newLoc = moveDrone(0,-1,0)
    if (newLoc == -1):
        break
print(droneState)
print()

print('Attach')
attach()
print()

print('Move up 5 spaces')
moveDrone(0,1,0)
moveDrone(0,1,0)
moveDrone(0,1,0)
moveDrone(0,1,0)
moveDrone(0,1,0)
print(droneState)
print()

print('Show stack')
print(field[50,:,50])
print()

print('Demo release and drop')
release()
print(field[50,:,50])
print()
print(droneState)