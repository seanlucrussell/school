// R2 Assignment
// Author: Sean Russell
// Date:   Sep 4, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.util.Scanner;

public class R2 {


	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		
		int i = 0;
		double d = 0.0;
		
		System.out.print("Enter an integer: ");
		i = keyboard.nextInt();
		System.out.println("You entered: " + i);
		
		System.out.print("Now enter a double: ");
		d = keyboard.nextDouble();
		System.out.printf("Now you entered: %.2f\n", d);

		
		String myString = "Bazinga";
		
		System.out.println(myString.length());
		System.out.println(myString.charAt(2));
		System.out.println(myString.indexOf('z'));
		System.out.println(myString.toUpperCase());
		
	}

}
