public class consumer implements Runnable{
	
	circBuffer buffer;
	
	public consumer(circBuffer buffer){
		this.buffer = buffer;
	}

	public void run() {
		double total = 0;
		int count = 0;
		double item = 0;
		while (count <= 1000000){
			// retrieve item from buffer
			synchronized(ProducerConsumer.cntrl){
			while(buffer.empty()){
				try {
					ProducerConsumer.cntrl.wait();
				} catch (InterruptedException e) {}
			}
			item = buffer.get();
			ProducerConsumer.cntrl.notifyAll();
			}
			// do other stuff
			count ++;
			total += item;
			if(count%100000==0)
				System.out.printf("Consumer: Consumed %d items, Cumulative value of consumed items=%.3f\n",count,total);
		}
		System.out.println("Producer: Finished consuming 1000000 items");
	}

}
