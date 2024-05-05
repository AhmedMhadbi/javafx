package com.example.bankmangment;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import entities.Relever;
import entities.Rembourssement;
import entities.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import services.EmailService;
import services.SRelever;
import services.SRembourssement;
import services.STransaction;

import javax.mail.MessagingException;

public class TransactionController {



    @FXML
    private ResourceBundle resources;
    @FXML
    private TextField iba;
    @FXML
    private URL location;

    @FXML
    private Label welcome;

    @FXML
    private TextField nomm;

    @FXML
    private Button moditr;

    @FXML
    private Button supptr;

    @FXML
    private ImageView adminimage;

    @FXML
    private Label adminname;

    @FXML
    private Label adminid;

    @FXML
    private ListView<Transaction> listTerrain;

    @FXML
    private TextField pre;

    @FXML
    private TextField nomr;

    @FXML
    private TextField iban;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField mont;
    @FXML
    private Button aj;
    @FXML
    void ajt(ActionEvent event) throws MessagingException {
        try {
            String nomDestinataire = nomr.getText();
            String prenomDestinataire = pre.getText();
            String ibanDestinataire = iban.getText();
            String iban = iba.getText();
            Double montant = Double.parseDouble(mont.getText());
            LocalDate dateTransaction = datePicker.getValue();

            // Vérification de la longueur des champs
            if (nomDestinataire.length() < 3 || prenomDestinataire.length() < 3 || ibanDestinataire.length() < 3) {
                showAlert("Erreur", "Les champs doivent contenir au moins 3 caractères.");
                return; // Arrêter l'exécution si les conditions ne sont pas remplies
            }

            // Vérification de la date
            if (dateTransaction.isBefore(LocalDate.now())) {
                showAlert("Erreur", "La date de transaction ne peut pas être dans le passé.");
                return; // Arrêter l'exécution si les conditions ne sont pas remplies
            }


            Transaction newTransaction = new Transaction();
            newTransaction.setNom_destinataire(nomDestinataire);
            newTransaction.setPrenom_destinataire(prenomDestinataire);
            newTransaction.setIban_destinataire(ibanDestinataire);
            newTransaction.setIban(iban);
            newTransaction.setMontant(montant);
            newTransaction.setDate_transaction(dateTransaction);
            newTransaction.setClient_id(1);
            newTransaction.setClient2_id(2);


            STransaction service = new STransaction();
            service.ajouter(newTransaction);


            EmailService emailService= new EmailService();
            // Code pour envoyer l'e-mail
            emailService.sendEmail(iban,"test","test content");


            Relever newRelever = new Relever();
            newRelever.setCredit(-montant);
            newRelever.setIban(ibanDestinataire);
            newRelever.setOperation("virement");
            newRelever.setDebit(1000.0-montant);
            newRelever.setDate(dateTransaction);
            SRelever services = new SRelever();
            services.ajouter(newRelever);

            showAlert("Succès", "La transaction a été ajoutée avec succès.");


            refreshTransactionsList();

            // Effacer les champs du formulaire
            nomm.clear();
            pre.clear();
            iba.clear();
            mont.clear();
            datePicker.setValue(null);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez vérifier les données saisies.");
        }
    }



    @FXML
    void moditr(ActionEvent event) {
        Transaction selectedTransaction = listTerrain.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            // Récupération des valeurs des champs de texte
            String nomDestinataire = nomr.getText().trim();
            String prenomDestinataire = pre.getText().trim();
            String ibanDestinataire = iban.getText().trim();
            String iban = iba.getText().trim();
            Double montant = Double.parseDouble(mont.getText().trim());
            LocalDate date = datePicker.getValue();


            // Vérification de la longueur des champs
            if (nomDestinataire.length() < 3 || prenomDestinataire.length() < 3 || ibanDestinataire.length() < 3 || iban.length() < 3) {
                showAlert("Erreur", "Les champs doivent contenir au moins 3 caractères.");
                return; // Arrêter l'exécution si les conditions ne sont pas remplies
            }

            // Vérification de la date
            if (date == null || date.isBefore(LocalDate.now())) {
                showAlert("Erreur", "La date doit être correctement définie et ne peut pas être dans le passé.");
                return; // Arrêter l'exécution si les conditions ne sont pas remplies
            }

            // Mise à jour de l'objet Transaction
            selectedTransaction.setNom_destinataire(nomDestinataire);
            selectedTransaction.setPrenom_destinataire(prenomDestinataire);
            selectedTransaction.setIban_destinataire(ibanDestinataire);
            selectedTransaction.setIban(iban);
            selectedTransaction.setMontant(montant);
            selectedTransaction.setDate_transaction(date);
            selectedTransaction.setClient_id(1);
            selectedTransaction.setClient2_id(2);


            // Mise à jour de la transaction dans la base de données
            try {
                STransaction service = new STransaction();
                service.modifier(selectedTransaction); // Assurez-vous que la méthode `modifier` existe et fonctionne correctement

                // Actualisation de la liste
                listTerrain.getItems().set(listTerrain.getSelectionModel().getSelectedIndex(), selectedTransaction);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("La transaction a été mise à jour avec succès.");
                alert.showAndWait();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Échec de la mise à jour de la transaction");
                alert.setContentText("Une erreur est survenue : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Si aucune transaction n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une transaction à modifier.");
            alert.showAndWait();
        }
    }

    @FXML
    void supptr(ActionEvent event) {
        Transaction selectedTransaction = listTerrain.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            STransaction service = new STransaction(); // Service adapté à 'Transaction'
            try {
                // Suppression de la transaction, en utilisant l'ID approprié
                service.supprimer(selectedTransaction.getId()); // Adaptez le nom de méthode et l'acquisition de l'ID

                // Suppression de l'élément de la ListView
                listTerrain.getItems().remove(selectedTransaction);

                // Effacer la sélection
                listTerrain.getSelectionModel().clearSelection();

                // Nettoyage des champs textuels
                nomm.clear();
                pre.clear();
                nomr.clear();
                iban.clear();
                iba.clear();
                mont.clear();
                datePicker.setValue(null);

                // Afficher une alerte de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("La transaction a été supprimée avec succès.");
                alert.showAndWait();
            } catch (SQLException e) {
                // Afficher une alerte d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Échec de la suppression de la transaction");
                alert.setContentText("Une erreur est survenue : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Afficher une alerte d'avertissement si rien n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une transaction à supprimer.");
            alert.showAndWait();
        }
    }


    @FXML
    void initialize() {
        assert listTerrain != null : "fx:id=\"listTerrain\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert supptr != null : "fx:id=\"supptr\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert moditr != null : "fx:id=\"moditr\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert nomm != null : "fx:id=\"nomm\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert pre != null : "fx:id=\"pre\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert nomr != null : "fx:id=\"nomr\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert iban != null : "fx:id=\"iban\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert datePicker != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'AdminRelever.fxml'.";
        assert mont != null : "fx:id=\"mont\" was not injected: check your FXML file 'AdminRelever.fxml'.";

        STransaction serviceRelever = new STransaction();
        try {
            ObservableList<Transaction> relevers = FXCollections.observableArrayList(serviceRelever.afficher());
            listTerrain.setItems(relevers);

            listTerrain.setCellFactory(listView -> new ListCell<>() {
                @Override
                public void updateItem(Transaction relever, boolean empty) {
                    super.updateItem(relever, empty);
                    if (empty || relever == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(10);
                        Label idLabel = new Label(String.valueOf(relever.getId()));
                        idLabel.setMinWidth(100);
                        Label debitLabel = new Label(String.valueOf(relever.getNom_destinataire()));
                        debitLabel.setMinWidth(100);
                        Label creditLabel = new Label(String.valueOf(relever.getPrenom_destinataire()));
                        creditLabel.setMinWidth(100);
                        Label ibanLabel = new Label(relever.getIban_destinataire());
                        ibanLabel.setMinWidth(100);
                        Label ibaLabel = new Label(relever.getIban());
                        ibaLabel.setMinWidth(100);
                        Label operationLabel = new Label(String.valueOf(relever.getMontant()));

                        operationLabel.setMinWidth(100);
                        Label dateLabel = new Label(relever.getDate_transaction().toString());
                        dateLabel.setMinWidth(100);
                        hbox.getChildren().addAll(idLabel, debitLabel, creditLabel, ibanLabel, operationLabel, dateLabel);
                        setGraphic(hbox);
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

        listTerrain.setOnMouseClicked(event -> {
            Transaction selectedItem = listTerrain.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                nomm.setText(selectedItem.getNom_destinataire());
                pre.setText(selectedItem.getPrenom_destinataire());
                nomr.setText(selectedItem.getNom_destinataire());
                iban.setText(selectedItem.getIban_destinataire());
                iba.setText(selectedItem.getIban_destinataire());
                mont.setText(String.valueOf(selectedItem.getMontant()));
                datePicker.setValue(selectedItem.getDate_transaction());
            }
        });
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void refreshTransactionsList() {
        try {
            STransaction serviceRelever = new STransaction();
            ObservableList<Transaction> transactions = FXCollections.observableArrayList(serviceRelever.afficher());
            listTerrain.setItems(transactions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

