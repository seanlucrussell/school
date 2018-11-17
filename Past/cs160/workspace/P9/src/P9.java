// P9 Assignment
// Author: Sean Russell
// Date:   Nov 5, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class P9 {

	private static String pieName = "My Pie Chart";
	private static int pieSize;
	private static String[] pieLabels;
	private static double[] pieData;
	private static String barName = "My Bar Chart";
	private static int barSize;
	private static double[] barData0;
	private static double[] barData1;
	private static String lineName = "My Line Graph";
	private static int lineSize;
	private static double[] lineData0;
	private static double[] lineData1;
	private static double[] lineData2;
	
	public static void main(String[] args){
		readFile(args[0]);
		displayCharts();
	}
	
	private static void readFile(String inputFile) {
		try{
			// Creating scanner to read file
			Scanner fileReader = new Scanner(new File(inputFile));
			
			// Setting values for various fields
			pieSize = fileReader.nextInt();
			barSize = fileReader.nextInt();
			lineSize = fileReader.nextInt();
			pieData = new double[pieSize];
			pieLabels = new String[pieSize];
			barData0 = new double[barSize];
			barData1 = new double[barSize];
			lineData0 = new double[lineSize];
			lineData1 = new double[lineSize];
			lineData2 = new double[lineSize];

			// Setting data for pie arrays
			for(int i=0;i<pieData.length;i++){
				pieData[i] = fileReader.nextDouble();
			}
			for(int i=0;i<pieLabels.length;i++){
				pieLabels[i] = fileReader.next();
			}

			// Setting data for bar arrays
			for(int i=0;i<barData0.length;i++){
				barData0[i] = fileReader.nextDouble();
			}
			for(int i=0;i<barData1.length;i++){
				barData1[i] = fileReader.nextDouble();
			}
			
			// Setting data for line arrays
			for(int i=0;i<lineData0.length;i++){
				lineData0[i] = fileReader.nextDouble();
			}
			for(int i=0;i<lineData1.length;i++){
				lineData1[i] = fileReader.nextDouble();
			}
			for(int i=0;i<lineData2.length;i++){
				lineData2[i] = fileReader.nextDouble();
			}
			fileReader.close();
		} catch (FileNotFoundException e){
			System.out.println("ERROR!");
			System.exit(0);	
		}
		
	} 

	private static void displayCharts() {
		
		// PIE GRAPH
		Plotter pieChart = new Plotter(pieName);
		pieChart.pieChartData(pieSize, pieData); 
		pieChart.pieChartLabels(pieSize, pieLabels);
		pieChart.drawGraph(Plotter.eType.PIECHART);

		// BAR GRAPH
		Plotter barChart = new Plotter(barName);
		barChart.barChartData(0, barSize, barData0);
		barChart.barChartData(1, barSize, barData1);
		barChart.drawGraph(Plotter.eType.BARCHART);

		// LINE GRAPH
		Plotter lineChart = new Plotter(lineName);
		lineChart.lineGraphData(0, lineSize, lineData0);
		lineChart.lineGraphData(1, lineSize, lineData1);
		lineChart.lineGraphData(2, lineSize, lineData2);
		lineChart.drawGraph(Plotter.eType.LINEGRAPH);
		
	}
}
