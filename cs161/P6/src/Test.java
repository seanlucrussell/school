// P6 Assignment
// Author: Sean Russell
// Date:   Apr 22, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

public class Test {
	
	public static void main(String args[]){
		ChessBoard c = new ChessBoard();
		c.move("d2","d4");
		c.move("g8","f6");
		c.move("c1","c4");
		c.move("g7","g6");
		c.move("g2","g3");
		c.move("f8","g7");
		c.move("f1","g2");
		c.move("f6","d5");
		c.move("g2","e4");
		System.out.println(c);
	}
	
}
