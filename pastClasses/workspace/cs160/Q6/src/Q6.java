// Q6 Assignment
// Author: Sean Russell
// Date:   Oct 30, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu
import java.util.Scanner;

public class Q6 {
    
    // Class method (DO NOT MODIFY)
    public void foo(String s, double d, int i1, int i2, int i3) {
        System.out.println("Called FOO() with the parameters: " + s + ", " + d + ", " + i1 + ", " + i2 + ", " + i3);
    }
    
    public static void main(String[] args) {

        // Get input from user
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Please enter an integer greater than 2 and hit enter: ");
        int iVal1 = keyboard.nextInt();
        if (iVal1 <= 2) { 
            System.out.println("ERROR: you did not enter an integer greater than 2!");
            System.out.println("Halting program.");
            System.exit(0);
        }
        System.out.print("Please enter integer greater than 0 and hit enter: ");
        int iVal2 = keyboard.nextInt();
        if (iVal2 <= 0) { 
            System.out.println("ERROR: you did not enter an integer greater than 0!");
            System.out.println("Halting program.");
            System.exit(0);
        }
        System.out.print("Please enter a double and hit enter: ");
        double dVal = keyboard.nextDouble();
        System.out.print("Please enter a String without spaces (at least " + (iVal1+1) + " characters long) and hit enter: ");
        String sVal = keyboard.next();
        if (sVal.length() < iVal1+1) { 
            System.out.println("ERROR: you did not enter an string with at least " + (iVal1+1) + " characters!");
            System.out.println("Halting program.");
            System.exit(0);
        }
        keyboard.close();
        
        // Create a Q6 object
        Q6 q6 = new Q6();
        
		// Add code here
        
        // Output line 1
        System.out.println((iVal1 + iVal2)/dVal);
        
        // Output line 2
        System.out.println(sVal.substring(1,iVal1));
        
        // Output line 3
        switch (iVal2) {
        	case 666: case 13:
        		System.out.println("Unlucky number");
        		break;
        	case 7: case 2:
        		System.out.println("Lucky number");
        		break;
        	default:
        		System.out.println("Neutral number");
        }
        
        // Output line 4
        if (iVal1 < iVal2){
        	System.out.println("First is smaller");
        } else if (iVal1 == iVal2){
        	System.out.println("Both equal");
        } else {
        	System.out.println("First is bigger");
        }
        
        // Output line 5
        for(int i=0;i<iVal2;i++){
        	System.out.print(sVal + ",");
        }
        System.out.println();
        
        // Output line 6
        double[] iValDoubles = new double[iVal1];
        for(int i=0;i<iValDoubles.length;i++){
        	iValDoubles[i] = 1.0;
        }
        iValDoubles[0] = dVal;
        iValDoubles[iVal1-1] = dVal;
        for(int i=0;i<iValDoubles.length;i++){
        	System.out.print(iValDoubles[i] + ",");
        }
        System.out.println();
        
        // Output line 7
        q6.foo(sVal, dVal, iVal1, iVal2, 160);
        
        // Output line 8
        System.out.println("Final result: " + q6.areaOfTriangle(7.5, 20));
        
    }
    
    public double areaOfTriangle(double base, double height){
    	return base * height * 0.5;
    }
    
}