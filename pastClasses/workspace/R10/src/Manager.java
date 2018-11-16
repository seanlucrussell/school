// R10 Assignment
// Author: Sean Russell
// Date:   Apr 1, 2015
// Class:  CS160
// Email:  slrussel@rams.colostate.edu
public class Manager extends Engineer{

	public Manager(String name, int id, int managerID) {
		super(name, id, managerID);
	}
	
	public boolean work(){
		System.out.println(this + " I am a perfect manager");
		return true;
	}
	
	public boolean equals(Object o){
		if (!(o instanceof Manager)){
			return false;
		}
		
		Manager m = (Manager) o;
		
		if (m.id == this.id){
			return true;
		}
		
		return false;
		
	}
	
	public String toString(){
		return "Manager " +  name + " ID: " + id;
	}
	
	public void handleCrisis(){
		System.out.println(this + " is handling a crisis");
	}
	
}