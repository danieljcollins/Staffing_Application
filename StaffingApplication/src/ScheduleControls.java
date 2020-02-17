import javafx.application.Application;

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
	
	String firstName, lastName;
	Employee employee;
	TableView employeeTableView;
	String dateString = "";
	
	Button sickDayButton;
	TextField sickDayTF;
	
	Button extendShiftButton;
	TextField extendShiftTF;
	
	Button redeployButton;
	ChoiceBox redeployCB;	
	
	public ScheduleControls(String fName, String lName, Employee emp, TableView empTableView, String day) {
		this.firstName = fName;
		this.lastName = lName;
		this.employee = emp;
		this.employeeTableView = empTableView;
		this.dateString = day;
	}
	
	public void showSheduleControls() {
		Stage stage = new Stage();
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
		
		sickDayButton = new Button("Add sick day(s)");
		sickDayTF = new TextField("1");
		sickDayTF.setMinWidth(25);
				
		redeployButton = new Button("Redeploy");	// switches the currently assigned shift with a different shift that day
		redeployCB = new ChoiceBox();		
		
		extendShiftButton = new Button("Extend Shift");
		extendShiftTF = new TextField("1");
		extendShiftTF.setMinWidth(25);
		
		gridPane.add(dateLabel, 0, 0);
		gridPane.add(dateLabelData, 1, 0);
		
		gridPane.add(employeeNameLabelTitle, 0, 1);
		gridPane.add(employeeNameLabel, 1, 1);
		
		gridPane.add(sickDayButton, 0, 2);
		gridPane.add(sickDayTF, 1, 2);
		
		gridPane.add(redeployButton, 0, 3);
		gridPane.add(redeployCB, 1, 3);		
		
		gridPane.add(extendShiftButton, 0, 4);
		gridPane.add(extendShiftTF, 1, 4);
				 
        stage.setScene(scene);
        stage.show();
        
        buttonEvents();
	}
	
	public void buttonEvents() {
		sickDayButton.setOnMouseClicked((new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				System.out.println("Sick Day(s) Added.");
				employee.addSickDays(Integer.parseInt( sickDayTF.getText()) );	// add user input error checking code later
				employeeTableView.refresh();
			}
		}
		));
	} 
}