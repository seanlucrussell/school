import java.util.ArrayList;

// P5 Assignment
// Author: Sean Russell
// Date:   Apr 13, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

public class King extends ChessPiece{

	public King(String color, int row, int column) {
		super(color, row, column);
	}
	
	public String toString(){
		if (color.equals("white"))
			return "\u2654";
		else 
			return "\u265A";
	}
	

	public ArrayList<String> legalMoves(ChessPiece[][] board){
		ArrayList<String> lmoves = new ArrayList<String>();

		if (!board[row+1][column+1].color.equals(color) || board[row+1][column+1] == null)
			lmoves.add(moveToString(row+1,column+1));
		if (!board[row+1][column].color.equals(color) || board[row+1][column] == null)
			lmoves.add(moveToString(row+1,column));
		if (!board[row+1][column-1].color.equals(color) || board[row+1][column-1] == null)
			lmoves.add(moveToString(row+1,column-1));
		if (!board[row][column+1].color.equals(color) || board[row][column-1] == null)
			lmoves.add(moveToString(row,column+1));
		if (!board[row][column-1].color.equals(color) || board[row][column-1] == null)
			lmoves.add(moveToString(row,column-1));
		if (!board[row-1][column+1].color.equals(color) || board[row-1][column+1] == null)
			lmoves.add(moveToString(row-1,column+1));
		if (!board[row-1][column].color.equals(color) || board[row-1][column] == null)
			lmoves.add(moveToString(row-1,column));
		if (!board[row-1][column-1].color.equals(color) || board[row-1][column-1] == null)
			lmoves.add(moveToString(row-1,column-1));
		
		return lmoves;
	}
	

	private String moveToString(int row, int column){
		return Character.toString((char) (column + 97)) + (row + 1);
	}
	
	
}
