// P1 Assignment
// Author: Sean Russell
// Date:   Jan 27, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class P1 {

	boolean formArectangle(int i, int j, int k, int m, int[][] lineSegments) {
		P1 p1 = new P1();
		if ((p1.areParallel(i, j, lineSegments) && p1.areParallel(i, k,
				lineSegments))
				|| (p1.areParallel(i, j, lineSegments) && p1.areParallel(i, m,
						lineSegments))
				|| (p1.areParallel(i, m, lineSegments) && p1.areParallel(i, k,
						lineSegments))
				|| (p1.areParallel(m, j, lineSegments) && p1.areParallel(m, k,
						lineSegments))) {
			return false;
		}
		if ((p1.areIntersecting(i, j, lineSegments))
				|| (p1.areIntersecting(i, k, lineSegments))
				|| (p1.areIntersecting(i, m, lineSegments))
				|| (p1.areIntersecting(j, k, lineSegments))
				|| (p1.areIntersecting(j, m, lineSegments))
				|| (p1.areIntersecting(k, m, lineSegments))) {
			return false;
		}
		if ((p1.areEqual(i, j, lineSegments))
				|| (p1.areEqual(i, k, lineSegments))
				|| (p1.areEqual(i, m, lineSegments))
				|| (p1.areEqual(j, k, lineSegments))
				|| (p1.areEqual(j, m, lineSegments))
				|| (p1.areEqual(k, m, lineSegments))) {
			return false;
		}
		// If two lines are parallel, they must not share an endpoint
		if ((p1.areParallel(i, j, lineSegments) && p1.shareEndpoint(i, j, lineSegments))
				|| (p1.areParallel(i, k, lineSegments) && p1.shareEndpoint(i, k, lineSegments))
				|| (p1.areParallel(i, m, lineSegments) && p1.shareEndpoint(i, m, lineSegments))
				|| (p1.areParallel(j, k, lineSegments) && p1.shareEndpoint(j, k, lineSegments))
				|| (p1.areParallel(j, m, lineSegments) && p1.shareEndpoint(j, m, lineSegments))
				|| (p1.areParallel(k, m, lineSegments) && p1.shareEndpoint(k, m, lineSegments))) {
			return false;
		}
		
		// If two lines are perpendicular, they must share an endpoint
		if (!p1.areParallel(i, j, lineSegments)){
			if (!p1.shareEndpoint(i, j, lineSegments)){
				return false;
			}
		}
		if (!p1.areParallel(i, k, lineSegments)){
			if (!p1.shareEndpoint(i, k, lineSegments)){
				return false;
			}
		}
		if (!p1.areParallel(i, m, lineSegments)){
			if (!p1.shareEndpoint(i, m, lineSegments)){
				return false;
			}
		}
		if (!p1.areParallel(j, k, lineSegments)){
			if (!p1.shareEndpoint(j, k, lineSegments)){
				return false;
			}
		}
		if (!p1.areParallel(j, m, lineSegments)){
			if (!p1.shareEndpoint(j, m, lineSegments)){
				return false;
			}
		}
		if (!p1.areParallel(k, m, lineSegments)){
			if (!p1.shareEndpoint(k, m, lineSegments)){
				return false;
			}
		}

		return true;
	}

	boolean areIntersecting(int i, int j, int[][] lineSegments) {
		P1 p1 = new P1();
		if (p1.areEqual(i, j, lineSegments)
				|| p1.shareEndpoint(i, j, lineSegments)
				|| p1.areParallel(i, j, lineSegments)) {
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

	boolean areParallel(int i, int j, int[][] lineSegments) {
		if ((lineSegments[i][0] == lineSegments[i][2] && lineSegments[j][0] == lineSegments[j][2])
				|| (lineSegments[i][1] == lineSegments[i][3] && lineSegments[j][1] == lineSegments[j][3])) {
			return true;
		}
		return false;
	}

	boolean shareEndpoint(int i, int j, int[][] lineSegments) {
		P1 p1 = new P1();
		if (p1.areEqual(i, j, lineSegments)) {
			return false;
		} else if ((lineSegments[i][0] == lineSegments[j][0] && lineSegments[i][1] == lineSegments[j][1])
				|| (lineSegments[i][0] == lineSegments[j][2] && lineSegments[i][1] == lineSegments[j][3])
				|| (lineSegments[i][2] == lineSegments[j][0] && lineSegments[i][3] == lineSegments[j][1])
				|| (lineSegments[i][2] == lineSegments[j][2] && lineSegments[i][3] == lineSegments[j][3])) {
			return true;
		}
		return false;
	}

	boolean areEqual(int i, int j, int[][] lineSegments) {
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

	boolean isValid(int x1, int y1, int x2, int y2) {
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

	int[][] readLineSegments(String fileName) {
		// Use array list to read in lines
		ArrayList<ArrayList<Integer>> intArray = new ArrayList<ArrayList<Integer>>();
		P1 p1 = new P1();
		int rowNumber = 0;
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
					if (p1.isValid(x1, y1, x2, y2)) {
						intArray.add(new ArrayList<Integer>());
						intArray.get(rowNumber).add(x1);
						intArray.get(rowNumber).add(y1);
						intArray.get(rowNumber).add(x2);
						intArray.get(rowNumber).add(y2);
						rowNumber++;
					}
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		int[][] returnInts = new int[intArray.size()][4];
		for (int i = 0; i < returnInts.length; i++) {
			for (int j = 0; j < returnInts[i].length; j++) {
				returnInts[i][j] = intArray.get(i).get(j);
			}
		}

		return returnInts;
	}

	public static void main(String[] args) {
		P1 p1 = new P1();
		int[][] i1 = p1.readLineSegments("test.csv");
		for (int i = 0; i < i1.length; i++) {
			System.out.println(Arrays.toString(i1[i]));
		}
		System.out.println(p1.formArectangle(0, 1, 2, 3, i1));
	}

}
