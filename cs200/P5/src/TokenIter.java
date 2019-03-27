import java.util.Iterator;
import java.util.ArrayList;

public class TokenIter implements Iterator<String> {

	// input line to be tokenized
	private String line;

	// the next Token, null if no next Token
	private String nextToken;
	
	private ArrayList<String> parsedLine;
	private int currentToken;
	

	// look for next key tokens: "true", "false", "or" "and", "not", "(", ")"
	private void parseExpression() {
		line = line.replaceAll("\\(", " \\( ");
		line = line.replaceAll("\\)", " \\) ");
		line = line.replaceAll("=", " = ");
		line = line.replaceAll("\\s+", " ").trim();
		String[] splitLine = line.split(" ");
		for (int i=0;i<splitLine.length;i++)
			if (splitLine[i].matches("true|false|and|or|not|=|\\(|\\)") || isIdentifier(splitLine[i]))
				parsedLine.add(splitLine[i]);
	}
	
	private boolean isIdentifier(String s){
		if (s.length()==0)
			return false;
		if (!Character.isAlphabetic(s.charAt(0)))
			return false;
		for (int i=0;i<s.length();i++)
			if(!(Character.isAlphabetic(s.charAt(i))||Character.isDigit(s.charAt(i))))
				return false;
		return true;
	}

	// implement
	public TokenIter(String line){
		this.line = line;
		parsedLine = new ArrayList<String>();
		currentToken = 0;
		parseExpression();
		if (parsedLine.size()>0){
			nextToken = parsedLine.get(0);
		}
	}

	// implement
	public boolean hasNext() {
		if (parsedLine.size() <= currentToken)
			return false;
		return true;
	}

	// implement
	public String next() {
		if (parsedLine.size() <= currentToken)
			nextToken = null;
		else
			nextToken = parsedLine.get(currentToken);
		currentToken ++;
		return nextToken;
	}

	// provided, do not change
	public void remove() {
		throw new UnsupportedOperationException();
	}

	// provided
	public static void main(String[] args) {
		String line;
		// you can play with other inputs on the command line
		if (args.length > 0)
			line = args[0];
		// or do the standard test
		else
			line = "abc = )true or false )and";
		System.out.println("line: [" + line + "]");
		TokenIter tokIt = new TokenIter(line);
		while (tokIt.hasNext()) {
			System.out.println("next token: [" + tokIt.next() + "]");
		}
	}
}
