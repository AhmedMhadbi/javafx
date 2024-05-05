package entities;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import services.SEtablissement;
import services.SRelever;
import services.SRembourssement;

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
                SRelever m = new SRelever();
                Set<Relever> list = m.afficher();

                // Adding data to PDF
                for (Relever u : list) {
                    contentStream.showText("Id : " + u.getId());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Débit : " + u.getDebit());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Crédit : " + u.getCredit());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("IBAN : " + u.getIban());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Opération : " + u.getOperation());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Date : " + u.getDate());
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


    public void generatePdfm(String filename) throws FileNotFoundException, IOException, SQLException {
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
                SRembourssement m = new SRembourssement();
                Set<Rembourssement> list = m.afficher();

                // Adding data to PDF
                for (Rembourssement u : list) {

                    contentStream.showText("Débit : " + u.getMontant());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Crédit : " + u.getDate_remboursement());
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


    public void generatePdfetablissement(String filename) throws FileNotFoundException, IOException, SQLException {
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
                SEtablissement m = new SEtablissement();
                Set<Etablissement> list = m.afficher();

                // Adding data to PDF
                for (Etablissement u : list) {

                    contentStream.showText("Nom : " + u.getNom());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Adresse : " + u.getAdresse());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Numero du contact : " + u.getNum_contact());
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
