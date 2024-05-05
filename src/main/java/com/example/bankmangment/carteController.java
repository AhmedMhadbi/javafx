package com.example.bankmangment;

import entities.Cartebancaire;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.SCarteBancaire;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import org.controlsfx.control.Rating;

public class carteController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label welcome;

    @FXML
    private Button aj;

    @FXML
    private Button supptr;

    @FXML
    private TextField nomm;

    @FXML
    private ImageView adminimage;

    @FXML
    private Label adminname;

    @FXML
    private Label adminid;

    @FXML
    private TextField cusid;

    @FXML
    private TextField cusname1;

    @FXML
    private TextField cusname2;

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField etb;



    @FXML
    private VBox cardsContainer;  // VBox replacing the ListView

    @FXML
    private Rating Rate;

    private Cartebancaire selectedCarte;  // Variable to hold the selected card

    @FXML
    private void ajt(ActionEvent event) {
        String numeroCarte = cusid.getText().trim();
        if (numeroCarte.length() != 16) {
            showNotification( "Le numéro de carte doit comporter exactement 16 chiffres.");
            return;
        }

        String cvv = cusname2.getText().trim();
        if (cvv.length() != 4) {
            showNotification( "Le CVV de carte doit comporter exactement 4 chiffres.");
            return;
        }

        LocalDate dateExpiration = datePicker.getValue();
        if (dateExpiration == null || dateExpiration.isBefore(LocalDate.now())) {
            showNotification( "La date d'expiration de la carte ne peut pas être dans le passé ou vide.");
            return;
        }

        int isFrozen;
        try {
            isFrozen = Integer.parseInt(cusname1.getText().trim());
        } catch (NumberFormatException e) {
            showNotification( "La valeur de gel doit être un nombre entier valide.");
            return;
        }

        int etablissement_id;
        try {
            etablissement_id = Integer.parseInt(etb.getText().trim());
        } catch (NumberFormatException e) {
            showNotification( "La valeur de gel doit être un nombre entier valide.");
            return;
        }


        double ratingValue = Rate.getRating();
        System.out.println("Rating submitted: " + ratingValue);


        Cartebancaire newCarte = new Cartebancaire(1,isFrozen,etablissement_id,  numeroCarte , cvv, dateExpiration);
        SCarteBancaire serviceCarteBancaire = new SCarteBancaire();
        serviceCarteBancaire.ajouter(newCarte);
        HBox carteDetails = createCarteHBox(newCarte);
        cardsContainer.getChildren().add(carteDetails);
        showNotification( "La carte bancaire a été ajoutée avec succès.");
        clearInputFields();
    }

    @FXML
    void supptr(ActionEvent event) {
        if (selectedCarte != null && currentlySelectedHBox != null) {
            try {
                SCarteBancaire serviceCarteBancaire = new SCarteBancaire();
                serviceCarteBancaire.supprimer(selectedCarte.getId());
                cardsContainer.getChildren().remove(currentlySelectedHBox);
                showNotification("La carte a été supprimée avec succès.");
                selectedCarte = null;
                currentlySelectedHBox = null;
            } catch (SQLException e) {
                showNotification("Erreur lors de la suppression de la carte: " + e.getMessage());
            }
        } else {
            showNotification( "Veuillez sélectionner une carte à supprimer.");
        }
    }


    private HBox createCarteHBox(Cartebancaire carte) {
        HBox hbox = new HBox(10);
        hbox.setStyle("-fx-background-color: #6F73D2; -fx-padding: 10; -fx-border-color: #4c4177; -fx-border-width: 0 0 1 0;");

        Label numCarteLabel = new Label("Numéro Carte: " + carte.getNum_carte());
        Label cvvLabel = new Label("CVV: " + carte.getCvv());
        Label dateExpLabel = new Label("Date Expiration: " + carte.getDate_exp().toString());
        Label isFrozenLabel = new Label("Is Frozen: " + (carte.getIs_frozen() == 1 ? "Yes" : "No"));
        hbox.getChildren().addAll( numCarteLabel, cvvLabel, dateExpLabel, isFrozenLabel);
        hbox.setUserData(carte);
        hbox.setOnMouseClicked(this::handleCardSelection);
        return hbox;
    }

    private HBox currentlySelectedHBox;  // Pour garder une trace de l'HBox actuellement sélectionné

    private void handleCardSelection(MouseEvent event) {
        HBox clickedHBox = (HBox) event.getSource();
        Cartebancaire selectedCard = (Cartebancaire) clickedHBox.getUserData();

        // Désélectionner l'ancien HBox sélectionné en réinitialisant son style
        if (currentlySelectedHBox != null) {
            currentlySelectedHBox.setStyle("");  // Remettre le style par défaut ou ajuster selon vos besoins
        }

        // Mettre à jour le style du HBox sélectionné pour le marquer comme actif
        clickedHBox.setStyle("-fx-background-color: lightblue; -fx-border-color: #4c4177; -fx-border-width: 0 0 1 0;");

        // Mise à jour de la référence à l'HBox actuellement sélectionné
        currentlySelectedHBox = clickedHBox;

        // Stocker l'objet Cartebancaire sélectionné pour usage dans d'autres méthodes
        selectedCarte = selectedCard;

        // Mettre à jour les champs de saisie ou autres éléments d'interface selon les données de la carte sélectionnée
        updateInputFields(selectedCard);
    }

    private void updateInputFields(Cartebancaire carte) {
        cusid.setText(carte.getNum_carte());
        etb.setText(String.valueOf(carte.getEtablissement_id()));  // Exemple de conversion en String si nécessaire

        cusname1.setText(String.valueOf(carte.getIs_frozen()));  // Exemple de conversion en String si nécessaire
        cusname2.setText(carte.getCvv());
        datePicker.setValue(carte.getDate_exp());
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearInputFields() {
        cusid.clear();
        etb.clear();

        cusname1.clear();
        cusname2.clear();
        datePicker.setValue(null);
    }

    @FXML
    void initialize() {
        SCarteBancaire serviceCarteBancaire = new SCarteBancaire();
        try {
            ObservableList<Cartebancaire> cartes = FXCollections.observableArrayList(serviceCarteBancaire.afficher());
            for (Cartebancaire carte : cartes) {
                HBox carteDetails = createCarteHBox(carte);
                cardsContainer.getChildren().add(carteDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void showNotification(String message) {
        Stage notificationStage = new Stage();
        notificationStage.initStyle(StageStyle.TRANSPARENT);
        notificationStage.setAlwaysOnTop(true);

        Label notificationLabel = new Label(message);
        notificationLabel.getStyleClass().add("notification-label");

        StackPane layout = new StackPane(notificationLabel);
        layout.getStyleClass().add("notification-pane");

        Scene notificationScene = new Scene(layout, 400, 100);
        notificationScene.setFill(null);

        // Load CSS with proper null check
        URL cssUrl = getClass().getResource("style.css");
        if (cssUrl != null) {
            notificationScene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("Failed to load CSS file: check the resource path.");
            // Optionally apply a default style in case CSS file is missing
            notificationLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-padding: 10;");
        }

        notificationStage.setScene(notificationScene);

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        notificationStage.setX(bounds.getMaxX() - 400);
        notificationStage.setY(bounds.getMaxY() - 140);
        notificationStage.show();

        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(5), ev -> {
            notificationStage.close();
        }));
        timeline.play();
    }



}
