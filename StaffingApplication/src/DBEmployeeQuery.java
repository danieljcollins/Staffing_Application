import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBEmployeeQuery {
	
	ResultSet rs;
	ResultSetMetaData rsmd;
	int rsSize;
	
	private final String queryEmployeeData = "SELECT * from employees;";
	
	ObservableList<Employee> employeeData = FXCollections.observableArrayList(); // will contain DB data
	
	public DBEmployeeQuery() {
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
			int sickDays;
			Boolean empPool;
			Boolean empMdr;
			Boolean empLinen;
			Boolean empDock;
			
			while(rs.next()){
				empId = Integer.parseInt(rs.getString("id"))-1;	// subtract 1 to convert from sql based id to 0 based for java loops	
				empFirstName = rs.getString("first_name");
				empLastName = rs.getString("last_name");
				empHoursWorkedTotal = Integer.parseInt(rs.getString("hours_worked"));
				
				sickDays = Integer.parseInt(rs.getString("sick_days"));
				
				empEmail = rs.getString("email");
				
				String tempPool = rs.getString("pool");
				String tempMdr = rs.getString("mdr");
				String tempLinen = rs.getString("linen");
				String tempDock = rs.getString("dock");
				
				// convert MySQL's boolean which is represented as a 1 or 0 to a Java Boolean, since String.parseBoolean() does not recognize a String of 1 or 0 as true and false respectively
				if(tempPool.contentEquals("1")) {
					empPool = true;
				}
				else {
					empPool = false;
				}
				
				if(tempMdr.contentEquals("1")) {
					empMdr = true;
				}
				else {
					empMdr = false;
				}
				
				if(tempLinen.contentEquals("1")) {
					empLinen = true;
				}
				else {
					empLinen = false;
				}
				
				if(tempDock.contentEquals("1")) {
					empDock = true;
				}
				else {
					empDock = false;
				}								
				
				// now add all of that to an Employee object
				employeeData.add(new Employee(empId, empFirstName, empLastName, empHoursWorkedTotal, sickDays, empEmail, empPool, empLinen, empMdr, empDock));				
			}
						
			// sort employeeData by seniority (hours_worked from Database)
			Collections.sort(employeeData,  new SortByHoursWorked());
			
			//employeeData.forEach((n) -> System.out.println(n)); // diag print out
		}
		catch(Exception e){
			System.out.println("Error Type: " + e);
		}
	} // ends constructor
	
	public ObservableList<Employee> getEmployeeData(){
		return this.employeeData;
	}
}
