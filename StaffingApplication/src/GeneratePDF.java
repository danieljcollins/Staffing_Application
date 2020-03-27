import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class GeneratePDF {
	
	// this method will be sent an employee index value after which it will loop through the scheduled shifts for that
	// pay period and determine when the associated Employee is scheduled. 
	// from there it will generate a personalized PDF schedule for that employee alone
	public void generateSchedule(int empIndex) {
		
	}
	
	// This method will create a .pdf file that contains all of the schedule data for that pay period
	// it will take the Shift ObservableList object
	public void generateSchedule() {
		// Create a document and add a page to it
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage( page );

		// Create a new font object selecting one of the PDF base fonts
		PDFont font = PDType1Font.HELVETICA_BOLD;
		
		try {
			// Start a new content stream which will "hold" the to be created content
			PDPageContentStream contentStream = new PDPageContentStream(document, page);
	
			// Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
			contentStream.beginText();
			contentStream.setFont( font, 12 );
			contentStream.moveTextPositionByAmount( 100, 700 );
			contentStream.drawString( "Hello World" );
			contentStream.endText();
	
			// Make sure that the content stream is closed:
			contentStream.close();
	
			// Save the results and ensure that the document is properly closed:
			document.save( "Hello World.pdf");
			document.close();
		}
		catch(Exception e) {
			System.out.println("PDFBox Error: " + e.getMessage());
		}
	}

}
