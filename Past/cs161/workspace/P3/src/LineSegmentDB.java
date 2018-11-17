// P3 Assignment
// Author: Sean Russell
// Date:   Feb 9, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

import java.util.ArrayList;

public class LineSegmentDB {

	public ArrayList<LineSegment> lineSegments;

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
		lineSegments = new ArrayList<LineSegment>();
	}

	public boolean addLineSegment(int x1, int y1, int x2, int y2) {
		LineSegment segment = new LineSegment(x1, y1, x2, y2);
		return addLineSegment(segment);
	}

	public boolean addLineSegment(LineSegment segment) {
		for (LineSegment arraySegment : lineSegments) {
			if (segment.equals(arraySegment)) {
				return false;
			}
		}
		lineSegments.add(segment);
		return true;
	}

	public static void main(String[] args) {
		LineSegmentDB db1 = new LineSegmentDB();
		LineSegmentDB db2 = new LineSegmentDB();
		db1.addLineSegment(2,2,4,2);
		db1.addLineSegment(3,3,3,1);
		db1.addLineSegment(4,2,4,10);
		db1.addLineSegment(4,10,18,10);
		db2.addLineSegment(4,2,2,2);
		db2.addLineSegment(4,2,4,10);
		db2.addLineSegment(3,3,3,1);
		db2.addLineSegment(3,3,3,10);
		System.out.println(db1.union(db2));
	}

	public int size() {
		return lineSegments.size();
	}

	public boolean areIntersecting(int i, int j) {
		if (lineSegments.get(i).intersects(lineSegments.get(j))) {
			return true;
		}
		return false;
	}

	public ArrayList<LineSegment> union(LineSegmentDB other) {
		boolean x = true;
		ArrayList<LineSegment> returnList = new ArrayList<LineSegment>();
		for (LineSegment thisSegment : lineSegments) {
			returnList.add(thisSegment);
		}
		for (LineSegment thisSegment : other.lineSegments) {
			x = true;
			for (LineSegment otherSegment : lineSegments) {
				if (thisSegment.equals(otherSegment)) {
					x = false;
				}
			}
			if (x==true){
				returnList.add(thisSegment);
			}
		}
		return returnList;
	}

	public ArrayList<LineSegment> boxFilter(int xmin, int xmax, int ymin,
			int ymax) {
		ArrayList<LineSegment> returnList = new ArrayList<LineSegment>();
		for (LineSegment segment : lineSegments) {
			if (segment.inBox(xmin, xmax, ymin, ymax)) {
				returnList.add(segment);
			}
		}
		return returnList;
	}

	public LineSegment getSegment(int i) {
		return lineSegments.get(i);
	}

	public int indexOf(LineSegment segment) {
		return lineSegments.indexOf(segment);
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LineSegmentDB)) {
			return false;
		}
		LineSegmentDB otherDB = (LineSegmentDB) other;
		for (LineSegment segment : lineSegments) {
			if (otherDB.indexOf(segment) == -1) {
				return false;
			}
		}
		for (LineSegment segment : otherDB.lineSegments) {
			if (indexOf(segment) == -1) {
				return false;
			}
		}
		return true;
	}

	public String toString() {
		return lineSegments.toString();
	}
	
}
