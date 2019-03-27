

import java.util.Iterator;


public class TokenIter implements Iterator<String>{

	//input line to be tokenized
	private String line;

	// the next Token, null if no next Token
	private String nextToken;

	public TokenIter(String line){
		this.line = line;
		nextToken = getToken();
	}

	private String getToken(){
		line = line.trim()+ " "; // to scan past the last token
		if(line.length()==1)
			return null;
		else{
			char first = line.charAt(0);
			String val;
			int currIndex = 0;

			// number
			if(Character.isDigit(first)){	
				currIndex = 0;
				while(Character.isDigit(line.charAt(currIndex)) )
					currIndex++;
				val = line.substring(0, currIndex);
			}
			// special character
			else {
				val = line.substring(currIndex,++currIndex);
				if(!(val.equals("(") || val.equals(")") || val.equals("+") ||
				   val.equals("-") || val.equals("*") || val.equals("/") ) ){
				 System.out.println("***ERROR invalid character on input: " + val);
				 System.exit(0);
				}
			}
			line = line.substring(currIndex);
			return val;
		}
	}
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return nextToken!=null;
	}

	@Override
	public String next() {
		// TODO Auto-generated method stub
        String t = nextToken;
        nextToken = getToken();
		return t;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args){
		String line = "15 +( 26+37) + 48";
		System.out.println("line: [" + line + "]");
		TokenIter tokIt = new TokenIter(line);
		while(tokIt.hasNext()){
			System.out.println("next token: [" + tokIt.next() + "]");
		}


	}
}
