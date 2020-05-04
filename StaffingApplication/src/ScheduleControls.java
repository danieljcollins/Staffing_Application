/* ScheduleControls.java
 * Author: Daniel Collins 
 * Date: May 2020
 * Purpose: The purpose of this class is to open a small window that allows the user to view and change aspects of the employee
 * that they selected in the Schedule tab. One example would be to change the scheduled employee's status to sick and 
 * then open controls which will help the user pick an employee to call in order to fill that empty shift.
 * After which, the employee's sick time will be incremented, and the Schedule will be changed to reflect the changes made.
 */

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.*;
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
//import javafx.util.Callback;

public class ScheduleControls{
	
	// UI
	Stage stage;
	
	String firstName, lastName;
	Employee employee;
	
	TableView employeeTableView;
	TableView scheduleTableView;
	
	String dateString = "";
	int dayNumber;
	
	Button sickDayButton;
	TextField sickDayTF;
	
	Button extendShiftButton;
	TextField extendShiftTF;
	
	Button redeployButton;
	ChoiceBox redeployCB;
	
	Button unassignShiftButton;	
	
	ObservableList<Shift> shiftData;
	Shift shiftSelected;
	
	ObservableList<Employee> availableEmployeeData;
	ObservableList<Employee> employeeData;
	
	// these two components will display available employees that can fill empty shifts
	TableView availableEmployeeTable = new TableView<Employee>();	
	
	public ScheduleControls(String fName, String lName, Employee emp, TableView empTableView, TableView schTableView, String dayString, int dayNum, ObservableList<Shift> shData, Shift shiftSel, ObservableList<Employee> empData) {
		this.firstName = fName;
		this.lastName = lName;
		this.employee = emp;
		this.employeeTableView = empTableView;
		this.scheduleTableView = schTableView;
		this.dateString = dayString;
		this.dayNumber = dayNum;
		this.shiftData = shData;
		this.shiftSelected = shiftSel;
		this.employeeData = empData;				
	}
	
	public ObservableList<Shift> getShiftData(){
		return this.shiftData;
	}
	
	public void showSheduleControls() {
		stage = new Stage();
		stage.setTitle("Schedule Controls");
		
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(300, 300);
		gridPane.setHgap(25);
		gridPane.setVgap(25);
		gridPane.setPadding(new Insets(25,25,25,25));
		
		Scene scene = new Scene(gridPane);
		
		Label employeeNameLabelTitle = new Label("Employee Name: ");
		Label employeeNameLabel = new Label(firstName + " " + lastName);
		
		Label dateLabel = new Label("Date:");
		Label dateLabelData = new Label(dateString);
		
		Label shiftLabel = new Label("Shift:");
		Label shiftLabelData = new Label( shiftSelected.getShiftName() );	//("Empty Value");		
		
		sickDayButton = new Button("Add sick day(s)");
		sickDayTF = new TextField("1");
		sickDayTF.setMinWidth(25);
				
		redeployButton = new Button("Redeploy");	// switches the currently assigned shift with a different shift that day
						
		// list of shifts to populate choice box, derived from shiftData which came from MySQL database
		ObservableList<String> shiftCBList = FXCollections.observableArrayList();
		for(int i = 0; i < shiftData.size(); i++){
			shiftCBList.add(shiftData.get(i).shiftName);			
		}
		redeployCB = new ChoiceBox(shiftCBList);
		
		extendShiftButton = new Button("Extend Shift");
		extendShiftTF = new TextField("1");
		extendShiftTF.setMinWidth(25);
		
		unassignShiftButton = new Button("Unassign Shift");		
		
		// set up Table of employees that are available to fill empty shifts
		Label availableEmployeeTableLabel = new Label("Available Employees");
		availableEmployeeTableLabel.setPadding(new Insets(25, 25, 5, 25));
		availableEmployeeTableLabel.setFont(new Font("Default", 24));
		availableEmployeeTable = new TableView<Employee>(availableEmployeeData);		
		availableEmployeeTable.setMaxHeight(200);
		TableColumn employeeFirstNameCol = new TableColumn("First Name");
		TableColumn employeeLastNameCol = new TableColumn("Last Name");		
		
		employeeFirstNameCol.setMinWidth(100);
		employeeFirstNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
		employeeLastNameCol.setMinWidth(100);
		employeeLastNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
		availableEmployeeTable.getColumns().addAll(employeeFirstNameCol, employeeLastNameCol);
		availableEmployeeTable.setVisible(false);
				
		gridPane.add(dateLabel, 0, 0);
		gridPane.add(dateLabelData, 1, 0);
		
		gridPane.add(shiftLabel, 0, 1);
		gridPane.add(shiftLabelData, 1, 1);
		
		gridPane.add(employeeNameLabelTitle, 0, 2);
		gridPane.add(employeeNameLabel, 1, 2);
		
		gridPane.add(sickDayButton, 0, 3);
		gridPane.add(sickDayTF, 1, 3);
		
		gridPane.add(redeployButton, 0, 4);
		gridPane.add(redeployCB, 1, 4);		
		
		gridPane.add(extendShiftButton, 0, 5);
		gridPane.add(extendShiftTF, 1, 5);
		
		gridPane.add(unassignShiftButton, 0, 6);
				
		gridPane.add(availableEmployeeTableLabel, 0, 7);
		gridPane.add(availableEmployeeTable, 0, 8);
				 
        stage.setScene(scene);
        stage.show();
        
        userEvents();
	}
	
	// This method sets up the various user interface component listeners to reduce code clutter in the constructor method
	public void userEvents() {
		// this will add sick day(s) to the selected employee and set their current status to sick so they can't be auto-scheduled until their status is set back to not sick
		sickDayButton.setOnMouseClicked((new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {				
				employee.addSickDays(Integer.parseInt( sickDayTF.getText()) );	// add user input error checking code later
				employee.setSickStatus(true);
				employeeTableView.refresh();
				
				// remove employee name from schedule
				shiftData.get(shiftSelected.shiftID).setEmployeeName(dayNumber, "");
				scheduleTableView.refresh();
				
				
				stage.close();
			}
		}));
		
		// this takes the redeploy choiceBox selection and assigns the selected shift(ChoiceBox) to the selected 
		// employee (Schedule tab). If there's an employee assigned to the selected shift, it will switch.
		// if there isn't, the old shift will be empty and the selected shift will be assigned to the selected 
		// employee.
		redeployButton.setOnAction((new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {				
				// pseudo code:
				// check if ChoiceBox has been selected (if blank, error message or nothing happens)
				// if selected, swap info in shifts ObservableList between the two				
				if(redeployCB.getValue() != null) {					
					Shift shift1 = shiftData.get(shiftSelected.shiftID);
					int shift1Index = shiftData.get(shiftSelected.shiftID).shiftID;
					
					Shift shift2;
					int shift2Index;					
					
					// find the matching shift between the ChoiceBox selection and an actual Shift object
					// and then swap the employee names on the schedule (shiftData object)
					
					for(int shift = 0; shift < shiftData.size(); shift++) {						
						if(shiftData.get(shift).shiftName.equals( redeployCB.getValue() ) && shiftData.get(shift).date.equals(shiftSelected.date) ) {							
							shift2 = shiftData.get(shift);							
							shift2Index = shift;
							
							String tempName = shiftData.get(shift2Index).getEmployeeName(dayNumber);
														
							shiftData.get(shift2Index).setEmployeeName(dayNumber, shiftData.get(shift1Index).getEmployeeName(dayNumber) );	//shift1.getEmployeeName(i));
							shiftData.get(shift1Index).setEmployeeName(dayNumber, tempName);
							
							scheduleTableView.refresh();
							
							stage.close();
						}
					} // ends for shift = 0 loop				
				}				
			}
		}));	// ends redeployButton.setOnAction()
		
		// this will unassign an employee from their assigned shift and return them to the available employee pool
		unassignShiftButton.setOnAction((new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Shift shift1 = shiftData.get(shiftSelected.shiftID);
				int shift1Index = shiftData.get(shiftSelected.shiftID).shiftID;
								
				// find the matching shift between the selected shift (when they entered ScheduleControls.java) and an actual Shift object
				// and then swap the employee names on the schedule (shiftData object)							
				for(int shift = 0; shift < shiftData.size(); shift++) {
					if(shiftData.get(shift).shiftName.equals( shiftSelected.shiftName ) && shiftData.get(shift).date.equals(shiftSelected.date) ) {
						shiftData.get(shift1Index).setEmployeeName(dayNumber, "");	// clear the schedule of the employee name to make way for a new employee to be assigned later						
						// revert the changes made when the employee was scheduled						
						employee.hoursWorkedThisPayPeriod -= 8;
						employee.hoursWorkedTotal -= 8;
						employee.daysConsecutivelyWorked -= 1;	// design bug: note, when a shift is unassigned it currently breaks the 5 day consecutive work logic. keep this in mind so that it can be redesigned later (see: TODOLIST.txt)
										
						scheduleTableView.refresh();
						
						stage.close();
					}
				} // ends for shift = 0 loop
			}
		}));	// ends unassignButton.setOnAction()
		
		// this event handler will allow the user to click on availableEmployeeTable, select an employee from the list which will then populate that employee name
		// in UserInterface.java's scheduleTableView in the Schedule tab
		EventHandler<javafx.scene.input.MouseEvent> availableEmployeeTableEventHandler = new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
		    public void handle(javafx.scene.input.MouseEvent e) {
				availableEmployeeTable.getSelectionModel().setCellSelectionEnabled(true);
		        if(e.getClickCount() == 2) {
		        	Employee employeeSelected = (Employee)availableEmployeeTable.getSelectionModel().getSelectedItem(); // the name of the column that was selected
					//System.out.println("Employee Selected: " + employeeSelected.getEmployeeName());	
									   
					//Shift shift1 = shiftData.get(shiftSelected.shiftID);
					int shift1Index = shiftData.get(shiftSelected.shiftID).shiftID;
									
					// find the matching shift between the selected shift (when they entered ScheduleControls.java) and an actual Shift object
					// and then swap the employee names on the schedule (shiftData object)							
					for(int shift = 0; shift < shiftData.size(); shift++) {
						if(shiftData.get(shift).shiftName.equals( shiftSelected.shiftName ) && shiftData.get(shift).date.equals(shiftSelected.date) ) {
							shiftData.get(shift1Index).setEmployeeName(dayNumber, employeeSelected.getEmployeeName());
											
							scheduleTableView.refresh();
							
							stage.close();
						}
					} // ends for shift = 0 loop
				}
			}
		};	// ends eventHandler() for clicking on availableEmployeeTable
		availableEmployeeTable.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, availableEmployeeTableEventHandler);        //Adding the event handler for mouse clicks
		
	} // ends userEvents()
	
	public void setAvailableEmployees(ObservableList<Employee> availEmp) {
		this.availableEmployeeData = availEmp;
	}
	
	public void showEmployeeList() {
		availableEmployeeTable.setVisible(true);
	}
} // ends class