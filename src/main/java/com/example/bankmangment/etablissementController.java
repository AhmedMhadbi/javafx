package com.example.bankmangment;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Cartebancaire;
import entities.Etablissement;
import entities.Relever;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import services.SCarteBancaire;
import services.SEtablissement;
import services.SRelever;


public class etablissementController {



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label welcome;

    @FXML
    private TextField nomm;

    @FXML
    private Button aj;

    @FXML
    private Button supptr;

    @FXML
    private ImageView adminimage;

    @FXML
    private Label adminname;

    @FXML
    private Label adminid;

    @FXML
    private ListView<Etablissement> listTerrain;

    @FXML
    private TextField add;

    @FXML
    private TextField nomet;

    @FXML
    private TextField num;
    @FXML
    private Button moditr;

    @FXML
    private Button search;


    @FXML
    private TextField Searchfield;
    @FXML
    private ObservableList<Etablissement> TerrainsList;


    @FXML
    void ajt(ActionEvent event) {
        String etablissementNom = nomet.getText().trim();
        String etablissementadd = add.getText().trim();
        String numText = num.getText().trim(); // Récupérer le texte du champ de texte du numéro
        int etablissementNum;

        // Vérification de la longueur du nom de l'établissement
        if (etablissementNom.length() < 3) {
            showAlert("Erreur de Validation", "Le nom de l'établissement doit comporter au moins 3 caractères.");
            return;
        }

        // Vérification de la longueur de l'adresse de l'établissement
        if (etablissementadd.length() < 3) {
            showAlert("Erreur de Validation", "L'adresse de l'établissement doit comporter au moins 3 caractères.");
            return;
        }

        // Vérification du format du numéro de l'établissement
        if (numText.length() != 8) {
            showAlert("Erreur de Validation", "Le numéro de contact doit comporter exactement 8 chiffres.");
            return;
        }

        try {
            etablissementNum = Integer.parseInt(numText);
        } catch (NumberFormatException e) {
            showAlert("Erreur de Validation", "Le numéro de contact doit être un nombre entier valide.");
            return;
        }



        Etablissement newEvent = new Etablissement();
        newEvent.setNom(etablissementNom);
        newEvent.setAdresse(etablissementadd);
        newEvent.setNum_contact(etablissementNum);
        newEvent.setCompte_id(1);



        SEtablissement se = new SEtablissement();
        se.ajouter(newEvent);
        listTerrain.getItems().add(newEvent);
        showAlert("Succès", "L etablissement a été ajoutée avec succès.");

        clearInputFields();





    }


    @FXML
    void moditr(ActionEvent event) {
        Etablissement selectedEtablissement = listTerrain.getSelectionModel().getSelectedItem();
        if (selectedEtablissement != null) {
            // Récupération des valeurs des champs de texte
            String nom = nomet.getText().trim();
            String adresse = add.getText().trim();
            String numText = num.getText().trim(); // Récupérer le texte du champ de texte du numéro
            int numContact;

            // Vérification de la longueur du nom de l'établissement
            if (nom.length() < 3) {
                showAlert("Erreur de Validation", "Le nom de l'établissement doit comporter au moins 3 caractères.");
                return;
            }

            // Vérification de la longueur de l'adresse de l'établissement
            if (adresse.length() < 3) {
                showAlert("Erreur de Validation", "L'adresse de l'établissement doit comporter au moins 3 caractères.");
                return;
            }

            // Vérification du format du numéro de l'établissement
            if (numText.length() != 8) {
                showAlert("Erreur de Validation", "Le numéro de contact doit comporter exactement 8 chiffres.");
                return;
            }

            try {
                numContact = Integer.parseInt(numText);
            } catch (NumberFormatException e) {
                showAlert("Erreur de Validation", "Le numéro de contact doit être un nombre entier valide.");
                return;
            }


            // Mise à jour de l'objet Etablissement
            selectedEtablissement.setNom(nom);
            selectedEtablissement.setAdresse(adresse);
            selectedEtablissement.setNum_contact(numContact);
            selectedEtablissement.setCompte_id(1); // Fixer Compte_id à 1

            // Mise à jour de l'établissement dans la base de données
            try {
                SEtablissement se = new SEtablissement();
                se.modifier(selectedEtablissement); // Assurez-vous que la méthode modifier existe dans SEtablissement
                // Actualisation de la liste
                listTerrain.getItems().set(listTerrain.getSelectionModel().getSelectedIndex(), selectedEtablissement);
                showAlert("Succès", "L'établissement a été mis à jour avec succès.");
            } catch (SQLException e) {
                showAlert("Erreur", "Échec de la mise à jour de l'établissement : " + e.getMessage());
            }
        } else {
            // Si aucun établissement n'est sélectionné
            showAlert("Aucune sélection", "Veuillez sélectionner un établissement à modifier.");
        }
    }

    @FXML
    void supptr(ActionEvent event) {
        Etablissement selectedProject = listTerrain.getSelectionModel().getSelectedItem(); // Correct ListView for projects
        if (selectedProject != null) {
            SEtablissement sp = new SEtablissement();
            try {
                // Call method to delete the project, assuming there is a method like deleteProject
                sp.supprimer(selectedProject.getId()); // Use getId or the appropriate method to obtain the ID

                // Remove the selected item from the ListView
                listTerrain.getItems().remove(selectedProject);

                // Clear the selection
                listTerrain.getSelectionModel().clearSelection();

                nomm.clear();
                nomet.clear();
                num.clear();
                add.clear();

                // Display a confirmation alert
                showAlert("Succès", "L etablissement a été supprimé avec succès.");
            } catch (SQLException e) {
                // Display an error alert
                showAlert("Erreur de Base de Données", "Une erreur est survenue lors de la suppression du etablissement : " + e.getMessage());
            }
        } else {
            // Display a warning alert
            showAlert("Aucune sélection", "Veuillez sélectionner un etablissement à supprimer.");
        }
    }

    @FXML
    void initialize() {
        assert listTerrain != null : "fx:id=\"listTerrain\" was not injected: check your FXML file 'AdminProjects.fxml'.";

        SEtablissement serviceEtablissement = new SEtablissement();
        try {
            ObservableList<Etablissement> etablissements = FXCollections.observableArrayList(serviceEtablissement.afficher());
            listTerrain.setItems(etablissements);

            listTerrain.setCellFactory(listView -> new ListCell<>() {
                @Override
                public void updateItem(Etablissement etablissement, boolean empty) {
                    super.updateItem(etablissement, empty);
                    if (empty || etablissement == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(10); // HBox with spacing of 10
                        Label nomLabel = new Label(etablissement.getNom());
                        nomLabel.setMinWidth(100); // Set minimum width for the label
                        Label adresseLabel = new Label(etablissement.getAdresse());
                        adresseLabel.setMinWidth(100); // Set minimum width for the label
                        Label numContactLabel = new Label(String.valueOf(etablissement.getNum_contact()));
                        numContactLabel.setMinWidth(100); // Set minimum width for the label
                        hbox.getChildren().addAll(nomLabel, adresseLabel, numContactLabel);
                        setGraphic(hbox); // Set the custom layout as the graphic of the list cell
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace(); // For debugging purposes, you might want to print the stack trace
            // Consider showing an alert to the user or logging the error to a file
        }

        listTerrain.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click
                Etablissement selectedItem = listTerrain.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    nomm.setText(selectedItem.getNom());
                    nomet.setText(selectedItem.getNom());

                    add.setText(selectedItem.getAdresse());
                    num.setText(String.valueOf(selectedItem.getNum_contact()));
                }
            }
        });
    }



    private void clearInputFields() {
        nomm.clear();
        nomet.clear();
        num.clear();
        add.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



    @FXML
    void search(ActionEvent event) {
        String keyword = Searchfield.getText().toLowerCase().trim();

        ObservableList<Etablissement> allItems = listTerrain.getItems();
        ObservableList<Etablissement> filteredList = FXCollections.observableArrayList();

        // Vérifier si le champ de recherche n'est pas vide
        if (!keyword.isEmpty()) {
            for (Etablissement terrain : allItems) {
                if (terrain.getNom().toLowerCase().contains(keyword)) {
                    filteredList.add(terrain);
                }
            }

            listTerrain.setItems(filteredList);
        } else {
            // Si le champ de recherche est vide, actualiser la liste avec tous les éléments
            refreshList();
        }
    }


    private void refreshList() {
        SEtablissement service = new SEtablissement();
        try {
            ObservableList<Etablissement> relevers = FXCollections.observableArrayList(service.afficher());
            listTerrain.setItems(relevers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

