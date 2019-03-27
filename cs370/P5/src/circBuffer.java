
public class circBuffer{

	private double[] buffer;
	private int tail;
	private int head;
	private int size;
	
	// construct a buffer with provided size
	public circBuffer(int size){
		buffer = new double[size];
		head = 0;
		tail = 0;
		size = 0;
	}
	
	public boolean empty(){
		return size == 0;
	}
	
	public boolean full(){
		return size == buffer.length;
	}
	
	// add an item to the buffer
	// returns true if successfully added, false if buffer is full
	public boolean add(double item){
		if (this.full()){
			System.out.println("Queue is already full!");
	        return false;
		}
	    head = (head+1) % buffer.length;
	    size++;
	    buffer[head] = item;
		return true;
	}
	
	// get an item from the buffer
	// returns double, or 0 if buffer is empty
	public double get(){
		if (this.empty()){
			System.out.println("Queue is already empty!");
			return 0;
		}
		tail = (tail+1) % buffer.length;
		size--;
		return buffer[tail];
	}

}
