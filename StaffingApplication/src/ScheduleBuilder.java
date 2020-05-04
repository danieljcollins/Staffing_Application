/* Schedule.java
 * Author: Daniel Collins
 * Purpose: The purpose of this file is to populate an ObservableArrayList for a JavaFX Table that 
 * represents a two-week schedule for an organization. Essentially to schedule shifts to employees 
 * based on employee data, shift data for that org automatically based on custom business rules for
 * that organization.
 */

import java.util.ArrayList;
import java.time.LocalTime;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScheduleBuilder{
		
	ObservableList<Shift> shifts = FXCollections.observableArrayList();
	ObservableList<Employee> employees = FXCollections.observableArrayList();		
	
	public ObservableList<Shift> getShiftObjects(){
		return shifts;
	}
	
	// let's start by bringing in the shift and employee objects that were derived from the database calls
	public ScheduleBuilder(ObservableList<Shift> sObjects, ObservableList<Employee> eObjects){
		this.shifts = sObjects;
		this.employees = eObjects;		
	} // ends constructor
	
	// this will take the employee data and shift data, apply "business rules" to each employee to determine
	// their candidacy for filling a work shift
	public void buildSchedule() {
		for(int dayNumber = 0; dayNumber < 14; dayNumber++) {
			// here i'm going to add a counter which keeps track of employees who have been given a couple days off
			// so after two days their counter will be reset and they can come back to work fully replenished
			for(int j = 0; j < employees.size(); j++) {
				if(employees.get(j).getDaysConsecutivelyOffWork() >= 2) {	// they've had two days off, and it's time to come back to work
					employees.get(j).setDaysConsecutivelyOffWork(0);
					employees.get(j).setDaysConsecutivelyWorked(0);
				}
				if(employees.get(j).daysConsecutivelyWorked >= 5) {	// this means that they're currently off work
					employees.get(j).daysConsecutivelyOffWork++;					
				}				
			}
			for(int i = 0; i < shifts.size(); i++) {
				for(int j = 0; j < employees.size(); j++) {					
					if(shifts.get(i).getIsFilled(dayNumber) == false) {	// if it's not already filled, let's try to fill it
						if(trainingCheck(employees.get(j), shifts.get(i)) && notScheduled(dayNumber, shifts.get(i), employees.get(j)) && overTimeCheck(employees.get(j)) && daysConsecutivelyWorkedCheck(employees.get(j)) && sickCheck(employees.get(j)) ) {						
							fillShift(dayNumber, shifts.get(i), employees.get(j));
							//break;	// the shift has been filled, so move on to the next shift iteration
						}
						else {
							//System.out.println("This employee has already been scheduled.");
						}
					}
					else {	// if the shift is filled, break out of the employee checking loop and go onto the next shift to fill
						//System.out.println("Shift: " + shifts.get(i).shiftName + " has been filled already.");
						//break;
					}										
				}
			}
		}
	} // ends buildSchedule()
	
	// this method assigns an employee name to the shift object for a particular day in the pay period
	public void fillShift(int dNum, Shift s, Employee e) {
		
		// scheduling code:
		shifts.get( s.getShiftID() ).setEmployeeName(dNum, e.getEmployeeName() );
		shifts.get( s.getShiftID() ).setIsFilled(dNum, true);		
		
		e.hoursWorkedThisPayPeriod += 8;
		e.hoursWorkedTotal += 8;
		e.daysConsecutivelyWorked += 1;
	}
			
	// new code section: business scheduling rule methods which will filter through shift and employee data to populate shifts
	
	//	checks if the employee is already scheduled on that day
	public boolean notScheduled(int dNum, Shift s, Employee e){		
		// by looking into the shifts, it can be determined if the employeeIndex matches an employeeIndex assigned to a shift
		// if that employee index isn't found, they are therefore a candidate to be scheduled for a shift on that day
		for(int i = 0; i < shifts.size(); i++) {
			if( shifts.get(i).getEmployeeName(dNum).equals( e.getEmployeeName() ) ) {				
				return false;	// if it's the same, then we found a shift where that employee is already scheduled				
			}
			else {
				// if it's not the same, great, move onto the next shift to compare
			}
		}		
		return true;	// if the loop concluded without finding that the employee is scheduled to a shift, then they are not scheduled
	}
	
	// this will check if the employee is currently sick
	public boolean sickCheck(Employee emp) {
		if(emp.getSickStatus() == false) {	// if they are not sick, they have "passed" this check and therefore I'll return true
			//System.out.println("Employee has been checked, and they are not sick");
			return true;
		}
		else {
			//System.out.println(emp.getEmployeeName() + " has been checked, and they are currently sick");
			return false;
		}
	}
	
	// will check seniority of employee to ascertain shift assignment order (highest seniority first)
	// returns employee ID of next highest employee
	// this is currently unnneccessary since after the employee data is migrated from the dat)abase, the data
	// is sorted by highest seniority first. therefore the next available employee will have the highest seniority or will be
	// tied in ranking with the next employee
	public boolean seniorityCheck(int empIndex){
		//int maxHoursFound = 0;
		//int employeeIdMaxSeniorityFound = 0;
		return false;
	}
	
	// will check previous start time of employee and make sure the next available shift is 11 hours or greater
	public boolean startTimeCheck(int empIndex){
		return false;		
	}
	
	// will check if employee has met 73 hours of work in the pay period or less, lest the employee need to be paid for overtime at a higher rate of pay	// 
	public boolean overTimeCheck(Employee e){
		if(e.getHoursWorkedThisPayPeriod() >= 73) {	// in this system, all shifts are 8 hours so one additional shift will put them into 81 hours, one hour of overtime pay, which we will try to avoid
			//System.out.println("Employee : " + e.getEmployeeName() + " has reached over time.");
			return false;	
			
		}
		return true;	// here I use true to indicate that they have "passed" the test, and are a candidate for a shift
	}
	
	public boolean daysConsecutivelyWorkedCheck(Employee e){
		if(e.getDaysConsecutivelyWorked() < 5) {			
			return true;	// hard-coding in 5 consecutive days, after which they'll not be scheduled to recouperate			
		}
		else {
			//System.out.println("Employee: " + e.getEmployeeName() + " has worked 5 days and will get a day off now.");
			return false;
		}		
	}
	
	// will check if employee has the necessary training for the shift that they are next in line for
	public boolean trainingCheck(Employee e, Shift s){
		if(s.getRequiredTraining().equals("P")) {
			//System.out.println("This shift being filled requires Pool training");
			if(e.getTrainingPool()) {
				//System.out.println("Employee is trained for Pool shifts and is eligible");
				return true;				
			}
		}
		else if(s.getRequiredTraining().equals("L")) {
			if(e.getTrainingLinen()) {
				//System.out.println("Employee is trained for Linen shifts and is eligible");
				return true;
				
			}
		}
		else if(s.getRequiredTraining().equals("D")) {
			if(e.getTrainingDock()) {
				//System.out.println("Employee is trained for Dock shifts and is eligible");
				return true;
			}
		}
		else if(s.getRequiredTraining().equals("M")) {
			if(e.getTrainingMdr()) {
				//System.out.println("Employee is trained for MDR shifts and is eligible");
				return true;
			}
		}
		//System.out.println("Employee " + e.getFirstName() + " is not trained for the shift and is ineligible");
		return false;
	}
	
	// the purpose of this method is to check the employee list for available employees to fill an empty shift manually
	// this is done if the auto-scheduler doesn't fill a shift, or an employee is sick and thus a shift has become unexpectedly unfilled
	public ObservableList<Employee> getAvailableEmployees(Shift shiftToFill, int dayNum){	//LocalDate dateOfShift){
		ObservableList<Employee> availableEmployees = FXCollections.observableArrayList();
		availableEmployees.clear();
		
		for(int j = 0; j < employees.size(); j++) {		
			if(trainingCheck(employees.get(j), shiftToFill) && notScheduled(dayNum, shiftToFill, employees.get(j)) && overTimeCheck(employees.get(j)) && daysConsecutivelyWorkedCheck(employees.get(j)) && sickCheck(employees.get(j)) ) {				
				availableEmployees.add(employees.get(j));	// add employee to the list that will be returned
				//System.out.println("Available employee found: + " + employees.get(j).firstName + " " + employees.get(j).lastName );
			}
			else {
				//System.out.println(employees.get(j).getEmployeeName() + " is unavailable for some reason and won't be listed." );
			}
		}		
		
		return availableEmployees;
	}
}
