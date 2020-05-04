/* Vacation.java
 * Author: Daniel Collins 
 * Date: May 2020
 * Purpose: This class is used to store vacation dates that an employee has booked 
 */

import java.time.LocalDate;

public class Vacation {
	
	LocalDate vacationDate;
	
	public Vacation(LocalDate newDate) {
		this.vacationDate = newDate;
	}
	
	public Vacation() {
		
	}
	
	public void setVacationDate(LocalDate date) {
		this.vacationDate = date;
	}
	
	public LocalDate getVacationDate() {
		return vacationDate;
	}
	
	public String getVacationDateString() {
		return vacationDate.toString();
	}
}
