// Q5 Assignment
// Author: Sean Russell
// Date:   Oct 21, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu
public class Q5 {
	
	private double pi = 3.1416;
	private String stringPizza = "Pizza";
	
	public static void main(String[] args) {
		Q5 q5 = new Q5();
		System.out.println(q5.giveMePizza());
		System.out.println(q5.calculateQuadratic(4, 2, -5, 2.0));
		System.out.println(q5.calculateAverage(22, 34, 46));
		System.out.println(q5.areaOfCircle(3.5));
	}
	
	public String giveMePizza(){
		return stringPizza;
	}
	
	public double calculateQuadratic(int a, int b, int c, double x){
		return (a*x*x)+(b*x)+c;
	}
	
	public double calculateAverage(int value1,int value2,int value3){
		return (value1 + value2 + value3) / 3.0;
	}
	
	public double areaOfCircle(double r){
		return pi*r*r;
	}
	
}
