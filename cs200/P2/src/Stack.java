import java.util.ArrayList;

public class Stack implements StackIF {

    // ArrayList opStack implements Stack, initialized in constructor
    private ArrayList<String> opStack;
    // debug flag, set in constructor
    private boolean debug;
    
    public Stack(boolean debug){
    	this.debug = debug;
    	opStack = new ArrayList<String>();
    }


	/* Implement
	 * push String op on opStack
	 * In debug mode print "push: " + pushed value
	 */
	public void push(String op){
		if (debug)
			System.out.println("push: " + op);
		opStack.add(op);
	}


	/* Implement
	 * pop and return String from opStack
	 * If opStack empty, throw StackException("popping from empty stack!")
	 * In debug mode print "pop: " + popped value
	 */
	public String pop() throws StackException{
		if (this.isEmpty())
			throw new StackException("popping from empty stack!");
		if (debug)
			System.out.println("pop: " + opStack.get(opStack.size()-1));
		return opStack.remove(opStack.size()-1);
	}


	/* Implement
	 * return, but do not pop, top of opStack
	 * If opStack empty, throw StackException("peeking empty stack!")
	 * In debug mode print "peek: " + peeked value
	 */
	public String peek() throws StackException{
		if (this.isEmpty())
			throw new StackException("peeking empty stack!");
		if (debug)
			System.out.println("peek: " + opStack.get(opStack.size()-1));
		return opStack.get(opStack.size()-1);
	}

        /* Implement
         * return true if opStack empty, false otherwise
         */
	public boolean isEmpty() {
		return opStack.isEmpty();
	}

        // provided, do not change
	public String toString(){
		return opStack.toString();
	}
	
        public static void main(String[] args) throws StackException{
    	Stack s = new Stack(true);
    	s.push("not");
    	s.push("true");
    	System.out.println(s);
	s.peek();
    	System.out.println(s);	
    	System.out.println(s.pop());
	System.out.println(s);
    	System.out.println(s.pop());
    }
}
