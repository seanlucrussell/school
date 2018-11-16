
public class Metrics {
	protected int numCompares;
	protected int numSwaps;
	
	public Metrics(){
		numCompares = 0;
		numSwaps = 0;
	}
	
	public Metrics(int compares, int swaps){
		numCompares = compares;
		numSwaps = swaps;
	}
	
	public String toString() {
		return "METRICS --> Comparisions: "+numCompares+", Swaps: "+numSwaps;
	}
}
