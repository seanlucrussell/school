public class Tree {

	// Privided, DON'T CHANGE
	// the root of the tree
	private TreeNode root;

	// empty tree
	public Tree() {
		this.root = null;
	}

	// rootItem, empty children
	public Tree(TreeNode root) {
		this.root = root;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public void preorderTraverse() {
		if (!isEmpty())
			preorderTraverse(root, "");
		else
			System.out.println("null");
	}

	// print root item
	// print left tree
	// print right tree
	public void preorderTraverse(TreeNode node, String indent) {
		System.out.println(indent + node.getItem());
		if (node.getLeft() != null)
			preorderTraverse(node.getLeft(), indent + " ");
		if (node.getRight() != null)
			preorderTraverse(node.getRight(), indent + " ");

	}

	// if tree empty return null
	// else evaluate the tree by postorder traversal
	// and return its value
	public Boolean postorderEval(BST symTab) {
		Boolean res = null;
		if (!isEmpty()) {
			try {
				res = postorderEval(root,symTab);
			} catch (BSTException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	// IMPLEMENT
	
	// CHANGED THIS FUNCTION
	public Boolean postorderEval(TreeNode node, BST symTab) throws BSTException {
		// evaluate left tree
		// evaluate right tree (if not null)
		// evaluate operator in node and return Boolean result
		if (node.getItem().equals("false"))
			return false;
		else if (node.getItem().equals("true"))
			return true;
		else if (node.getItem().equals("not"))
			return !postorderEval(node.getLeft(),symTab);
		else if (node.getItem().equals("and"))
			return postorderEval(node.getLeft(),symTab) && postorderEval(node.getRight(),symTab);
		else if (node.getItem().equals("or"))
			return postorderEval(node.getLeft(),symTab) || postorderEval(node.getRight(),symTab);
		else if (isIdentifier(node.getItem())){
			return symTab.retrieveItem(node.getItem()).getVal();
		}
		return false;
	}

	// ADDED THIS FUNCTION
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
/*
	// EXERCISE
	public static void main(String[] args) {
		System.out.println("Stepwise build infix expression: not not (true or false) and true");

		Tree t = new Tree();
		System.out.println("tree: ");
		t.preorderTraverse();
		System.out.println("result:\n" + t.postorderEval() + "\n");

		TreeNode a = new TreeNode("true");
		t = new Tree(a);
		System.out.println("tree: ");
		t.preorderTraverse();
		System.out.println("result:\n" + t.postorderEval() + "\n");

		TreeNode b = new TreeNode("false");
		TreeNode or = new TreeNode("or", a, b);
		t = new Tree(or);
		System.out.println("tree: ");
		t.preorderTraverse();
		System.out.println("result:\n" + t.postorderEval() + "\n");

		TreeNode not2 = new TreeNode("not", or);
		t = new Tree(not2);
		System.out.println("tree: ");
		t.preorderTraverse();
		System.out.println("result:\n" + t.postorderEval() + "\n");

		TreeNode not = new TreeNode("not", not2);
		TreeNode and = new TreeNode("and", not, new TreeNode("true"));
		t = new Tree(and);
		System.out.println("tree: ");
		t.preorderTraverse();
		System.out.println("result:\n" + t.postorderEval() + "\n");
	}
*/
}
