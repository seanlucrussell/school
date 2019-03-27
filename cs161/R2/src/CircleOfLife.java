public class CircleOfLife {

	public static void main(String args[]) {
		// Create a new Species object here, passing in the appropriate
		// arguments
		Species grox = new Species("Grox", 100, 10.0);

		// print out the species' growth rate
		System.out.println(grox.growth());
		// use the species' toString here
		System.out.println(grox);
		// call populationInXYears here
		grox.grow();
		grox.grow();
		System.out.println(grox);
		// Create a new Species object here, passing in the appropriate
		// arguments using a very large number for the population (e.g.
		// 100000000)
		Species borg = new Species("Borg", 100000000, 18.0);
		// print out the species' population to make sure it is set to 1500
		System.out.println(borg);
		// call populationInXYears here, feel free to hardcode in the int to be
		// passed to the method
		System.out.println(borg.populationInXYears(15));
		// call mergeSpecies here
		// test that mergeSpecies is doing what you expected it to
		grox.mergeSpecies(borg);
		System.out.println(grox);
	}
}
