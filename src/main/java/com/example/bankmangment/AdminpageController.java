package com.example.bankmangment;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AdminpageController {



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label welcome;

    @FXML
    private Label mssg1;

    @FXML
    private Button cr;

    @FXML
    private Button et;

    @FXML
    private Button tr;

    @FXML
    private Button rm;

    @FXML
    private Button re;

    @FXML
    private Button crt;

    @FXML
    private TextArea quotedis;



    @FXML
    void crt(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("carte.fxml"));
            Parent root = loader.load();

            // Obtient la scène du bouton actuel et y place le nouveau root
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            // Met à jour le titre de la fenêtre, si désiré
            stage.setTitle("Carte Bancaire");

            // Affiche la nouvelle vue
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Log l'exception
            showAlert("Erreur de Chargement", "Impossible de charger la vue.");
        }

    }

    @FXML
    void cr(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminCredit.fxml"));
            Parent root = loader.load();

            // Obtient la scène du bouton actuel et y place le nouveau root
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            // Met à jour le titre de la fenêtre, si désiré
            stage.setTitle("Credit");

            // Affiche la nouvelle vue
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Log l'exception
            showAlert("Erreur de Chargement", "Impossible de charger la vue.");
        }
    }

    @FXML
    void et(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("etablissement.fxml"));
            Parent root = loader.load();

            // Obtient la scène du bouton actuel et y place le nouveau root
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            // Met à jour le titre de la fenêtre, si désiré
            stage.setTitle("Etablissement");

            // Affiche la nouvelle vue
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Log l'exception
            showAlert("Erreur de Chargement", "Impossible de charger la vue.");
        }
    }

    @FXML
    void re(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminRelever.fxml"));
            Parent root = loader.load();

            // Obtient la scène du bouton actuel et y place le nouveau root
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            // Met à jour le titre de la fenêtre, si désiré
            stage.setTitle("Relever");

            // Affiche la nouvelle vue
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Log l'exception
            showAlert("Erreur de Chargement", "Impossible de charger la vue.");
        }
    }

    @FXML
    void rm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Remboursser.fxml"));
            Parent root = loader.load();

            // Obtient la scène du bouton actuel et y place le nouveau root
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            // Met à jour le titre de la fenêtre, si désiré
            stage.setTitle("Rebourssement");

            // Affiche la nouvelle vue
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Log l'exception
            showAlert("Erreur de Chargement", "Impossible de charger la vue.");
        }
    }

    @FXML
    void tr(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminTransaction.fxml"));
            Parent root = loader.load();

            // Obtient la scène du bouton actuel et y place le nouveau root
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            // Met à jour le titre de la fenêtre, si désiré
            stage.setTitle("Transaction");

            // Affiche la nouvelle vue
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Log l'exception
            showAlert("Erreur de Chargement", "Impossible de charger la vue.");
        }
    }

    @FXML
    void initialize() {
        assert welcome != null : "fx:id=\"welcome\" was not injected: check your FXML file 'AdminPage.fxml'.";
        assert mssg1 != null : "fx:id=\"mssg1\" was not injected: check your FXML file 'AdminPage.fxml'.";
        assert cr != null : "fx:id=\"cr\" was not injected: check your FXML file 'AdminPage.fxml'.";
        assert et != null : "fx:id=\"et\" was not injected: check your FXML file 'AdminPage.fxml'.";
        assert tr != null : "fx:id=\"tr\" was not injected: check your FXML file 'AdminPage.fxml'.";
        assert rm != null : "fx:id=\"rm\" was not injected: check your FXML file 'AdminPage.fxml'.";
        assert re != null : "fx:id=\"re\" was not injected: check your FXML file 'AdminPage.fxml'.";
        assert crt != null : "fx:id=\"crt\" was not injected: check your FXML file 'AdminPage.fxml'.";
        assert quotedis != null : "fx:id=\"quotedis\" was not injected: check your FXML file 'AdminPage.fxml'.";

    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}




