import java.text.DecimalFormat;

public class Species {

	private String speciesName;
	private int population;
	private double growthRate;
	
	public String name(){
		return speciesName;
	}
	
	public int pop(){
		return population;
	}
	
	public double growth(){
		return growthRate;
	}
	
	public Species(String name, int pop, double gRate) {
		if (pop < 1) pop = 1;
		if (pop > 1500) pop = 1500;
		if (gRate < 1) gRate = 1;
		if (gRate > 20) gRate = 20;
		speciesName = name;
		population = pop;
		growthRate = gRate;
	}
	
	public void mergeSpecies(Species other) {
		speciesName = speciesName + other.name();
		population += other.pop();
		if (growthRate < other.growth()) growthRate = other.growth();
	}

	public String toString() {
		return "Name of species: " + speciesName + "\nPopulation: " + population + "\nGrowth Rate: " + new DecimalFormat("##").format(growthRate) + "%";
	}

	// increases the population according to the growth rate of the species,
	// i.e.
	// updates the population instance variable
	public void grow() {
		population += (int)(population * growthRate / 100);
	}

	// returns the population of the species in x years according to its growth
	// rate
	public int populationInXYears(int x) {
		int totalPop = population;
		for (int i=0;i<x;i++){
			int popGrowth = (int)(totalPop * growthRate / 100);
			totalPop += popGrowth;
		}
		return totalPop;
	}

}
