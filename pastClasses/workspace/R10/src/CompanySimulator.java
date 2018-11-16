import java.util.ArrayList;
import java.util.Random;


public class CompanySimulator {

	private int currentTime;
	

	private ArrayList<Employee> employees;

	private void initCompany() {

            String [] names = {
		"Leon Mcdonald",
		"Frankie Johnson",
		"Todd Rosenthal",
		"Mauricio Curran",
		"Randy Feinstein",
		"Donald Munoz",
		"Bonnie Barnhardt",
		"Gary Foley",
		"Brittney Wilson",
		"Lyndsay Loomis",
		"Madge Cartwright",
		"Stella Coan"};

		employees = new ArrayList<Employee>();
		
		int id = 1;
		int managerID = 1;
		for (int i = 0; i < 3; i++) {
			employees.add(new Manager(names[id], id, 0));  // we assume that all managers have a common manager whose ID is 0
			managerID = id;
			id += 1;
			for (int j = 0; j < 2; j++) {
				employees.add(new Engineer(names[id], id, managerID));
				id+=1;
			}
		}
	}
	
	public void run(int runTime) {
		initCompany();
		
		Random timeGen = new Random();

		currentTime = 0;
		while (currentTime <= runTime) {

			int scheduledTime = timeGen.nextInt(10) + 1;

			currentTime += scheduledTime;
			System.out.println("Current Time: " + currentTime);
			performWork();
			
			System.out.println("\n");
		}
	}

	private void performWork() {
		
		for (Employee emp : employees) {
			if (!emp.work()){
				System.out.println(emp + " Caused a crisis!");
				manageCrisis(emp);
			}
		}

	}
	private void manageCrisis(Employee emp) {
		
		Manager tempMan = new Manager("",emp.managerID,0);
		
		((Manager) employees.get(employees.indexOf(tempMan))).handleCrisis();;
		
//		for (Employee e: employees){
//			if(e instanceof Manager){
//				if(e.id == emp.managerID){
//					((Manager) e).handleCrisis();
//				}
//			}
//		}
	}

	public static void main(String[] args) {
		CompanySimulator sim = new CompanySimulator();
		sim.run(100);
	}


}