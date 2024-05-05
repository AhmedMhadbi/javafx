package com.example.bankmangment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.Pdf;
import entities.Relever;
import entities.Rembourssement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import services.SRembourssement;
import java.sql.SQLException;
import java.time.LocalDate;

public class RembourssementController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label welcome;

    @FXML
    private Button moditr;

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
    private ListView<Rembourssement> listTerrain;

    @FXML
    private TextField mont;

    @FXML
    private TextField crid;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button search;

    @FXML
    private Button pdf;
    @FXML
    private TextField Searchfield;
    @FXML
    private ObservableList<Rembourssement> TerrainsList;



    @FXML
    void ajt(ActionEvent event) {
        try {
            int creditId = Integer.parseInt(crid.getText());
            double montant = Double.parseDouble(mont.getText());
            LocalDate dateRemboursement = datePicker.getValue();

            Rembourssement newRemboursement = new Rembourssement(creditId, montant, dateRemboursement);

            SRembourssement service = new SRembourssement();
            service.ajouter(newRemboursement);

            showAlert("Succès", "Le remboursement a été ajouté avec succès.");

            // Rafraîchir la liste des remboursements
            refreshRemboursementsList();

            // Effacer les champs du formulaire
            crid.clear();
            mont.clear();
            datePicker.setValue(null);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez vérifier les données saisies.");
        }
    }

    @FXML
    void moditr(ActionEvent event) {
        Rembourssement selectedRemboursement = listTerrain.getSelectionModel().getSelectedItem();
        if (selectedRemboursement != null) {
            try {
                int creditId = Integer.parseInt(crid.getText());
                double montant = Double.parseDouble(mont.getText());
                LocalDate dateRemboursement = datePicker.getValue();

                selectedRemboursement.setCredit_id(creditId);
                selectedRemboursement.setMontant(montant);
                selectedRemboursement.setDate_remboursement(dateRemboursement);

                SRembourssement service = new SRembourssement();
                service.modifier(selectedRemboursement);

                showAlert("Succès", "Le remboursement a été modifié avec succès.");

                // Rafraîchir la liste des remboursements
                refreshRemboursementsList();

                // Effacer les champs du formulaire
                crid.clear();
                mont.clear();
                datePicker.setValue(null);
            } catch (NumberFormatException | SQLException e) {
                showAlert("Erreur", "Veuillez vérifier les données saisies.");
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un remboursement à modifier.");
        }
    }

    @FXML
    void supptr(ActionEvent event) {
        Rembourssement selectedRemboursement = listTerrain.getSelectionModel().getSelectedItem();
        if (selectedRemboursement != null) {
            SRembourssement service = new SRembourssement();
            try {
                // Appeler la méthode pour supprimer le remboursement
                service.supprimer(selectedRemboursement.getId());

                // Supprimer l'élément sélectionné de la ListView
                listTerrain.getItems().remove(selectedRemboursement);

                // Effacer la sélection
                listTerrain.getSelectionModel().clearSelection();

                // Afficher une alerte de confirmation
                showAlert("Succès", "Le remboursement a été supprimé avec succès.");
            } catch (SQLException e) {
                // Afficher une alerte d'erreur
                showAlert("Erreur de Base de Données", "Une erreur est survenue lors de la suppression du remboursement : " + e.getMessage());
            }
        } else {
            // Afficher une alerte d'avertissement
            showAlert("Aucune sélection", "Veuillez sélectionner un remboursement à supprimer.");
        }
    }

    @FXML
    void initialize() {
        assert listTerrain != null : "fx:id=\"listTerrain\" was not injected: check your FXML file.";

        SRembourssement serviceRemboursement = new SRembourssement();
        try {
            ObservableList<Rembourssement> remboursements = FXCollections.observableArrayList(serviceRemboursement.afficher());
            listTerrain.setItems(remboursements);

            listTerrain.setCellFactory(listView -> new ListCell<>() {
                @Override
                public void updateItem(Rembourssement remboursement, boolean empty) {
                    super.updateItem(remboursement, empty);
                    if (empty || remboursement == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // Créer une boîte HBox pour afficher les informations du remboursement
                        HBox hbox = new HBox(10); // HBox avec un espacement de 10
                        // Créer des labels pour chaque attribut du remboursement
                        Label creditIdLabel = new Label("Crédit ID: " + remboursement.getCredit_id());
                        Label montantLabel = new Label("Montant: " + remboursement.getMontant());
                        Label dateRemboursementLabel = new Label("Date de remboursement: " + remboursement.getDate_remboursement());
                        // Ajouter les labels à la HBox
                        hbox.getChildren().addAll(creditIdLabel, montantLabel, dateRemboursementLabel);
                        // Définir la HBox comme mise en page personnalisée du ListCell
                        setGraphic(hbox);
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace(); // Pour des raisons de débogage, vous pouvez imprimer la trace de la pile
            // Envisagez d'afficher une alerte à l'utilisateur ou de consigner l'erreur dans un fichier
        }

        // Gérer le double-clic sur un remboursement pour afficher ses détails
        listTerrain.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-clic
                Rembourssement selectedItem = listTerrain.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Afficher les détails du remboursement dans les champs de texte appropriés
                    crid.setText(String.valueOf(selectedItem.getCredit_id()));
                    mont.setText(String.valueOf(selectedItem.getMontant()));
                    datePicker.setValue(selectedItem.getDate_remboursement());
                }
            }
        });
    }

    @FXML
    void search(ActionEvent event) {
        String keyword = Searchfield.getText().trim().toLowerCase();

        ObservableList<Rembourssement> allItems = listTerrain.getItems();
        ObservableList<Rembourssement> filteredList = FXCollections.observableArrayList();

        // Vérifier si le champ de recherche n'est pas vide
        if (!keyword.isEmpty()) {
            for (Rembourssement remboursement : allItems) {
                // Convertir la date en chaîne de caractères et la comparer avec le mot-clé
                String dateAsString = remboursement.getDate_remboursement().toString().toLowerCase();
                if (dateAsString.contains(keyword)) {
                    filteredList.add(remboursement);
                }
            }

            listTerrain.setItems(filteredList);
        } else {
            // Si le champ de recherche est vide, actualiser la liste avec tous les éléments
            refreshRemboursementsList();
        }
    }


    @FXML
    void pdf(ActionEvent event) {
        // Créer une instance de la classe Pdf
        Pdf pdfGenerator = new Pdf();

        try {
            // Appeler la méthode generatePdfm en passant le nom du fichier PDF
            pdfGenerator.generatePdfm("releves");
            showAlert("Succès", "Le PDF a été généré avec succès.");
        } catch (IOException | SQLException e) {
            showAlert("Erreur", "Une erreur est survenue lors de la génération du PDF : " + e.getMessage());
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Méthode pour rafraîchir la liste des remboursements
    private void refreshRemboursementsList() {
        try {
            SRembourssement serviceRemboursement = new SRembourssement();
            listTerrain.setItems(FXCollections.observableArrayList(serviceRemboursement.afficher()));
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de charger les données des remboursements.");
        }
    }



}
