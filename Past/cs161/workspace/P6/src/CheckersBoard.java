// P6 Assignment
// Author: Sean Russell
// Date:   Apr 20, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

public class CheckersBoard extends GameBoard{

	public void initialize() {
		placePiece(new Stone("white"),"a8");
		placePiece(new Stone("white"),"c8");
		placePiece(new Stone("white"),"e8");
		placePiece(new Stone("white"),"g8");
		placePiece(new Stone("white"),"b7");
		placePiece(new Stone("white"),"d7");
		placePiece(new Stone("white"),"f7");
		placePiece(new Stone("white"),"h7");
		placePiece(new Stone("white"),"a6");
		placePiece(new Stone("white"),"c6");
		placePiece(new Stone("white"),"e6");
		placePiece(new Stone("white"),"g6");
		
		placePiece(new Stone("black"),"a2");
		placePiece(new Stone("black"),"c2");
		placePiece(new Stone("black"),"e2");
		placePiece(new Stone("black"),"g2");
		placePiece(new Stone("black"),"b1");
		placePiece(new Stone("black"),"d1");
		placePiece(new Stone("black"),"f1");
		placePiece(new Stone("black"),"h1");
		placePiece(new Stone("black"),"b3");
		placePiece(new Stone("black"),"d3");
		placePiece(new Stone("black"),"f3");
		placePiece(new Stone("black"),"h3");
	}

	public boolean move(String fromPosition, String toPosition) {
		return false;
	}
	
	public static void main(String[] args){
		CheckersBoard b = new CheckersBoard();
		b.initialize();
		System.out.println(b);
	}

}
