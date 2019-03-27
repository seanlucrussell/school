
public class Employee {

    protected String name;
    protected int id;
    protected int managerID;

    public Employee(String name, int id, int managerID) {
		this.name = name;
		this.id = id;
		
    this.managerID = managerID;
	}

	public String getName() {
		return name;
	}

	public int getID() {
		return id;
	}
	public int getManagerID() {
		return managerID;
	}

  public boolean work() {
      return true;
  }

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
