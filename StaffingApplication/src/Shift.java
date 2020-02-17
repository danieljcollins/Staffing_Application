import java.time.LocalTime;

// this file represents one shift of a organization
public class Shift{
	
	int dayNumber;
	int shiftID;	// will coincide with the database shift table ID
	String shiftName;		// will coincide with the database shift table shift_name
	
	// create 14 employeeName Strings, each will represent an employee filling this shift, once per day, for 14 days (a pay period)
	
	String employeeNameDay1 = " ";
	String employeeNameDay2 = " ";
	String employeeNameDay3 = " ";
	String employeeNameDay4 = " ";
	String employeeNameDay5 = " ";
	String employeeNameDay6 = " ";
	String employeeNameDay7 = " ";
	String employeeNameDay8 = " ";
	String employeeNameDay9 = " ";
	String employeeNameDay10 = " ";
	String employeeNameDay11 = " ";
	String employeeNameDay12 = " ";
	String employeeNameDay13 = " ";
	String employeeNameDay14 = " ";	

	String[] employeeNames = new String[14];	
	
	String trainingRequired;	// "pool", linen, mdr, or dock
	
	LocalTime startTime;	// the time of day when the shift begins
	LocalTime endTime;		// the time of day when the shift ends	
	
	Boolean [] isShiftFilled = new Boolean[14];	
	
	// constructor
	public Shift(int sID, String sName, LocalTime sTime, LocalTime eTime, String requiredTraining){
		this.shiftID = sID;
		this.shiftName = sName;
		//this.dayNumber = dNum;
		this.startTime = sTime;
		this.endTime = eTime;
		this.trainingRequired = requiredTraining;		
		
		// initialize all shifts being unfilled (so no employeeNames will have been entered)
		for(int i = 0; i < 14; i++) {
			employeeNames[i] = "";			
		}		
		
		// initialize all shifts being unfilled
		for(int i = 0; i < 14; i++) {
			isShiftFilled[i] = false;			
		}				
	}
	
	//get methods ************************
	
	public int getDayNumber() {
		return this.dayNumber;
	}
	
	public String getShiftName() {
		return this.shiftName;
	}
	
	public int getShiftID() {
		return this.shiftID;
	}	
	
	public LocalTime getStartTime() {
		return this.startTime;
	}
	
	public LocalTime getEndTime() {
		return this.endTime;
	}
	
	public String getRequiredTraining() {
		return trainingRequired;
	}
	
	public String getEmployeeName(int dayNum) {
		return employeeNames[dayNum];
	}
	
	public String getEmployeeNameDay1() {
		return this.employeeNameDay1;
	}
	
	public String getEmployeeNameDay2() {
		return this.employeeNameDay2;
	}
	
	public String getEmployeeNameDay3() {
		return this.employeeNameDay3;
	}
	
	public String getEmployeeNameDay4() {
		return this.employeeNameDay4;
	}
	
	public String getEmployeeNameDay5() {
		return this.employeeNameDay5;
	}
	
	public String getEmployeeNameDay6() {
		return this.employeeNameDay6;
	}
	
	public String getEmployeeNameDay7() {
		return this.employeeNameDay7;
	}
	
	public String getEmployeeNameDay8() {
		return this.employeeNameDay8;
	}
	
	public String getEmployeeNameDay9() {
		return this.employeeNameDay9;
	}
	
	public String getEmployeeNameDay10() {
		return this.employeeNameDay10;
	}
	
	public String getEmployeeNameDay11() {
		return this.employeeNameDay11;
	}
	
	public String getEmployeeNameDay12() {
		return this.employeeNameDay12;
	}
	
	public String getEmployeeNameDay13() {
		return this.employeeNameDay13;
	}
	
	public String getEmployeeNameDay14() {
		return this.employeeNameDay14;
	}
	
	public Boolean getIsFilled(int dayNum) {
		return this.isShiftFilled[dayNum];
	}	
	
	//set methods ************************
	public void setShiftName(String sn) {
		this.shiftName = sn;
	}
	
	public void setShiftID(int sID) {
		this.shiftID = sID;
	}
	
	public void setDayNumber(int dNum) {
		this.dayNumber = dNum;
	}
	
	public void setStartTime(LocalTime st) {
		this.startTime = st;
	}
	
	public void setEndTime(LocalTime et) {
		this.endTime = et;
	}
	
	public void setEmployeeName(int dayNum, String empName) {
		this.employeeNames[dayNum] = empName;
		if(dayNum == 0) {
			this.employeeNameDay1 = empName;
		}
		else if(dayNum == 1) {
			this.employeeNameDay2 = empName;
		}
		else if(dayNum == 2) {
			this.employeeNameDay3 = empName;
		}
		else if(dayNum == 3) {
			this.employeeNameDay4 = empName;
		}
		else if(dayNum == 4) {
			this.employeeNameDay5 = empName;
		}
		else if(dayNum == 5) {
			this.employeeNameDay6 = empName;
		}
		else if(dayNum == 6) {
			this.employeeNameDay7 = empName;
		}
		else if(dayNum == 7) {
			this.employeeNameDay8 = empName;
		}
		else if(dayNum == 8) {
			this.employeeNameDay9 = empName;
		}
		else if(dayNum == 9) {
			this.employeeNameDay10 = empName;
		}
		else if(dayNum == 10) {
			this.employeeNameDay11 = empName;
		}
		else if(dayNum == 11) {
			this.employeeNameDay12 = empName;
		}
		else if(dayNum == 12) {
			this.employeeNameDay13 = empName;
		}
		else if(dayNum == 13) {
			this.employeeNameDay14 = empName;
		}		
	}
	
	public void setIsFilled(int dayNum, Boolean f) {
		this.isShiftFilled[dayNum] = f;
	}	
}