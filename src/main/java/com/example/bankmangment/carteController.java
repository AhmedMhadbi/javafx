package com.example.bankmangment;
import entities.Cartebancaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import services.SCarteBancaire;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


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
    private ListView<Cartebancaire> listTerrain;

    @FXML
    private void ajt(ActionEvent event) {


        String numeroCarte = cusid.getText().trim();
        if (numeroCarte.length() != 16) {
            showAlert("Erreur de Validation", "Le numéro de carte doit comporter exactement 16 chiffres.");
            return;
        }

        String cvv = cusname2.getText().trim();
        if (cvv.length() != 4) {
            showAlert("Erreur de Validation", "Le CVV  de carte doit comporter exactement 4 chiffres.");
            return;
        }

        LocalDate dateExpiration = datePicker.getValue();
        if (dateExpiration == null || dateExpiration.isBefore(LocalDate.now())) {
            showAlert("Erreur de Date", "La date d'expiration de la carte ne peut pas être dans le passé ou vide.");
            return;
        }

        int isFrozen; // Vous devez définir comment vous gérez cette valeur dans votre application
        try {
            isFrozen = Integer.parseInt(cusname1.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Erreur de Validation", "La valeur de gel doit être un nombre entier valide.");
            return;
        }



        Cartebancaire newEvent = new Cartebancaire();
        newEvent.setNum_carte(numeroCarte);
        newEvent.setDate_exp(dateExpiration);
        newEvent.setCvv(cvv);
        newEvent.setIs_frozen(isFrozen);
        newEvent.setCompte_id(1);


        SCarteBancaire serviceCarteBancaire = new SCarteBancaire();
        serviceCarteBancaire.ajouter(newEvent);
        listTerrain.getItems().add(newEvent);
        showAlert("Succès", "La carte bancaire a été ajoutée avec succès.");

        clearInputFields();
    }


    @FXML
    void supptr(ActionEvent event) {

        Cartebancaire selectedProject = listTerrain.getSelectionModel().getSelectedItem(); // Correct ListView for projects
        if (selectedProject != null) {
            SCarteBancaire sp = new SCarteBancaire();

            try {
                // Call method to delete the project, assuming there is a method like deleteProject
                sp.supprimer(selectedProject.getId()); // Use getId or the appropriate method to obtain the ID

                // Remove the selected item from the ListView
                listTerrain.getItems().remove(selectedProject);

                // Clear the selection
                listTerrain.getSelectionModel().clearSelection();

                // Optionally clear input fields if applicable
                nomm.clear();


                // Display a confirmation alert
                showAlert("Succès", "La carte a été supprimé avec succès.");
            } catch (SQLException e) {
                // Display an error alert
                showAlert("Erreur de Base de Données", "Une erreur est survenue lors de la suppression du carte : " + e.getMessage());
            }
        } else {
            // Display a warning alert
            showAlert("Aucune sélection", "Veuillez sélectionner un projet à supprimer.");
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
    void initialize() {
        SCarteBancaire serviceCarteBancaire = new SCarteBancaire();

        try {
            ObservableList<Cartebancaire> cartes = FXCollections.observableArrayList(serviceCarteBancaire.afficher());
            listTerrain.setItems(cartes);

            listTerrain.setCellFactory(listView -> new ListCell<Cartebancaire>(){
                @Override
                public void updateItem(Cartebancaire carte, boolean empty) {
                    super.updateItem(carte, empty);
                    if (empty || carte == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(10); // HBox with spacing of 10
                        Label idLabel = new Label("ID: " + carte.getId());
                        Label compteIdLabel = new Label("Compte ID: " + carte.getCompte_id());
                        Label isFrozenLabel = new Label("Is Frozen: " + carte.getIs_frozen());
                        Label numCarteLabel = new Label("Numéro Carte: " + carte.getNum_carte());
                        Label cvvLabel = new Label("CVV: " + carte.getCvv());
                        Label dateExpLabel = new Label("Date Expiration: " + carte.getDate_exp().toString());

                        hbox.getChildren().addAll(idLabel, compteIdLabel, isFrozenLabel, numCarteLabel, cvvLabel, dateExpLabel);
                        setGraphic(hbox); // Set the custom layout as the graphic of the list cell
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

        listTerrain.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click
                Cartebancaire selectedItem = listTerrain.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    nomm.setText(selectedItem.getNum_carte());


                }
            }
        });
    }


    private void clearInputFields() {
        nomm.clear();
        cusid.clear();
        cusname2.clear();
        datePicker.setValue(null);
        cusname1.clear();
    }

}
