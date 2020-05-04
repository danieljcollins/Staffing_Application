/* DBShiftQuery.java
 * Author: Daniel Collins 
 * Date: May 2020
 * Purpose: This class handles the Connection and the ResultSet of the SQL query which will populate the
 * application data pertaining to the Shift objects.
 */

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.time.LocalDate;
import java.time.LocalTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// this class runs a database query to populate the ObservableList shiftData which is a cornerstone of the application
public class DBShiftQuery {
	
	ResultSet rs;
	ResultSetMetaData rsmd;
	int rsSize;
	
	private final String queryShiftData = "SELECT * from shifts;";
	
	ObservableList<Shift> shiftData = FXCollections.observableArrayList(); // will contain DB data
	
	public DBShiftQuery() {
		DbManager dbm = new DbManager();
		
		try {
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
			String shiftRequiredTraining;
						
			while(rs.next()){
				shiftId = Integer.parseInt(rs.getString("id"))-1;	// subtract 1 to convert from sql based id to 0 based for java loops	
				shiftName = rs.getString("shift_name");
				shiftStartTime = LocalTime.parse(rs.getString("start_time"));				
				shiftEndTime = LocalTime.parse(rs.getString("end_time"));
				shiftRequiredTraining = rs.getString("required_training");
								
				shiftData.add(new Shift(shiftId, shiftName, LocalDate.now(), shiftStartTime, shiftEndTime, shiftRequiredTraining));								
			}
			
			//shiftData.forEach((n) -> System.out.println(n)); // diag print out			
			
		} // ends try
		catch(Exception e){
			System.out.println("Error Type: " + e);
		}
	}
	
	public ObservableList<Shift> getShiftData(){
		return this.shiftData;
	}
}
