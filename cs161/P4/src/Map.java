// P4 Assignment
// Author: Sean Russell
// Date:   Feb 17, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
	
	private ArrayList<Road> roads; 
	private ArrayList<String> allPaths;
	
	public Map(String filename){
		roads = new ArrayList<Road>();
		allPaths = new ArrayList<String>();
		try{
			Scanner scan = new Scanner(new File(filename));
			while (scan.hasNextLine()){
				String line = scan.nextLine();
				String[] parts = line.split(",");
				roads.add(new Road(parts[0],parts[1]));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
	
	public String toString(){
		String ret = "Map";
		for (Road r: roads) ret += "\n" + r;
		return ret;
	}
	
	public String findPath(String startCity, String goalCity) {
		if (startCity.equals(goalCity)) return goalCity;
		for (Road r: roads){
			if (r.isFrom(startCity)){
				String tempPath = findPath(r.endsAt(), goalCity);
				if (tempPath == null) return null;
				return startCity + "->" + tempPath;
			}
		}
		return null;
	}
	
	public void findAllPaths(String startCity, String goalCity) {
		clearPaths();
		for (String x : findAllPathsHelper(startCity, goalCity)) allPaths.add(x);
	}
	
	private ArrayList<String> findAllPathsHelper(String startCity, String goalCity){
		ArrayList<String> returnList = new ArrayList<>();
		if (startCity.equals(goalCity)) returnList.add(startCity);
		
		for (Road r: roads){
			if (r.isFrom(startCity)){
				ArrayList<String> newList = findAllPathsHelper(r.endsAt(), goalCity);
				for (String path: newList){
					returnList.add(startCity + "->" + path);
				}
			}
		}
		return returnList;
	}
	
	public ArrayList<String> getPaths() {
		return allPaths;
	}
	
	public void clearPaths(){
		allPaths.clear();
	}
	
}