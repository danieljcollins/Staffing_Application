import java.time.LocalDate;

// this class is used to store vacation dates that an employee has booked
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
