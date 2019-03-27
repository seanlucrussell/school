// R11 Assignment
// Author: Sean Russell
// Date:   Apr 8, 2015
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.util.Random;

public class Programmer extends Employee{
	
	private int linesOfCodePerDay;
	
	public Programmer(String name, int id, int linesPerDay) {
		super(name, id);
		linesOfCodePerDay = linesPerDay;
	}
	
	public String toString(){
		return super.toString();
	}
	
	
	public int work() {
		Random r = new Random();
		return (int) ((r.nextDouble() + .5) * linesOfCodePerDay);
	}
	
}
