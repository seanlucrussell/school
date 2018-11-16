// R11 Assignment
// Author: Sean Russell
// Date:   Apr 8, 2015
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.util.Random;


public class Tester extends Employee{
	
	private int linesOfCodeTestedPerDay;
	
	public Tester(String name, int id, int linesTestedPerDay) {
		super(name, id);
		linesOfCodeTestedPerDay = linesTestedPerDay;
	}
	
	public String toString(){
		return super.toString();
	}
	
	public int work() {
		Random r = new Random();
		return (int) ((r.nextDouble()/2 + .75) * linesOfCodeTestedPerDay);
	}

}
