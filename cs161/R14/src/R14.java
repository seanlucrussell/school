import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class R14 {

	public static Metrics bubbleSort (Comparable [] array) {
		int numCompares = 0;
		int numSwaps = 0;
		for (int position = array.length-1; position>=0;position--) {
			for (int i= 0 ;i< position;i++) {
				numCompares++;
				if (array[i].compareTo(array[i+1]) > 0) {
					swap(array,i, i+1);
					numSwaps++;
				}
			}
		}
		return new Metrics(numCompares, numSwaps);
	}
	
	public static Metrics bubbleSortFixed (Comparable [] array) {
		//COMPLETE THIS METHOD
		int numCompares = 0;
		int numSwaps = 0;
		for (int position = array.length-1; position>=0;position--) {
			for (int i= 0 ;i< position;i++) {
				numCompares++;
				if (array[i].compareTo(array[i+1]) > 0) {
					swap(array,i, i+1);
					numSwaps++;
				}
			}
			if (numSwaps == 0)
				break;
		}
		return new Metrics(numCompares, numSwaps);
	}
	
	public static Metrics selectionSort(Comparable[] array, boolean debug){
		int numCompares = 0;
		int numSwaps = 0;
		
		int min;
		
		for (int i=0;i < array.length-1;i++) {
			min = i;
			for (int j=i+1;j < array.length;j++){
				numCompares++;
				if (array[j].compareTo(array[min]) < 0) {
					min = j;
				}
			}
			swap(array,min,i);
			numSwaps++;
			if (debug) System.out.println("Move "+(i+1)+ " (swapped "+ array[i]+" and "+array[min]+"): "+ Arrays.toString(array));
		}
		return new Metrics(numCompares, numSwaps);
	} 
	
	// swap the elements at indexes i and j in array
	private static void swap(Comparable[] array, int i, int j) {
		Comparable temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	public static ArrayList<Integer> removeDuplicates(Integer[] arr) {
		ArrayList<Integer> a = new ArrayList<Integer>(Arrays.asList(arr));
		
		for (int i=1;i<a.size();i++){
			if (a.get(i-1).equals(a.get(i))){
				a.remove(i);
				i--;
			}
		}
		return a;
	}

	private static Integer[] makeSortedArray(int total) {
		Integer[] arr = new Integer[total];
		
		for (int i=0;i<total;i++) {
			arr[i] = i+1;
		}
		
		return arr;
	}
	
	public static void main(String[] args) {
		
		// TESTING BUBBLE SORT VS IMPROVED BUBBLE SORT
		
		// create arrays of integers
		Integer[] sortMe1 = {-45, 67, 90, 1, -45, 25, 4, 99, -67, 25, 25, 1000, -1000, 999};
		Integer[] sortMe2 = makeSortedArray(sortMe1.length);
		Integer[] sortMe3 = sortMe1.clone();
		Integer[] sortMe4 = sortMe2.clone();
		
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("TESTING BUBBLE SORT VS IMPROVED BUBBLE SORT");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++\n");
		
		System.out.println("====Original BubbleSort====");
		System.out.println("Test 1");
		System.out.println("Original: "+Arrays.toString(sortMe1));
		System.out.println(bubbleSort(sortMe1));
		System.out.println("Sorted: "+Arrays.toString(sortMe1));
		System.out.println("---------------------------");
		System.out.println("Test 2");
		System.out.println("Original: "+Arrays.toString(sortMe2));
		System.out.println(bubbleSort(sortMe2));
		System.out.println("Sorted: "+Arrays.toString(sortMe2));
		
		System.out.println("\n====Fixed BubbleSort====");
		System.out.println("Test 1");
		System.out.println("Original: "+Arrays.toString(sortMe3));
		System.out.println(bubbleSortFixed(sortMe3));
		System.out.println("Sorted: "+Arrays.toString(sortMe3));
		System.out.println("---------------------------");
		System.out.println("Test 2");
		System.out.println("Original: "+Arrays.toString(sortMe4));
		System.out.println(bubbleSortFixed(sortMe4));
		System.out.println("Sorted: "+Arrays.toString(sortMe4));
		
		
		// TESTING IF SELECTION SORT IS STABLE
		
		// create arrays of fancy names
		FancyName[] names1 = FancyName.createList(args[0]);
		FancyName[] names2 = names1.clone();
		
		System.out.println("\n\n++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("TESTING IF SELECTION SORT IS STABLE");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
		
		System.out.println("====Selection Sort====");
		System.out.println("Test 1");
		System.out.println("Original: "+Arrays.toString(names1));
		System.out.println(selectionSort(names1,false));
		System.out.println("Sorted: "+Arrays.toString(names1));
		
		// REMOVING DUPLICATES
		// create array with duplicates
		Integer[] iHaveDuplicates = {-45, 7, 7, -45, -45, 25, 9, 7, -67, 7, 25, 1000, 1000, 999};
		
		System.out.println("\n\n++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("REMOVING DUPLICATES WITH THE HELP OF SORTING");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++\n");
		
		System.out.println("\n====Duplicate Deletion====");
		System.out.println("Original: "+Arrays.toString(iHaveDuplicates));
		System.out.println(bubbleSortFixed(iHaveDuplicates));
		System.out.println("Sorted: "+Arrays.toString(iHaveDuplicates));
		System.out.println("Duplicates Deleted: "+removeDuplicates(iHaveDuplicates));
		

		
	}

}
