// R11 Assignment
// Author: Sean Russell
// Date:   Apr 8, 2015
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

import java.util.ArrayList;

public class Project {
	
	private int linesOfCode;
	private int linesOfCodeWritten;
	private int linesOfCodeTested;
	private int duration;
	private ArrayList<Employee> employees;
	
	public Project(int linesOfCode, int duration, ArrayList<Employee> employee){
		this.linesOfCode = linesOfCode;
		this.duration = duration;
		linesOfCodeWritten = 0;
		linesOfCodeTested = 0;
		employees = new ArrayList<Employee>();
		for (Employee e: employee){
			addEmployee(e);
		}
	}
	
	public void addEmployee(Employee e){
		employees.add(e);
	}
	
	public int doProject(){
		int time = 0;
		while (linesOfCode > linesOfCodeWritten || linesOfCode > linesOfCodeTested){
			for (Employee e: employees){
				if (e instanceof Programmer && linesOfCode > linesOfCodeWritten){
					linesOfCodeWritten += e.work();
				}
				else if (e instanceof Tester && linesOfCode > linesOfCodeTested){
					linesOfCodeTested += e.work();
				}
			}
			time++;
			System.out.println("Day " + time);
			System.out.println("Lines Written: " + linesOfCodeWritten);
			System.out.println("Lines Tested: " + linesOfCodeTested);
			System.out.println();
		}
		if (time - duration > 0){
			return time - duration;
		}
		return 0;
	}
	
	public static void main(String args[]){
		Programmer bartley = new Programmer("Bartley",5,45);
		Tester swordfight = new Tester("SwordFight",3,59);
		ArrayList<Employee> eees = new ArrayList<Employee>();
		eees.add(bartley);
		eees.add(swordfight);
		Project p = new Project(135,5,eees);
		System.out.println(p.doProject());
	}
	
}