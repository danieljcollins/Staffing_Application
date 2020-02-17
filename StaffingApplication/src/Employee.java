// each employee in the MariaDB database will have it's critical info tranposed to java objects
// which will help schedule each employee
// EmployeeBuilder.java will perform initialization of the objects
// ShiftBuilder.java will then use these objects to create a schedule

import java.util.Comparator;
public class Employee{
	Integer employeeID;
	String firstName;
	String lastName;
	String email;
	
	int hoursWorkedTotal;	// this is their seniority, which is a ranking system based on hours worked. shifts will be given to highest ranking employees first
	int hoursWorkedThisPayPeriod;	// this is to prevent an employee from working too much, both for their health and to avoid giving out unneccessary overtime pay
	
	int daysConsecutivelyWorked = 0;	// this is to help prevent workers from having to work too many days in a row (for their health and happiness)
	int daysConsecutivelyOffWork = 0;	// this is to ensure that the employee gets 2 days off work to enjoy life and rest
	
	int sickDays = 0;	// keeps track of how many days the employee is sick (total)
			
	// indicates whether the employee is trained to perform a certain type of work
	Boolean pool;
	Boolean linen;
	Boolean mdr;
	Boolean dock;
	
	// compare class for ordering list by hoursWorked
	class SortByHoursWorked implements Comparator<Employee> {
		public int compare(Employee a, Employee b) {
			return a.hoursWorkedTotal - b.hoursWorkedTotal;
		}		
	}
	
	// constructor
	public Employee(int empId, String fName, String lName, int hoursTotal, int sDays, String el, Boolean pl, Boolean ln, Boolean mr, Boolean dk){
		this.employeeID = empId;
		this.firstName = fName;
		this.lastName = lName;
		this.hoursWorkedTotal = hoursTotal;
		
		this.sickDays = sDays;
		
		this.email = el;
		
		this.pool = pl;
		this.linen = ln;
		this.mdr = mr;
		this.dock = dk;
	}
	
	// get methods
	public int getEmployeeID() {
		return this.employeeID;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getEmployeeName() {
		return this.firstName + " " + this.lastName;
	}
	
	public int getHoursWorkedTotal() {
		return this.hoursWorkedTotal;
	}
	
	public int getSickDays() {
		return this.sickDays;
	}
	
	public int getHoursWorkedThisPayPeriod() {
		return this.hoursWorkedThisPayPeriod;
	}
	
	public int getDaysConsecutivelyWorked() {
		return this.daysConsecutivelyWorked;
	}
	
	public int getDaysConsecutivelyOffWork() {
		return this.daysConsecutivelyOffWork;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public Boolean getPool() {
		return this.pool;
	}
	
	public Boolean getLinen() {
		return this.linen;
	}
	
	public Boolean getMdr() {
		return this.mdr;
	}
	
	public Boolean getDock() {
		return this.dock;
	}	
	
	// set methods
	public void setDaysConsecutivelyWorked(int dayAmount) {
		this.daysConsecutivelyWorked = dayAmount;
	}
	
	public void setDaysConsecutivelyOffWork(int dayAmount) {
		this.daysConsecutivelyOffWork = dayAmount;
	}
	
	public void addSickDays(int dayAmount) {
		this.sickDays += dayAmount;
	}
}
