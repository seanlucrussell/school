import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class ArrayHeapSort {

	private int[] A;

	public ArrayHeapSort(int[] A){
		this.A = A;
		buildHeap();
	}

	private void heapify(int i, int size){
		// left and right subtrees are already heaps
		// need to bubble element i in place top down
		int leftChild = 2*i+1;
		if (leftChild > size)
			return;
		int rightChild = leftChild + 1;
		int maxChild = (rightChild >= size) ? leftChild : (A[leftChild] > A[rightChild]) ? leftChild : rightChild;
		System.out.println(size);
		System.out.println(A[maxChild]);
		if (A[i] >= A[maxChild])
			return;
		swap(i,maxChild);
		heapify(maxChild,size);
	}

	private void swap(int a, int b){
		int temp = A[a];
		A[a] = A[b];
		A[b] = temp;
	}

	// implement the buildHeap algorithm described in the lecture notes 
	private void buildHeap(){
		for (int i=A.length/2;i>=0;i--){
			heapify(i,A.length);
		}
	}

	// implement the in place heapSort algorithm described in the lecture notes
	public void heapSort(){
		for (int i=1;i<A.length;i++){
			System.out.println(this);
			swap(0,A.length-i);
			heapify(0,A.length-i);
		}
	}

	// return the heap content
	public String toString(){
		return Arrays.toString(A);
	}

	public static void main(String[] args) throws FileNotFoundException{
		// scanner for input file
		Scanner scan = new Scanner( new File (args[0]));
		// first int in input: number of ints to sort following 
		int n = Integer.parseInt(scan.next());
		int[] A = new int[n];
		for(int i = 0; i<n; i++)
			A[i] = Integer.parseInt(scan.next());
		System.out.println("    in: " + Arrays.toString(A));
		ArrayHeapSort ahs = new ArrayHeapSort(A);
		System.out.println("  heap: " + ahs);
		ahs.heapSort();
		System.out.println("sorted: " + ahs);

	}
}
