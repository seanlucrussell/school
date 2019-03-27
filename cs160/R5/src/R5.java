// R5 Assignment
// Author: Sean Russell
// Date:   Sep 23, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.util.Scanner;

public class R5 {
	
	public static void main(String[] args){
		
		Scanner keyboard = new Scanner(System.in);

		// Here we print a welcome message
		System.out.println("Welcome to a simple calculator program!");
		System.out.println("---------------------------------------");
		System.out.println("All we know how to do is add, subtract, multiply, divide, and compute modulo.");
		System.out.println();

		// Here we get input from the user
		System.out.print("Please enter a the first number of your calculation: ");
		double firstNumber = keyboard.nextDouble();

		System.out.print("Please enter the operator (+|-|*|/|%): ");
		// ADD: get operator using Scanner object
		char operator = keyboard.next().charAt(0);

		System.out.print("Please enter a the second number of your calculation: ");
		// ADD: get the second number use the Scanner object
		double secondNumber = keyboard.nextDouble();
		
		// Here we will calculate the result:
		double result = 0;
		boolean validOperator = true;
		// ADD: write a switch statement that will calculate
		// the correct result.
		
		switch (operator) {
		case '+':
			result = firstNumber + secondNumber;
			break;
		case '-':
			result = firstNumber - secondNumber;
			break;
		case '*':
			result = firstNumber * secondNumber;
			break;
		case '/':
			if (secondNumber != 0) {
				result = firstNumber / secondNumber;
			} else {
				validOperator = false;
			}
			break;
		case '^':
			result = Math.pow(firstNumber,secondNumber);
			break;
		case '%':
			result = firstNumber % secondNumber;
			break;
		default:
			validOperator = false;
		}
		
		if (validOperator == true) {
			System.out.println("---------------------------------------");
			System.out.println("Result:");
			System.out.printf("%.2f %c %.2f = %.2f\n",firstNumber,operator,secondNumber,result);
			System.out.println("---------------------------------------");
		} else {
			System.out.println("Error: Invalid operation");
		}

		keyboard.close();
		System.out.println("End of program.");
	}
}
