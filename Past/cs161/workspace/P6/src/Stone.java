import java.util.ArrayList;

// P6 Assignment
// Author: Sean Russell
// Date:   Apr 20, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

public class Stone extends GamePiece{

	public Stone(String colorArg, int rowArg, int columnArg) {
		super(colorArg, rowArg, columnArg);
	}
	
	public Stone(String colorArg) {
		super(colorArg);
	}

	public String toString(){
		if (color.equals("white"))
			return "W";
		return "B";
	}
	
	public ArrayList<String> legalMoves(GamePiece[][] board) {
		ArrayList<String> moves = new ArrayList<String>();
		
		// for black stones
		if (color.equals("black")){
			if (row == 7)
				return moves;
			if (isOpen(board,column+1,row+1))
				moves.add(position(column+1,row+1));
			else if (isOpen(board,column+2,row+2)){
				moves.add(position(column+2,row+2));
			}
			if (isOpen(board,column-1,row+1))
				moves.add(position(column-1,row+1));
		}
		
		// for white stones
		if (color.equals("white")){
			if (row == 0)
				return moves;
			if (isOpen(board,column+1,row-1))
				moves.add(position(column+1,row-1));
			if (isOpen(board,column-1,row-1))
				moves.add(position(column-1,row-1));
		}
		
		return moves;
	}

}
