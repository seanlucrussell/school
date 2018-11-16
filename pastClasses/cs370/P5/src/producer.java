import java.util.Random;

public class producer implements Runnable{

	circBuffer buffer;
	
	public producer(circBuffer buffer){
		this.buffer = buffer;
	}
	
	public void run() {
		Random random = new Random();
		double total = 0;
		int count = 0;
		double item;
		while (count <= 1000000){
			item = random.nextDouble() * 100.0;
			// put item into the buffer
			synchronized(ProducerConsumer.cntrl){
				while(buffer.full()){
					try {
						ProducerConsumer.cntrl.wait();
					} catch (InterruptedException e) {}
				}
				buffer.add(item);
				ProducerConsumer.cntrl.notifyAll();
			}
			// do other stuff
			count ++;
			total += item;
			if(count%100000==0)
				System.out.printf("Producer: Produced %d items, Cumulative value of produced items=%.3f\n",count,total);
		}
		System.out.println("Producer: Finished generating 1000000 items");
	}

}