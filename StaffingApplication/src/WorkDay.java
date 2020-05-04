/* WorkDay.java
 * Author: Daniel Collins 
 * Date: May 2020
 * Purpose: This file isn't currently being used, but may be used in the future to contain Schedule data

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WorkDay {
	
	ObservableList<Shift> shifts = FXCollections.observableArrayList();
	ObservableList<Employee> employees = FXCollections.observableArrayList();
		
	ObservableList<Shift> shiftDataDay1 = FXCollections.observableArrayList();
	ObservableList<Shift> shiftDataDay2 = FXCollections.observableArrayList();
	ObservableList<Shift> shiftDataDay3 = FXCollections.observableArrayList();
	ObservableList<Shift> shiftDataDay4 = FXCollections.observableArrayList();
	ObservableList<Shift> shiftDataDay5 = FXCollections.observableArrayList();
	
	ObservableList<Shift> shiftDataDay6 = FXCollections.observableArrayList();
	ObservableList<Shift> shiftDataDay7 = FXCollections.observableArrayList();
	ObservableList<Shift> shiftDataDay8 = FXCollections.observableArrayList();
	ObservableList<Shift> shiftDataDay9 = FXCollections.observableArrayList();
	ObservableList<Shift> shiftDataDay10 = FXCollections.observableArrayList();
	
	ObservableList<Shift> shiftDataDay11 = FXCollections.observableArrayList();
	ObservableList<Shift> shiftDataDay12 = FXCollections.observableArrayList();
	ObservableList<Shift> shiftDataDay13 = FXCollections.observableArrayList();
	ObservableList<Shift> shiftDataDay14 = FXCollections.observableArrayList();
	
	
	// constructor
	public WorkDay(ObservableList<Shift> sObjects, ObservableList<Employee> eObjects) {
		this.shifts = sObjects;
		this.employees = eObjects;
		
		// populate Schedule Tab with shifts assigned to employees based on business logic imposed on each employee's data
        ScheduleBuilder sb = new ScheduleBuilder(shifts, employees);
        sb.buildSchedule();
        shiftDataDay1 = sb.getShiftObjects();
        shiftDataDay2 = sb.getShiftObjects();        
	}
	
	// get methods	
	public ObservableList<Shift> getShiftDataDay1(){
		return this.shiftDataDay1;
	}
	
	public ObservableList<Shift> getShiftDataDay2(){
		return this.shiftDataDay2;
	}
	
	// set methods		
	public void setShiftDataDay1(ObservableList<Shift> sd) {
		this.shiftDataDay1 = sd;
	}
	
	public void setShiftDataDay2(ObservableList<Shift> sd) {
		this.shiftDataDay2 = sd;
	}

}

*/
