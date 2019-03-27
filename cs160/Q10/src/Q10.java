// Q10 Assignment
// Author: Sean Russell
// Date:   Dec 2, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

public class Q10 {
	
	public static int[][] numbers = new int[3][3];
	public static String[][] grid = new String[4][8];
	
	public static void doStuff(String[][] data){
		System.out.println(data.length);
		System.out.println(data[0].length);
		System.out.println(data[0][0]);
		System.out.println(data[2][4]);
		System.out.println(data[data.length-1][data[0].length-1]);
		data[1][1] = "Hello";
	}
	
	public static void init(String[][] data, String value){
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				data[i][j] = value;
			}
		}
	}
	
	public static void convertToCoordinates(String[][] data){
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				data[i][j] = "("+i+","+j+")";
			}
		}
	}
	
	public static void print(String[][] data){
		for(int i=0;i<data.length;i++){
			System.out.print("[");
			for(int j=0;j<data[0].length;j++){
				System.out.print(data[i][j] + ", ");
			}
			System.out.println("]");
		}
	}
	
	public static void printBonus(String[][] data){
		for(int i=0;i<data.length;i++){
			System.out.print("[");
			for(int j=0;j<data[i].length;j++){
				System.out.print(data[i][j] + ", ");
			}
			System.out.println("]");
		}
	}
	
	public static void main(String[] args) {
		
	}

}
