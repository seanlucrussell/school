public class Tree {
    private TreeNode root;
    
    
    //empty tree
    public Tree(){
    	this.root = null;
    }
    
    // rootItem, empty children
    public Tree(TreeNode root){
    	this.root = root;
    }
    
    public boolean isEmpty(){
    	return root==null;
    }
    
    public void preorderTraverse(){
    	if (!isEmpty())
    		preorderTraverse(root,"");
    	else
    		System.out.println("root is null");
    }
    
	public void preorderTraverse(TreeNode node, String indent){
		System.out.println(indent+node.getItem());
		if(node.getLeft()!=null) preorderTraverse(node.getLeft(),indent+" ");
		if(node.getRight()!=null) preorderTraverse(node.getRight(),indent+" ");
		
	}
}
