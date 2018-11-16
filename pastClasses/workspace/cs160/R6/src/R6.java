// R6 Assignment
// Author: Sean Russell
// Date:   Sep 30, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu
public class R6 {

	public static boolean and(boolean b0, boolean b1) {
		return b0&&b1;
	}

	public static boolean or(boolean b0, boolean b1) {
		return b0||b1;
	}
	
	public static boolean not(boolean b0) {
		return !b0;
	}

	public static boolean xor(boolean b0, boolean b1) {
		return b0^b1;
	}

	public static boolean nand(boolean b0, boolean b1) {
		return not(and(b0,b1));
	}

	public static boolean nor(boolean b0, boolean b1) {
		return not(or(b0,b1));
	}
	
	public static boolean special(boolean b0, boolean b1) {
		return xor(nand(b0,b1),or(b0,b1));
	}

	public static void printHeader(String logicalOperatorName) {
	    System.out.println("b0\tb1\tb0 " + logicalOperatorName + " b1");
	}

	public static void printRow(boolean b0, boolean b1, boolean result) {
	    System.out.println(b0 + "\t" + b1 + "\t" + result);
	}

	public static void main(String[] args) {

	    // Print truth table (AND)
		printHeader("AND");
	    printRow(true, true, and(true,true));
	    printRow(true, false, and(true,false));
	    printRow(false, true, and(false,true));
	    printRow(false, false, and(false,false));
		
	    printHeader("OR");
	    printRow(true, true, or(true,true));
	    printRow(true, false, or(true,false));
	    printRow(false, true, or(false,true));
	    printRow(false, false, or(false,false));
		
	    printHeader("XOR");
	    printRow(true, true, xor(true,true));
	    printRow(true, false, xor(true,false));
	    printRow(false, true, xor(false,true));
	    printRow(false, false, xor(false,false));
		
	    printHeader("NAND");
	    printRow(true, true, nand(true,true));
	    printRow(true, false, nand(true,false));
	    printRow(false, true, nand(false,true));
	    printRow(false, false, nand(false,false));
		
	    printHeader("NOR");
	    printRow(true, true, nor(true,true));
	    printRow(true, false, nor(true,false));
	    printRow(false, true, nor(false,true));
	    printRow(false, false, nor(false,false));
	    
	    printHeader("SPECIAL");
	    printRow(true, true, special(true,true));
	    printRow(true, false, special(true,false));
	    printRow(false, true, special(false,true));
	    printRow(false, false, special(false,false));
		
	    // add code here

	}
	
}
