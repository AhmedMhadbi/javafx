module com.example.bankmangment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.pdfbox;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;
    requires javafx.swing;
    requires org.controlsfx.controls;


    opens com.example.bankmangment to javafx.fxml;
    exports com.example.bankmangment;
}