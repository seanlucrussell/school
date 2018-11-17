// IdVal is a String KeyItem 
// Strings are Comparable
public class IdVal extends KeyItem<String> {
	
	// CHANGED TO BOOLEAN
	private Boolean val;
	
	// CHANGED TO MATCH DATATYPES
	public IdVal(String id, Boolean val){
		super(id);
		this.val = val;
	}
	
	// ADDED THIS FUNCTION
	public Boolean getVal() {
		return val;
	}

	public String toString(){
		return "[" + getKey() + ": " + val + "]"; 
	}
	
	public static void main(String[] args){
		IdVal iv1 = new IdVal("abc", true);
		IdVal iv2 = new IdVal("bcd", false);
		if(iv1.getKey().compareTo(iv2.getKey())<0)
			System.out.println(iv1 + " < " + iv2);
	}
}
