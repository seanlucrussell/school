// Midterm Assignment
// Author: Sean Russell
// Date:   Feb 25, 2015
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.util.ArrayList;

public class Trail {
	
	// Instance Variables
	private String name;
	private double length;
	private int difficulty;
	
	// Constructor
	public Trail(String name, double length, int difficulty){
		if (length <= 0 || difficulty <= 0 || difficulty > 5){
			throw new IllegalArgumentException("Length must be positive and difficulty must be between 1 and 5");
		}
		this.name = name;
		this.length = length;
		this.difficulty = difficulty;
	}
	
	
	// Getter methods
	public String getName(){
		return name;
	}
	public double getLength(){
		return length;
	}
	public int getDifficulty(){
		return difficulty;
	}
	
	
	public boolean equals(Object other){
		if(!(other instanceof Trail)){
			return false;
		}
		Trail otherTrail = (Trail) other;
		return (otherTrail.getName().equals(name));
	}
	
	public int moreDifficult(Trail other){
		if(other.getDifficulty()>difficulty){
			return -1;
		}
		if(other.getDifficulty()==difficulty){
			return 0;
		}
		return 1;
	}
	
	public String toString(){
		return "Name: " + name + "\tLength: " + length + "\tDifficulty: " + difficulty;
	}
	
	public static void main(String[] args){
		Trail blueberry = new Trail("Blueberry", 4.2, 4);
		Trail coconut = new Trail("Coconut", 3.6, 2);
		Trail banana = new Trail("Banana", 1.4, 1);
		Trail pineapple = new Trail("Pineapple", 5.7, 5);
		Trail coconut2 = new Trail("Coconut", 6.4, 3);
		Trail raspberry = new Trail("Raspberry", 3.2, 2);
		
		// Print all of the trails
		System.out.println("List of Trails:");
		System.out.println(blueberry);
		System.out.println(coconut);
		System.out.println(banana);
		System.out.println(pineapple);
		System.out.println(coconut2);
		System.out.println(raspberry);
		
		// Testing the equals method
		System.out.println("coconut is equal to coconut2: " + coconut.equals(coconut2) + " (Expected true)");
		System.out.println("coconut2 is equal to coconut: " + coconut2.equals(coconut) + " (Expected true)");
		System.out.println("coconut is equal to blueberry: " + coconut.equals(blueberry) + " (Expected false)");
		System.out.println("banana is equal to pineapple: " + banana.equals(pineapple) + " (Expected false)");
		
		// Creating the ArrayList of trails
		ArrayList<Trail> trailMap = new ArrayList<>();
		
		// Adding each trail
		trailMap.add(blueberry);
		trailMap.add(coconut);
		trailMap.add(banana);
		trailMap.add(pineapple);
		trailMap.add(coconut2);
		trailMap.add(raspberry);
		
		// Getting the index of an object from the ArrayList
		System.out.println("Index of Pineapple: " + trailMap.indexOf(pineapple));
		
		// Print all Trail objects whose difficulty is 3 or more
		System.out.println("Trails of difficulty 3 or greater:");
		for (Trail t: trailMap){
			if (t.getDifficulty() >= 3){
				System.out.println(t);
			}
		}
		
		// Print the most difficult trail
		System.out.println("The most difficult trail is:");
		// Set the first member of the arraylist as the baseline most difficult
		// Then iterate through the list, and if any are more difficult, set that as the new baseline
		Trail baseline = trailMap.get(0);
		for (Trail t: trailMap){
			if (t.moreDifficult(baseline)==1){
				baseline = t;
			}
		}
		System.out.println(baseline);
		
	}
	
}
