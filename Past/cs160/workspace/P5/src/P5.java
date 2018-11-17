// P5 Assignment
// Author: Sean Russell
// Date:   Sep 29, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

import java.util.Scanner;

public class P5 {

	public static void main(String[] args) {
		printPrimes(0, 50);
		System.out.println(removeVowels("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
		printFibonacci(50);
		computeStatistics(-1);
	}
	
	// Method to check whether an integer is prime.
	public static boolean isPrime(int number) {
	    for(int i=2;i<=number/2;i++)
	        if(number%i==0)
	            return false;
	    return true;
	}

	// Method to print primes in a specified range
	// ANOTHER METHOD IS COMPLETE
	public static void printPrimes(int start, int end) {
	    for(int i=start;i<=end;i++){
	    	if(isPrime(i)){
	    		System.out.print(i + ", ");
	    	}
	    }
	    System.out.println();
	}

	// Method to remove vowels from a string
	// ROCKIN IT! METHOD IS DONE
	public static String removeVowels(String input) {
		String returnVal = "";
	    for(int i=0;i<input.length();i++) {
	    	switch (input.charAt(i)){
	    		case 'a': case 'e': case 'i': case 'o': case 'u': case 'A': case 'E': case 'I': case 'O': case 'U':
	    			break;
	    		default:
	    			returnVal = returnVal + input.charAt(i);
	    	}
	    }
	    return returnVal;
	}

	
	// Method to generate and print a sequence of Fibonacci numbers
	// WOOHOO ALMOST DONE
	public static void printFibonacci(int maximum) {
	    int firstTerm = 0;
	    int secondTerm = 1;
	    int fibonacci = 0;
	    
	    while(fibonacci<maximum){
	    	System.out.print(fibonacci + ", ");
	    	fibonacci = firstTerm + secondTerm;
	    	firstTerm = secondTerm;
	    	secondTerm = fibonacci;
	    }
	    System.out.println();
	}
	
	
	// Method to find and print the minimum/maximum/mean of a set of positive numbers
	public static void computeStatistics(int sentinel) {
	    Scanner keyboard = new Scanner(System.in);
	    int value = 0, count = 0, minimum = Integer.MAX_VALUE, maximum = Integer.MIN_VALUE;
	    double mean = 0.0;
	    while(true) {
	    	value = keyboard.nextInt();
	    	if(value==sentinel){
	    		break;
	    	}
	    	if(value < minimum){
	    		minimum = value;
	    	}
	    	if(value > maximum){
	    		maximum = value;
	    	}
	    	mean = mean + value;
	    	count ++;
	    }
	    mean = mean / count;
	    
	    keyboard.close();
	    
	    System.out.println("Count: " + count);
	    System.out.println("Average: " + mean);
	    System.out.println("Maximum: " + maximum);
	    System.out.println("Minimum: " + minimum);
	}

}
