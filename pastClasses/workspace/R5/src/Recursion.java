// R5 Assignment
// Author: Sean Russell
// Date:   Feb 18, 2015
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

public class Recursion {

	/*
	 * Precondition: n >= 0 Postcondition: returns the nth number in a sequence
	 * where each element is twice its predecessor, and starts with 1.
	 */
	public int pracSeq1(int n) {
		if (n == 0) {
			return 1;
		}
		return 2 * pracSeq1(n - 1);
	}

	/*
	 * Precondition: n >= 0 Postcondition: returns the nth number in a sequence
	 * where each element is the sum of the previous 3, and starts with 1,2,3
	 */
	public int pracSeq2(int n) {
		if (n == 0) {
			return 1;
		}
		if (n == 1) {
			return 2;
		}
		if (n == 2) {
			return 3;
		}
		return pracSeq2(n - 1) + pracSeq2(n - 2) + pracSeq2(n - 3);

	}

	/*
	 * Precondition: word is an instance of String Postcondition: returns true
	 * if word is a palindrome, and false otherwise
	 */
	public boolean palindrome(String word) {
		if (word.length()==1||word.length()==0){
			return true;
		}
		if (!(word.charAt(0)==word.charAt(word.length()-1))){
			return false;
		}
		return palindrome(word.substring(1, word.length()-1));
	}

	/*
	 * Precondition: x >= 1 Postcondition: returns the appropriate star pattern
	 * for the value of x
	 */
	public String starString(int x) {
		if(x == 1){
			return "*";
		}
		String n = starString(x-1);
		String[] parts = n.split("\n");
		return "*" + parts[0] + "\n" + n;
	}

	public static void main(String args[]) {

		Recursion rec = new Recursion();
		// Each number being passed to the methods are the indices
		// of the number we are looking for
		// modify the code to use assertions to verify the expected result
		System.out.println("pracSeq1(int x):");
		System.out.println("Answer: " + rec.pracSeq1(5) + "\tExpecting: 32");
		System.out
				.println("Answer: " + rec.pracSeq1(7) + "\tExpecting: 128\n");

		System.out.println("pracSeq2(int x):");
		System.out.println("Answer: " + rec.pracSeq2(4) + "\tExpecting: 11");
		System.out.println("Answer: " + rec.pracSeq2(5) + "\tExpecting: 20\n");

		 System.out.println("\'car\' is a palindrome?: " +
		 rec.palindrome("car"));
		 System.out.println("\'racecar\' is a palindrome?: " +
		 rec.palindrome("racecar"));
		 System.out.println("\'hannah\' is a palindrome?: " +
		 rec.palindrome("hannah"));
		 System.out.println("\'banana\' is a palindrome?: " +
		 rec.palindrome("banana") + "\n");
		
		 System.out.println(rec.starString(10));
	}
}
