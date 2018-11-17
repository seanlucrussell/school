// P3 Assignment
// Author: Sean Russell
// Date:   Sep 9, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

import java.util.Scanner;

public class P3 {

	public static void main(String[] args) {
		
		//Declare variables
		double grocery1, grocery2, grocery3, grocery4, grocery5, nonGrocery1, nonGrocery2, totalGrocery, totalNonGrocery, taxGrocery, taxNonGrocery, totalCost, totalTax, grandTotal;
		Scanner keyboard = new Scanner(System.in);
		
		//Code
		
		System.out.print("First grocery item: ");
		grocery1 = keyboard.nextDouble();		
		System.out.print("Second grocery item: ");
		grocery2 = keyboard.nextDouble();		
		System.out.print("Third grocery item: ");
		grocery3 = keyboard.nextDouble();		
		System.out.print("Fourth grocery item: ");
		grocery4 = keyboard.nextDouble();		
		System.out.print("Fifth grocery item: ");
		grocery5 = keyboard.nextDouble();
		
		System.out.print("First non-grocery item: ");
		nonGrocery1 = keyboard.nextDouble();
		System.out.print("Second non-grocery item: ");
		nonGrocery2 = keyboard.nextDouble();
		
		totalGrocery = grocery1 + grocery2 + grocery3 + grocery4 + grocery5;
		totalNonGrocery = nonGrocery1 + nonGrocery2;
		
		taxGrocery = totalGrocery * .008;
		taxNonGrocery = totalNonGrocery * .072;
		
		totalCost = totalGrocery + totalNonGrocery;
		totalTax = taxGrocery + taxNonGrocery;
		
		grandTotal = totalCost + totalTax;
		
		System.out.printf("Total grocery: $%.2f", totalGrocery);
		System.out.printf("\nTotal non-grocery: $%.2f", totalNonGrocery);
		System.out.printf("\nTax grocery: $%.2f", taxGrocery);
		System.out.printf("\nTax non-grocery: $%.2f", taxNonGrocery);
		System.out.printf("\nTotal cost: $%.2f", totalCost);
		System.out.printf("\nTotal tax: $%.2f", totalTax);
		System.out.printf("\nGrand total: $%.2f", grandTotal);
		
		keyboard.close();
	}

}
