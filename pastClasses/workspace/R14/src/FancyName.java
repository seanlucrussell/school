import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FancyName implements Comparable<FancyName> {
	protected String name;
	protected String title;
	
	public FancyName(String name) {
		this.name = name;
		this.title = null;
	}
	
	public FancyName(String name, String title) {
		this.name = name;
		this.title = title;
	}
	
	public String toString() {
		if (title != null) {
			return name + " " + title;
		} else {
			return name;
		}	
	}
	
	public boolean equals(Object o) {
		if (o instanceof FancyName) {
			FancyName other = (FancyName)o;
			return name.equals(other.name);
		}
		return false;
	}

	public int compareTo(FancyName other) {
		return name.compareTo(other.toString().split(" ")[0]);
	}
	
	public static void main(String args[]){
		FancyName b = new FancyName("Brett");
		FancyName c = new FancyName("Courtney");
		System.out.println(c.compareTo(b));
	}
	
	public static FancyName[] createList(String filename) {
		FancyName[] names = null;
		try {
			Scanner scan = new Scanner(new File(filename));
			int lineCount = scan.nextInt();
			scan.nextLine(); // eat return char
			names = new FancyName[lineCount];
			
			for (int i=0;i < lineCount;i++) {
				String line = scan.nextLine();
				String[] tokens = line.split("\\|");
				if (tokens.length == 2) {
					names[i] = new FancyName(tokens[0], tokens[1]);
				} else {
					names[i] = new FancyName(tokens[0]);
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found!\nExiting program.");
			System.exit(0);
		}
		return names;
	}
}


