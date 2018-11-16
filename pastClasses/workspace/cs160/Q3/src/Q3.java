// Q3 Assignment
// Author: Sean Russell, Sten Larson
// Date:   Sep 16, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.util.Scanner;

public class Q3 {

	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		
		// Declare variables
		String restaurantName;
		String serverName;
		double subtotal;
		double tax;
		double total;
		double taxRate = 0.06;
		double tipRate1 = 0.10;
		double tipRate2 = 0.15;
		double tipRate3 = 0.20;
				
		// Ask and receive input from the user
		
		System.out.print("Restaurant name: ");
		restaurantName = keyboard.nextLine();
		
		System.out.print("Server name: ");
		serverName = keyboard.nextLine().toUpperCase();
		
		System.out.print("Subtotal: ");
		subtotal = keyboard.nextDouble();
		
		
		// Perform calculations
		
		tax = subtotal * taxRate;
		total = tax + subtotal;
		
		
		int x = serverName.indexOf(' ');
		String serverFirstName = serverName.substring(0, x);
		
		// Print receipt
		
		
		System.out.println("=====================================");
		System.out.println(restaurantName);
		System.out.println("Your server was: " + serverFirstName);
		System.out.printf("Subtotal: $%.2f\n", subtotal);
		System.out.printf("Tax: $%.2f\n", tax);
		System.out.println("=====================================");
		System.out.printf("Total: $%.2f\n\n", total);
		System.out.println("Suggested tips:");
		System.out.printf("10%%: $%.2f\n", total * tipRate1);
		System.out.printf("15%%: $%.2f\n", total * tipRate2);
		System.out.printf("20%%: $%.2f\n\n", total * tipRate3);
		System.out.println("Thank you!");
		System.out.println("=====================================");
		
	}

}