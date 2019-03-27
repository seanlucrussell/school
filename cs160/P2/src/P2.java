// P2 Assignment
// Author: Sean Russell
// Date:   Sep 1, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

public class P2 {
	
	public static void main(String[] args) {
		
		byte b = 28;
		short s = 12345;
		int i = 775533;
		long l = 6269876024L;
		float f = 9.345f;
		double d = 77.5331d;
		
		char c1 = '*', c2 = 'Z', c3 = '8';
		String s1 = "Computer", s2 = "Science", s3 = "Super";
		
		System.out.println(b + ";" + s + ";" + i + ";" + l);
		System.out.println(f + "," + d);
		System.out.println((b + s + i + l)/999999);
		System.out.printf("%.4f\n", Math.sqrt(f*d));
		System.out.printf("%.6f\n", b/f);
		System.out.println(c1 + "-" + c2 + "-" + c3);
		System.out.println((char)(c1 - 2) + "~" + (char)(c2 - 2) + "~" + (char)(c3 - 2));
		System.out.println(s1 + " " + s2 + " is " + s3 + "!!!");
		System.out.println(s1.length() + "," + s2.length() + "," + s3.length());
		System.out.println(s2.toUpperCase());
		System.out.println(s1.substring(1, 7));
		System.out.println(s2.indexOf('n'));
		System.out.println(s3.charAt(2));
		
		
	}
}
