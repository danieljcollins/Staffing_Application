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

// EmployeeControls.java
// This class will build a GUI window to allow the user to interact with Employee.java objects
// Features such as:
// adding vacation time
// managing what an employee is trained/authorized to do (ie. what shifts can they work)
// managing their sick status
public class EmployeeControls {
	Stage stage;
	TableView employeeTableView;

	ObservableList employeeData = FXCollections.observableArrayList();
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
	
	public EmployeeControls(int empID, ObservableList<Employee> empData, TableView empTableView) {
		this.employeeData = empData;
		this.employee = (Employee)employeeData.get(empID);
		this.employeeName = employee.getEmployeeName();	
		this.employeeTableView = empTableView;	// this is so I can refresh the TableView in UserInterface.java when data is changed from here
	}
	
	public void showEmployeeControls() {
		stage = new Stage();
		stage.setTitle("Employee Controls");
		
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(300, 300);
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
		
		// add components to gridPane layout
		
		gridPane.add(employeeNameLabel, 0, 0);
		
		gridPane.add(sickStatusLabel, 0, 1);
		gridPane.add(sickStatusCB, 1, 1);
		
		gridPane.add(vacactionStartLabel, 0, 2);
		gridPane.add(vacactionEndLabel, 1, 2);
		gridPane.add(vacationStartDatePicker, 0, 3);
		gridPane.add(vacationEndDatePicker, 1, 3);
		gridPane.add(vacationSubmitButton, 0, 4);
		
		gridPane.add(trainingPoolCheckBox, 0, 5);
		gridPane.add(trainingLinenCheckBox, 1, 5);
		gridPane.add(trainingMdrCheckBox, 2, 5);
		gridPane.add(trainingDockCheckBox, 3, 5);		
		
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
				employee.addVacation( vacationStartDatePicker.getValue(), vacationEndDatePicker.getValue() );
				
				// test vacation date code
				ArrayList<LocalDate> vacationDates = employee.getVacationDates();
				for(int i = 0; i < vacationDates.size(); i++){	//employee.getVacationDates()){
					System.out.println("Vacation Booked: " + vacationDates.get(i).toString());
				}
			}			
		});
	}

}
