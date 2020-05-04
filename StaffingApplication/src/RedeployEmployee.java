/* RedeployChoiceBoxEventListener.java
 * Author: Daniel Collins 
 * Date: May 2020
 * Purpose: Being called from ScheduleControls.java (typically), this will fire from a JavaFX button.
 * It will take the user selected item from the ChoiceBox, and then determine the selected employee and the shift
 * that was selected (at the same time from Schedule tabe of UserInterface.java)
 * and then switch those two employees shifts so they are assigned the opposite shifts.
 */

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class RedeployEmployee {
	ObservableList<Shift> shifts;
	ObservableList<Employee> employees;	
	
	public RedeployEmployee(ObservableList<Shift> sList, ObservableList<Employee> eList) {
		this.shifts = sList;
		this.employees = eList;
	}
	
	public void switchShifts() {
		
	}
}
