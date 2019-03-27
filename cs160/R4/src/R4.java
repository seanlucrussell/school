// R4 Assignment
// Author: Sean Russell
// Date:   Sep 18, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.util.Scanner;

public class R4 {
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Please enter a full name: ");
		String fullName = keyboard.nextLine().toLowerCase();
		System.out.print("Is the person happy? (y/n): ");
		char happyAnswer = keyboard.next().toLowerCase().charAt(0);
		boolean isHappy = happyAnswer == 'y';
		if (fullName.equals("chris wilcox")) {
			if (isHappy) {
				System.out
						.println("Looks like Chris had his chai from the Alley Cat!");
			} else {
				System.out
						.println("Chris needs to visit the Alley Cat to get his chai fix!!");
			}
		} else if (fullName.equals("cthulu")) {
			if (isHappy) {
				System.out
						.println("We are greatful for your mercy, o deep one.");
			} else {
				System.out.println("The end is neigh! REPENT!");
			}
		} else {
			System.out.println("Quoth the raven, 'nevermore'");
		}
		keyboard.close();
		System.out.println("End of program.");
	}
}
