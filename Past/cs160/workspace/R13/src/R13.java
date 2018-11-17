// R13 Assignment
// Author: Sean Russell
// Date:   Oct 28, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

import java.util.Arrays;

public class R13 {

    private static double PI = 3.1416;
    
    // Calculate volume of sphere
    public static double sphereVolume(double radius) {
        double volume = (4.0 / 3.0) * PI * radius * radius * radius;
        return volume;
    }
    
    // Convert string to uppercase
    public static String toUppercase(String input) {
        String output = "";
        for (int i = 0; i < input.length(); i++) {
            output += Character.toUpperCase(input.charAt(i));
        }
        return output;
    }
    
    // Truncate values in array of double
    public static void truncateArray(double []array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = Math.floor(array[i]);
        }
    }
    
    public static void main(String[] args) {
        
        
        double radius = 10.0;
        double volume = sphereVolume(radius);
        System.out.println("Sphere with radius of " + radius + " has volume " + volume);

        String string = "abcdefGHIJKL012345&%^*";
        System.out.println("Original: " + string);
        System.out.println("Uppercase: " + toUppercase(string));
        
        double array[] = {1.23, 2.34, 3.45, 4.56, 5.67, 6.78, 7.893982987};
        System.out.println("Original: " + Arrays.toString(array));
        truncateArray(array);
        System.out.println("Uppercase: " + Arrays.toString(array));
    }
}