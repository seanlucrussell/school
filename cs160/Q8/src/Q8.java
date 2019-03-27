// Q8 Assignment
// Author: Sean Russell
// Date:   Nov 11, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Q8 {

	public static int numTokens = 0;
	public static int numIntTokens = 0;
	public static int numDoubleTokens = 0;
	public static int numCharTokens = 0;
	public static int numStringTokens = 0;
	
	public static void readFile(String filename){
		
		try {
			
			Scanner fileReader = new Scanner(new File(filename));
			
			// Code goes here:
			numTokens = fileReader.nextInt();
			
			for(int i=0;i<numTokens;i++){
				
				if(fileReader.hasNextInt()){
					numIntTokens++;
					fileReader.nextInt();
				}
				else if(fileReader.hasNextDouble()){
					numDoubleTokens++;
					fileReader.nextDouble();
				}
				else if(fileReader.hasNext()){
					if(fileReader.next().length()==1){
						numCharTokens++;
					} else{
						numStringTokens++;
					}
				}
				
			}
			
			fileReader.close();
		
		} catch (FileNotFoundException e){
			System.out.println(e);
			System.exit(0);
		}
		
	}
	
	public static void writeFile(String filename){
		
		try {
			
			PrintWriter fileOutput = new PrintWriter(new File(filename));
			
			// Code goes here:
			fileOutput.println("Total number of tokens: " + numTokens);
			fileOutput.println("Ints: " + numIntTokens);
			fileOutput.println("Doubles: " + numDoubleTokens);
			fileOutput.println("Chars: " + numCharTokens);
			fileOutput.println("Strings: " + numStringTokens);
			
			fileOutput.close();
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.exit(0);
		}
		
	}
	
	public static void main(String[] args){
		readFile(args[0]);
		writeFile(args[1]);
	}
}
