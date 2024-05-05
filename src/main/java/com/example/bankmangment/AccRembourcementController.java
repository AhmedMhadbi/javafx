package com.example.bankmangment;
import entities.Rembourssement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.SRembourssement;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AccRembourcementController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL
            location;

    @FXML
    private Label welcome;

    @FXML
    private ImageView adminimage;

    @FXML
    private Label adminname;

    @FXML
    private Label adminid;

    @FXML
    private TextField mont;

    @FXML
    private TextField crid;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button aj;

    private int eventID;

    public void setId(int id) {
        this.eventID = id;
    }

    @FXML
    void ajt(ActionEvent event) {
        try {
            Double montant = Double.parseDouble(mont.getText().trim());
            LocalDate dateRemboursement = datePicker.getValue();

            Rembourssement participation = new Rembourssement(eventID, montant,dateRemboursement);
            SRembourssement servicePartc = new SRembourssement();
            servicePartc.ajouter(participation);

            // Redirection vers Participation.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Remboursser.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Rembourssement ajoutée avec succès !");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "n est pas valide.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page ");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header) {
        // Affichage d'une alerte
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(header);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        assert welcome != null : "fx:id=\"welcome\" was not injected: check your FXML file 'AccRembourcement.fxml'.";
        assert adminimage != null : "fx:id=\"adminimage\" was not injected: check your FXML file 'AccRembourcement.fxml'.";
        assert adminname != null : "fx:id=\"adminname\" was not injected: check your FXML file 'AccRembourcement.fxml'.";
        assert adminid != null : "fx:id=\"adminid\" was not injected: check your FXML file 'AccRembourcement.fxml'.";
        assert mont != null : "fx:id=\"mont\" was not injected: check your FXML file 'AccRembourcement.fxml'.";
        assert crid != null : "fx:id=\"crid\" was not injected: check your FXML file 'AccRembourcement.fxml'.";
        assert datePicker != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'AccRembourcement.fxml'.";
        assert aj != null : "fx:id=\"aj\" was not injected: check your FXML file 'AccRembourcement.fxml'.";

    }
}

