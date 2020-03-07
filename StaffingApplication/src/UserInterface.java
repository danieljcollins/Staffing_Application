import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.*;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.LocalTime;

/* This file will build the user interface using JavaFX 
 */
public class UserInterface extends Application{	
	
    ObservableList<Employee> employeeData = FXCollections.observableArrayList(); // will contain DB data	
	ObservableList<Shift> shiftData = FXCollections.observableArrayList(); // will contain DB data
	
	// GUI components
	TableView<Shift> scheduleTableView;	// = new TableView<Shift>(shiftData);
	TableView<Shift> shiftTableView;
	TableView<Employee> employeeTableView;
	
	Tab employeeTab;
	Tab scheduleTab = new Tab();	// create a tab for schedule table
	
	// schedule tools tab
	DatePicker datePicker = new DatePicker();
	Button generateScheduleButton = new Button("Generate Schedule");
	
	// schedule Tab specific	
	TableColumn employeeNameDay1Col;
	TableColumn employeeNameDay2Col;
	TableColumn employeeNameDay3Col;
	TableColumn employeeNameDay4Col;
	TableColumn employeeNameDay5Col;
	TableColumn employeeNameDay6Col;
	TableColumn employeeNameDay7Col;
	TableColumn employeeNameDay8Col;
	TableColumn employeeNameDay9Col;
	TableColumn employeeNameDay10Col;
	TableColumn employeeNameDay11Col;
	TableColumn employeeNameDay12Col;
	TableColumn employeeNameDay13Col;
	TableColumn employeeNameDay14Col;
	
	Button exportToPDFButton, saveToDBButton, importFromXMLButton, exportToXMLButton;
                            	
	public UserInterface(){
		DBShiftQuery dbsq = new DBShiftQuery();
		this.shiftData = dbsq.getShiftData();
		
		DBEmployeeQuery dbeq = new DBEmployeeQuery();
		this.employeeData = dbeq.getEmployeeData();		
	} //ends constructor
	
	@Override
    public void start(Stage stage) {
		//Scene scene = new Scene(new Group());
        stage.setTitle("Automated Staffing Solution");
        stage.setWidth(800);
        stage.setHeight(600);
        
        // populate Schedule Tab with shifts assigned to employees based on business logic imposed on each employee's data        
        ScheduleBuilder sb = new ScheduleBuilder(shiftData, employeeData);
        sb.buildSchedule();
        shiftData = sb.getShiftObjects();
                
		// shifts table setup
		shiftTableView = new TableView<Shift>(shiftData);
		
		TableColumn shiftNameCol = new TableColumn("Shift Name");
		shiftNameCol.setMinWidth(100);
        shiftNameCol.setCellValueFactory(new PropertyValueFactory<Shift, String>("shiftName"));
        
        TableColumn shiftStartTimeCol = new TableColumn("Start Time");
        shiftStartTimeCol.setMinWidth(100);
        shiftStartTimeCol.setCellValueFactory(new PropertyValueFactory<Shift, LocalTime>("startTime"));
        
        TableColumn shiftEndTimeCol = new TableColumn("End Time");
        shiftEndTimeCol.setMinWidth(100);
        shiftEndTimeCol.setCellValueFactory(new PropertyValueFactory<Shift, LocalTime>("endTime"));
        
        shiftTableView.setItems(shiftData);
        shiftTableView.getColumns().addAll(shiftNameCol, shiftStartTimeCol, shiftEndTimeCol);        
        
        // create a tab pane for the shift table
        TabPane dataTabPane = new TabPane();
        Tab shiftTab = new Tab();
        shiftTab.setText("Shifts");
        shiftTab.setContent(shiftTableView);
        dataTabPane.getTabs().add(shiftTab);
        
        // employee table setup
 		employeeTableView = new TableView<Employee>(employeeData);
 		
 		TableColumn employeeIDCol = new TableColumn("Employee ID");
 		employeeIDCol.setMinWidth(100);
 		employeeIDCol.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeID"));
 		
 		TableColumn employeeFirstNameCol = new TableColumn("First Name");
 		employeeFirstNameCol.setMinWidth(100);
 		employeeFirstNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
         
        TableColumn employeeLastNameCol = new TableColumn("Last Name");
        employeeLastNameCol.setMinWidth(100);
        employeeLastNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        
        TableColumn employeeHoursTotalCol = new TableColumn("Seniority(Hours)");
        employeeHoursTotalCol.setMinWidth(150);
        employeeHoursTotalCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("hoursWorkedTotal"));
        
        TableColumn employeeSickDaysCol = new TableColumn("Sick Days");
        employeeSickDaysCol.setMinWidth((150));
        employeeSickDaysCol.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("sickDays"));
        
        TableColumn employeeEmailCol = new TableColumn("Email");
        employeeEmailCol.setMinWidth(150);
        employeeEmailCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
        
        TableColumn employeePoolTrainingCol = new TableColumn("Pool Training");
        employeePoolTrainingCol.setMinWidth(150);
        employeePoolTrainingCol.setCellValueFactory(new PropertyValueFactory<Employee, Boolean>("pool"));
        
        TableColumn employeeLinenTrainingCol = new TableColumn("Linen Training");
        employeeLinenTrainingCol.setMinWidth(150);
        employeeLinenTrainingCol.setCellValueFactory(new PropertyValueFactory<Employee, Boolean>("linen"));
        
        TableColumn employeeMDRTrainingCol = new TableColumn("MDR Training");
        employeeMDRTrainingCol.setMinWidth(150);
        employeeMDRTrainingCol.setCellValueFactory(new PropertyValueFactory<Employee, Boolean>("mdr"));
        
        TableColumn employeeDockTrainingCol = new TableColumn("Dock Training");
        employeeDockTrainingCol.setMinWidth(150);
        employeeDockTrainingCol.setCellValueFactory(new PropertyValueFactory<Employee, Boolean>("dock"));
         
        employeeTableView.setItems(employeeData);
        employeeTableView.getColumns().addAll(employeeIDCol, employeeFirstNameCol, employeeLastNameCol, employeeHoursTotalCol, employeeEmailCol, employeeSickDaysCol, employeePoolTrainingCol, employeeLinenTrainingCol, employeeMDRTrainingCol, employeeDockTrainingCol);        
        
        // create a tab for employee table        
        employeeTab = new Tab();
        employeeTab.setText("Employee");        
        employeeTab.setContent(employeeTableView);
        dataTabPane.getTabs().add(employeeTab);
        
		 // set up a user controls tab to generate schedule
		Tab scheduleToolsTab = new Tab("Schedule Tools");
		GridPane scheduleToolsGP = new GridPane();
		scheduleToolsGP.setHgap(25);
		scheduleToolsGP.setVgap(25);
		scheduleToolsGP.setPadding(new Insets(25,25,25,25));
		
		Label datePickerLabel = new Label("Select first day of pay period to generate a schedule:");
		datePicker = new DatePicker();
		generateScheduleButton = new Button("Generate Schedule");
		
		scheduleToolsGP.add(datePickerLabel, 0, 0);
		scheduleToolsGP.add(datePicker, 1, 0);
		scheduleToolsGP.add(generateScheduleButton, 0, 2);		
		
		scheduleToolsTab.setContent(scheduleToolsGP);
		dataTabPane.getTabs().add(scheduleToolsTab);	
        
        // schedule table setup
		TableColumn scheduleShiftNameCol = new TableColumn("Shift Name");
		scheduleShiftNameCol.setMinWidth(100);
		scheduleShiftNameCol.setCellValueFactory(new PropertyValueFactory<Shift, String>("shiftName"));		
		
		employeeNameDay1Col = new TableColumn("Day 1");
		employeeNameDay1Col.setMinWidth(150);
		employeeNameDay1Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay1"));		
				
		employeeNameDay2Col = new TableColumn("Day 2");
		employeeNameDay2Col.setMinWidth(150);
		employeeNameDay2Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay2"));
		
		employeeNameDay3Col = new TableColumn("Day 3");
		employeeNameDay3Col.setMinWidth(150);
		employeeNameDay3Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay3"));		
				
		employeeNameDay4Col = new TableColumn("Day 4");
		employeeNameDay4Col.setMinWidth(150);
		employeeNameDay4Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay4"));
		
		employeeNameDay5Col = new TableColumn("Day 5");
		employeeNameDay5Col.setMinWidth(150);
		employeeNameDay5Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay5"));		
				
		employeeNameDay6Col = new TableColumn("Day 6");
		employeeNameDay6Col.setMinWidth(150);
		employeeNameDay6Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay6"));
		
		employeeNameDay7Col = new TableColumn("Day 7");
		employeeNameDay7Col.setMinWidth(150);
		employeeNameDay7Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay7"));		
				
		employeeNameDay8Col = new TableColumn("Day 8");
		employeeNameDay8Col.setMinWidth(150);
		employeeNameDay8Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay8"));
		
		employeeNameDay9Col = new TableColumn("Day 9");
		employeeNameDay9Col.setMinWidth(150);
		employeeNameDay9Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay9"));		
				
		employeeNameDay10Col = new TableColumn("Day 10");
		employeeNameDay10Col.setMinWidth(150);
		employeeNameDay10Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay10"));
		
		employeeNameDay11Col = new TableColumn("Day 11");
		employeeNameDay11Col.setMinWidth(150);
		employeeNameDay11Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay11"));		
				
		employeeNameDay12Col = new TableColumn("Day 12");
		employeeNameDay12Col.setMinWidth(150);
		employeeNameDay12Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay12"));
		
		employeeNameDay13Col = new TableColumn("Day 13");
		employeeNameDay13Col.setMinWidth(150);
		employeeNameDay13Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay13"));
		
		employeeNameDay14Col = new TableColumn("Day 14");
		employeeNameDay14Col.setMinWidth(150);
		employeeNameDay14Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay14"));
		
		scheduleTableView = new TableView<Shift>(shiftData);
		//scheduleTableView.setContent(shiftData);		
		scheduleTableView.getColumns().addAll(
				scheduleShiftNameCol, 
				employeeNameDay1Col, employeeNameDay2Col,
				employeeNameDay3Col, employeeNameDay4Col,
				employeeNameDay5Col, employeeNameDay6Col,
				employeeNameDay7Col, employeeNameDay8Col,
				employeeNameDay9Col, employeeNameDay10Col,
				employeeNameDay11Col, employeeNameDay12Col,
				employeeNameDay13Col, employeeNameDay14Col
		);			
		
		// add schedule table tab
		//Tab scheduleTab = new Tab();	// create a tab for schedule table
		scheduleTab.setText("Schedule");
		
		exportToPDFButton = new Button();
		saveToDBButton = new Button();
		importFromXMLButton = new Button();
		exportToXMLButton = new Button();
		
		exportToPDFButton.setText("Export to PDF");
		saveToDBButton.setText("Save to Database");
		importFromXMLButton.setText("Import schedule from XML file");
		exportToXMLButton.setText("Export Schedule to XML file");		
		
		VBox scheduleTabVBox = new VBox();
		scheduleTabVBox.getChildren().add(scheduleTableView);
		
		HBox scheduleTabHBox = new HBox();		
		scheduleTabHBox.setPadding(new Insets(25, 25, 25, 25));
		scheduleTabHBox.getChildren().addAll(exportToPDFButton, saveToDBButton, importFromXMLButton, exportToXMLButton);
		scheduleTabVBox.getChildren().add(scheduleTabHBox);	// add the HBox to the VBox
		
		scheduleTab.setContent(scheduleTabVBox);		
		
		dataTabPane.getTabs().add(scheduleTab);
		
		// make ScheduleTab un-clickable until date range is set
		scheduleTab.setDisable(true);
		
		// disable the ability for the user to close any tabs
		shiftTab.closableProperty().setValue(false);
		employeeTab.closableProperty().setValue(false);
		scheduleToolsTab.closableProperty().setValue(false);
		scheduleTab.closableProperty().setValue(false);
		
		BorderPane borderPane = new BorderPane();	

		borderPane.setCenter(dataTabPane);
		
		Scene scene = new Scene(borderPane);
 
        stage.setScene(scene);
        stage.show();
        
        handleEvents();	// run an initialization method to set up mouse events etc.
        handleScheduleControls();
    } // ends start()
	
	// this method will set up things like mouse events, button clicks, etc in it's own method
	public void handleEvents() {        
        
        // the purpose of this is to label the columns of the scheduleTable with the appropriate dates.
        // once the starting day is selected, the program can then name all subsequent columns according
        // to the date of those shifts.
        // then the schedule will appear.
        generateScheduleButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent event) {
        		LocalDate selectedDate = datePicker.getValue();	// get the user selected date so that it can be used to appropriately label the columns
        		
        		// since a 14 day pay-period is contiguous I can infer the subsequent dates and their labels from the user selected date
        		employeeNameDay1Col.setText(selectedDate.toString());
        		employeeNameDay2Col.setText(selectedDate.plusDays(1).toString());
        		employeeNameDay3Col.setText(selectedDate.plusDays(2).toString());
        		employeeNameDay4Col.setText(selectedDate.plusDays(3).toString());
        		employeeNameDay5Col.setText(selectedDate.plusDays(4).toString());
        		employeeNameDay6Col.setText(selectedDate.plusDays(5).toString());
        		employeeNameDay7Col.setText(selectedDate.plusDays(6).toString());
        		employeeNameDay8Col.setText(selectedDate.plusDays(7).toString());
        		employeeNameDay9Col.setText(selectedDate.plusDays(8).toString());
        		employeeNameDay10Col.setText(selectedDate.plusDays(9).toString());
        		employeeNameDay11Col.setText(selectedDate.plusDays(10).toString());
        		employeeNameDay12Col.setText(selectedDate.plusDays(11).toString());
        		employeeNameDay13Col.setText(selectedDate.plusDays(12).toString());
        		employeeNameDay14Col.setText(selectedDate.plusDays(13).toString());        		
        		
        		// enable the Schedule tab now that the dates have been set
        		scheduleTab.setDisable(false);
        	}        	
        }));        
	} // ends handleEvents()
	
	public void handleScheduleControls(){
		// Creating the mouse event handler for clicking cells in scheduleTable
		// the purpose of this handler is to allow the user to select an employee name on a particular shift (date, shift name)
		// and then interact with that in various business ways. one example being to allow a user to
		// indicate that the assigned employee is sick and will be unable to work that day
		// another example would be to swap two employees's shifts for that day
        EventHandler<javafx.scene.input.MouseEvent> eventHandler = 
           new EventHandler<javafx.scene.input.MouseEvent>() { 
           
           @Override 
           public void handle(javafx.scene.input.MouseEvent e) {
              scheduleTableView.getSelectionModel().setCellSelectionEnabled(true);
              if(e.getClickCount() == 2) {
            	  
            	  TableColumn tableColumnSelected = scheduleTableView.getSelectionModel().getSelectedCells().get(0).getTableColumn(); // the name of the column that was selected              
	        	  int tableRowSelected = scheduleTableView.getSelectionModel().getSelectedCells().get(0).getRow(); // the index of the row that was selected
	        	  	        	  
	        	  // determine if the shift is empty and needs to be assigned, or whether the ScheduleControls.java needs to load
	        	  // place code here
	        	  
	        	  Shift shiftSelected = shiftData.get(tableRowSelected);
	        	  
	        	  String dateSelected = tableColumnSelected.getText();
	        	  int dayNumber = scheduleTableView.getSelectionModel().getSelectedCells().get(0).getColumn()-1;	// subtract 1 from index to account for the column of shift names in Schedule tab's shiftData TableView	        	  
	        	  	        	  
	        	  String empName = (String)tableColumnSelected.getCellData(tableRowSelected);
	        	  	        	  
	        	  if( empName.equals("") ){	// there's no employee assigned, so I'll open options to fill that shift with an employee
	        		  System.out.println("Shift not assigned to this location");
	        	  }
	        	  else {	// there's an employee in that spot so I'll open ScheduleControls.java	        	  	        	  
		        	  int midIndex = empName.indexOf(" ");
		        	  String firstName = empName.substring(0, midIndex);
		        	  String lastName = empName.substring(midIndex+1, empName.length());	        	  
		        	  
		        	  for(int i = 0; i < employeeData.size(); i++) {
		        		  if( employeeData.get(i).getFirstName().equals(firstName) && employeeData.get(i).getLastName().equals(lastName) ){
		        			  ScheduleControls sC = new ScheduleControls( firstName, lastName, employeeData.get(i), employeeTableView, scheduleTableView, dateSelected, dayNumber, shiftData, shiftSelected );
		    	        	  sC.showSheduleControls();
		    	        	  //shiftData = sC.getShiftData(); 	// shiftData is passed by reference	    	        	  
		    	        	  scheduleTableView.refresh();	    	        	  
		        		  }
		        	  }
	        	  }
              }
           } 
        };
        scheduleTableView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandler);        //Adding the event handler      	
	} // ends handleScheduleControls()
    
    public static void main(String[] args){    	
    	Application.launch(args);
    }	
} // ends class

// for sorting employeeData by hours worked total, as shifts are given to highest seniority employees first
class SortByHoursWorked implements Comparator<Employee> {
	public int compare(Employee a, Employee b) {
		return b.hoursWorkedTotal - a.hoursWorkedTotal;
	}		
}
