import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;



public class LineSegmentDB {
	
	public int[][] lineSegments;   //an array for storing the line segments
	public LineSegmentDB() {
		/** a constructor that creates an empty line segment database. 
		 * The size method (described below) should return the value 0 for an empty database.
		 */
		lineSegments = new int[0][4]; //creating an empty line segment database. 
	}; 
	
	
	public void readLineSegments(String fileName) {
		Scanner scanner;
		int numLines = 0;
		File file = new File(fileName);
		int[] lineSegment = new int[4];

		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.length()==0){
					
				}
				else if (line.charAt(0) != '#') {
					String[] tokens = line.split(",");
					for (int j = 0; j < tokens.length; j++) {
						lineSegment[j] = Integer.parseInt(tokens[j]);
					}
					if (isValid(lineSegment[0], lineSegment[1], lineSegment[2],
							lineSegment[3])) {
						numLines++;
					}

				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		// allocate array of the appropriate size
		lineSegments = new int[numLines][4];
		// the index of the current line segment
		int i = 0;

		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				// Make sure segment is longer than 0
				if (line.length()==0){
					
				}
				else if (line.charAt(0) != '#') {
					String[] tokens = line.split(",");
					for (int j = 0; j < tokens.length; j++) {
						lineSegment[j] = Integer.parseInt(tokens[j]);
					}
					if (isValid(lineSegment[0], lineSegment[1], lineSegment[2],
							lineSegment[3])) {
						for (int j = 0; j < lineSegment.length; j++) {
							lineSegments[i][j] = lineSegment[j];
						}
						i++;
					}
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	
	}
	
	public boolean isValid(int x1, int y1, int x2, int y2) {
		if (x1 == x2 && y1 == y2) {
			return false;
		}
		return (x1 == x2) || (y1 == y2);
	}
	
	public boolean areEqual(int i, int j) {
		/**Returns true if the two line segments indexed by i and j have the same 
		 * end points (note that order does not matter!) and false otherwise.
		 */
		int indexLimit = this.size()-1;
		if(i>indexLimit){
			return false;
		}
		if(j>indexLimit){
			return false;
		}
		int[] array_i = this.lineSegments[i];
		int[] array_j = this.lineSegments[j];
		if((array_i[0] == array_j[0]) && (array_i[1] == array_j[1]) && (array_i[2] == array_j[2]) && (array_i[3] == array_j[3]) )
		{
			return true;
		}else if((array_i[0] == array_j[2]) && (array_i[1] == array_j[3]) && (array_i[2] == array_j[0]) && (array_i[3] == array_j[1]) )
		{
			return true;			
		}else{
			return false;
		}
	}
	
	public boolean shareEndpoint(int i, int j) {
		return ((!areEqual(i, j))
				&& ((lineSegments[i][0] == lineSegments[j][0])
				&& (lineSegments[i][1] == lineSegments[j][1])
				|| (lineSegments[i][0] == lineSegments[j][2])
				&& (lineSegments[i][1] == lineSegments[j][3])
				|| (lineSegments[i][2] == lineSegments[j][0])
				&& (lineSegments[i][3] == lineSegments[j][1]) 
				|| (lineSegments[i][2] == lineSegments[j][2])
				&& (lineSegments[i][3] == lineSegments[j][3])));

	}
	
	public boolean isParallelToX(int i, int[][] lineSegments) {
		// to be parallel to the x axis the y coordinates need to be equal:
		return (lineSegments[i][1] == lineSegments[i][3]);
	}
	
	public boolean isParallelToY(int i, int[][] lineSegments) {
		// to be parallel to the y axis the x coordinates need to be equal:
		return (lineSegments[i][0] == lineSegments[i][2]);
	}
	
	public boolean areParallel(int i, int j, int[][] lineSegments){
		return (isParallelToX(i, lineSegments) == isParallelToX(j, lineSegments));
	}
	
	public boolean areIntersecting(int i, int j, int[][] lineSegments) {
		if (isParallelToX(i, lineSegments)==isParallelToX(j, lineSegments)) {
			return false;
		}
		int parallelToX, parallelToY;
		if (isParallelToX(i, lineSegments)) {
			parallelToX = i;
			parallelToY = j;
		} else {
			parallelToX = j;
			parallelToY = i;
		}
		return (lineSegments[parallelToX][1] > Math.min(
				lineSegments[parallelToY][1], lineSegments[parallelToY][3])
				&& lineSegments[parallelToX][1] < Math.max(
						lineSegments[parallelToY][1],lineSegments[parallelToY][3])
				&& lineSegments[parallelToY][0] > Math.min(
						lineSegments[parallelToX][0],lineSegments[parallelToX][2]) 
				&& lineSegments[parallelToY][0] < Math.max(
						lineSegments[parallelToX][0], lineSegments[parallelToX][2]));
	}
	
	public int size(){
		return lineSegments.length;
	} 
	
	public void clear(){
		/**- clears out the database.
		 * 
		 */
		this.lineSegments = new int[0][4]; 
		/*
		 * clears out the database. The reference to old 2D array is deleted and it should be picked
		 * up by the garbage collector.
		 */
		
		
	} 
	
	public String toString() {
		String retStr = "[";
		for (int i = 0; i < lineSegments.length; i++) {
			retStr += "[";
			for (int j = 0; j < lineSegments[i].length; j++) {
				retStr += lineSegments[i][j] + " ";
			}
			
			if (i == lineSegments.length-1){
				retStr += "]";
			} else {
				retStr += "]\n";
			}
		}
		retStr += "]";
		return retStr;
	}
	
	public static void main(String[] args) {
		
		LineSegmentDB db = new LineSegmentDB();
		System.out.println(db.size());
		db.readLineSegments("lineSegments.csv");
		System.out.println(db);
		System.out.println(db.size());
		System.out.println(db.areEqual(5, 7));
		// TEST IDEAS:
		// Verify the db is empty
		// Read some line segments in and verify they've been added
		// Compare line segments and verify that areEquals() is correct
	}
}

