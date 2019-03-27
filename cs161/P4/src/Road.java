// P4 Assignment
// Author: Sean Russell
// Date:   Feb 17, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

public class Road {

	private String start;
	private String end;
	
	public Road(String firstCity, String secondCity){
		start = firstCity;
		end = secondCity;		
		
	}
	
	public boolean isFrom(String city) {
		if (city.equals(start)) return true;
		return false;
	}
	
	public String endsAt() {
		return end;
	}
	
	public String toString(){
		return start + "->" + end;
	}
}
