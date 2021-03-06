public class Equation {

	// you can choose to use debug or not
	// we will not test debug mode
	private boolean debug;

	// contains next Token or null at end of line
	private String nextToken;

	// Token Iterator, set in constructor
	private TokenIter itTokens;

	// IMPLEMENT
	public Equation(TokenIter iter, boolean debug) {
		itTokens = iter;
		this.debug = debug;
		nextTok("");
	}

	private void nextTok(String indent) {
		if (itTokens.hasNext())
			nextToken = itTokens.next();
		else
			nextToken = null;
		if (debug)
			System.out.println(indent + "next token: " + nextToken);
	}

	// line parses a line: LHS "=" RHS
	// LHS = identifier
	// RHS = ( token )+
	// add LHS entry to depGraph
	// for all identifiers x in RHS, add edge x->LHS in graph
	public void line(DepGraph depGraph) throws GraphException {
		String source = nextToken;
		if (source == null)
			return;
		if (isIdentifier(source)) {
			depGraph.addAdjList(source);
		}
		else
			throw new GraphException("LHS not identifier");
		itTokens.next();
		while (itTokens.hasNext()) {
			nextToken = itTokens.next();
			if (isIdentifier(nextToken)) {
				depGraph.addDest(nextToken, source);
			}
		}
	}

	private boolean isIdentifier(String s) {
		if (s.length() == 0)
			return false;
		if (s.toLowerCase().matches("true|false|not|and|or"))
			return false;
		if (!Character.isAlphabetic(s.charAt(0)))
			return false;
		for (int i = 0; i < s.length(); i++)
			if (!(Character.isAlphabetic(s.charAt(i)) || Character.isDigit(s.charAt(i))))
				return false;
		return true;
	}
}
