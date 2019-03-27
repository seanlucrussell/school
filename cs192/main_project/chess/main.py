import board

chessBoard = board.Board()

move = ""
team = "W"


while not chessBoard.isCheckMate("W") and not chessBoard.isCheckMate("B"):
	chessBoard.displayBoard()
	if team=="W":
		move = raw_input("White move: ").lower()
		if move == "quit":
			break
		while True:
			if move == "quit":
				break
			elif len(move)==4 and (move[0]=="a" or move[0]=="b" or move[0]=="c" or move[0]=="d" or move[0]=="e" or move[0]=="f" or move[0]=="g" or move[0]=="h") and (move[2]=="a" or move[2]=="b" or move[2]=="c" or move[2]=="d" or move[2]=="e" or move[2]=="f" or move[2]=="g" or move[2]=="h") and (move[1]=="1" or move[1]=="2" or move[1]=="3" or move[1]=="4" or move[1]=="5" or move[1]=="6" or move[1]=="7" or move[1]=="8") and (move[3]=="1" or move[3]=="2" or move[3]=="3" or move[3]=="4" or move[3]=="5" or move[3]=="6" or move[3]=="7" or move[3]=="8"):
				if chessBoard.checkLegal(chessBoard.translate(move),team):
					chessBoard.move(chessBoard.translate(move),team)
					break
				else:
					chessBoard.displayBoard()
					move = raw_input("Invalid, white move: ").lower()
			else:
				chessBoard.displayBoard()
				move = raw_input("Invalid, white move: ").lower()
		if move=="quit":
			break
		team="B"
		
		
	elif team=="B":
		move = raw_input("Black move: ").lower()
		if move == "quit":
			break
		while True:
			if move == "quit":
				break
			elif len(move)==4 and (move[0]=="a" or move[0]=="b" or move[0]=="c" or move[0]=="d" or move[0]=="e" or move[0]=="f" or move[0]=="g" or move[0]=="h") and (move[2]=="a" or move[2]=="b" or move[2]=="c" or move[2]=="d" or move[2]=="e" or move[2]=="f" or move[2]=="g" or move[2]=="h") and (move[1]=="1" or move[1]=="2" or move[1]=="3" or move[1]=="4" or move[1]=="5" or move[1]=="6" or move[1]=="7" or move[1]=="8") and (move[3]=="1" or move[3]=="2" or move[3]=="3" or move[3]=="4" or move[3]=="5" or move[3]=="6" or move[3]=="7" or move[3]=="8"):
				if  chessBoard.checkLegal(chessBoard.translate(move),team):
					chessBoard.move(chessBoard.translate(move),team)
					break
				else:
					chessBoard.displayBoard()
					move = raw_input("Invalid, black move: ").lower()
			else:
				chessBoard.displayBoard()
				move = raw_input("Invalid, black move: ").lower()
		if move == "quit":
			break
		team="W"

if chessBoard.isCheckMate("W"):
	print "Black Wins!"
if chessBoard.isCheckMate("B"):
	print "White Wins!"