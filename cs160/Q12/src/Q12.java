// Q12 Assignment
// Author: Sean Russell
// Date:   Dec 11, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Q12 implements QuizInterface {

	public static int numberLines = 0;
	public static int numberChars = 0;
	public static int numberLetters = 0;
	public static int numberDigits = 0;
	public static String[] fileContents;
	public static double[] testArray = { -92.2, -45.9, -16.4, -31.2, -67.8, -95.0,
			-71.1 };

	public static void main(String[] args) {
		Q12 q12 = new Q12();
		q12.readFile("input.txt");
		q12.computeStatistics(fileContents);
		q12.printStatistics();
		q12.writeFile("out.txt", fileContents);
		System.out.println(q12.findLargest(testArray));
	}

	@Override
	public void readFile(String filename) {
		try {
			Scanner scan = new Scanner(new File(filename));
			numberLines = scan.nextInt();
			scan.nextLine();
			fileContents = new String[numberLines];
			for (int i = 0; i < numberLines; i++) {
				fileContents[i] = scan.nextLine();
			}
			scan.close();
		} catch (Exception e) {
		}
	}

	@Override
	public void computeStatistics(String[] strings) {
		numberChars = 0;
		numberLetters = 0;
		numberDigits = 0;
		for (int i = 0; i < strings.length; i++) {
			numberChars += strings[i].length();
			for (int j = 0; j < strings[i].length(); j++) {
				if (Character.isLetter(strings[i].charAt(j))) {
					numberLetters++;
				}
				if (Character.isDigit(strings[i].charAt(j))) {
					numberDigits++;
				}
			}
		}
	}

	@Override
	public void printStatistics() {
		System.out.println("Number of lines: " + numberLines);
		System.out.println("Number of characters: " + numberChars);
		System.out.println("Number of letters: " + numberLetters);
		System.out.println("Number of digits: " + numberDigits);
	}

	@Override
	public void writeFile(String filename, String[] strings) {
		try {
			PrintWriter author = new PrintWriter(new File(filename));
			for (int i=0;i<strings.length;i++){
				author.println(i+1 + ": " + strings[i]);
			}
			author.close();
		} catch (Exception e) {

		}
	}

	@Override
	public double findLargest(double[] values) {
		double greatest = values[0];
		
		for(int i=0;i<values.length;i++){
			if (values[i]>greatest){
				greatest=values[i];
			}
		}
		
		return greatest;
	}
}
