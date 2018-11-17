import os

class Board:
	
	def __init__(self):
		# IDEA: have variables that allow for different (Customizable?) board configurations
		self.gameBoard = [
		["BR","BN","BB","BQ","BK","BB","BN","BR"],
		["BP","BP","BP","BP","BP","BP","BP","BP"],
		["__","__","__","__","__","__","__","__"],
		["__","__","__","__","__","__","__","__"],
		["__","__","__","__","__","__","__","__"],
		["__","__","__","__","__","__","__","__"],
		["WP","WP","WP","WP","WP","WP","WP","WP"],
		["WR","WN","WB","WQ","WK","WB","WN","WR"]]
		
	def displayBoard(self):
		# Improve formatting to make it prettier
		os.system("clear")
		print "    a  b  c  d  e  f  g  h"
		print "8  " + " ".join(self.gameBoard[0])
		print "7  " +  " ".join(self.gameBoard[1])
		print "6  " + " ".join(self.gameBoard[2])
		print "5  " + " ".join(self.gameBoard[3])
		print "4  " + " ".join(self.gameBoard[4])
		print "3  " + " ".join(self.gameBoard[5])
		print "2  " + " ".join(self.gameBoard[6])
		print "1  " + " ".join(self.gameBoard[7])
	
	def checkLegal(self,movement,player):
		#check to see if move would put own side in check
		
		#create a temporary board updated using the move, if that move is legal, return true
		tempBoard = []
		
		if self.checkLegalMove(movement,player):
			tempPiece = self.gameBoard[movement[2]][movement[3]]
			self.gameBoard[movement[2]][movement[3]] = self.gameBoard[movement[0]][movement[1]]
			self.gameBoard[movement[0]][movement[1]] = "__"
			if not self.isCheck(player):
				self.gameBoard[movement[0]][movement[1]] = self.gameBoard[movement[2]][movement[3]]
				self.gameBoard[movement[2]][movement[3]] = tempPiece
				return True
			self.gameBoard[movement[0]][movement[1]] = self.gameBoard[movement[2]][movement[3]]
			self.gameBoard[movement[2]][movement[3]] = tempPiece
			return False
		else:
			return False
	
	def isCheck(self,player):
		x = False
		
		king = [0,0]
		
		for i in range(0,64):
			if player == "B":
				if self.gameBoard[i/8][i%8]=="BK":
					king = [i/8,i%8]
			if player == "W":
				if self.gameBoard[i/8][i%8]=="WK":
					king = [i/8,i%8]
		
		for i in range(0,64):
			movement = [i%8,i/8,king[0],king[1]]
			if player == "B" and self.gameBoard[i%8][i/8][0]=="W":
				if self.checkLegalMove(movement,"W"):
					x = True
			movement = [i%8,i/8,king[0],king[1]]
			if player == "W" and self.gameBoard[i%8][i/8][0]=="B":
				if self.checkLegalMove(movement,"B"):
					x = True
		
		return x
	
	def checkLegalMove(self, movement, player):
		# Rules:
		# Must not put king in check
		# Must follow the movement pattern for the piece
		# Piece must end on board
		
		# Boolean value, returns false if move is not legal
		isLegal = True 
		
		# Check to make sure the piece that is being moved is on the right team
		if self.gameBoard[movement[0]][movement[1]][0]!=player:
			isLegal = False
		
		# Check to make sure the piece that is being moved does not capture your own piece
		if player==self.gameBoard[movement[2]][movement[3]][0]:
			isLegal = False
		
		# Make sure the piece isn't staying in the same place
		if movement[0]==movement[2] and movement[1]==movement[3]:
			isLegal = False
		
		# Check which piece it is and limit movement accordingly
		piece = self.gameBoard[movement[0]][movement[1]][1]
		
		if piece=="P":
			x = False
			if player == "B":
				if movement[0]==movement[2]-1 and movement[1]==movement[3]:
					if  self.gameBoard[movement[2]][movement[3]]=="__":
						x = True
				elif movement[0]==movement[2]-1 and movement[1]==movement[3]-1:
					if self.gameBoard[movement[2]][movement[3]]!="__":
						x = True
				elif movement[0]==movement[2]-1 and movement[1]==movement[3]+1:
					if self.gameBoard[movement[2]][movement[3]]!="__":
						x = True
				elif movement[0]==movement[2]-2 and movement[1]==movement[3] and movement[0]==1:
					if  self.gameBoard[movement[2]][movement[3]]=="__" and self.gameBoard[movement[2]-1][movement[3]]=="__":
						x = True
				else:
					x = False
				if isLegal == True:
					isLegal = x
			
			if player == "W":
				if movement[0]==movement[2]+1 and movement[1]==movement[3]:
					if  self.gameBoard[movement[2]][movement[3]]=="__":
						x = True
				elif movement[0]==movement[2]+1 and movement[1]==movement[3]-1:
					if self.gameBoard[movement[2]][movement[3]]!="__":
						x = True
				elif movement[0]==movement[2]+1 and movement[1]==movement[3]+1:
					if self.gameBoard[movement[2]][movement[3]]!="__":
						x = True
				elif movement[0]==movement[2]+2 and movement[1]==movement[3] and movement[0]==6:
					if  self.gameBoard[movement[2]][movement[3]]=="__" and self.gameBoard[movement[2]+1][movement[3]]=="__":
						x = True
				else:
					x = False
				if isLegal == True:
					isLegal = x
		elif piece=="Q":
			x = False
			if movement[0]==movement[2] or movement[1]==movement[3]:
				x = True
			for i in range(0,8):
				if (movement[0]==movement[2]+i and movement[1]==movement[3]+i) or (movement[0]==movement[2]-i and movement[1]==movement[3]+i) or (movement[0]==movement[2]+i and movement[1]==movement[3]-i) or (movement[0]==movement[2]-i and movement[1]==movement[3]-i):
					x = True
			if isLegal==True:
				isLegal = x
			
			# Moving down
			if movement[0]<movement[2] and movement[1]==movement[3]:
				for i in range(1,movement[2]-movement[0]):
					if self.gameBoard[movement[0]+i][movement[1]]!="__":
						isLegal = False
			
			# Moving up
			if movement[0]>movement[2] and movement[1]==movement[3]:
				for i in range(1,movement[0]-movement[2]):
					if self.gameBoard[movement[0]-i][movement[1]]!="__":
						isLegal = False
			
			# Moving left
			if movement[1]<movement[3] and movement[0]==movement[2]:
				for i in range(1,movement[3]-movement[1]):
					if self.gameBoard[movement[0]][movement[1]+i]!="__":
						isLegal = False
			
			# Moving right
			if movement[1]>movement[3] and movement[0]==movement[2]:
				for i in range(1,movement[1]-movement[3]):
					if self.gameBoard[movement[0]][movement[1]-i]!="__":
						isLegal = False
			
			# Moving up-right
			if movement[0]>movement[2] and movement[1]<movement[3]:
				for i in range(1,movement[0]-movement[2]):
					if movement[0]-i >= 0 and movement[1]+i < 8:
						if self.gameBoard[movement[0]-i][movement[1]+i]!="__":
							isLegal = False
			
			# Moving down-right
			elif movement[0]<movement[2] and movement[1]<movement[3]:
				for i in range(1,movement[2]-movement[0]):
					if movement[0] + i < 8 and movement[1] + i < 8:
						if self.gameBoard[movement[0]+i][movement[1]+i]!="__":
							isLegal = False
			
			# Moving down-left
			elif  movement[0]<movement[2] and movement[1]>movement[3]:
				for i in range(1,movement[1]-movement[3]):
					if movement[0]+i < 8 and movement[1]-i >= 0:
						if self.gameBoard[movement[0]+i][movement[1]-i]!="__":
							isLegal = False
			
			# Moving up-left
			elif  movement[0]>movement[2] and movement[1]>movement[3]:
				for i in range(1,movement[1]-movement[3]):
					if movement[0]-i >= 0 and movement[1]-i >= 0:
						if self.gameBoard[movement[0]-i][movement[1]-i]!="__":
							isLegal = False
		elif piece=="B":
			x = False
			for i in range(0,8):
				if (movement[0]==movement[2]+i and movement[1]==movement[3]+i) or (movement[0]==movement[2]-i and movement[1]==movement[3]+i) or (movement[0]==movement[2]+i and movement[1]==movement[3]-i) or (movement[0]==movement[2]-i and movement[1]==movement[3]-i):
					x = True
			if isLegal==True:
				isLegal = x
			
			
			# Moving up-right
			if movement[0]>movement[2] and movement[1]<movement[3]:
				for i in range(1,movement[0]-movement[2]):
					if movement[0]-i >= 0 and movement[1]+i < 8:
						if self.gameBoard[movement[0]-i][movement[1]+i]!="__":
							isLegal = False
			
			# Moving down-right
			elif movement[0]<movement[2] and movement[1]<movement[3]:
				for i in range(1,movement[2]-movement[0]):
					if movement[0] + i < 8 and movement[1] + i < 8:
						if self.gameBoard[movement[0]+i][movement[1]+i]!="__":
							isLegal = False
			
			# Moving down-left
			elif  movement[0]<movement[2] and movement[1]>movement[3]:
				for i in range(1,movement[1]-movement[3]):
					if movement[0]+i < 8 and movement[1]-i >= 0:
						if self.gameBoard[movement[0]+i][movement[1]-i]!="__":
							isLegal = False
			
			# Moving up-left
			elif  movement[0]>movement[2] and movement[1]>movement[3]:
				for i in range(1,movement[1]-movement[3]):
					if movement[0]-i >= 0 and movement[1]-i >= 0:
						if self.gameBoard[movement[0]-i][movement[1]-i]!="__":
							isLegal = False
		elif piece=="N":
			if (movement[0]!=movement[2]+2 or movement[1]!=movement[3]+1) and (movement[0]!=movement[2]-2 or movement[1]!=movement[3]+1) and (movement[0]!=movement[2]+2 or movement[1]!=movement[3]-1) and (movement[0]!=movement[2]-2 or movement[1]!=movement[3]-1) and (movement[0]!=movement[2]+1 or movement[1]!=movement[3]+2) and (movement[0]!=movement[2]-1 or movement[1]!=movement[3]+2) and (movement[0]!=movement[2]+1 or movement[1]!=movement[3]-2) and (movement[0]!=movement[2]-1 or movement[1]!=movement[3]-2):
				isLegal = False
		elif piece=="R":
			if movement[0]!=movement[2] and movement[1]!=movement[3]:
				isLegal = False
			
			# Moving down
			if movement[0]<movement[2]:
				for i in range(1,movement[2]-movement[0]):
					if self.gameBoard[movement[0]+i][movement[1]]!="__":
						isLegal = False
			
			# Moving up
			if movement[0]>movement[2]:
				for i in range(1,movement[0]-movement[2]):
					if self.gameBoard[movement[0]-i][movement[1]]!="__":
						isLegal = False
			
			# Moving left
			if movement[1]<movement[3]:
				for i in range(1,movement[3]-movement[1]):
					if self.gameBoard[movement[0]][movement[1]+i]!="__":
						isLegal = False
			
			# Moving right
			if movement[1]>movement[3]:
				for i in range(1,movement[1]-movement[3]):
					if self.gameBoard[movement[0]][movement[1]-i]!="__":
						isLegal = False
		elif piece=="K":
			if (movement[0]!=movement[2]+1 and movement[0]!=movement[2]-1 and movement[0]!=movement[2]) or (movement[1]!=movement[3]+1 and movement[1]!=movement[3]-1 and movement[1]!=movement[3]):
				isLegal = False 
		
		return isLegal
	
	def isCheckMate(self, player):
		# Use is check to test every possible move
		# Loop through every piece of own player, test every move, if ANY return false, isCheckMate returns false
		x = True
		# Loop through every piece owned by player
		for i in range(0,64):
			if self.gameBoard[i/8][i%8][0] == player:
				# Loop through every move and test
				for j in range(0,64):
					movement = [i/8,i%8,j/8,j%8]
					if self.checkLegal(movement,player):
						# Create temp board, update it
						tempPiece = self.gameBoard[movement[2]][movement[3]]
						self.gameBoard[movement[2]][movement[3]] = self.gameBoard[movement[0]][movement[1]]
						self.gameBoard[movement[0]][movement[1]] = "__"
						if not self.isCheck(player):
							x = False
						self.gameBoard[movement[0]][movement[1]] = self.gameBoard[movement[2]][movement[3]]
						self.gameBoard[movement[2]][movement[3]] = tempPiece
		
		return x
	
	def move(self, movement,player):
		#movement = self.translate(movement)
		if self.checkLegal(movement,player)==True:
			self.gameBoard[movement[2]][movement[3]] = self.gameBoard[movement[0]][movement[1]]
			self.gameBoard[movement[0]][movement[1]] = "__"
	
	def translate(self, movement):
		# Takes string as input, returns places on board
		# String in format "wxyz" where
		# w = from the bottom, the row of the piece to be moved, starting at 1
		# x = from the left, the column of the piece to be moved, starting at a
		# y = from the bottom, the row of the destination, starting at 1
		# z = from the left, the column of the destination, starting at a
		
		startColumn = ord(movement[0]) - 97
		startRow = 8-int(movement[1])
		
		endColumn = ord(movement[2]) - 97
		endRow = 8-int(movement[3])
		
		return [startRow,startColumn,endRow,endColumn]