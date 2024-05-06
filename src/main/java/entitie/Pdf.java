package entitie;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import services.ServiceUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

/**
 * Class to generate PDF from a list of Relever objects.
 */
public class Pdf {

    /**
     * Generates a PDF file from a list of Relever objects.
     *
     * @param filename the name of the PDF file to be generated
     * @throws FileNotFoundException if the file cannot be found
     * @throws IOException if an I/O error occurs
     * @throws SQLException if there is an error accessing the database
     */
    public void generatePdf(String filename) throws FileNotFoundException, IOException, SQLException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("La liste :");
                contentStream.newLineAtOffset(0, -20);

                // Fetching data from SRelever service
                ServiceUser m = new ServiceUser();
                Set<User> list = m.afficher();

                // Adding data to PDF
                for (User u : list) {
                    contentStream.showText("Id : " + u.getId());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Name : " + u.getName());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Email : " + u.getEmail());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Address : " + u.getAddress());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Phone : " + u.getPhone());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Balance : " + u.getBalance());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("---------------------------------------------------------------------------------------------------------------------------------- ");
                    contentStream.newLineAtOffset(0, -15);
                }
                contentStream.endText();
            }

            document.save(filename + ".pdf");
        }

        // Opening the generated PDF
        Process pro = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filename + ".pdf");
    }


}
