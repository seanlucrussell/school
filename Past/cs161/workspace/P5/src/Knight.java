import java.util.ArrayList;

// P5 Assignment
// Author: Sean Russell
// Date:   Apr 13, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

public class Knight extends ChessPiece{

	public Knight(String color, int row, int column) {
		super(color, row, column);
	}
	
	public String toString(){
		if (color == "white")
			return "\u2658";
		else
			return "\u265E";
	}
	
	public ArrayList<String> legalMoves(ChessPiece[][] board){
		ArrayList<String> lmoves = new ArrayList<String>();

		if (!board[row+2][column+1].getColor().equals(color))
			lmoves.add(moveToString(row+2,column+1));
		if (!board[row+2][column-1].getColor().equals(color))
			lmoves.add(moveToString(row+2,column-1));
		if (!board[row-2][column+1].getColor().equals(color))
			lmoves.add(moveToString(row-2,column+1));
		if (!board[row-2][column-1].getColor().equals(color))
			lmoves.add(moveToString(row-2,column-1));
		if (!board[row+1][column+2].getColor().equals(color))
			lmoves.add(moveToString(row+1,column+2));
		if (!board[row+1][column-2].getColor().equals(color))
			lmoves.add(moveToString(row+1,column-2));
		if (!board[row-1][column+2].getColor().equals(color))
			lmoves.add(moveToString(row-1,column+2));
		if (!board[row-1][column-2].getColor().equals(color))
			lmoves.add(moveToString(row-1,column-2));
		
		return lmoves;
	}
	
	private String moveToString(int row, int column){
		return Character.toString((char) (column + 97)) + (row + 1);
	}
	
	
}
