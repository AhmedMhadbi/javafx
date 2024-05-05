package com.example.bankmangment;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import entities.Credit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import services.SCredit;

public class CreditController {

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
    private ListView<Credit> listTerrain;

    @FXML
    private TextField mont;

    @FXML
    private TextField mens;

    @FXML
    private TextField montep;

    @FXML
    private TextField tp;

    @FXML
    private TextField nbm;

    @FXML
    private TextField montpy;

    @FXML
    private TextField st;

    @FXML
    private TextField ti;

    @FXML
    private TextField ds;

    @FXML
    private DatePicker datePicker;

    @FXML
    private DatePicker datePicker1;

    @FXML
    private DatePicker datePicker2;

    @FXML
    private ImageView image_id;

    @FXML
    private Button Import_btn;

    private String imagePath;
    private Image image;
    @FXML
    private AnchorPane principal;

    private final SCredit sa = new SCredit();


    @FXML
    private void ajt(ActionEvent event) {
        // Récupération des valeurs des champs de texte
        int nbMois = Integer.parseInt(nbm.getText().trim());
        double montant = Double.parseDouble(mont.getText().trim());
        double montantEmprunte = Double.parseDouble(montep.getText().trim());
        double montantPaye = Double.parseDouble(montpy.getText().trim());
        double mensualite = Double.parseDouble(mens.getText().trim());
        double tauxInteret = Double.parseDouble(ti.getText().trim());
        String type = tp.getText().trim();
        String status = st.getText().trim();
        String description = ds.getText().trim();
        LocalDate dateDemande = datePicker.getValue();
        LocalDate dateEmission = datePicker1.getValue();
        LocalDate dateEcheance = datePicker2.getValue();


        // Vérification de la longueur des champs type, status et description
        if (type.length() < 3 || status.length() < 3 || description.length() < 3) {
            showAlert("Erreur de saisie", "Les champs 'Type', 'Statut' et 'Description' doivent contenir au moins 3 caractères.");
            return;
        }

        // Vérification des dates
        LocalDate currentDate = LocalDate.now();
        if (dateDemande.isBefore(currentDate) || dateEmission.isBefore(currentDate) || dateEcheance.isBefore(currentDate)) {
            showAlert("Erreur de saisie", "Les dates ne peuvent pas être dans le passé.");
            return;
        }

        // Création de l'objet Credit
        Credit newCredit = new Credit();
        newCredit.setNb_mois(nbMois);
        newCredit.setMontant(montant);
        newCredit.setMontant_emprunte(montantEmprunte);
        newCredit.setMontant_paye(montantPaye);
        newCredit.setMensualite(mensualite);
        newCredit.setTaux_interet(tauxInteret);
        newCredit.setType(type);
        newCredit.setStatus(status);
        newCredit.setDescription(description);
        newCredit.setDate_demande(dateDemande);
        newCredit.setDate_emission(dateEmission);
        newCredit.setDate_echeance(dateEcheance);
        newCredit.setImage_name(imagePath); // Assuming imagePath is managed similarly

        // Ajout du crédit à la base de données
        try {
            SCredit service = new SCredit(); // Assurez-vous que le service adapté à 'Credit' est utilisé
            service.ajouter(newCredit); // Assurez-vous que la méthode `ajouter` existe et fonctionne correctement
            showAlert("Succès", "Le crédit a été ajouté avec succès.");
        } catch (Exception e) {
            showAlert("Erreur lors de l'ajout", e.getMessage());
        }
        listTerrain.getItems().set(listTerrain.getSelectionModel().getSelectedIndex(), newCredit);

        // Réinitialiser les champs du formulaire
        nbm.clear();
        mont.clear();
        montep.clear();
        montpy.clear();
        mens.clear();
        ti.clear();
        tp.clear();
        st.clear();
        ds.clear();
        datePicker.setValue(null);
        datePicker1.setValue(null);
        datePicker2.setValue(null);
    }

    @FXML
    void moditr(ActionEvent event) {
        Credit selectedCredit = listTerrain.getSelectionModel().getSelectedItem(); // Assurez-vous que 'listTerrain' est votre ListView pour les crédits
        if (selectedCredit != null) {
            try {
                // Récupération des valeurs des champs de texte
                int id = selectedCredit.getId();
                int nbMois = Integer.parseInt(nbm.getText());
                double montant = Double.parseDouble(mont.getText());
                double montantEmprunte = Double.parseDouble(montep.getText());
                double montantPaye = Double.parseDouble(montpy.getText());
                double mensualite = Double.parseDouble(mens.getText());
                double tauxInteret = Double.parseDouble(ti.getText());
                String type = tp.getText();
                String status = st.getText();
                String description = ds.getText();
                LocalDate dateDemande = datePicker.getValue();
                LocalDate dateEmission = datePicker1.getValue();
                LocalDate dateEcheance = datePicker2.getValue();


                // Vérification de la longueur des champs type, status et description
                if (type.length() < 3 || status.length() < 3 || description.length() < 3) {
                    showAlert("Erreur de saisie", "Les champs 'Type', 'Statut' et 'Description' doivent contenir au moins 3 caractères.");
                    return;
                }

                // Vérification des dates
                LocalDate currentDate = LocalDate.now();
                if (dateDemande.isBefore(currentDate) || dateEmission.isBefore(currentDate) || dateEcheance.isBefore(currentDate)) {
                    showAlert("Erreur de saisie", "Les dates ne peuvent pas être dans le passé.");
                    return;
                }

                // Mise à jour de l'objet Credit
                selectedCredit.setNb_mois(nbMois);
                selectedCredit.setMontant(montant);
                selectedCredit.setMontant_emprunte(montantEmprunte);
                selectedCredit.setMontant_paye(montantPaye);
                selectedCredit.setMensualite(mensualite);
                selectedCredit.setTaux_interet(tauxInteret);
                selectedCredit.setType(type);
                selectedCredit.setStatus(status);
                selectedCredit.setDescription(description);
                selectedCredit.setImage_name(imagePath); // Mise à jour du nom de l'image avec le chemin imagePath
                selectedCredit.setDate_demande(dateDemande);
                selectedCredit.setDate_emission(dateEmission);
                selectedCredit.setDate_echeance(dateEcheance);

                // Mise à jour du crédit dans la base de données
                SCredit service = new SCredit(); // Assurez-vous que le service adapté à 'Credit' est utilisé
                service.modifier(selectedCredit); // Assurez-vous que la méthode `modifier` existe et fonctionne correctement

                // Actualisation de la liste
                listTerrain.getItems().set(listTerrain.getSelectionModel().getSelectedIndex(), selectedCredit);

                // Affichage d'une alerte de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Le crédit a été mis à jour avec succès.");
                alert.showAndWait();
            } catch (NumberFormatException e) {
                // Affichage d'une alerte en cas d'erreur de format numérique
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Format de données incorrect");
                alert.setContentText("Veuillez entrer des données numériques valides.");
                alert.showAndWait();
            } catch (Exception e) {
                // Affichage d'une alerte en cas d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Échec de la mise à jour du crédit");
                alert.setContentText("Une erreur est survenue : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Si aucun crédit n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un crédit à modifier.");
            alert.showAndWait();
        }
    }



    @FXML
    void supptr(ActionEvent event) {
        Credit selectedCredit = listTerrain.getSelectionModel().getSelectedItem(); // Assurez-vous que 'listTerrain' est votre ListView pour les crédits
        if (selectedCredit != null) {
            SCredit service = new SCredit();
            try {
                // Appel de la méthode pour supprimer le crédit, en supposant qu'il existe une méthode comme deleteCredit
                service.supprimer(selectedCredit.getId()); // Utilisez getId ou votre méthode actuelle pour obtenir l'ID

                // Supprimer l'élément sélectionné de la ListView
                listTerrain.getItems().remove(selectedCredit);

                // Effacer la sélection
                listTerrain.getSelectionModel().clearSelection();

                // Effacer les champs de texte si nécessaire
                nomm.clear();
                mont.clear();
                mens.clear();
                montep.clear();
                tp.clear();
                nbm.clear();
                montpy.clear();
                st.clear();
                ti.clear();
                ds.clear();
                datePicker.setValue(null);
                datePicker1.setValue(null);
                datePicker2.setValue(null);

                // Afficher une alerte de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Le crédit a été supprimé avec succès.");
                alert.showAndWait();
            } catch (SQLException e) {
                // Afficher une alerte d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Échec de la suppression du crédit");
                alert.setContentText("Une erreur est survenue : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Afficher une alerte d'avertissement
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un crédit à supprimer.");
            alert.showAndWait();
        }
    }


    @FXML
    void initialize() {
        assert listTerrain != null : "fx:id=\"listTerrain\" was not injected: check your FXML file 'AdminCredit.fxml'.";

        try {
            ObservableList<Credit> credits = FXCollections.observableArrayList(sa.afficher());
            listTerrain.setItems(credits);

            listTerrain.setCellFactory(listView -> new ListCell<>() {
                @Override
                public void updateItem(Credit credit, boolean empty) {
                    super.updateItem(credit, empty);
                    if (empty || credit == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(10); // HBox avec un espacement de 10
                        Label nameLabel = new Label(credit.getType());
                        nameLabel.setMinWidth(100); // Définir une largeur minimale pour l'étiquette
                        Label descLabel = new Label(credit.getDescription());
                        descLabel.setMinWidth(100); // Définir une largeur minimale pour l'étiquette
                        Label webLabel = new Label(credit.getStatus());
                        webLabel.setMinWidth(100); // Définir une largeur minimale pour l'étiquette
                        hbox.getChildren().addAll(nameLabel, descLabel, webLabel);
                        setGraphic(hbox); // Définir la mise en page personnalisée comme graphique de la cellule de liste
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur de base de données", "Échec du chargement des données de crédit.");
        }

        listTerrain.setOnMouseClicked(event -> {
            Credit selectedItem = listTerrain.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Remplir les champs de texte avec les valeurs du crédit sélectionné
                nomm.setText(selectedItem.getType());
                mont.setText(String.valueOf(selectedItem.getMontant()));
                mens.setText(String.valueOf(selectedItem.getMensualite()));
                montep.setText(String.valueOf(selectedItem.getMontant_emprunte()));
                tp.setText(selectedItem.getType());
                nbm.setText(String.valueOf(selectedItem.getNb_mois()));
                montpy.setText(String.valueOf(selectedItem.getMontant_paye()));
                st.setText(selectedItem.getStatus());
                ti.setText(String.valueOf(selectedItem.getTaux_interet()));
                ds.setText(selectedItem.getDescription());
                datePicker.setValue(selectedItem.getDate_demande());
                datePicker1.setValue(selectedItem.getDate_emission());
                datePicker2.setValue(selectedItem.getDate_echeance());

                // Assuming you store the image path as a URL or path string in the Association
                if (selectedItem.getImage_name() != null && !selectedItem.getImage_name().isEmpty()) {
                    imagePath = selectedItem.getImage_name();
                    image_id.setImage(new Image(imagePath));
                } else {
                    imagePath = null;
                    image_id.setImage(null); // No image available for this association
                }
            }
        });
    }




    @FXML
    public void Import(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null); // Changed from principal.getScene().getWindow() to null for simplicity
        if (file != null) {
            imagePath = file.toURI().toString();
            // Supprimer le préfixe "file:/"
            if (imagePath.startsWith("file:/")) {
                imagePath = imagePath.substring(6); // Enlève "file:/" du début du chemin
            }
            image_id.setImage(new Image(imagePath));
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

