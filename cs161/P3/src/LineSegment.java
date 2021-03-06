// P3 Assignment
// Author: Sean Russell
// Date:   Feb 9, 2015
// Class:  CS161
// Email:  srussel@rams.colostate.edu

import java.util.Arrays;

public class LineSegment {

	private int[] coordinates = new int[4];

	public LineSegment(int[] coordinates) {
		this(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
	}

	public LineSegment(int x1, int y1, int x2, int y2) {
		if (this.isValid(x1, y1, x2, y2)) {
			coordinates[0] = x1;
			coordinates[1] = y1;
			coordinates[2] = x2;
			coordinates[3] = y2;
		} else {
			throw new IllegalArgumentException(
					"Illegal line segment coordinates");
		}
	}

	public int[] getCoordinates() {
		int returnInts[] = new int[4];
		for (int i = 0; i < 4; i++) {
			returnInts[i] = coordinates[i];
		}
		return returnInts;
	}

	public String toString() {
		return Arrays.toString(coordinates);
	}

	public boolean equals(Object other) {
		if (!(other instanceof LineSegment)) {
			return false;
		}
		LineSegment otherSegment = (LineSegment) other;
		int[] segmentA = this.getCoordinates();
		int[] segmentB = otherSegment.getCoordinates();
		if ((segmentA[0] == segmentB[0] && segmentA[1] == segmentB[1]
				&& segmentA[2] == segmentB[2] && segmentA[3] == segmentB[3])
				|| (segmentA[0] == segmentB[2] && segmentA[1] == segmentB[3]
						&& segmentA[2] == segmentB[0] && segmentA[3] == segmentB[1])) {
			return true;
		}
		return false;
	}

	private boolean isValid(int x1, int y1, int x2, int y2) {
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

	public boolean intersects(LineSegment other) {
		int[] segment1 = this.getCoordinates();
		int[] segment2 = other.getCoordinates();

		if (this.equals(other) || this.sharesEndpoint(other)
				|| this.isParallel(other)) {
			return false;
		}

		int xlow = 0, xmid = 0, xhigh = 0, ylow = 0, ymid = 0, yhigh = 0;

		if (segment1[0] != segment1[2]) {
			System.out.println("Made it here!");
			if (segment1[0] < segment1[2]) {
				xlow = segment1[0];
				xhigh = segment1[2];
			} else {
				xhigh = segment1[0];
				xlow = segment1[2];
			}
			if (segment2[1] < segment2[3]) {
				ylow = segment2[1];
				yhigh = segment2[3];
			} else {
				yhigh = segment2[1];
				ylow = segment2[3];
			}
			xmid = segment2[0];
			ymid = segment1[1];
		} else {
			if (segment2[0] < segment2[2]) {
				xlow = segment2[0];
				xhigh = segment2[2];
			} else {
				xhigh = segment2[0];
				xlow = segment2[2];
			}
			if (segment1[1] < segment1[3]) {
				ylow = segment1[1];
				yhigh = segment1[3];
			} else {
				yhigh = segment1[1];
				ylow = segment1[3];
			}
			xmid = segment1[0];
			ymid = segment2[1];
		}

		if (xlow < xmid && xmid < xhigh && ylow < ymid && ymid < yhigh) {
			return true;
		}

		return false;
	}

	private boolean sharesEndpoint(LineSegment other) {
		int[] segment1 = this.getCoordinates();
		int[] segment2 = other.getCoordinates();

		if (this.equals(other)) {
			return false;
		} else if ((segment1[0] == segment2[0] && segment1[1] == segment2[1])
				|| (segment1[0] == segment2[2] && segment1[1] == segment2[3])
				|| (segment1[2] == segment2[0] && segment1[3] == segment2[1])
				|| (segment1[2] == segment2[2] && segment1[3] == segment2[3])) {
			return true;
		}
		return false;
	}

	private boolean isParallel(LineSegment other) {
		int[] segment1 = this.getCoordinates();
		int[] segment2 = other.getCoordinates();

		if ((segment1[0] == segment1[2] && segment2[0] == segment2[2])
				|| (segment1[1] == segment1[3] && segment2[1] == segment2[3])) {
			return true;
		}
		return false;
	}

	public boolean inBox(int xmin, int xmax, int ymin, int ymax) {
		if (coordinates[0] >= xmin && coordinates[0] <= xmax
				&& coordinates[2] >= xmin && coordinates[2] <= xmax
				&& coordinates[1] >= ymin && coordinates[1] <= ymax
				&& coordinates[3] >= ymin && coordinates[3] <= ymax) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		// LineSegment l1 = new LineSegment(3,0,3,8);
		// LineSegment l2 = new LineSegment(8,3,0,3);
		// System.out.println(l1.inBox(0,8,0,8));
	}

}