// Palindrome.java
// Class: cs200

public class Palindrome {

//	private void generatePalindromeRecursive(String s,int index)
//	{
//		// first print the character, then recurse on the next character index
//		System.out.print(s.charAt(index));
//		if(index<s.length()-1)
//		{
//			// question: will index++ or ++index work on the method call below?
//			generatePalindromeRecursive(s,index+1);
//		}
//		System.out.print(s.charAt(index));
//		
//	}
	
//	public void generatePalindrome(String s) {
//		// generate palindrome by concatenating the reverse of s to s
//		generatePalindromeRecursive(s,0);
//		System.out.println();
//		
//	}
	
	public void generatePalindrome(String s) {
		Stack st = new Stack();
		for (int i=0;i<s.length();i++){
			st.push(s.charAt(i));
			System.out.print(s.charAt(i));
		}
		while (!st.isEmpty()) {
			System.out.print(st.pop());
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
	    Palindrome p = new Palindrome();
		p.generatePalindrome("abc");

	}

	

}
