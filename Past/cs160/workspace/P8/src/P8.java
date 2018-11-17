import java.util.Arrays;

// P8 Assignment
// Author: Sean Russell
// Date:   Oct 20, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

public class P8 {

	public static void main(String[] args) {
        P8 p8 = new P8();
        
        int[] integerArray = p8.arrayOfPrimes();
        System.out.println(Arrays.toString(integerArray));
        double[] doubleArray = p8.arrayOfSquares();
        System.out.println(Arrays.toString(doubleArray));
        String[] stringArray = p8.arrayOfStrings("java c fortran html c java fortran c++");
        System.out.println(Arrays.toString(stringArray));
        char[] charArray = p8.arrayOfChars("Java rocks!");
        System.out.println(Arrays.toString(charArray));
        System.out.println("Sum of integer array = " + p8.sumArray(integerArray));
        
        double[] doubleArray2 = new double[6];
        doubleArray2[0] = 1;
        doubleArray2[1] = 2;
        doubleArray2[2] = 3;
        doubleArray2[3] = 4;
        doubleArray2[4] = 5;
        doubleArray2[5] = 6;
        System.out.println(p8.averageArray(doubleArray2));
        
        String[] stringArray2 = new String[6];
        stringArray2[0] = "BOOP";
        stringArray2[1] = "HELLA";
        stringArray2[2] = "BOOP";
        stringArray2[3] = "Boop";
        stringArray2[4] = "WHERE DA HOOD AT";
        stringArray2[5] = "BOOP";
        System.out.println(p8.frequency("BOOP",stringArray2));
        
        char[] charArray2 = new char[26];
        charArray2[0] = 'a';
        charArray2[1] = 'b';
        charArray2[2] = 'c';
        charArray2[3] = 'd';
        charArray2[4] = 'e';
        charArray2[5] = 'f';
        charArray2[6] = 'g';
        charArray2[7] = 'h';
        charArray2[8] = 'i';
        charArray2[9] = 'j';
        charArray2[10] = 'k';
        charArray2[11] = 'l';
        charArray2[12] = 'm';
        charArray2[12] = 'n';
        charArray2[14] = 'o';
        charArray2[15] = 'p';
        charArray2[16] = 'q';
        charArray2[17] = 'r';
        charArray2[18] = 's';
        charArray2[19] = 'T';
        charArray2[20] = 'U';
        charArray2[21] = 'V';
        charArray2[22] = 'w';
        charArray2[23] = 'x';
        charArray2[24] = 'y';
        charArray2[25] = 'z';
        p8.encodeChars(charArray2);
	}
	
	public int[] arrayOfPrimes() {
		int[] primeArray = new int[16];
		int primeCount = 0;
		for(int i=1;i<=50;i++){
			if(isPrime(i)){
				primeArray[primeCount] = i;
				primeCount++;
			}
		}
		return primeArray;
	}
	
	public double[] arrayOfSquares(){
		double[] squareArray = new double[13];
		for(int i=0;i<=12;i++){
			squareArray[i] = Math.pow(i*.5,2);
		}
		return squareArray;
	}
	
	public String[] arrayOfStrings(String input){
		String[] tokenArray = input.split("[ \\t\\n\\f\\r]");
		return tokenArray;
	}
	
	public char[] arrayOfChars(String input) {
		char[] charArray = new char[input.length()];
		for(int i=0;i<input.length();i++){
			charArray[i] = input.charAt(i);
		}
		return charArray;
	}
	
	public int sumArray(int[] input){
		int arraySum = 0;
		for(int i=0;i<input.length;i++){
			arraySum += input[i];
		}
		return arraySum;
	}
	
	public double averageArray(double[] input){
		double arraySum = 0;
		for(int i=0;i<input.length;i++){
			arraySum += input[i];
		}
		return arraySum / input.length;
	}

	public int frequency(String keyword, String[] strings){
		int keywordCount = 0;
		for(int i=0;i<strings.length;i++){
			if(strings[i].equals(keyword)){
				keywordCount++;
			}
		}
		return keywordCount;
	}
	
	public void encodeChars(char[] input){
		for(int i=0;i<input.length;i++){
			if(input[i]=='a'||input[i]=='b'||input[i]=='c'||input[i]=='d'||input[i]=='e'||input[i]=='f'||input[i]=='g'||input[i]=='h'||input[i]=='i'||input[i]=='j'||input[i]=='k'||input[i]=='l'||input[i]=='m'||input[i]=='n'||input[i]=='o'||input[i]=='p'||input[i]=='q'||input[i]=='r'||input[i]=='s'||input[i]=='t'||input[i]=='u'||input[i]=='v'||input[i]=='w'||input[i]=='x'||input[i]=='y'){
				input[i]++;
			}else if(input[i]=='z'){
				input[i]='a';
			}
		}
		System.out.println(Arrays.toString(input));
	}
	
	public boolean isPrime(int number) {
	    assert(number>0);
	    if (number == 0) return false;
	    for(int i = 2; i <= number/2; i++)
	        if (number % i == 0) return false;
	    return true;
	}

}
