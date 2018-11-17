
public class ParseTreeExpr {

	private boolean debug;

	private String nextToken;	
	private TokenIter itTokens;

	public ParseTreeExpr(TokenIter iter, boolean debug){
		itTokens = iter;
		this.debug = debug;
		nextTok("");
	}

	private void nextTok(String indent){
		if(itTokens.hasNext())
			nextToken = itTokens.next();
		else 
			nextToken = null;
		if(debug)
			System.out.println(indent+"next token: " + nextToken);
	}

	private void error(String errMess) throws ParseException{
		throw new ParseException(errMess);
	}

	
	public Tree line() throws ParseException{
		TreeNode root;
		Tree tree = new Tree();
		if(nextToken!=null){
			root = expr("");
			tree = new Tree(root);
		}
		if(nextToken!=null)
			error("end of line expected");
		return tree;
	}

	// expr = term ( "+" term )*
	// Notice that we only parse sums of terms, 
	// not differences
	

	private TreeNode expr(String indent) throws ParseException{
		if(debug)
			System.out.println(indent+"expr");

		TreeNode left = term(indent+" ");
		while(nextToken != null && nextToken.equals("+") ){
			String op = nextToken;
			nextTok(indent+" ");
			TreeNode right = term(indent+" ");
			left = new TreeNode(op,left, right);
		}
		return left;
	}

	// term = "(" expr ")" | number 
	// Notice that we only parse sums, not products
	// In the full expression parser, there would be 
	// code to parse a term as a product of factors 
	// and a factor would be 
	//         "(" term ")" | number

	private TreeNode term(String indent) throws ParseException{
		if(debug)
			System.out.println(indent + "term");
		if(nextToken.equals("(")){
			nextTok(indent+" ");
			TreeNode E = expr(indent+" ");
			if(nextToken.equals(")") )
				nextTok(indent+" ");
			else
				error(") expected");
			return E;
		}
		else
			return number(indent+" ");
	}

	private TreeNode number(String indent) throws ParseException{
		if(debug)
			System.out.println(indent + "number");
		if(Character.isDigit(nextToken.charAt(0))){
			String num = nextToken;
			nextTok(indent);
			return new TreeNode(num);
		}
		else {
			error("number expected");
			return null; // not
		}
	}
}
