import java.util.ArrayList;
import java.util.Scanner;

public class In2Post {
	// Use expQueue to create the postfix expression
	private Queue expQueue;

	// opStack maintains operators while building expQueue
	private Stack opStack;

	// evalStack is used for evaluation of the postfix expression in expQueue
	private Stack evalStack;

	// when scanning a next Token, put it in nextToken
	private String nextToken;

	// Iterator tokIt gets the input line in the constructor and produces tokens
	private TokenIter tokIt;

	// provided, used in TestServer codes, do not change
	public Queue getExpQueue() {
		return expQueue;
	}

	// implement constructor
	public In2Post(String line, boolean debug) {
		// show line with white space
		System.out.println("input line: [" + line + "]");
		// initialize tokIt, opStack, expQueue and evalStack
		tokIt = new TokenIter(line);
		opStack = new Stack(debug);
		expQueue = new Queue(debug);
		evalStack = new Stack(debug);
	}

	// convert infix to postfix using the algorithm discussed in
	// lecture L3: Queues
	public void convertIn2Post() throws StackException, QueueException {
		while (tokIt.hasNext()) {
			String token = tokIt.next();
			if (token.equals("true") || token.equals("false")) {
				expQueue.enqueue(token);
			}
			else if (token.equals("not")) {
				opStack.push(token);
			}
			else if (token.equals("and")) {
				if (!opStack.isEmpty() && opStack.peek().equals("not")) {
					expQueue.enqueue(opStack.pop());
				}
				opStack.push(token);
			}
			else if (token.equals("or")) {
				if (!opStack.isEmpty() && (opStack.peek().equals("not") || opStack.peek().equals("and"))) {
					expQueue.enqueue(opStack.pop());
				}
				opStack.push(token);
			}
			else if (token.equals("(")) {
				opStack.push(token);
			}
			else if (token.equals(")")) {
				while (!opStack.isEmpty() && !opStack.peek().equals("(")) {
					expQueue.enqueue(opStack.pop());
					if (opStack.peek().equals("(")) {
						opStack.pop();
						break;
					}
				}
			}
		}
		while (!opStack.isEmpty()) {
			expQueue.enqueue(opStack.pop());
		}
	}

	/*
	 * evaluate the postfix expression in expQueue using evalStack and returning
	 * the final result value when encountering an operand, push it on evalStack
	 * when encountering an n-ary operator, pop n values off the evalStack (n=2:
	 * right, then left) perform the operation, and push the result on the
	 * evalStack at the end of the expression, pop the result off the stack and
	 * return it
	 * 
	 * expQueue is either empty, or contains a valid postfix expression if
	 * expQueue empty return ""
	 */
	public String evalPost() throws QueueException, StackException {
		while (expQueue.size()>0){
			String nextElement = expQueue.dequeue();
			if (nextElement.equals("true") || nextElement.equals("false")){
				evalStack.push(nextElement);
			}
			if (nextElement.equals("not")) {
				String val = evalStack.pop();
				if (val.equals("true")){
					evalStack.push("false");
				}
				else if (val.equals("false")){
					evalStack.push("true");
				}
			}
			else if (nextElement.equals("and")) {
				String val1 = evalStack.pop();
				String val2 = evalStack.pop();
				if (val1.equals("true") && val2.equals("true")){
					evalStack.push("true");
				}
				else {
					evalStack.push("false");
				}
			}
			else if (nextElement.equals("or")) {
				String val1 = evalStack.pop();
				String val2 = evalStack.pop();
				if (val1.equals("false") && val2.equals("false")){
					evalStack.push("false");
				}
				else {
					evalStack.push("true");
				}
			}
		}
		if (!evalStack.isEmpty()) {
			return evalStack.pop();
		}
		return "";
	}

	public static void main(String[] args) throws StackException,
			QueueException {
		// exercise expressions
		boolean db = true;
		System.out.println("Testing In2Post, debug: " + db);

		String line = "";
		In2Post ex0 = new In2Post(line, db);
		ex0.convertIn2Post();
		System.out.println("postfix: " + ex0.expQueue);
		System.out.println("result: " + ex0.evalPost());

		line = " true or  false  ";
		In2Post ex2 = new In2Post(line, db);
		ex2.convertIn2Post();
		System.out.println("postfix: " + ex2.expQueue);
		System.out.println("result: " + ex2.evalPost());

		line = "not true";
		In2Post ex4 = new In2Post(line, db);
		ex4.convertIn2Post();
		System.out.println("postfix: " + ex4.expQueue);
		System.out.println("result: " + ex4.evalPost());

		db = false;
		System.out.println("debug: " + db);

		line = "true or true and false";
		In2Post ex5 = new In2Post(line, db);
		ex5.convertIn2Post();
		System.out.println("postfix: " + ex5.expQueue);
		System.out.println("result: " + ex5.evalPost());

		line = "true and (true or false)";
		In2Post ex6 = new In2Post(line, db);
		ex6.convertIn2Post();
		System.out.println("postfix: " + ex6.expQueue);
		System.out.println("result: " + ex6.evalPost());

		line = "not true or true";
		In2Post ex8 = new In2Post(line, db);
		ex8.convertIn2Post();
		System.out.println("postfix: " + ex8.expQueue);
		System.out.println("result: " + ex8.evalPost());

		line = "not (not true or false)";
		In2Post ex9 = new In2Post(line, db);
		ex9.convertIn2Post();
		System.out.println("postfix: " + ex9.expQueue);
		System.out.println("result: " + ex9.evalPost());
	}
}
