import java.time.LocalTime;
// each employee in the MariaDB database will have it's critical info tranposed to java objects
// which will help schedule each employee
// EmployeeBuilder.java will perform initialization of the objects
// ShiftBuilder.java will then use these objects to create a schedule
public class Employee{
	Integer employeeID;
	String firstName;
	String lastName;
	
	int hoursWorkedTotal;	// this is their seniority, which is a ranking system based on hours worked. shifts will be given to highest ranking employees first
	int hoursWorkedThisPayPeriod;	// this is to prevent an employee from working too much, both for their health and to avoid giving out unneccessary overtime pay
	
	int daysConsecutivelyWorked = 0;	// this is to help prevent workers from having to work too many days in a row (for their health and happiness)
	int daysConsecutivelyOffWork = 0;	// this is to ensure that the employee gets 2 days off work to enjoy life and rest
			
	// indicates whether the employee is trained to perform a certain type of work
	boolean pool;
	boolean linen;
	boolean mdr;
	boolean dock;
	
	// constructor
	public Employee(int empId, String fName, String lName, int hoursTotal){
		this.employeeID = empId;
		this.firstName = fName;
		this.lastName = lName;
		this.hoursWorkedTotal = hoursTotal;
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
	
	public int getHoursWorkedThisPayPeriod() {
		return this.hoursWorkedThisPayPeriod;
	}
	
	public int getDaysConsecutivelyWorked() {
		return this.daysConsecutivelyWorked;
	}
	
	public int getDaysConsecutivelyOffWork() {
		return this.daysConsecutivelyOffWork;
	}
	
	// set methods
	public void setDaysConsecutivelyWorked(int dayAmount) {
		this.daysConsecutivelyWorked = dayAmount;
	}
	
	public void setDaysConsecutivelyOffWork(int dayAmount) {
		this.daysConsecutivelyOffWork = dayAmount;
	}
}
