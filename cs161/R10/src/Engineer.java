// R10 Assignment
// Author: Sean Russell
// Date:   Apr 1, 2015
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.util.Random;

public class Engineer extends Employee{

	public Engineer(String name, int id, int managerID) {
		super(name, id, managerID);
	}
	
	public boolean work() {
		Random crisisGen = new Random();
		if (crisisGen.nextInt(10) == 0) {
			return false;
		} else {
			System.out.println(this + " is programming");
		}
		return true;
	}
	
	public String toString() {
		return "Engineer " + super.toString();
	}
	
}
