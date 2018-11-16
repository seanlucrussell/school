public abstract class Employee {
	
    private String name;
    private int id;
    
    public Employee(String name,int id){
    	setName(name);
    	setID(id);
    }
    
    public void setName(String name){
    	this.name = name;
    }
    
    public void setID(int id){
    	this.id = id;
    }
    
    public String getName(){
    	return name;
    }
    
    public int getID(){
    	return id;
    }
    
    public abstract int work();
    
    public String toString() {
    	return name + " ID: " + id;
    }
    
    public boolean equals(Object other) {
    	if (other instanceof Employee && other != null) {
    		return (name.equals(((Employee)other).name) && id == ((Employee)other).id);
    	}
    	return false;
    }
}