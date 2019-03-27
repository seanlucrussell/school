import java.util.Arrays;

// R12 Assignment
// Author: Sean Russell
// Date:   Oct 23, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

public class R12 {

	public static void main(String[] args) {
		double[] grades = {81.2,92.5,48.9,78.8};
		int[] numbers= {12,42,33,67,92,58};
		String[] arguments = new String[9];

		System.out.println(grades.length);
		System.out.println(numbers.length);
		System.out.println(arguments.length);

		System.out.println(grades[3]);
		System.out.println(grades[1]);
		System.out.println(numbers[2]);
		
		numbers[0] = 99;
		grades[3] = 90.5;
		arguments[6] = "maze7.txt";
		
		for(int i=0;i<grades.length;i++){
			System.out.print(grades[i] + ",");
		}
		System.out.println();
		for(int i=0;i<numbers.length;i++){
			System.out.print(numbers[i] + " ");
		}
		System.out.println();
		for(int i=0;i<arguments.length;i++){
			System.out.print(arguments[i] + "_");
		}
		System.out.println();

		System.out.println(Arrays.toString(grades));
		System.out.println(Arrays.toString(numbers));
		System.out.println(Arrays.toString(arguments));
		
		System.out.printf("%.3f\n",arrayAverage(grades));
		
		System.out.println(Arrays.toString(swapArray(grades)));
		
	}
	
	public static double arrayAverage(double[] array){
		double returnVal = 0.0;
		for(int i=0;i<array.length;i++){
			returnVal += array[i];
		}
		return returnVal / array.length;
	}
	
	public static double[] swapArray(double[] a){
		double[] r = new double[a.length];
		for(int i=0;i<a.length;i++){
			r[a.length-i-1] = a[i];
		}
		return r;
	}
	
}
