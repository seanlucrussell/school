public class Animal {

	// Instance variables
	private String name;
	private int topSpeed;

	// Getters and Setters
	public String getName() {
		return name;
	}
	public int getTopSpeed() {
		return topSpeed;
	}

	public String setName(String name) {
		this.name = name;
		return name;
	}
	public int setTopSpeed(int topSpeed) {
		if (topSpeed > 70 || topSpeed < 0) {
			throw new IllegalArgumentException(
					"topSpeed must be between 0 and 70");
		}
		this.topSpeed = topSpeed;
		return topSpeed;
	}

	// Constructor
	public Animal(String name, int topSpeed) {
		setName(name);
		setTopSpeed(topSpeed);
	}

	// toString
	public String toString() {
		return "Name: " + name + " Top Speed: " + topSpeed;
	}

	// equals
	public boolean equals(Object other) {
		if (!(other instanceof Animal)) {
			return false;
		}
		Animal otherAnimal = (Animal) other;
		if (topSpeed - otherAnimal.getTopSpeed() >= -2
				&& topSpeed - otherAnimal.getTopSpeed() <= 2) {
			return true;
		}
		return false;
	}

}
