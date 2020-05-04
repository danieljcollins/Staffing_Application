/* Employee.java
* Author: Daniel Collins 
* Date: May 2020
* Purpose: Each employee in the MariaDB database will have it's critical info transposed to Employee.java java objects
* which will help schedule each employee EmployeeBuilder.java will perform initialization of the objects
* ShiftBuilder.java will then use these objects to create a schedule
*/

import java.util.Comparator;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Employee{
	Integer employeeID;
	String firstName;
	String lastName;
	String email;
	String phoneNumber;
	
	int hoursWorkedTotal;	// this is their seniority, which is a ranking system based on hours worked. shifts will be given to highest ranking employees first
	int hoursWorkedThisPayPeriod;	// this is to prevent an employee from working too much, both for their health and to avoid giving out unneccessary overtime pay
	
	int daysConsecutivelyWorked = 0;	// this is to help prevent workers from having to work too many days in a row (for their health and happiness)
	int daysConsecutivelyOffWork = 0;	// this is to ensure that the employee gets 2 days off work to enjoy life and rest
	
	int sickDays = 0;	// keeps track of how many days the employee is sick (total)
	boolean currentlySick = false;	// will be true if the employee is sick and hasn't confirmed that they are well yet
	
	ArrayList<LocalDate> sickDates = new ArrayList<LocalDate>();	// for every sick day, the date of the sick day will be added
	
	ObservableList<Vacation> vacationBooked = FXCollections.observableArrayList();	// for every day of every vacation, a new date will be added for that vacation day
			
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
	
	public Employee() {
		
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
	
	public boolean getSickStatus() {
		return this.currentlySick;
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
	
	public Boolean getTrainingPool() {
		return this.pool;
	}
	
	public Boolean getTrainingLinen() {
		return this.linen;
	}
	
	public Boolean getTrainingMdr() {
		return this.mdr;
	}
	
	public Boolean getTrainingDock() {
		return this.dock;
	}
	
	public ObservableList<Vacation> getVacationDateList(){
		return vacationBooked;
	}	
	
	// set methods
	public void setTrainingPool(boolean val) {
		this.pool = val;
	}
	
	public void setTrainingLinen(boolean val) {
		this.linen = val;
	}
	
	public void setTrainingMdr(boolean val) {
		this.mdr = val;
	}
	
	public void setTrainingDock(boolean val) {
		this.dock = val;
	}	
	
	public void setDaysConsecutivelyWorked(int dayAmount) {
		this.daysConsecutivelyWorked = dayAmount;
	}
	
	public void setDaysConsecutivelyOffWork(int dayAmount) {
		this.daysConsecutivelyOffWork = dayAmount;
	}
	
	public void addSickDays(int dayAmount) {
		this.sickDays += dayAmount;
	}
	
	// if false, they are currently able to work
	// if true, they are sick and unable to work
	public void setSickStatus(boolean status) {
		this.currentlySick = status;
	}
	
	// this will add vacation dates, day by day to a list of vacation dates (when they are on vacation and unable to work)
	// ScheduleBuilder.java will check this to determine if they are available to work on that day
	// the easiest way to accomplish this is to add all vacation dates day by date for easy comparison to the current day when building schedules (which are built day by day in this application)
	public void addVacation(LocalDate start, LocalDate end) {		
		int numberOfDays = end.compareTo(start);	// determine the number of days between start and end dates
		for(int i = 0; i <= numberOfDays; i++) {			
			this.vacationBooked.add( new Vacation(start.plusDays(i)) );		// add each LocalDate to this.vacationBooked
		}
	}	
	
	// this method will check if the employee is currently on a booked vacation
	public boolean checkVacationStatus(LocalDate date) {
		//pseudo code: check if they are assigned vacation in the emp object for the date that is currently being scheduled		
		return true;	// they are not on vacation
	}
}
