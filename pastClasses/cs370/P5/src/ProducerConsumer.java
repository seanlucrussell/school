
public class ProducerConsumer {

	final public static Object cntrl = new Object();
	
	public static void main(String[] args) throws InterruptedException {
		circBuffer buffer = new circBuffer(4);
		Thread pThread = new Thread(new producer(buffer));
		Thread cThread = new Thread(new consumer(buffer));
		pThread.start();
		cThread.start();
		pThread.join();
		cThread.join();
		System.out.println("Exiting!");
	}

}
