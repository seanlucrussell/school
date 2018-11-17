
public interface StackIF {
	    // add String op to top of stack
    	// for the Array List implementation, the top of the
	    // stack is is at the end of the Array List
	    public void push(String op);
		
		// remove and return item from top of stack
		// for the Array List implementation, the top of the
		// stack is is at the end of the Array List 
		// throw Stack Exception when stack empty
		public String pop() throws StackException;
		
		// return, but do not remove, item from top of stack
		// for the Array List implementation, the top of the
		// stack is is at the end of the Array List 
		// throw Stack Exception when stack empty
		public String peek() throws StackException;	
		
		// return true if stack empty, false otherwise
		public boolean isEmpty();

}
