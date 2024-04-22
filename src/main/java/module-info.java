module com.example.bankmangment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.pdfbox;


    opens com.example.bankmangment to javafx.fxml;
    exports com.example.bankmangment;
}