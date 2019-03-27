// P10 Assignment
// Author: Sean Russell
// Date:   Nov 14, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Music {
	
	private String songTitle;
    private String albumName;
    private String artistName;
    private int releaseYear;
    
    public static Music[] arrayTitles;
    
    public static void readLibrary(String inputFile){
    	try {
    		Scanner mScan = new Scanner(new File(inputFile));
    		
    		int fileNum = mScan.nextInt();
    		mScan.nextLine();
    		arrayTitles = new Music[fileNum];
    		
    		for (int i=0;i<fileNum;i++) {
    			arrayTitles[i] = new Music(mScan.nextLine(),mScan.nextLine(),mScan.nextLine(),mScan.nextInt());
    			mScan.nextLine();
    		}
    		
    		mScan.close();
    	} catch (FileNotFoundException e) {
    		System.out.println("ERROR!");
			System.exit(0);	
    	}
    }
    
    public static void writeLibrary(String outputFile){
    	try {
    		PrintWriter mWrite = new PrintWriter(new File(outputFile));
    		
    		double totalC = 0.0;
    		
    		for (int i=0;i<arrayTitles.length;i++){
    			mWrite.println(i+1 + ": " + arrayTitles[i].toString());
    			totalC += arrayTitles[i].getPrice();
    		}
    		
    		mWrite.printf("Total cost: $%.2f",totalC);
    		
    		mWrite.close();
    	} catch (FileNotFoundException e) {
    		System.out.println("ERROR!");
			System.exit(0);	
    	}
    }
    
    // Constructor
    public Music(String title, String album, String artist, int year) {
    	songTitle = title;
    	albumName = album;
    	artistName = artist;
    	releaseYear = year;
    }

    // Get methods
    public String getTitle() {
    	return songTitle;
    }
    public String getAlbum() {
    	return albumName;
    }
    public String getArtist() {
    	return artistName;
    }
    public int getYear() {
    	return releaseYear;
    }
    
    public String toString() {
    	return songTitle + ", " + albumName + ", " + artistName + ", " + releaseYear;
    }
    
    public double getPrice() {
    	if (releaseYear < 1970){
    		return 1.29;
    	} else if (releaseYear < 1980){
    		return 1.89;
    	} else if (releaseYear < 1990){
    		return 0.69;
    	} else if (releaseYear < 2000){
    		return 0.99;
    	} else {
    		return 1.39;
    	}
    }
	
	public static void main(String[] args) {
		readLibrary(args[0]);
		writeLibrary(args[1]);
	}

}
