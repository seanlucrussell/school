// R7 Assignment
// Author: Sean Russell
// Date:   Oct 2, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.util.Scanner;

public class R7 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		printCharsInString("O stone be not so");
		printCharsInAsciiTable(' ','~');
		System.out.println(computeFactorial(0));
		Scanner uinput = new Scanner(System.in);
		areWeThereYet(uinput);
		uinput.close();
	}
	
	public static void printCharsInString(String s) {
	    for(int i=s.length()-1;i>=0;i--){
	    	System.out.print(s.charAt(i));
	    }
	    System.out.println();
	}

	public static void printCharsInAsciiTable(char start, char end) {
	    for(int i=start;i<=end;i++){
	    	System.out.print((char)i);
	    }
	    System.out.println();
	}

	public static int computeFactorial(int num) {
	    int returnVal = 1;
		if(num==0){
	    	return 1;
	    }else{
	    	while(num>0){
	    		returnVal *= num;
	    		num--;
	    	}
	    	return returnVal;
	    }
	}

	public static void areWeThereYet(Scanner keyboard) {
		do {
	    	System.out.print("Are we there yet? ");
	    } while (!keyboard.next().toLowerCase().equals("yes"));
		System.out.println("Finally!");
	}


}
