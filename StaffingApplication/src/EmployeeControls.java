/* EmployeeControls.java
 * Author: Daniel Collins 
 * Date: May 2020
 * Purpose: When the user double-clicks an employee name from the Employee tab of UserInterface.java, this
 * class is called. This class handles a UI Window with several controls which allows the user to interact
 * with the Employee object that was selected. One example is modifying the Employee's training boolean values.
 */

import java.time.LocalTime;
import java.time.LocalDate;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

// EmployeeControls.java
// This class will build a GUI window to allow the user to interact with Employee.java objects
// Features such as:
// adding vacation time
// managing what an employee is trained/authorized to do (ie. what shifts can they work)
// managing their sick status
public class EmployeeControls {
	Stage stage;
	TableView employeeTableView;

	ObservableList<Employee> employeeData = FXCollections.observableArrayList();
	Employee employee;
	String employeeName;
	
	ChoiceBox sickStatusCB;
	
	CheckBox trainingPoolCheckBox;
	CheckBox trainingLinenCheckBox;
	CheckBox trainingMdrCheckBox;
	CheckBox trainingDockCheckBox;	
	
	DatePicker vacationStartDatePicker;
	DatePicker vacationEndDatePicker;
	Button vacationSubmitButton;
	
	TableView vacationBookedTableView;
	
	public EmployeeControls(int empID, ObservableList<Employee> empData, TableView empTableView) {
		this.employeeData = empData;
		this.employee = (Employee)employeeData.get(empID);
		this.employeeName = employee.getEmployeeName();	
		this.employeeTableView = empTableView;	// this is so I can refresh the TableView in UserInterface.java when data is changed from here
	}
	
	public void showEmployeeControls() {
		stage = new Stage();
		stage.setTitle("Employee Controls");
		//stage.setHeight(1600);
		stage.setWidth(900);
		
		GridPane gridPane = new GridPane();				
		gridPane.setHgap(25);
		gridPane.setVgap(25);
		gridPane.setPadding(new Insets(25,25,25,25));
		
		Label employeeNameLabel = new Label("Employee Name: " + employeeName);
		
		// setSickStatus components (true or false controls, probably use a radio button, or a drop down choicebox)
		Label sickStatusLabel = new Label("Sick Status:");
		//set up a list to display in the choice box:
		ObservableList<String> sickStatusList = FXCollections.observableArrayList();
		sickStatusList.add("Not Sick"); //sickStatus = false
		sickStatusList.add("Sick");	// sickStatus = true		
		
		sickStatusCB = new ChoiceBox(sickStatusList);
		
		//set sickStatusCB based on Employee data
		if(employee.getSickStatus() == false) {
			sickStatusCB.getSelectionModel().select(0);	// employee is not sick and set the choicebox to indicate that
		}
		else {
			sickStatusCB.getSelectionModel().select(1);
		}		
		
		// set vacation time components(two calendar components, one for the start date, one for the end date, with a button to confirm the addition)
		
		Label vacactionStartLabel = new Label("Vacation Start Date:");
		Label vacactionEndLabel = new Label("Vacation End Date:");
		vacationStartDatePicker = new DatePicker();
		vacationEndDatePicker = new DatePicker();
		vacationSubmitButton = new Button("Submit Vacation");
		
		Label currentlyBookedVacationLabel = new Label("Currently Booked Vacation:");
		ObservableList<Vacation> vacationBooked = employee.getVacationDateList();
		vacationBookedTableView = new TableView<Vacation>(vacationBooked);		
		TableColumn dateCol = new TableColumn("Date");
		dateCol.setMinWidth(150);
		dateCol.setCellValueFactory( new PropertyValueFactory<Vacation, String>("vacationDateString") );		
		vacationBookedTableView.getColumns().addAll(dateCol);
		
		// set training components (check boxes for each training type is probably sufficient with an update button to confirm the changes)
		
		trainingPoolCheckBox = new CheckBox("Pool");
		trainingLinenCheckBox = new CheckBox("Linen");
		trainingMdrCheckBox = new CheckBox("MDR");
		trainingDockCheckBox = new CheckBox("Dock");
		
		// set the CheckBoxes to indicate the employees current level of training
		if(employee.getTrainingPool() == true) {
			trainingPoolCheckBox.setSelected(true);
		}
		if(employee.getTrainingLinen() == true) {
			trainingLinenCheckBox.setSelected(true);
		}
		if(employee.getTrainingMdr() == true) {
			trainingMdrCheckBox.setSelected(true);
		}
		if(employee.getTrainingDock() == true) {
			trainingDockCheckBox.setSelected(true);
		}
		
		// add UI components to gridPane layout
		gridPane.add(employeeNameLabel, 0, 0);
		
		// sick status UI components
		gridPane.add(sickStatusLabel, 0, 1);
		gridPane.add(sickStatusCB, 1, 1);
		
		// vacation UI components
		gridPane.add(vacactionStartLabel, 0, 2);
		gridPane.add(vacactionEndLabel, 1, 2);
		gridPane.add(vacationStartDatePicker, 0, 3);
		gridPane.add(vacationEndDatePicker, 1, 3);
		gridPane.add(vacationSubmitButton, 0, 4);
		
		gridPane.add(currentlyBookedVacationLabel, 0, 5);
		gridPane.add(vacationBookedTableView, 0, 6);
		
		// training UI components
		HBox trainingHBox = new HBox();		
		//trainingHBox.setPadding(new Insets(0,0,25,25));
		Label trainingLabel = new Label("Training:");
		trainingHBox.getChildren().add(trainingLabel);
		trainingHBox.getChildren().add(trainingPoolCheckBox);
		trainingHBox.getChildren().add(trainingLinenCheckBox);
		trainingHBox.getChildren().add(trainingMdrCheckBox);
		trainingHBox.getChildren().add(trainingDockCheckBox);
		trainingHBox.setMargin(trainingLabel, new Insets(0,25,0,0));
		trainingHBox.setMargin(trainingPoolCheckBox, new Insets(0,25,0,0));
		trainingHBox.setMargin(trainingLinenCheckBox, new Insets(0,25,0,0));
		trainingHBox.setMargin(trainingMdrCheckBox, new Insets(0,25,0,0));
		trainingHBox.setMargin(trainingDockCheckBox, new Insets(0,25,0,0));		
		
		gridPane.add(trainingHBox, 0, 7);		
		
		Scene scene = new Scene(gridPane);
		
		stage.setScene(scene);
        stage.show();
        
        initializeComponentListeners();
	} // ends showEmployeeControls()
	
	// this method will initialize the listeners for JavaFX components and actions performed
	public void initializeComponentListeners() {
		// this listener will modify the Employee.java object to either be sick or not sick based on the ChoiceBox selection
		sickStatusCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number newValue){
				if(newValue.intValue() == 0) {
					employee.setSickStatus(false);
				}
				else {
					employee.setSickStatus(true);
				}
				employeeTableView.refresh();
			}
		});
		
		// this listener will track mouse-clicks on the Vacation Booked table; if the user clicks on a date a dialog will open asking
		// if they would like to cancel the vacation, removing it from the list.
		EventHandler<javafx.scene.input.MouseEvent> vacationBookedTableEventHandler = new EventHandler<javafx.scene.input.MouseEvent>() {
			public void handle(javafx.scene.input.MouseEvent e) {
				vacationBookedTableView.getSelectionModel().setCellSelectionEnabled(true);
				if(e.getClickCount() == 2) {					
					int vacationDateSelected = vacationBookedTableView.getSelectionModel().getSelectedIndex();
					cancelVacationDay(vacationDateSelected);
				}
			}
		};
		vacationBookedTableView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, vacationBookedTableEventHandler);        //Adding the event handler
		
		// this listener will modify the Employee.java object's training variables based on the CheckBox selections
		trainingPoolCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) { employee.setTrainingPool(new_val); employeeTableView.refresh();}
	    });
		
		trainingLinenCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) { employee.setTrainingLinen(new_val); employeeTableView.refresh();}
	    });
		
		trainingMdrCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) { employee.setTrainingMdr(new_val); employeeTableView.refresh();}
	    });
		
		trainingDockCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) { employee.setTrainingDock(new_val); employeeTableView.refresh();}
	    });
		
		// this button will take the LocalDate from each DatePicker component and send it to the Employee.java object, which will then add the LocalDates between those two dates (inclusive) to a list of booked vacation days. They will not be scheduled or available on these dates in other words to ScheduleBuilder.java
		vacationSubmitButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// TODO: Add verification checks on the user input at some point (checking the enddate is chronologically after the start date for example)
				employee.addVacation( vacationStartDatePicker.getValue(), vacationEndDatePicker.getValue() );
				vacationBookedTableView.refresh();
			}
		});
	}
	
	/* Mathod Name: cancelVacationDay()
	 * Purpose: When the user clicks on a date in the booked vacation list, a dialog will open asking the user if they want to cancel that vacation day booking.
	 * The user will be presented with a Yes and No button. 
	 * Selecting Yes will cancel the day and remove it from the Employee object, and refresh the GUI in EmployeeControls.java to reflect the change
	 */
	public void cancelVacationDay(int index) {
		// remove the selected vacation date from the Employee object
		for(int i = 0; i < employeeData.size(); i++) {
			if( employeeData.get(i).getEmployeeName().contains(employeeName)  ) {
				employeeData.get(i).getVacationDateList().remove(index);
			}
		}
	}

}
