// Lab1 Assignment
// Author: Sean Russell
// Date:   Jan 21, 2015
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Lab1 {

	// BEGIN PART 1

	/**
	 * Test if x is divisible by 3
	 * 
	 * @param x
	 *            integer to test
	 * @return true if x is divisible by 3
	 */
	public boolean isDivisibleBy3(int x) {
		if (x % 3 == 0)
			return true;
		else
			return false;
	}

	/**
	 * Test if x is divisible by 5
	 * 
	 * @param x
	 *            integer to test
	 * @return true if x is divisible by 5
	 */
	public boolean isDivisibleBy5(int x) {
		if (x % 5 == 0)
			return true;
		else
			return false;
	}

	/**
	 * Use helper functions isDivisibleBy3() and isDivisibleBy5() to implement.
	 * 
	 * @param x
	 *            integer to see if it divisible by 3, 5, or both
	 * @return "is divisible by 3" if x is divisible by 3, "is divisible by 5"
	 *         if divisible by 5, or "is divisible by 3 and 5" if divisible by 3
	 *         and 5
	 */

	public String testDivisors(int x) {
		if (isDivisibleBy3(x) && isDivisibleBy5(x))
			return "is divisible by 3 and 5";
		else if (isDivisibleBy3(x))
			return "is divisible by 3";
		else if (isDivisibleBy5(x))
			return "is divisible by 5";
		else
			return "";
	}

	/**
	 * Print i and the result of testDivisors for all i such that first <= i <=
	 * last
	 * 
	 * Example: 1 2 3 "is divisible by 3" 4 5 "is divisible by 5" ... 15
	 * "is divisible by 3 and 5"
	 * 
	 * @param first
	 *            integer to start the test range, inclusive
	 * @param last
	 *            integer to end the test range, inclusive
	 */

	public void testRange(int first, int last) {
		for (int i = first; i <= last; i++) {
			System.out.print(i + " ");
			System.out.println(testDivisors(i));
		}
	}

	// END PART 1

	// BEGIN PART 2

	/**
	 * Extract all positive integers from the intArray. Returned array is
	 * exactly as long as the number of positive integers in values.
	 * 
	 * @param intArray
	 *            containing all the numbers
	 * @return array containing all of the positive integers in values.
	 */

	public int[] extractPositives(int[] intArray) {
		ArrayList<Integer> test = new ArrayList<Integer>();
		for (int i = 0; i < intArray.length; i++) {
			if (intArray[i] >= 0) {
				test.add(intArray[i]);
			}
		}
		int ret[] = new int[test.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = test.get(i).intValue();
		}
		return ret;
	}

	// END PART 2

	// BEGIN PART 3

	/**
	 * Read in data from a file, print out the data in the specified format -
	 * "Name: *name*, Age:*age*" and then print out all of the names in a row.
	 * 
	 * @param filename
	 *            string
	 */
	public void readAndPrint(String filename) {
		try {
			Scanner scan = new Scanner(new File(filename));
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<Integer> ages = new ArrayList<Integer>();
			while (scan.hasNext()) {
				names.add(scan.next());
				ages.add(scan.nextInt());
			}
			for (int i = 0; i < names.size(); i++) {
				System.out.println("Person: " + names.get(i) + ", Age: "
						+ ages.get(i));
			}
			for (int i = 0; i < names.size() - 1; i++) {
				System.out.print(names.get(i) + ", ");
			}
			System.out.print(names.get(names.size()-1));
			scan.close();
		} catch (FileNotFoundException e) {

		}
	}

	// END PART 3

	public static void main(String[] args) {
		Lab1 lab = new Lab1();
		Scanner keyboard = new Scanner(System.in);
		System.out.print("First: ");
		int first = keyboard.nextInt();
		System.out.print("Last: ");
		int last = keyboard.nextInt();
		lab.testRange(first, last);
		try {
			Scanner scan = new Scanner(new File(args[0]));

			ArrayList<Integer> test = new ArrayList<Integer>();
			while (scan.hasNextInt()) {
				test.add(scan.nextInt());
			}
			int ret[] = new int[test.size()];
			for (int i = 0; i < ret.length; i++) {
				ret[i] = test.get(i).intValue();
			}

			System.out.println(Arrays.toString(lab.extractPositives(ret)));

			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}

		lab.readAndPrint(args[1]);
		keyboard.close();
	}

}
