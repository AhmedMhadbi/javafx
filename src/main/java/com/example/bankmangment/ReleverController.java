package com.example.bankmangment;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.Pdf;
import entities.Relever;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import services.SEtablissement;
import services.SRelever;


public class ReleverController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label welcome;

    @FXML
    private TextField nomm;

    @FXML
    private Button moditr;

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
    private ListView<Relever> listTerrain;

    @FXML
    private TextField db;

    @FXML
    private TextField op;

    @FXML
    private TextField cr;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField ib;
    @FXML
    private Button search;

    @FXML
    private Button pdf;
    @FXML
    private TextField Searchfield;
    @FXML
    private ObservableList<Relever> TerrainsList;



    @FXML
    void ajt(ActionEvent event) {
        double debit = Double.parseDouble(db.getText().trim());
        double credit = Double.parseDouble(cr.getText().trim());
        String ibanText = ib.getText().trim();
        String operationText = op.getText().trim();
        LocalDate date = datePicker.getValue();

        // Vérification de la longueur de l'IBAN
        if (ibanText.length() != 10) {
            showAlert("Validation de l'IBAN", "L'IBAN doit avoir exactement 10 caractères.");
            return;
        }

        // Vérification de la longueur de l'opération
        if (operationText.length() < 3) {
            showAlert("Validation de l'opération", "L'opération doit avoir au moins 3 caractères.");
            return;
        }

        // Vérification de la validité de la date
        if (date == null || date.isAfter(LocalDate.now())) {
            showAlert("Validation de la date", "La date doit être correctement définie et ne peut pas être dans le futur.");
            return;
        }

        Relever newRelever = new Relever(debit, credit, ibanText, operationText, date);
        SRelever service = new SRelever();
        service.ajouter(newRelever); // Supposant que la méthode existe pour ajouter à la base de données

        listTerrain.getItems().add(newRelever); // Mettre à jour la ListView
        showAlert("Succès", "Le relevé a été ajouté avec succès.");
        db.clear();
        cr.clear();
        ib.clear();
        op.clear();
        datePicker.setValue(null);
    }

    @FXML
    void moditr(ActionEvent event) {
        Relever selectedRelever = listTerrain.getSelectionModel().getSelectedItem();
        if (selectedRelever != null) {
            double debit = Double.parseDouble(db.getText().trim());
            double credit = Double.parseDouble(cr.getText().trim());
            String ibanText = ib.getText().trim();
            String operationText = op.getText().trim();
            LocalDate date = datePicker.getValue();

            // Vérification de la longueur de l'IBAN
            if (ibanText.length() != 10) {
                showAlert("Validation de l'IBAN", "L'IBAN doit avoir exactement 10 caractères.");
                return;
            }

            // Vérification de la longueur de l'opération
            if (operationText.length() < 3) {
                showAlert("Validation de l'opération", "L'opération doit avoir au moins 3 caractères.");
                return;
            }

            // Vérification de la validité de la date
            if (date == null || date.isAfter(LocalDate.now())) {
                showAlert("Validation de la date", "La date doit être correctement définie et ne peut pas être dans le futur.");
                return;
            }

            // Mise à jour de l'objet Relever
            selectedRelever.setDebit(debit);
            selectedRelever.setCredit(credit);
            selectedRelever.setIban(ibanText);
            selectedRelever.setOperation(operationText);
            selectedRelever.setDate(date);

            // Mise à jour du relevé dans la base de données
            try {
                SRelever service = new SRelever();
                service.modifier(selectedRelever); // Assurez-vous que la méthode `modifier` existe et fonctionne correctement

                // Actualisation de la liste
                listTerrain.getItems().set(listTerrain.getSelectionModel().getSelectedIndex(), selectedRelever);
                showAlert("Succès", "Le relevé a été mis à jour avec succès.");
            } catch (SQLException e) {
                showAlert("Erreur", "Échec de la mise à jour du relevé : " + e.getMessage());
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un relevé à modifier.");
        }
    }

    @FXML
    void supptr(ActionEvent event) {
        Relever selectedRelever = listTerrain.getSelectionModel().getSelectedItem();
        if (selectedRelever != null) {
            SRelever service = new SRelever();
            try {
                service.supprimer(selectedRelever.getId()); // Assurez-vous que la méthode `supprimer` existe et fonctionne correctement

                // Suppression de l'élément de la ListView
                listTerrain.getItems().remove(selectedRelever);

                // Effacer la sélection
                listTerrain.getSelectionModel().clearSelection();

                // Nettoyer les champs textuels
                db.clear();
                cr.clear();
                ib.clear();
                op.clear();
                datePicker.setValue(null);

                showAlert("Succès", "Le relevé a été supprimé avec succès.");
            } catch (SQLException e) {
                showAlert("Erreur", "Échec de la suppression du relevé : " + e.getMessage());
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un relevé à supprimer.");
        }
    }

    @FXML
    void initialize() {
        assert welcome != null : "fx:id=\"welcome\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert nomm != null : "fx:id=\"nomm\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert moditr != null : "fx:id=\"moditr\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert aj != null : "fx:id=\"aj\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert supptr != null : "fx:id=\"supptr\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert adminimage != null : "fx:id=\"adminimage\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert adminname != null : "fx:id=\"adminname\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert adminid != null : "fx:id=\"adminid\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert listTerrain != null : "fx:id=\"listTerrain\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert db != null : "fx:id=\"db\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert op != null : "fx:id=\"op\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert cr != null : "fx:id=\"cr\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert datePicker != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert ib != null : "fx:id=\"ib\" was not injected: check your FXML file 'AdminRelever.fxml'.";

        SRelever service = new SRelever();
        try {
            ObservableList<Relever> relevers = FXCollections.observableArrayList(service.afficher());
            listTerrain.setItems(relevers);

            listTerrain.setCellFactory(listView -> new ListCell<Relever>() {
                @Override
                public void updateItem(Relever relever, boolean empty) {
                    super.updateItem(relever, empty);
                    if (empty || relever == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(10);
                        Label debitLabel = new Label(String.valueOf(relever.getDebit()));
                        debitLabel.setMinWidth(100);
                        Label creditLabel = new Label(String.valueOf(relever.getCredit()));
                        creditLabel.setMinWidth(100);
                        Label ibanLabel = new Label(relever.getIban());
                        ibanLabel.setMinWidth(100);
                        Label operationLabel = new Label(relever.getOperation());
                        operationLabel.setMinWidth(100);
                        Label dateLabel = new Label(relever.getDate().toString());
                        dateLabel.setMinWidth(100);
                        hbox.getChildren().addAll(debitLabel, creditLabel, ibanLabel, operationLabel, dateLabel);
                        setGraphic(hbox);
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

        listTerrain.setOnMouseClicked(event -> {
            Relever selectedItem = listTerrain.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                db.setText(String.valueOf(selectedItem.getDebit()));
                cr.setText(String.valueOf(selectedItem.getCredit()));
                ib.setText(selectedItem.getIban());
                op.setText(selectedItem.getOperation());
                datePicker.setValue(selectedItem.getDate());
            }
        });
    }

    @FXML
    void search(ActionEvent event) {
        String keyword = Searchfield.getText().toLowerCase().trim();

        ObservableList<Relever> allItems = listTerrain.getItems();
        ObservableList<Relever> filteredList = FXCollections.observableArrayList();

        // Vérifier si le champ de recherche n'est pas vide
        if (!keyword.isEmpty()) {
            for (Relever terrain : allItems) {
                if (terrain.getOperation().toLowerCase().contains(keyword)) {
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
        SRelever service = new SRelever();
        try {
            ObservableList<Relever> relevers = FXCollections.observableArrayList(service.afficher());
            listTerrain.setItems(relevers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void pdf(ActionEvent event) {
        // Créer une instance de la classe Pdf
        Pdf pdfGenerator = new Pdf();

        try {
            // Appeler la méthode generatePdf en passant le nom du fichier PDF
            pdfGenerator.generatePdf("releves");
            showAlert("Succès", "Le PDF a été généré avec succès.");
        } catch (IOException | SQLException e) {
            showAlert("Erreur", "Une erreur est survenue lors de la génération du PDF : " + e.getMessage());
        }
    }




    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
