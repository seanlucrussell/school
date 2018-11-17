// P5 Assignment
// Author: Sean Russell
// Date:   Apr 13, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

public class ChessBoard {
	
	// Need to fix:
	// STRING MOVES INPUT FROM BOTTOM LEFT
	// ROW AND COLUMN VALUES FROM 0 TO 7
	
	
	protected ChessPiece[][] board;
	
	public boolean move(String fromPosition, String toPosition){
		return false;
	}
	
	public ChessBoard(){
		board = new ChessPiece[8][8];
		for(int i=0;i<64;i++){
			board[i/8][i%8] = null;
		}
	}
	
	public boolean placePiece(String piece, String position){
		int[] positionInts = stringToNumber(position);
		if (board[positionInts[1]][positionInts[0]] != null){
			return false;
		}
		
		switch (piece) {
			case "K":
				board[positionInts[1]][positionInts[0]] = new King("white",positionInts[1],positionInts[0]);
				break;
			case "k":
				board[positionInts[1]][positionInts[0]] = new King("white",positionInts[1],positionInts[0]);
				break;
			case "Q":
				board[positionInts[1]][positionInts[0]] = new Queen("white",positionInts[1],positionInts[0]);
				break;
			case "q":
				board[positionInts[1]][positionInts[0]] = new Queen("white",positionInts[1],positionInts[0]);
				break;
			case "R":
				board[positionInts[1]][positionInts[0]] = new Rook("white",positionInts[1],positionInts[0]);
				break;
			case "r":
				board[positionInts[1]][positionInts[0]] = new Rook("white",positionInts[1],positionInts[0]);
				break;
			case "B":
				board[positionInts[1]][positionInts[0]] = new Bishop("white",positionInts[1],positionInts[0]);
				break;
			case "b":
				board[positionInts[1]][positionInts[0]] = new Bishop("white",positionInts[1],positionInts[0]);
				break;
			case "N":
				board[positionInts[1]][positionInts[0]] = new Knight("white",positionInts[1],positionInts[0]);
				break;
			case "n":
				board[positionInts[1]][positionInts[0]] = new Knight("white",positionInts[1],positionInts[0]);
				break;
			case "P":
				board[positionInts[1]][positionInts[0]] = new Pawn("white",positionInts[1],positionInts[0]);
				break;
			case "p":
				board[positionInts[1]][positionInts[0]] = new Pawn("white",positionInts[1],positionInts[0]);
				break;
		}
		
		return true;
	}
	
	public void initialize(){
		board[0][0] = new Rook("white",0,0);
		board[0][1] = new Knight("white",0,1);
		board[0][2] = new Bishop("white",0,2);
		board[0][3] = new Queen("white",0,3);
		board[0][4] = new King("white",0,4);
		board[0][5] = new Bishop("white",0,5);
		board[0][6] = new Knight("white",0,6);
		board[0][7] = new Rook("white",0,7);

		board[1][0] = new Pawn("white",1,0);
		board[1][1] = new Pawn("white",1,1);
		board[1][2] = new Pawn("white",1,2);
		board[1][3] = new Pawn("white",1,3);
		board[1][4] = new Pawn("white",1,4);
		board[1][5] = new Pawn("white",1,5);
		board[1][6] = new Pawn("white",1,6);
		board[1][7] = new Pawn("white",1,7);
		
		for (int i=2;i<6;i++){
			for (int j=0;j<8;j++){
				board[i][j] = null;
			}
		}
		
		board[7][0] = new Rook("black",7,0);
		board[7][1] = new Knight("black",7,1);
		board[7][2] = new Bishop("black",7,2);
		board[7][3] = new Queen("black",7,3);
		board[7][4] = new King("black",7,4);
		board[7][5] = new Bishop("black",7,5);
		board[7][6] = new Knight("black",7,6);
		board[7][7] = new Rook("black",7,7);

		board[6][0] = new Pawn("black",6,0);
		board[6][1] = new Pawn("black",6,1);
		board[6][2] = new Pawn("black",6,2);
		board[6][3] = new Pawn("black",6,3);
		board[6][4] = new Pawn("black",6,4);
		board[6][5] = new Pawn("black",6,5);
		board[6][6] = new Pawn("black",6,6);
		board[6][7] = new Pawn("black",6,7);
	}
	
	public ChessPiece getPiece(String position){
		if (position == null){
			return null;
		}
		int[] x = stringToNumber(position);
		return board[x[1]][x[0]];
	}
	
	private int[] stringToNumber(String str){
		int[] retInt = {0,0};
		
		switch (str.charAt(0)) {
			case 'a':
				retInt[0] = 0;
				break;
			case 'b':
				retInt[0] = 1;
				break;
			case 'c':
				retInt[0] = 2;
				break;
			case 'd':
				retInt[0] = 3;
				break;
			case 'e':
				retInt[0] = 4;
				break;
			case 'f':
				retInt[0] = 5;
				break;
			case 'g':
				retInt[0] = 6;
				break;
			case 'h':
				retInt[0] = 7;
				break;
		}
		
		retInt[1] =  8 - Integer.parseInt(Character.toString(str.charAt(1)));
		
		return retInt;
	}
	
	public String toString(){
		String retVal = "";
		
		for (int i=0;i<8;i++){
			for (int j=0;j<8;j++){
				if (board[i][j] == null){
					retVal += " ";
				}
				else {
					retVal += board[i][j];
				}
			}
			retVal += "\n";
		}
		
		return retVal;
	}
	
	public static void main(String args[]){
		ChessBoard b = new ChessBoard();
		b.initialize();
		System.out.print(b);
		System.out.println(b.getPiece("e1"));
	}
	
}
