
public interface QueueIF {

	// enqueue String token at the end of Queue
	// In debug mode print "enqueue: " + enqueued value
	public void enqueue(String token);
	
	/* dequeue token from the front of the Queue
	 * If expQueue empty, return null
	 * In debug mode print "dequeue: " + dequeued value
	 */ 
	public String dequeue() throws QueueException;
	
	// returns size (#elements) of Queue
	public int size();	
}
