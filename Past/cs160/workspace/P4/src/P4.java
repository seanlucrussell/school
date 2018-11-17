// P4 Assignment
// Author: Sean Russell
// Date:   Sep 15, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

import java.util.Scanner;

public class P4 {

	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		
		// Input variables
		double salary;
		int exemptions;
		double interest;
		double capital;
		
		// Output variables
		double federalTotalIncome;
		double federalAdjustedIncome;
		double federalTotalTax = 0;
		double stateIncomeTax;
		
		// Input code
		System.out.print("Gross Salary: ");
		salary = keyboard.nextDouble();
		System.out.print("Number of Exemptions: ");
		exemptions = keyboard.nextInt();
		System.out.print("Interest Income: ");
		interest = keyboard.nextDouble();
		System.out.print("Capital Gains: ");
		capital = keyboard.nextDouble();
		
		// Calculations
		federalTotalIncome = salary + interest + capital;
		federalAdjustedIncome = federalTotalIncome - (exemptions * 1800);
		if (0 < federalAdjustedIncome && federalAdjustedIncome <= 10000){
			federalTotalTax = 0;
		} else if (10000 < federalAdjustedIncome && federalAdjustedIncome <= 25000){
			federalTotalTax = (federalAdjustedIncome - 10000) * 0.15;
		} else if (25000 < federalAdjustedIncome && federalAdjustedIncome <= 36000){
			federalTotalTax = ((federalAdjustedIncome - 25000) * 0.25) + 2250;
		} else if (36000 < federalAdjustedIncome){
			federalTotalTax = ((federalAdjustedIncome - 36000) * 0.28) + 2750 + 2250;
		}
		stateIncomeTax = federalAdjustedIncome * 0.06;
		
		// Output code
		System.out.printf("Total Income: $%.2f\n",federalTotalIncome);
		System.out.printf("Adjusted Income: $%.2f\n",federalAdjustedIncome);
		System.out.printf("Total Tax: $%.2f\n",federalTotalTax);
		System.out.printf("State Tax: $%.2f\n",stateIncomeTax);
		
		keyboard.close();
		
	}

}
