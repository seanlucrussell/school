public class ParseTreeExpr {

	// you can choose to use debug or not
	// we will not test debug mode
	private boolean debug;

	// contains next Token or null at end of line
	private String nextToken;

	// Token Iterator, set in constructor
	private TokenIter itTokens;

	// IMPLEMENT
	public ParseTreeExpr(TokenIter iter, boolean debug) {
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
	public Tree line() throws ParseException {
		TreeNode root;
		Tree tree = new Tree();
		if (nextToken != null) {
			root = expr("");
			tree = new Tree(root);
			tree.preorderTraverse();
		}
		if (nextToken != null)
			error("end of line expected");
		return tree;
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
		if (nextToken == "not") {
			nextTok(indent + " ");
			TreeNode left = factor(indent + " ");
			return new TreeNode("not",left,null);
		}
		else if (nextToken == "(") {
			nextTok(indent+" ");
			TreeNode E = expr(indent+" ");
			if(nextToken.equals(")") )
				nextTok(indent+" ");
			else
				error(") expected");
			return E;
		}
		else if (nextToken == "true") {
			nextTok(indent + " ");
			return new TreeNode("true");
		}
		else if (nextToken == "false") {
			nextTok(indent + " ");
			return new TreeNode("false");
		}
		return null;
	}

}
