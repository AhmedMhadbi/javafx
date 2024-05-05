package com.example.bankmangment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;

import entities.Credit;
import entities.Rembourssement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import services.SCredit;
import services.SRembourssement;

public class AdminRemboursement {

SCredit ST = new SCredit();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label welcome;

    @FXML
    private ImageView adminimage;

    @FXML
    private Label adminname;

    @FXML
    private Label adminid;

    @FXML
    private HBox hBoxEvents;

    @FXML
    private AnchorPane NomId;

    @FXML
    public void initialize() {
        try {
            Set<Credit> events = ST.afficher();  // Assurez-vous que la méthode afficher renvoie un Set<Event>

            for (Credit event : events) {
                String imagePath = event.getImage_name();  // Utiliser getImage() pour obtenir le chemin de l'image
                Image image = loadImage(imagePath);  // loadImage doit être une méthode définie pour charger une image
                ImageView imageView = new ImageView(image);

                Label nomLabel = new Label("Nom : " + event.getType());
                nomLabel.setStyle("-fx-text-fill: white;");

                Label descriptionLabel = new Label("Description : " + event.getDescription());
                descriptionLabel.setStyle("-fx-text-fill: white;");

                Label lieuLabel = new Label("Lieu : " + event.getDescription());
                lieuLabel.setStyle("-fx-text-fill: white;");

                Label dateLabel = new Label("Date : " + event.getDate_demande().toString());
                dateLabel.setStyle("-fx-text-fill: white;");

                Button reserverButton = new Button("Remboursser");
                reserverButton.setUserData(event.getId());
                reserverButton.setOnAction(this::ajouter_part);

                Button detailsButton = new Button("Détails");
                detailsButton.setUserData(event.getId());  // Assumer getId() renvoie l'identifiant de l'événement
                // Modifier ici pour éviter le conflit de nom de variable
               detailsButton.setOnAction(e -> showEventDetails(event));  // 'e' remplace 'event'

                VBox eventBox = new VBox(imageView, nomLabel, descriptionLabel, lieuLabel, dateLabel, detailsButton, reserverButton);
                eventBox.setSpacing(10);

                hBoxEvents.getChildren().add(eventBox);  // Assurez-vous que hBoxEvents est correctement initialisé et accessible
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Image loadImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                File imageFile = new File(imagePath);
                String imageUrl = imageFile.toURI().toURL().toString();
                return new Image(imageUrl, 150, 150, false, true);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                // Optionally, return a placeholder image if the URL is malformed
            }
        }
        // Return a default image or handle the error appropriately
        return new Image("/path/to/placeholder.png"); // Make sure this path is correct
    }





    private void showEventDetails(Credit event) {
        Stage detailsStage = new Stage();
        AnchorPane detailsPane = new AnchorPane();
        detailsPane.setPadding(new Insets(10));
        URL stylesheetURL = getClass().getResource("/styles.css");
        if (stylesheetURL != null) {
            detailsPane.getStylesheets().add(stylesheetURL.toExternalForm());
            detailsPane.getStyleClass().add("event-details");
        } else {
            System.out.println("Stylesheet not found.");
        }

        String details = String.format("Détails de Credit:\nType: %s\nDescription: %s\nMontant: %s\nDate: %s\n",
                event.getType(),
                event.getDescription(),
                event.getMontant(),
                event.getDate_demande().toString());
        Label detailsLabel = new Label(details);
        detailsLabel.setFont(new Font("Arial", 14));
        detailsLabel.setWrapText(true);

        String imagePath = event.getImage_name();
        Image image = null;
        if (imagePath != null) {
            try {
                File imageFile = new File(imagePath);
                image = new Image(new FileInputStream(imageFile), 200, 200, true, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // Gestion du cas où le chemin de l'image est null ou incorrect
            }
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(e -> detailsStage.close());
        closeButton.getStyleClass().add("close-button");

        detailsPane.getChildren().addAll(imageView, detailsLabel, closeButton);
        AnchorPane.setTopAnchor(imageView, 20.0);
        AnchorPane.setTopAnchor(detailsLabel, 230.0);
        AnchorPane.setTopAnchor(closeButton, 450.0);

        Scene detailsScene = new Scene(detailsPane, 400, 500);
        detailsStage.setScene(detailsScene);
        detailsStage.setTitle("Détails de l'Événement");
        detailsStage.show();
    }



    @FXML
    public void ajouter_part(ActionEvent actionEvent) {
        int idEvenement = (int)((Button) actionEvent.getSource()).getUserData();  // Obtention de l'ID de l'événement depuis le bouton
        try {
            // Chargement du fichier FXML pour la fenêtre d'ajout de participation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AccRembourcement.fxml"));
            Parent root = loader.load();

            // Obtention du contrôleur associé et passage de l'ID de l'événement
            AccRembourcementController controller = loader.getController();
            controller.setId(idEvenement);  // Assurez-vous que la méthode setId existe dans AjoutParticipationControlleur

            // Changement de la racine de la scène pour afficher la nouvelle fenêtre
            NomId.getScene().setRoot(root);  // 'NomId' doit être un élément UI de votre scène actuelle pouvant accéder à 'getScene()'
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







}

