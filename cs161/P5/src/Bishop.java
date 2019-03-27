import java.util.ArrayList;

// P5 Assignment
// Author: Sean Russell
// Date:   Apr 13, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

public class Bishop extends ChessPiece{

	public Bishop(String color, int row, int column) {
		super(color, row, column);
	}
	
	public String toString(){
		if (color.equals("white"))
			return "\u2657";
		else
			return "\u265D";
	}
	
	public ArrayList<String> legalMoves(ChessPiece[][] board){
		ArrayList<String> lmoves = new ArrayList<String>();
		
		
		// ++
		for (int i=1;i<=8;i++){
			if (row+i<=8 && column+i<=8){
				if (board[row+i][column+i].color.equals(color)){
					break;
				}
				if (board[row+i][column+i].color != null){
					lmoves.add(moveToString(row+1,column+i));
					break;
				}
				lmoves.add(moveToString(row+1,column+i));
			}
			else {
				break;
			}
		}
		
		// +-
		for (int i=1;i<=8;i++){
			if (row+i<=8 && column-i>=0){
				if (board[row+i][column-i].color.equals(color)){
					break;
				}
				if (board[row+i][column-i].color != null){
					lmoves.add(moveToString(row+1,column-i));
					break;
				}
				lmoves.add(moveToString(row+1,column-i));
			}
			else {
				break;
			}
		}
		
		// -+
		for (int i=1;i<=8;i++){
			if (row+i>=0 && column+i<=8){
				if (board[row-i][column+i].color.equals(color)){
					break;
				}
				if (board[row-i][column+i].color != null){
					lmoves.add(moveToString(row-1,column+i));
					break;
				}
				lmoves.add(moveToString(row-1,column+i));
			}
			else {
				break;
			}
		}
		
		// --
		for (int i=1;i<=8;i++){
			if (row-i>=0 && column-i>=0){
				if (board[row-i][column-i].color.equals(color)){
					break;
				}
				if (board[row-i][column-i].color != null){
					lmoves.add(moveToString(row-1,column-i));
					break;
				}
				lmoves.add(moveToString(row-1,column-i));
			}
			else {
				break;
			}
		}
		


		
		return lmoves;
	}
	

	private String moveToString(int row, int column){
		return Character.toString((char) (column + 97)) + (row + 1);
	}
	
	
}
