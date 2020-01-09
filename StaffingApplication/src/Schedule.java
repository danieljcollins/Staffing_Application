import java.time.LocalTime;

// similar to shift, but to be used with JavaFX Table model
// the key being that each Schedule object will represent one shift-type for a 14 day pay period. 
// each day, a different employee may fill that shift, which is stored in this object
// there will be one shift object for every shift-type
public class Schedule {
	String shiftName;
	int shiftID;
	LocalTime startTime;
	LocalTime endTime;
	
	String employeeNameDay1;
	String employeeNameDay2;
	String employeeNameDay3;
	String employeeNameDay4;
	String employeeNameDay5;
	String employeeNameDay6;
	String employeeNameDay7;
	String employeeNameDay8;
	String employeeNameDay9;
	String employeeNameDay10;
	String employeeNameDay11;
	String employeeNameDay12;
	String employeeNameDay13;
	String employeeNameDay14;
	
	boolean [] shiftFilled = new boolean[14];
		
	boolean pool;
	boolean linen;
	boolean mdr;
	boolean dock;
	
	// constructor
	public Schedule(int sID, String sName, LocalTime sTime, LocalTime eTime) {
		this.shiftID = sID;
		this.shiftName = sName;
		this.startTime = sTime;
		this.endTime = eTime;		
		
		// initialze all shifts as unfilled
		for(int i = 0; i < shiftFilled.length; i++) {
			shiftFilled[i] = false;
		}
	}
	
	//get methods
	public String getShiftName() {
		return shiftName;
	}
	
	public int getShiftID() {
		return shiftID;
	}
	
	public LocalTime getStarTime() {
		return startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	
	// the model I'm using here is simple, the columns will indicate which day of the pay period which is being
	// referred to. An employee name will be there, indicating who is working that shift (if any)
	// the first column will be the shift name+startTime
	public String getEmployeeNameDay1() {
		return employeeNameDay1;
	}
	public String getEmployeeNameDay2() {
		return employeeNameDay2;
	}
	public String getEmployeeNameDay3() {
		return employeeNameDay3;
	}
	public String getEmployeeNameDay4() {
		return employeeNameDay4;
	}
	public String getEmployeeNameDay5() {
		return employeeNameDay5;
	}
	public String getEmployeeNameDay6() {
		return employeeNameDay6;
	}
	public String getEmployeeNameDay7() {
		return employeeNameDay7;
	}
	public String getEmployeeNameDay8() {
		return employeeNameDay8;
	}
	public String getEmployeeNameDay9() {
		return employeeNameDay9;
	}
	public String getEmployeeNameDay10() {
		return employeeNameDay10;
	}
	public String getEmployeeNameDay11() {
		return employeeNameDay11;
	}
	public String getEmployeeNameDay12() {
		return employeeNameDay12;
	}
	public String getEmployeeNameDay13() {
		return employeeNameDay13;
	}
	public String getEmployeeNameDay14() {
		return employeeNameDay14;
	}	
	
	//set methods
	public void setShiftName(String sn) {
		this.shiftName = sn;
	}
	
	public void setShiftID(int sID) {
		this.shiftID = sID;
	}
	
	public void setStartTime(LocalTime st) {
		this.startTime = st;
	}
	
	public void setEndTime(LocalTime et) {
		this.endTime = et;
	}
}
