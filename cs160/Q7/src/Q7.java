import java.util.Arrays;

// Q7 Assignment
// Author: Sean Russell
// Date:   Nov 4, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu
public class Q7 {

	public double[] decimals = {1.1,2.2,3.3};
	public String[] names = new String[10];
	
	public static void doStuff(int[] numbers){
		
		System.out.println(numbers.length);
		System.out.println(numbers[3]);
		System.out.println(Arrays.toString(numbers));
		numbers[1] = 44;
		System.out.println(Arrays.toString(numbers));
		numbers[numbers.length-1] = 1001;
		System.out.println(Arrays.toString(numbers));
		numbers[numbers.length-2] = 24;
		System.out.println(Arrays.toString(numbers));
		
	}
	
	public static int frequency(int[] nums, int value){
		int count = 0;
		for(int i=0;i<nums.length;i++){
			if(nums[i]==value){
				count++;
			}
		}
		return count;
	}
	
	public static void squareElements(int[] nums){
		for(int i=0;i<nums.length;i++){
			nums[i] *= nums[i];
		}
	}
	
	public static void main(String[] args) {
		
		int testArray[] = {0,1,2,3,4,5,6,7,8,9};
		System.out.println(frequency(testArray,6));
		doStuff(testArray);
		squareElements(testArray);
		System.out.println(Arrays.toString(testArray));
		
	}

}