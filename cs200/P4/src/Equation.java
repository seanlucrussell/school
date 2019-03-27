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
	
	private void nextTok(String indent){
		if (itTokens.hasNext())
			nextToken = itTokens.next();
		else
			nextToken = null;
		if (debug)
			System.out.println(indent + "next token: " + nextToken);
	}

	// provided
	private void error(String errMess) throws ParseException {
		throw new ParseException(errMess);
	}

	// provided
	public IdVal line(BST symbolTable) throws BSTException, ParseException {
		TreeNode root;
		Tree tree = new Tree();
		String id="";
		if (nextToken != null) {
			id = nextToken;
			nextTok("");
			nextTok("");
			root = expr("");
			tree = new Tree(root);
		}
		if (nextToken != null)
			error("end of line expected");
		if (id.equals(""))
			return null;
		IdVal lineIdVal = new IdVal(id,tree.postorderEval(symbolTable));
		symbolTable.insertItem(lineIdVal);
		return lineIdVal;
	}

	// IMPLEMENT

	// expr = term ( "or" term )*
	private TreeNode expr(String indent) throws ParseException {
		if(debug)
			System.out.println(indent + "expr");
		
		TreeNode left = term(indent+" ");
		while (nextToken != null && nextToken.equals("or")) {
			String op = nextToken;
			nextTok(indent + " ");
			TreeNode right = term(indent + " ");
			left = new TreeNode(op,left,right);
		}
		return left;
	}

	// term = factor ( "and" factor )*
	private TreeNode term(String indent) throws ParseException {
		if(debug)
			System.out.println(indent + "term");
		
		TreeNode left = factor(indent+" ");
		while (nextToken != null && nextToken.equals("and")) {
			String op = nextToken;
			nextTok(indent + " ");
			TreeNode right = factor(indent + " ");
			left = new TreeNode(op,left,right);
		}
		return left;
	}

	// factor = "not" factor | "(" expr ")" | "true"| "false"
	private TreeNode factor(String indent) throws ParseException {
		if (debug)
			System.out.println(indent + "factor");
		if (nextToken.equals("not")) {
			nextTok(indent + " ");
			TreeNode left = factor(indent + " ");
			return new TreeNode("not",left,null);
		}
		else if (nextToken.equals("(")) {
			nextTok(indent+" ");
			TreeNode E = expr(indent+" ");
			if(nextToken.equals(")") )
				nextTok(indent+" ");
			else
				error(") expected");
			return E;
		}
		else if (nextToken.equals("true") || nextToken.equals("false") || isIdentifier(nextToken)) {
			String val = nextToken;
			nextTok(indent + " ");
			return new TreeNode(val);
		}
		return null;
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
}
