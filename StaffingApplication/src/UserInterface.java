// javafx implementation
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.*;
import java.util.ArrayList;
//import java.util

import java.time.LocalTime;

/* This file will build the user interface using JavaFX 
 */
public class UserInterface extends Application{	    
	
	ResultSet rs;
	ResultSetMetaData rsmd;
	int rsSize;
		
	// SQL queries
	private final String queryEmployeeData = "SELECT * from employees;";
    private final String queryShiftData = "SELECT * from shifts;";	
	
    ObservableList<Employee> employeeData = FXCollections.observableArrayList(); // will contain DB data	
	
	ObservableList<Shift> shiftData = FXCollections.observableArrayList(); // will contain DB data	
                            	
	public UserInterface(){
		//System.out.println("Running Constructor!");
		DbManager dbm = new DbManager();
		try{	// to do, move this Database logic to DbManager.java later, or a specific query Db class like "DbQuery.java"(a plan)
			System.out.println("Starting DB Connection...");
			dbm.createConnection();			
			
			/* employeeData will be populated from ResultSet
			 * Here the program will query the database. 
			 * From there, the program will obtain some information about the query results. 
			 * (how many rows, columns etc.)
			 * From there, for convenience ArrayList will be used to take the ResultSet and place the
			 * data into a Java Object that can be conveniently used.			 
			 */
			dbm.runSqlStatement(queryEmployeeData);
			
			rs = dbm.getResultSet();
			rsmd = dbm.getResultSetMetaData();
			
			// re-create the arrays for the JTable now that we have the ResultSet
			int employeeDataColumnCount = rsmd.getColumnCount();
			int employeeDataRowCount = dbm.getRsSize();
			
			// for diag print out
			//System.out.println("data column count employee: " + employeeDataColumnCount);
			//System.out.println("data row count employee: " + employeeDataRowCount);
			
			// populate the string array with the row data from the db result set			
			int empId;
			String empFirstName;
			String empLastName;
			String empEmail;
			int empHoursWorkedTotal;
			String empPool;
			String empMdr;
			String empLinen;
			String empDock;
			
			while(rs.next()){
				empId = Integer.parseInt(rs.getString("id"))-1;	// subtract 1 to convert from sql based id to 0 based for java loops	
				empFirstName = rs.getString("first_name");
				empLastName = rs.getString("last_name");
				empHoursWorkedTotal = Integer.parseInt(rs.getString("hours_worked"));
				/*
				empEmail = rs.getString("email");
				empHoursWorked = rs.getString("hours_worked");
				empPool = rs.getString("pool");
				empMdr = rs.getString("mdr");
				empLinen = rs.getString("linen");
				empDock = rs.getString("dock");
				*/
				
				// now add all of that to an Employee object
				employeeData.add(new Employee(empId, empFirstName, empLastName, empHoursWorkedTotal));				
			}
			
			//employeeData.forEach((n) -> System.out.println(n)); // diag print out				
			
			/* shiftData will be populated from ResultSet
			 * Here the program will query the database. 
			 * From there, the program will obtain some information about the query results. 
			 * (how many rows, columns etc.)
			 * From there, for convenience ArrayList will be used to take the ResultSet and place the
			 * data into a Java Object that can be conveniently used.			
			 */
			dbm.runSqlStatement(queryShiftData);
			
			rs = dbm.getResultSet();
			rsmd = dbm.getResultSetMetaData();		
			
			// diag print out
			//System.out.println("data column count shift: " + rsmd.getColumnCount());
			//System.out.println("data row count shift: " + dbm.getRsSize());
			
			// populate the string array with the row data from the db result set			
			int shiftId;
			String shiftName;
			LocalTime shiftStartTime;
			LocalTime shiftEndTime;
			//String shiftRequiredTraining;
						
			while(rs.next()){
				shiftId = Integer.parseInt(rs.getString("id"))-1;	// subtract 1 to convert from sql based id to 0 based for java loops	
				shiftName = rs.getString("shift_name");
				shiftStartTime = LocalTime.parse(rs.getString("start_time"));				
				shiftEndTime = LocalTime.parse(rs.getString("end_time"));
				//shiftRequiredTraining = rs.getString("required_training");
								
				shiftData.add(new Shift(shiftId, shiftName, shiftStartTime, shiftEndTime));
				//System.out.println("Shift: " + shiftName + " " + shiftStartTime + " " + shiftEndTime);				
			}
			
			//shiftData.forEach((n) -> System.out.println(n)); // diag print out
			
			// create duplicate Shift objects for the remaining days of the pay period (14 total)
			// not doing that strategy for now
			
		} // ends try
		catch(Exception e){
			System.out.println("Error Type: " + e);
		}
	} //ends constructor
	
	@Override
    public void start(Stage stage) {
		//Scene scene = new Scene(new Group());
        stage.setTitle("Automated Staffing Solution");
        stage.setWidth(800);
        stage.setHeight(600);
        
        // populate Schedule Tab with shifts assigned to employees based on business logic imposed on each employee's data
        // this works when scheduling 1 day
        ScheduleBuilder sb = new ScheduleBuilder(shiftData, employeeData);
        sb.buildSchedule();
        shiftData = sb.getShiftObjects();
                
		// shifts table test
		TableView<Shift> shiftTableView = new TableView<Shift>(shiftData);
		
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
        /*
        // create a vbox to place the table       
        VBox shiftVbox = new VBox();
        shiftVbox.setSpacing(5);
        shiftVbox.setPadding(new Insets(10, 0, 0, 10));
        shiftVbox.getChildren().addAll(shiftTableView);
        */
        // create a tab pane for the shift table
        TabPane dataTabPane = new TabPane();
        Tab shiftTab = new Tab();
        shiftTab.setText("Shifts");
        //shiftTab.setContent(shiftVbox);
        shiftTab.setContent(shiftTableView);
        dataTabPane.getTabs().add(shiftTab);
        
        // employee table test
 		TableView<Employee> employeeTableView = new TableView<Employee>(employeeData);
 		
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
         
        employeeTableView.setItems(employeeData);
        employeeTableView.getColumns().addAll(employeeIDCol, employeeFirstNameCol, employeeLastNameCol, employeeHoursTotalCol);        
        /* 
        // create a vbox to place the table       
        VBox employeeVbox = new VBox();
        employeeVbox.setSpacing(5);
        employeeVbox.setPadding(new Insets(10, 0, 0, 10));
        employeeVbox.getChildren().addAll(employeeTableView);
        */ 
        // create a tab for employee table        
        Tab employeeTab = new Tab();
        employeeTab.setText("Employee");
        //employeeTab.setContent(employeeVbox);
        employeeTab.setContent(employeeTableView);
        dataTabPane.getTabs().add(employeeTab);
        
        // schedule table test
		TableView<Shift> scheduleTableView = new TableView<Shift>(shiftData);
        		
		TableColumn scheduleShiftNameCol = new TableColumn("Shift Name");
		scheduleShiftNameCol.setMinWidth(100);
		scheduleShiftNameCol.setCellValueFactory(new PropertyValueFactory<Shift, String>("shiftName"));		
		
		TableColumn employeeNameDay1Col = new TableColumn("Day 1");
		employeeNameDay1Col.setMinWidth(150);
		employeeNameDay1Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay1"));		
				
		TableColumn employeeNameDay2Col = new TableColumn("Day 2");
		employeeNameDay2Col.setMinWidth(150);
		employeeNameDay2Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay2"));
		
		TableColumn employeeNameDay3Col = new TableColumn("Day 3");
		employeeNameDay3Col.setMinWidth(150);
		employeeNameDay3Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay3"));		
				
		TableColumn employeeNameDay4Col = new TableColumn("Day 4");
		employeeNameDay4Col.setMinWidth(150);
		employeeNameDay4Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay4"));
		
		TableColumn employeeNameDay5Col = new TableColumn("Day 5");
		employeeNameDay5Col.setMinWidth(150);
		employeeNameDay5Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay5"));		
				
		TableColumn employeeNameDay6Col = new TableColumn("Day 6");
		employeeNameDay6Col.setMinWidth(150);
		employeeNameDay6Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay6"));
		
		TableColumn employeeNameDay7Col = new TableColumn("Day 7");
		employeeNameDay7Col.setMinWidth(150);
		employeeNameDay7Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay7"));		
				
		TableColumn employeeNameDay8Col = new TableColumn("Day 8");
		employeeNameDay8Col.setMinWidth(150);
		employeeNameDay8Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay8"));
		
		TableColumn employeeNameDay9Col = new TableColumn("Day 9");
		employeeNameDay9Col.setMinWidth(150);
		employeeNameDay9Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay9"));		
				
		TableColumn employeeNameDay10Col = new TableColumn("Day 10");
		employeeNameDay10Col.setMinWidth(150);
		employeeNameDay10Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay10"));
		
		TableColumn employeeNameDay11Col = new TableColumn("Day 11");
		employeeNameDay11Col.setMinWidth(150);
		employeeNameDay11Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay11"));		
				
		TableColumn employeeNameDay12Col = new TableColumn("Day 12");
		employeeNameDay12Col.setMinWidth(150);
		employeeNameDay12Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay12"));
		
		TableColumn employeeNameDay13Col = new TableColumn("Day 13");
		employeeNameDay13Col.setMinWidth(150);
		employeeNameDay13Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay13"));
		
		TableColumn employeeNameDay14Col = new TableColumn("Day 14");
		employeeNameDay14Col.setMinWidth(150);
		employeeNameDay14Col.setCellValueFactory(new PropertyValueFactory<Shift, String>("employeeNameDay14"));
		
		scheduleTableView.setItems(shiftData);		
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
		
		// create a vbox to place the table
		/*
		VBox scheduleVbox = new VBox();
		scheduleVbox.setSpacing(5);
		scheduleVbox.setPadding(new Insets(10, 0, 0, 10));
		scheduleVbox.getChildren().addAll(scheduleTableView);
		*/
		// create a tab for schedule table
		Tab scheduleTab = new Tab();
		scheduleTab.setText("Schedule");
		//scheduleTab.setContent(scheduleVbox);
		scheduleTab.setContent(scheduleTableView);
		dataTabPane.getTabs().add(scheduleTab);   
        
		// previous layout code, likely will be deleted when layout is settled to my preference
        //((Group) scene.getRoot()).getChildren().addAll(dataTabPane);
		
		/*
		// alternate layout code GridPane
		GridPane gridPane = new GridPane();
		
		gridPane.setAlignment(Pos.CENTER);
		gridPane.add(dataTabPane, 0, 0);
		
		Scene scene = new Scene(gridPane);
		*/
		
		// alternate layout code BorderPane
		BorderPane borderPane = new BorderPane();
		//HBox hbox = addHBox()
		//border.setTop(hbox);
		//border.setLeft(addVBox());
		//addStackPane(hbox);         // Add stack to HBox in top region

		borderPane.setCenter(dataTabPane);		
		
		Scene scene = new Scene(borderPane);
 
        stage.setScene(scene);
        stage.show();
    } // ends start()
    
    public static void main(String[] args){    	
    	Application.launch(args);
    }	
} // ends class
