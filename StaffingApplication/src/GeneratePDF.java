/* GeneratePDF.java
 * Author: Daniel Collins 
 * Date: May 2020
 * Purpose: This class takes the Shift object ObservableArray and draws a table using PDFBox API calls, 
 * inserting the object data as it goes to produce a human-readable work schedule for the pay period.
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GeneratePDF {
	
	ObservableList<Employee> employeeData = FXCollections.observableArrayList();
	ObservableList<Shift> shiftData = FXCollections.observableArrayList();
		
	PDDocument document = new PDDocument();	// Create a document and add a page to it
	List<PDPage> pages = new ArrayList();	// this will contain the PDF images that are drawn using this class
	PDPageContentStream contentStream;
	
	int employeeIndex = 0;
	
	int margin = 10;	// pixel size of margins
	int cellHeight = 20;	// pixel height of each cell
	int cellHeaderHeight = 40;	// pixel height of the header row (date, day of the week displayed at the top of the table)
	int cellWidth = 40;	// pixel width of each cell
	int cellWidthEmployeeName = 150;	// pixel width of left-most column to fit an employee name once per row
	int rows;
	int columns = 14;	// one column for employee names (that is drawn outside of a loop
	int rowsPerPage = 25;	// 25 is pretty clean and leaves some space for a header (and a title if wanted). 30 fit without a header and default sizin
	BigDecimal pageAmount = new BigDecimal(1);
	
	// Send the objects from the main application to here to initialize the usage of this class
	public void sendData(ObservableList<Employee> employees, ObservableList<Shift> shifts) {
		this.employeeData = employees;
		this.shiftData = shifts;
	}
	
	// this method will be sent an employee index value after which it will loop through the scheduled shifts for that
	// pay period and determine when the associated Employee is scheduled. 
	// from there it will generate a personalized PDF schedule for that employee alone
	public void generateSchedule(int empIndex) {
		this.employeeIndex = empIndex;
		// pseudo code:
		// generate schedule that is one line, for the employee that matches the employee index sent in
	}
	
	// this method will prepare the number of pages (based on the default sizing that I've set) and how many
	// employees need to be displayed. Displaying one employees schedule is different than a whole team's schedule
	// due to having to send in unique PDPage objects 
	public void preparePages() {
		pageAmount.setScale(0, RoundingMode.CEILING);
		rows = employeeData.size();
		//System.out.println("Page Amount = " + pageAmount);
		//System.out.println("EmployeeData size:" + rows + " RowsPerPage: " + rowsPerPage);
		
		if(rows < rowsPerPage) {
			// one page is enough
			pages.add(new PDPage());
			pageAmount = new BigDecimal(1);
		}		
		else if(rows > rowsPerPage) {
			System.out.println("rows > rowsPerPage");
			// two or more pages is necessary
			
			BigDecimal empSizeBD = new BigDecimal(employeeData.size());
			BigDecimal rowSizeBD = new BigDecimal(rowsPerPage);			

			pageAmount = empSizeBD.divide(rowSizeBD, RoundingMode.CEILING);
			//System.out.println("Page Amount (after BigDecimal computation) = " + pageAmount);
			
			for(int i = 0; i < pageAmount.intValue(); i++) {
				pages.add(new PDPage());
			}
		}
	}
	
	// This method will create a .pdf file that contains all of the schedule data for that pay period
	// it will take the Shift ObservableList object
	public void generateSchedule() {
		int current = 0;
		int end = rows;
		
		//System.out.println("Page Amount = " + pageAmount);		
		
		for(int i = 0; i < pageAmount.intValue(); i++) {
			if(current < 25 && current < rows) {
				performDraws(pages.get(0), current, 25);				
			}
			else if(current >= 25 && current <= 50 && current < rows) {
				performDraws(pages.get(1), current, end);
			}
			current += rowsPerPage;			
		}		
		
	} // method ends
	
	// This method will draw the lines and the data
	// It needs a PDPage object from the List(which could vary by team size so dynamically determining the number
	// of pages is necessary.
	// It takes a range of start and end int variables, which "chunks" the work into an amount that will fit
	// on the page. The current default is 25 per page but that could be made user-configurable later.
	public void performDraws(PDPage page, int start, int end) {
		page.setRotation(90);
		document.addPage( page );

		// Create a new font object selecting one of the PDF base fonts
		PDFont font = PDType1Font.COURIER;
		
		try {
			// Start a new content stream which will "hold" the to be created content
			PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true, true);
									
			// draw a table using lines to display schedule data in human readable format
			PDRectangle pageSize = page.getMediaBox();
			float pageWidth = pageSize.getWidth();			
						
			// rotate the starting point for drawing, so that it's the expected point and not altered by 90 degrees
			contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
			
			int currentX = margin;
			int currentY = margin;
			
			// pseudo code (to offset the dynamic number of rows and align them to the top of the page):
			// if end variable matches rows, that means this will be the last page.
			// subtract end from start, to determine how many rows will need to be displayed
			// take the result, take the cell height and header(s) height(s) to place the Y coordinate higher up 
			// to align the last page table at the top of the page			
			if(end == rows) {
				int numRowsPadding = rowsPerPage - (end-start);
				int offset = (numRowsPadding*cellHeight); //+ cellHeaderHeight;
				currentY = offset;
			}
			
			// draw the horizontal lines for each row
			for(int i = start; i < end; i++) {
				// note: coordinates start from the bottom left of the "page"; rather than the top left				
				
				contentStream.moveTo(currentX, currentY);
				contentStream.lineTo((currentX + cellWidthEmployeeName + (columns*cellWidth) + cellWidth), currentY );
				contentStream.stroke();
				
				currentY += cellHeight;
								
				/* pseudo code:
				 * while drawing the horizontal lines in the loop, populate the left most cells with the employee names;
				 * the employees ObservableArray should be a good source of employee names to pop into the cells
				 * because PDFBox typically works from bottom left to top right, write the employee names in reverse
				 * seniority ranking or reverse alphabetical order
				 */
				// write in the left column data while making the horizontal row lines
				contentStream.beginText();
				contentStream.setFont( font, 12 );
				contentStream.newLineAtOffset(currentX+10, currentY-10);
				contentStream.showText( employeeData.get(i).getEmployeeName() );	// write the employee name for that row
				contentStream.endText();
				
				// store the currentX variable while I write the assigned shift names into the schedule
				int tempCurrentX = currentX;				
				
				// Write assign shifts into the schedule, one employee row at a time
				// iterate through shifts, if the employee name matches the name of that date, fill out the shift name under that date				
				for(int j = 0; j < shiftData.size(); j++) {
					currentX += cellWidthEmployeeName + cellWidth;	// add the padding so the writing begins in the first date cell
					
					for(int day = 0; day < 14; day++) { // to represent day 1 through 14 in the Shift object
						// if the employee name assigned to the shift matches the row of the employee that we're currently writing into the schedule
						if( shiftData.get(j).getEmployeeName(day).equalsIgnoreCase( employeeData.get(i).getEmployeeName() ) ) {
							contentStream.beginText();
							contentStream.setFont( font, 12 );
							contentStream.newLineAtOffset(currentX+10, (currentY-cellHeight)+10);
							contentStream.showText( shiftData.get(j).getShiftName() );
							contentStream.endText();
						}
						currentX += cellWidth;	// move the coordinates so that it writes into the next cell on the next loop						
					}
					currentX = tempCurrentX;	// reset the variable so that the next shift can be placed in the correct cell					
				}
				currentX = tempCurrentX;	// reset the variable so that horizontal line drawing can resume
			}
			
			// after the loop, add one more horizontal line to place a "cap" on the table
			contentStream.moveTo(currentX, currentY);
			contentStream.lineTo((currentX + cellWidthEmployeeName + (columns*cellWidth) + cellWidth), currentY );
			contentStream.stroke();
			currentY += cellHeaderHeight;	// to create a header above
			
			// Header Creation (Date, Day of the week display at the top of the table)
			/* pseudo code:
			 * from here, I need to write the text into the header. the first column being 
			 * "Employee" or "Employee Name". The subsequent columns being the dates with the name of the day 
			 * e.g. "MON" above the date
			 */			
			contentStream.moveTo(currentX, currentY);
			contentStream.lineTo((currentX + cellWidthEmployeeName + (columns*cellWidth) + cellWidth), currentY );
			contentStream.stroke();	
			
			/* pseudo code:
			 * To create a header, I'll draw the table cells and put "Shift" in the left-most column.
			 * Then I need to import the dates from the schedule using one of the objects from the main application;
			 * (possibly run a quick algorithm that sends in the table data from UserInterface.java into an array 
			 *  of some kind to use to populate the PDF schedule.
			 */			
			currentX = margin;	// reset the starting point to the far left margin
			currentY = (rowsPerPage*cellHeight) + cellHeight;	// set the starting point at the top of the page's table data; above the highest positioned entry
			
			// "Employee" Header label code section
			String [] daysOfWeek = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};	// note that in Canada, they ignore the ISO standard and start the week on Sunday			
			currentX += 10;	// reposition the writing position within the cell			
			contentStream.beginText();
			contentStream.newLineAtOffset(currentX, currentY);
			contentStream.showText("Employee");			
			contentStream.endText();
			currentX += cellWidthEmployeeName+cellWidth;
			
			// date entry Header label
			for(int i = 0; i < daysOfWeek.length; i++) {
				contentStream.beginText();				
				contentStream.newLineAtOffset(currentX, currentY+20);	// raise the height of the Day of the week to subsequently fit the date below it in the same cell				
				contentStream.showText(daysOfWeek[i]);
				contentStream.endText();
								
				contentStream.beginText();
				contentStream.newLineAtOffset(currentX,  currentY);				
				LocalDate dateTemp = shiftData.get(0).date;	// I'll grab the first date and increment from there, as the current application design doesn't attach dates to schedules directly in the ObservableList (due to how JavaFX tables work, I linked a less-complex object to the table, rather than a collection of objects which don't bind to table columns well from what I could tell)				
				contentStream.showText( String.valueOf(dateTemp.plusDays(i).getDayOfMonth()) );	// the day of the month placeholder
				contentStream.endText();
				
				currentX += cellWidth;	// prepare the text position for the next cell				
			}			
			// end of Header Code
			
			// Beginning of column line code
			// reset the X and Y coordinates
			currentX = margin;
			currentY = margin;
			
			// draw the initial vertical line for the table
			contentStream.moveTo(currentX, currentY);
			contentStream.lineTo(currentX, currentY+(rows*cellHeight) );
			contentStream.stroke();
			currentX += cellWidthEmployeeName;
			// draw the vertical line that will demarcate the employee names on the left side of the table
			contentStream.lineTo(currentX, currentY+(rows*cellHeight) );
			contentStream.stroke();			
			currentX += cellWidth;
			
			// draw the remaining vertical lines for each row
			for(int j = 0; j < columns; j++) {
				// note: coordinates start from the bottom left of the "page"; rather than the top left
				
				/* pseudo code:
				 * I will have to keep track of which employee row I'm currently writing for, and what day
				 * It may be better to write this from the row creation loop above.
				 * Otherwise while keeping track of what employee is being checked, it would check the date
				 * and determine if the employee is scheduled that day.
				 * If so, then print the shift name that they are assigned.
				 * 
				 *  Tracking the situation:
				 *  One tracking solution would be to save the coordinates while accessing the employee name 
				 *  in the row line drawing loop above into a new array.
				 *  Then when going through the dates and shifts, when a match is found it can quickly use the
				 *  contentStream.lineTo() method to re-locate the drawing cursor to the correct region and then
				 *  write the shift name to move on.
				 *  
				 *   Tracking the situation:
				 *   Another solution would be to determine the date column (1-14) that I'm trying to fill out,
				 *   then perform simple math like lineTo(columnWidthEmployeeName+columnWidth*1) and substitute the
				 *   *1 by whatever column to locate the drawing region of that date for that employee. That would
				 *   make this loop more suitable in the row line creation loop (I'm seeing a pattern here)
				 */
				
				contentStream.moveTo(currentX, currentY);
				contentStream.lineTo(currentX, currentY+(rows*cellHeight) );
				contentStream.stroke();				
				
				currentX += cellWidth;				
			}
			// after the loop, add one more vertical line to place a "cap" on the table
			contentStream.moveTo(currentX, currentY);
			contentStream.lineTo(currentX, currentY+(rows*cellHeight) );
			contentStream.stroke();			
			
			contentStream.close();	// close the stream for now
			
			// if the end variable matches total rows variable then all rows have been processed
			// the process should be completed so close the stream and save the PDF file
			if(rows == end) {
				// Make sure that the content stream is closed:
				contentStream.close();
				
				// Save the results and ensure that the document is properly closed:
				document.save("test_dynamic_algorithm.pdf");
				System.out.println("PDF File Generated: test_dynamic_algorithm.pdf");
				document.close();			
			}			
		}
		catch(Exception e) {
			System.out.println("PDFBox Error: " + e.getMessage());
		}
	} // ends method performDraws()
}