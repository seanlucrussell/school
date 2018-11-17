// R17 Assignment
// Author: Sean Russell
// Date:   Dec 4, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

public class R17 {
	
	public static void main(String[] args){
		
	}
	
	// Precondition: -10 <= x <= 7  
	// Postcondition: -3195 <= funcOne(x) <= 936
	public static int funcOne(int x) {

	    // Enforce specified precondition in method
	    assert (x >= -10 && x <= 7);
	    return (3 * x * x * x) - (2 * x * x) + 5;
	}

	// Preconditions: -5 <= x <= -2, 6 <= y <= 13   
	// Postcondition: -689 <= funcTwo(x,y) <= -37
	public static int funcTwo(int x, int y) {

	    // Enforce specified precondition in method
	    assert (x >= -5 && x <= -2);
	    assert (y >= 6  && y <= 13);
	    return (3 * x * x * x) - (y * x * x) + 11;
	}

	// Precondition: 1 <= x <= 4
	// Postcondition: 0.25 <= return value <= 1.0
	public static double funcThree(double x) {
		assert (x >= 4 && x <= 1);
	    return 1.0 / x; 
	}

	// Precondition: x is an integer  
	// Postcondition: returns an even integer
	// QUESTION: Is the postcondition true? Why or why not?
	// The postcontdition is true.
	// If x is an even integer, x gets squared and then subtracted from the result.
	// Any even number squared is even, and any even number subtracted from another even number is even
	// So if x is even, then retVal is even
	// If x is an odd integer, x gets added to itself, and then squared
	// Any odd integer added to itself becomes even
	// And any even integer squared is even
	// So if x is odd, then retVal is even
	public static int funcFour(int x) {

	    int retVal;
	    if (x % 2 == 0) {
	        retVal = x * x - x;
	    } else {
	        retVal = (x + x) * (x + x);
	    }
	    assert(retVal % 2 == 0);
	    return retVal;
	}

	// Precondition: x >= 1
	// QUESTION: Find the loop invariants before, during, and after the loop.
	// Loop invariant: 0 <= i <= x, result = i!
	public static void funcFive(int x) {

	    int result = 1;
	    int i = 0;
	    do {
	        i++;
	        result = result * i;
	    } while (i < x);
	}

	// #6
	// QUESTION: Does this correctly swap the locals a and b? Why or why not?
	// This function does not correctly swap a and b
	// If a >= b, then the function correctly swaps a and b
	// But if a < b, a becomes b and b becomes a modulo b
	public static void funcSix(int a, int b) {

	    System.out.println("Before: a = " + a + " and b = " + b);
	    if (a < b) {
	        int r = a % b;
	        a = b;
	        b = r;
	    }
	    else {
	        int r = b;
	        b = a;
	        a = r;
	    }
	    System.out.println("After: a = " + a + " and b = " + b);
	}
	
}
