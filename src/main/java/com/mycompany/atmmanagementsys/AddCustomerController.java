package com.mycompany.atmmanagementsys;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entitie.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceUser;

public class AddCustomerController  {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;




    @FXML
    private TextField uname;

    @FXML
    private TextField ph;

    @FXML
    private Label addcusconfirm;

    @FXML
    private TextField eml;

    @FXML
    private TextField bal;

    @FXML
    private TextField pass;

    @FXML
    private TextField add;

    @FXML
    private ImageView image_id;

    @FXML
    private Button Import_btn;
    private String imagePath;
    private Image image;
    @FXML
    private AnchorPane principal;
    @FXML
    private Button aj;

    ServiceUser sa = new ServiceUser();

    @FXML
    public void Import(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null); // Changed from principal.getScene().getWindow() to null for simplicity
        if (file != null) {
            imagePath = file.getAbsolutePath(); // Obtenez le chemin absolu du fichier
            image = new Image(file.toURI().toString());
            image_id.setImage(image);
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void ajt(ActionEvent event) {

        String textfieldpaasword = pass.getText().trim();
        String textfieldusername = uname.getText().trim();
        String textfieldadresse = add.getText().trim();
        String textfieldemail = eml.getText().trim();
        String textfieldphone = ph.getText().trim();
        double textfieldbalance = Double.parseDouble(bal.getText().trim());


        // Définir les expressions régulières pour la validation
        String usernameRegex = ".{2,}"; // Au moins 2 caractères
        String adresseRegex = ".{2,}"; // Au moins 2 caractères
        String emailRegex = "\\b[\\w.%-]+@[\\w.-]+\\.[a-zA-Z]{2,4}\\b"; // Email valide
        String phoneRegex = "\\d{8}"; // 8 chiffres exactement

        // Vérifier les validations
        if (!textfieldusername.matches(usernameRegex)) {
            showAlert("Erreur de validation", "Le nom d'utilisateur doit contenir au moins 2 caractères.");
            return;
        }
        if (!textfieldadresse.matches(adresseRegex)) {
            showAlert("Erreur de validation", "L'adresse doit contenir au moins 2 caractères.");
            return;
        }
        if (!textfieldemail.matches(emailRegex)) {
            showAlert("Erreur de validation", "Veuillez saisir une adresse e-mail valide.");
            return;
        }
        if (!textfieldphone.matches(phoneRegex)) {
            showAlert("Erreur de validation", "Le numéro de téléphone doit contenir exactement 8 chiffres.");
            return;
        }

        // Check if image is empty
        if (imagePath == null || imagePath.isEmpty()) {
            showAlert("Erreur de Validation", "Veuillez sélectionner une image.");
            return;
        }


        // Creating the association object
        User newAssociation = new User();


        newAssociation.setPassword(textfieldpaasword);
        newAssociation.setName(textfieldusername);
        newAssociation.setAddress(textfieldadresse);
        newAssociation.setEmail(textfieldemail);
        newAssociation.setPhone(textfieldphone);
        newAssociation.setBalance(textfieldbalance);


        newAssociation.setImage(imagePath); // Assuming you handle images similarly

        // Add the association to the database
        try {
            sa.ajouter(newAssociation);
            showAlert("Succès", "Customer a été ajoutée avec succès.");
        } catch (Exception e) {
            showAlert("Erreur lors de l'ajout", e.getMessage());
        }



        // Switch view to admin association list or dashboard
        switchToAdminEvent(event);
    }




    private void switchToAdminEvent(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/AdminPageCustomer.fxml"))); // Ajustez le chemin
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de la vue Admin .");
        }
    }

    @FXML
    void initialize() {
        assert uname != null : "fx:id=\"uname\" was not injected: check your FXML file 'AddCustomer.fxml'.";
        assert ph != null : "fx:id=\"ph\" was not injected: check your FXML file 'AddCustomer.fxml'.";
        assert addcusconfirm != null : "fx:id=\"addcusconfirm\" was not injected: check your FXML file 'AddCustomer.fxml'.";
        assert eml != null : "fx:id=\"eml\" was not injected: check your FXML file 'AddCustomer.fxml'.";
        assert bal != null : "fx:id=\"bal\" was not injected: check your FXML file 'AddCustomer.fxml'.";
        assert pass != null : "fx:id=\"pass\" was not injected: check your FXML file 'AddCustomer.fxml'.";
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'AddCustomer.fxml'.";
        assert image_id != null : "fx:id=\"image_id\" was not injected: check your FXML file 'AddCustomer.fxml'.";
        assert Import_btn != null : "fx:id=\"Import_btn\" was not injected: check your FXML file 'AddCustomer.fxml'.";
        assert aj != null : "fx:id=\"aj\" was not injected: check your FXML file 'AddCustomer.fxml'.";

    }
}
