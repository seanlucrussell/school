// Q2 Assignment
// Author: Sean Russell
// Date:   Sep 9, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.util.Scanner;

public class Q2 {

	public static void main(String[] args) {
		
		//Variables
		Scanner keyboard = new Scanner(System.in);
		String myString0, myString1;
		char myCharacter;
		int myInteger;
		double myDouble;
		
		//A
		System.out.print("String: ");
		myString0 = keyboard.nextLine();
		
		//B
		System.out.print("String: ");
		myString1 = keyboard.next();
		
		//C
		System.out.print("Character: ");
		String tempString = keyboard.next();
		myCharacter = tempString.charAt(0);
		
		//D
		System.out.print("Integer: ");
		myInteger = keyboard.nextInt();
		
		//E
		System.out.print("Double: ");
		myDouble = keyboard.nextDouble();
		
		//F
		System.out.println(myString0 + ";" + myString1 + ";" + myCharacter + ";" + myInteger +";" + myDouble);
		
		//G
		System.out.printf("%.4f\n", (myInteger + 3.5)/myDouble);
		
		//H
		System.out.println(myString0.toLowerCase());
		
		//I
		System.out.println(myString0.length() + "," + myString1.length());
		
		//J
		System.out.println(myString1.indexOf('m'));
		
		//K
		System.out.println(myString1.charAt(1) + "." + myString1.charAt(4));
		
		//L
		System.out.println(myString1.substring(1, 7));
	}

}
