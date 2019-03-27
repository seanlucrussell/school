// P5 Assignment
// Author: Sean Russell
// Date:   Apr 13, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

import java.util.ArrayList;

public class Pawn extends ChessPiece{

	public Pawn(String color, int row, int column) {
		super(color, row, column);
	}
	
	public String toString(){
		if (color == "white")
			return "\u2659";
		else
			return "\u265F";
	}
	
	public ArrayList<String> legalMoves(ChessPiece[][] board){
		ArrayList<String> lmoves = new ArrayList<String>();
		
		
		if (color == "Black"){
			if (board[row+1][column] == null && !board[row+1][column].color.equals(color))
				lmoves.add(moveToString(row+1,column));
			if (board[row+1][column+1] != null && !board[row+1][column].color.equals(color))
				lmoves.add(moveToString(row+1,column+1));
			if (board[row+1][column+1] != null && !board[row+1][column].color.equals(color))
				lmoves.add(moveToString(row+1,column-1));
		}
		if (color == "White"){
			if (board[row-1][column] == null && !board[row-1][column].color.equals(color))
				lmoves.add(moveToString(row-1,column));
			if (board[row-1][column+1] != null && !board[row-1][column].color.equals(color))
				lmoves.add(moveToString(row-1,column+1));
			if (board[row-1][column+1] != null && !board[row-1][column].color.equals(color))
				lmoves.add(moveToString(row-1,column-1));
		}
		
		return lmoves;
	}
	
	private String moveToString(int row, int column){
		return Character.toString((char) (column + 97)) + (row + 1);
	}
	
}
