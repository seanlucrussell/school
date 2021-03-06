// P2 Assignment
// Author: Sean Russell
// Date:   Feb 3, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class LineSegmentDB {

	public int lineSegments[][];

	// Finished

	public boolean isValid(int x1, int y1, int x2, int y2) {
		boolean returnVal = false;
		if ((x1 >= 0 && x1 <= 200 && y1 >= 0 && y1 <= 200 && x2 >= 0
				&& x2 <= 200 && y2 >= 0 && y2 <= 200)
				&& (x1 == x2 || y1 == y2)) {
			returnVal = true;
		}
		if (x1 == x2 && y1 == y2) {
			returnVal = false;
		}
		return returnVal;
	}

	public LineSegmentDB() {
		lineSegments = new int[0][4];
	}

	public boolean addLineSegment(int x1, int y1, int x2, int y2) {
		if (isValid(x1, y1, x2, y2)) {
			int tempSegments[][] = new int[lineSegments.length + 1][4];
			for (int i = 0; i < lineSegments.length; i++) {
				for (int j = 0; j < 4; j++) {
					tempSegments[i][j] = lineSegments[i][j];
				}
			}
			tempSegments[tempSegments.length - 1][0] = x1;
			tempSegments[tempSegments.length - 1][1] = y1;
			tempSegments[tempSegments.length - 1][2] = x2;
			tempSegments[tempSegments.length - 1][3] = y2;
			lineSegments = tempSegments;
			return true;
		}
		return false;
	}

	public boolean addLineSegment(int[] segment) {
		return addLineSegment(segment[0], segment[1], segment[2], segment[3]);
	}

	public int size() {
		return lineSegments.length;
	}

	public int readLineSegments(String fileName) {
		this.clear();
		int segmentCount = 0;
		try {
			Scanner scan = new Scanner(new File(fileName));
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				if (line.charAt(0) != '#') {
					String[] tokens = line.split(",");
					// process the tokens
					int x1 = Integer.parseInt(tokens[0]);
					int y1 = Integer.parseInt(tokens[1]);
					int x2 = Integer.parseInt(tokens[2]);
					int y2 = Integer.parseInt(tokens[3]);
					if (this.addLineSegment(x1, y1, x2, y2)) {
						segmentCount++;
					}
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		return segmentCount;
	}

	public void clear() {
		lineSegments = new int[0][4];
	}

	public boolean areEqual(int i, int j) {
		if ((lineSegments[i][0] == lineSegments[j][0]
				&& lineSegments[i][1] == lineSegments[j][1]
				&& lineSegments[i][2] == lineSegments[j][2] && lineSegments[i][3] == lineSegments[j][3])
				|| (lineSegments[i][0] == lineSegments[j][2]
						&& lineSegments[i][1] == lineSegments[j][3]
						&& lineSegments[i][2] == lineSegments[j][0] && lineSegments[i][3] == lineSegments[j][1])) {
			return true;
		}
		return false;
	}

	public boolean shareEndpoint(int i, int j) {
		if (this.areEqual(i, j)) {
			return false;
		} else if ((lineSegments[i][0] == lineSegments[j][0] && lineSegments[i][1] == lineSegments[j][1])
				|| (lineSegments[i][0] == lineSegments[j][2] && lineSegments[i][1] == lineSegments[j][3])
				|| (lineSegments[i][2] == lineSegments[j][0] && lineSegments[i][3] == lineSegments[j][1])
				|| (lineSegments[i][2] == lineSegments[j][2] && lineSegments[i][3] == lineSegments[j][3])) {
			return true;
		}
		return false;
	}

	public boolean areIntersecting(int i, int j) {
		if (this.areEqual(i, j) || this.shareEndpoint(i, j)
				|| this.areParallel(i, j)) {
			return false;
		}

		int xlow = 0, xmid = 0, xhigh = 0, ylow = 0, ymid = 0, yhigh = 0;

		if (lineSegments[i][0] != lineSegments[i][2]) {
			System.out.println("Made it here!");
			if (lineSegments[i][0] < lineSegments[i][2]) {
				xlow = lineSegments[i][0];
				xhigh = lineSegments[i][2];
			} else {
				xhigh = lineSegments[i][0];
				xlow = lineSegments[i][2];
			}
			if (lineSegments[j][1] < lineSegments[j][3]) {
				ylow = lineSegments[j][1];
				yhigh = lineSegments[j][3];
			} else {
				yhigh = lineSegments[j][1];
				ylow = lineSegments[j][3];
			}
			xmid = lineSegments[j][0];
			ymid = lineSegments[i][1];
		} else {
			if (lineSegments[j][0] < lineSegments[j][2]) {
				xlow = lineSegments[j][0];
				xhigh = lineSegments[j][2];
			} else {
				xhigh = lineSegments[j][0];
				xlow = lineSegments[j][2];
			}
			if (lineSegments[i][1] < lineSegments[i][3]) {
				ylow = lineSegments[i][1];
				yhigh = lineSegments[i][3];
			} else {
				yhigh = lineSegments[i][1];
				ylow = lineSegments[i][3];
			}
			xmid = lineSegments[i][0];
			ymid = lineSegments[j][1];
		}

		if (xlow < xmid && xmid < xhigh && ylow < ymid && ymid < yhigh) {
			return true;
		}

		return false;
	}

	public boolean areParallel(int i, int j) {
		if ((lineSegments[i][0] == lineSegments[i][2] && lineSegments[j][0] == lineSegments[j][2])
				|| (lineSegments[i][1] == lineSegments[i][3] && lineSegments[j][1] == lineSegments[j][3])) {
			return true;
		}
		return false;
	}

	public boolean formArectangle(int i, int j, int k, int m) {
		if ((this.areParallel(i, j) && this.areParallel(i, k))
				|| (this.areParallel(i, j) && this.areParallel(i, m))
				|| (this.areParallel(i, m) && this.areParallel(i, k))
				|| (this.areParallel(m, j) && this.areParallel(m, k))) {
			return false;
		}
		if ((this.areIntersecting(i, j)) || (this.areIntersecting(i, k))
				|| (this.areIntersecting(i, m)) || (this.areIntersecting(j, k))
				|| (this.areIntersecting(j, m)) || (this.areIntersecting(k, m))) {
			return false;
		}
		if ((this.areEqual(i, j)) || (this.areEqual(i, k))
				|| (this.areEqual(i, m)) || (this.areEqual(j, k))
				|| (this.areEqual(j, m)) || (this.areEqual(k, m))) {
			return false;
		}
		// If two lines are parallel, they must not share an endpoint
		if ((this.areParallel(i, j) && this.shareEndpoint(i, j))
				|| (this.areParallel(i, k) && this.shareEndpoint(i, k))
				|| (this.areParallel(i, m) && this.shareEndpoint(i, m))
				|| (this.areParallel(j, k) && this.shareEndpoint(j, k))
				|| (this.areParallel(j, m) && this.shareEndpoint(j, m))
				|| (this.areParallel(k, m) && this.shareEndpoint(k, m))) {
			return false;
		}

		// If two lines are perpendicular, they must share an endpoint
		if (!this.areParallel(i, j)) {
			if (!this.shareEndpoint(i, j)) {
				return false;
			}
		}
		if (!this.areParallel(i, k)) {
			if (!this.shareEndpoint(i, k)) {
				return false;
			}
		}
		if (!this.areParallel(i, m)) {
			if (!this.shareEndpoint(i, m)) {
				return false;
			}
		}
		if (!this.areParallel(j, k)) {
			if (!this.shareEndpoint(j, k)) {
				return false;
			}
		}
		if (!this.areParallel(j, m)) {
			if (!this.shareEndpoint(j, m)) {
				return false;
			}
		}
		if (!this.areParallel(k, m)) {
			if (!this.shareEndpoint(k, m)) {
				return false;
			}
		}

		return true;
	}

	// Unfinished

	public int removeDuplicates() {
		// Go through array, find if there are duplicate line segments (forwards
		// and back)
		// Delete all but one, keep track of the number deleted
		// At the end, move line segments towards the end to fill up empty
		// spaces
		// Finally, shorten the array
		int numberRemoved = 0;
		// Remove duplicate line segments
		for (int i = 0; i < lineSegments.length - 1; i++) {
			for (int j = i+1; j < lineSegments.length; j++) {
				if (((lineSegments[i][0] == lineSegments[j][0]
						&& lineSegments[i][1] == lineSegments[j][1]
						&& lineSegments[i][2] == lineSegments[j][2]
						&& lineSegments[i][3] == lineSegments[j][3])
						|| (lineSegments[i][0] == lineSegments[j][2]
						&& lineSegments[i][1] == lineSegments[j][3]
						&& lineSegments[i][2] == lineSegments[j][0]
						&& lineSegments[i][3] == lineSegments[j][1]))&&lineSegments[i][0]!=-1) {
					numberRemoved++;
					lineSegments[j][0] = -1;
				}
			}
		}
		
		for (int i=0;i<numberRemoved;i++){
			for (int j=lineSegments.length-1;j>0;j--){
				if (lineSegments[j][0]!=-1){
					for (int k=0;k<lineSegments.length;k++){
						if (lineSegments[k][0]==-1){
							lineSegments[k]=lineSegments[j];
							break;
						}
					}
				}
			}
		}
		
		int tempLineSegments[][] = new int[lineSegments.length-numberRemoved][4];
		for (int i=0;i<tempLineSegments.length;i++){
			for (int j=0;j<4;j++){
				tempLineSegments[i][j] = lineSegments[i][j];
			}
		}
		
		lineSegments = tempLineSegments;
		
		return numberRemoved;
	}

	public static void main(String[] args) {
		LineSegmentDB test = new LineSegmentDB();
		System.out.println(test.readLineSegments("test.csv"));
		System.out.println(test.removeDuplicates());
		for (int i = 0; i < test.lineSegments.length; i++) {
			if (test.lineSegments[i]!=null){
				System.out.println(Arrays.toString(test.lineSegments[i]));
			}
		}
		// System.out.println(test.size());
		// test.addLineSegment(1, 2, 2, 2);
		// System.out.println(Arrays.toString(test.lineSegments[0]));
		// System.out.println(test.size());
	}

}
