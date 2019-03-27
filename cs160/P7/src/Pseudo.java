// P7 Assignment
// Author: Sean Russell
// Date:   Oct 16, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

public class Pseudo {
	
	private String pseudoString = "";
	
	public static void main(String[] args) {

		// Instantiate class		
		Pseudo pseudo = new Pseudo();

		// Verify class
		pseudo.setString("1234567890 Computer Science !(*@&#)*&");
		System.out.println("pseudo = " + pseudo.getString());
		System.out.println("pseudo.charAt(5) = " + pseudo.charAt(5));
		System.out.println("pseudo.indexOf('C') = " + pseudo.indexOf('C'));
		System.out.println("pseudo.substring(11, 19) = " + pseudo.substring(11, 19));
		pseudo.setString("Compare");
		System.out.println("pseudo.equals(\"Compare\") = " + pseudo.equals("Compare"));
		System.out.println("pseudo.equals(\"Compare!\") = " + pseudo.equals("Compare!"));

		// String equivalent (should match!)
		String string = "1234567890 Computer Science !(*@&#)*&";
		System.out.println("string = " + string);
		System.out.println("string.charAt(5) = " + string.charAt(5));
		System.out.println("string.indexOf('C') = " + string.indexOf('C'));
		System.out.println("string.substring(11, 19) = " + string.substring(11, 19));
		string = "Compare";
		System.out.println("string.equals(\"Compare\") = " + string.equals("Compare"));
		System.out.println("string.equals(\"Compare!\") = " + string.equals("Compare!"));
	}

	
	public void setString(String newName) {
		pseudoString = newName;
	}
	
	public String getString() {
		return pseudoString;
	}
	
	public char charAt(int i) {
		return pseudoString.charAt(i);
	}
	
	public int indexOf(char c){
		for(int i=0;i<pseudoString.length();i++){
			if(pseudoString.charAt(i)==c){
				return i;
			}
		}
		return -1;
	}
	
	public String substring(int start,int end) {
		String returnVal = "";
		for(int i=0;i<pseudoString.length();i++){
			if(start<=i && i<end){
				returnVal+=pseudoString.charAt(i);
			}
		}
		return returnVal;
	}
	
	public boolean equals(String test) {
		for(int i=0;i<pseudoString.length();i++){
			if(pseudoString.charAt(i)!=test.charAt(i)){
				return false;
			}
		}
		return true;
	}
	
}
