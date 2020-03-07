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
import javafx.util.Callback;


// The purpose of this class is to open a small window that allows the user to view and change aspects of the employee
// that they selected in the Schedule tab. One example would be to change the scheduled employee's status to sick and 
// then open controls which will help the user pick an employee to call in order to fill that empty shift.
// After which, the employee's sick time will be incremented, and the Schedule will be changed to reflect the changes made.
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
	
	ObservableList<Shift> shiftData;
	Shift shiftSelected;	
	
	public ScheduleControls(String fName, String lName, Employee emp, TableView empTableView, TableView schTableView, String dayString, int dayNum, ObservableList<Shift> shData, Shift shiftSel) {
		this.firstName = fName;
		this.lastName = lName;
		this.employee = emp;
		this.employeeTableView = empTableView;
		this.scheduleTableView = schTableView;
		this.dateString = dayString;
		this.dayNumber = dayNum;
		this.shiftData = shData;
		this.shiftSelected = shiftSel;
		System.out.println("Shift Selected sent to Schedule Controls: " + shiftSelected.shiftID);
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
				 
        stage.setScene(scene);
        stage.show();
        
        userEvents();
	}
	
	public void userEvents() {
		// this will add sick day(s) to the selected employee
		sickDayButton.setOnMouseClicked((new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {				
				employee.addSickDays(Integer.parseInt( sickDayTF.getText()) );	// add user input error checking code later				
				employeeTableView.refresh();
				
				// remove employee name from schedule
				shiftData.get(shiftSelected.shiftID).setEmployeeName(dayNumber, "");
				scheduleTableView.refresh();
				
				stage.close();
			}
		}));
		
		// this sets up the shift redeployment ChoiceBox
		// This listened isn't necessary, I can remove this
		//ChangeListener redeployCBListener = new RedeployChoiceBoxEventListener();	// REMOVE
		//redeployCB.getSelectionModel().selectedItemProperty().addListener(redeployCBListener); //REMOVE
		
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
	}	
} // ends class