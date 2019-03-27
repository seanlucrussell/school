import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

// R14 Assignment
// Author: Sean Russell
// Date:   Nov 6, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu
public class R14 {

	public static void main(String[] args) {
		// Example of reading a file
		/*
		 * try { // make sure TA explains this line and the try/catch block
		 * Scanner fileReader = new Scanner(new File(args[0]));
		 * 
		 * // Examples: (not meant to be run as a program) String example1a =
		 * fileReader.next(); String example1b = fileReader.nextLine(); double
		 * example2 = fileReader.nextDouble(); int example3 =
		 * fileReader.nextInt(); char example4 = fileReader.next().charAt(0);
		 * 
		 * if (fileReader.hasNext()) { // I can grab a string token safely
		 * String example5 = fileReader.next(); }
		 * 
		 * if (fileReader.hasNextDouble()) { // I can grab a double safely
		 * double example6 = fileReader.nextDouble(); }
		 * 
		 * if (fileReader.hasNextInt()) { // I can grab a int safely int
		 * example7 = fileReader.nextInt(); }
		 * 
		 * fileReader.close();
		 * 
		 * } catch (FileNotFoundException e) { System.out.println("ERROR!");
		 * System.exit(0); }
		 * 
		 * // Example of writing to a file try { // make sure TA explains this
		 * line and the try/catch block PrintWriter fileOutput = new
		 * PrintWriter(new File(args[1]));
		 * 
		 * // Examples: fileOutput.println("Hey...");
		 * fileOutput.print("I've seen...");
		 * fileOutput.print("this stuff before.\n"); fileOutput.printf("%.3f\n",
		 * 3.456789);
		 * 
		 * // Important! Save the file fileOutput.close();
		 * 
		 * } catch (FileNotFoundException e) { System.out.println("ERROR!");
		 * System.exit(0); }
		 */
		readFile(args[0]);
		writeFile(args[1]);

	}

	public static void readFile(String inputFile) {
		try {
			Scanner inputScanner = new Scanner(new File(inputFile));
			inputScanner.nextInt();
			String test;
			while (inputScanner.hasNext()) {
				test = inputScanner.next().toLowerCase();
				if (test.equals("triangle")) {
					System.out.println(computeAreaTriangle(inputScanner.nextDouble(),
							inputScanner.nextDouble()));
				} else if (test.equals("circle"))
					System.out.println(computeAreaCircle(inputScanner.nextDouble()));
				else if (test.equals("rectangle"))
					System.out.println(computeAreaRectangle(inputScanner.nextDouble(),
							inputScanner.nextDouble()));
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR!");
			System.exit(0);
		}
	}

	public static void writeFile(String outputFile) {
		try {
			Scanner keyboard = new Scanner(System.in);
			PrintWriter outputWriter = new PrintWriter(new File(outputFile));

			boolean stop = true;
			System.out.println("Enter text: ");
			while (stop) {
				String writeString = keyboard.nextLine();
				if (writeString.toLowerCase().equals("stop")) {
					stop = false;
					break;
				}
				outputWriter.println(writeString);
			}

			keyboard.close();
			outputWriter.close();

		} catch (FileNotFoundException e) {
			System.out.println("ERROR!");
			System.exit(0);
		}

	}

	public static double computeAreaCircle(double radius) {
		return radius * radius * Math.PI;
	}

	public static double computeAreaRectangle(double height, double width) {
		return height * width;
	}

	public static double computeAreaTriangle(double height, double base) {
		return height * base * .5;
	}

}
