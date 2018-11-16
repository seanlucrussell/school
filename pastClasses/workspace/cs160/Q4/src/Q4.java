// Q4 Assignment
// Author: Sean Russell
// Date:   Sep 25, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Q4 {

	public static void main(String[] args) {
		// Declare variables
        int int0=0, int1=0, int2=0;
        double real0=0.0, real1=0.0, real2=0.0;
        String string0="", string1="", string2="";
        boolean p = true, q = true, r = false;

        try {

            // Open input stream
            Scanner scan = new Scanner(new File("data.txt"));

            // Read contents
            string0 = scan.nextLine();
            string1 = scan.nextLine();
            string2 = scan.nextLine();
            int0 = scan.nextInt();
            int1 = scan.nextInt();
            int2 = scan.nextInt();
            real0 = scan.nextDouble();
            real1 = scan.nextDouble();
            real2 = scan.nextDouble();
            p = scan.nextBoolean();
            q = scan.nextBoolean();
            r = scan.nextBoolean();

            // Close input stream
            scan.close();

        } catch (IOException e) {
            System.out.println("Cannot read file: data.txt");
            System.exit(0);
        }
        
        //A
        System.out.println((int0 + int1 + int2) * 123);
        
        //B
        System.out.println((real2 - real1) / 3.5);
        
        //C
        System.out.printf("%.7f\n", int2 * real1);
        
        //D
        System.out.println("Java programming is " + string0.charAt(13) + string0.charAt(11) + string0.charAt(14) + string0.charAt(12));
        
        //E
        System.out.println(string0.charAt(0) + "," + string1.charAt(0) + "," + string2.charAt(0));
        
        //F
        System.out.println(string0.equals(string2));
        
        //G
        System.out.println((p || q) && (q || !r));
        
        //H
        if (string1.length() < 10)
        	System.out.println("9 characters or fewer!");
        else
        	System.out.println("10 characters or more!");
        
        //I
        if (real1 < 8.67)
        	System.out.println(real1 + 1.234);
        else if ((real1 > 11.2) && (real1 <= 23.6))
        	System.out.println(real1 / 0.531);
        else
        	System.out.println(real1 * real1 / 4.221);
        
        //J
        switch (string1) {
        	case "Mexico City": case "Denver":
        		System.out.println("North America");
        		break;
        	case "Santiago": case "Lima":
        		System.out.println("South America");
        		break;
        	default:
        		System.out.println("Unknown City");
        		
        }
        	
	}

}
