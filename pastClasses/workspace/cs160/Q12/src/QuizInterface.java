// Final Programming Quiz (Interface File)
// Author: Chris Wilcox
// Date:   12/4/2014
// Class:  CS160
// Email:  wilcox@cs.colostate.edu

public interface QuizInterface {

	// Interface Methods
	
	// Reads file contents into an array of strings
	public void readFile(String filename);

	// Compute statistics for the array
	public void computeStatistics(String []strings);

	// Print statistics for the array
	public void printStatistics();
	
	// Writes the contents of the array to a file
	public void writeFile(String filename, String []strings);

	// Find the largest value in array
	public double findLargest(double []values);
}

