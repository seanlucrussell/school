// R11 Assignment
// Author: Sean Russell
// Date:   Oct 16, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

public class MagicEightBall {

	public static void main(String[] args) {
		
	}
	
	private String name = "unknown";

	public String whatIsMyName() {
		return name;
	}

	public void giveMeAName(String myNewName) {
		name = myNewName;
	}

	public void beAnnoying(String phrase, int timesToRepeat) {
		while(timesToRepeat>0){
			System.out.println(phrase);
			timesToRepeat--;
		}
	}

	public void sayHelloTo(String name){
		System.out.println("Hello, "+name+".");
	}
}
