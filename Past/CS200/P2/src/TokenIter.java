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
		if (line.equals("or")){
			parsedLine.add("or");
			return;
		}
		if (line.equals("not")){
			parsedLine.add("not");
			return;
		}
		if (line.equals("and")){
			parsedLine.add("and");
			return;
		}
		if (line.equals("true")){
			parsedLine.add("true");
			return;
		}
		if (line.equals("false")){
			parsedLine.add("false");
			return;
		}
		for (int i=0;i<line.length();i++){
			if (i>0) {
				if (line.charAt(i-1)!='(' && line.charAt(i-1)!=' ' && line.charAt(i-1)!=')') {
					continue;
				}
			}
			if (line.charAt(i)=='('){
				parsedLine.add("(");
				continue;
			}
			if (line.charAt(i)==')'){
				parsedLine.add(")");
				continue;
			}
			if (line.length()<i+3) {
				continue;
			}
			if (line.substring(i, i+3).equals("or ")){
				parsedLine.add("or");
				i+=2;
				continue;
			}
			if (line.length()<i+4) {
				continue;
			}
			if (line.substring(i, i+4).equals("and ")){
				parsedLine.add("and");
				i+=3;
				continue;
			}
			if (line.substring(i, i+4).equals("not ")){
				parsedLine.add("not");
				i+=3;
				continue;
			}
			if (line.substring(i, i+4).equals("not(")){
				parsedLine.add("not");
				parsedLine.add("(");
				i+=3;
				continue;
			}
			if (line.length()<i+5) {
				continue;
			}
			if (line.substring(i, i+5).equals("true ")){
				parsedLine.add("true");
				i+=4;
				continue;
			}
			if (line.substring(i, i+5).equals("true)")){
				parsedLine.add("true");
				parsedLine.add(")");
				i+=4;
				continue;
			}
			if (line.length()<i+6) {
				continue;
			}
			if (line.substring(i, i+6).equals("false ")){
				parsedLine.add("false");
				i+=5;
				continue;
			}
			if (line.substring(i, i+6).equals("false)")){
				parsedLine.add("false");
				parsedLine.add(")");
				i+=5;
				continue;
			}
		}
		if (line.length()<5){
			return;
		}
		if (line.subSequence(line.length()-5, line.length()).equals(" true") || line.subSequence(line.length()-5, line.length()).equals("(true") || line.subSequence(line.length()-5, line.length()).equals(")true")){
			parsedLine.add("true");
			return;
		}
		if (line.length()<6){
			return;
		}
		if (line.subSequence(line.length()-6, line.length()).equals(" false") || line.subSequence(line.length()-6, line.length()).equals("(false") || line.subSequence(line.length()-6, line.length()).equals(")false")){
			parsedLine.add("false");
			return;
		}
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
			line = ")true or false )and";
		System.out.println("line: [" + line + "]");
		TokenIter tokIt = new TokenIter(line);
		while (tokIt.hasNext()) {
			System.out.println("next token: [" + tokIt.next() + "]");
		}
	}
}
