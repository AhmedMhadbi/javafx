package com.example.bankmangment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entities.Etablissement;
import entities.Pdf;
import entities.Rembourssement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.SEtablissement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import services.SRembourssement;


public class etablissementController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private VBox vboxTerrain;
    @FXML private TextField nomet, add, num;
    @FXML private ImageView qrCodeImage; // ImageView pour afficher le QR Code
    @FXML
    private Button pdf;
    private Etablissement selectedEtablissement;

    @FXML
    private TextField Searchfield;
    @FXML
    private ObservableList<Etablissement> TerrainsList;



    @FXML
    void ajt(ActionEvent event) {
        try {
            Etablissement etablissement = new Etablissement(1, Integer.parseInt(num.getText().trim()), add.getText().trim(), nomet.getText().trim());
            SEtablissement service = new SEtablissement();
            service.ajouter(etablissement);
            HBox hbox = createEtablissementHBox(etablissement);
            vboxTerrain.getChildren().add(hbox);
            showAlert("Succès", "L'établissement a été ajouté avec succès.");
            clearInputFields();
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le numéro de contact doit être un nombre.");
        }
    }

    @FXML
    void moditr(ActionEvent event) {
        if (selectedEtablissement != null) {
            try {
                selectedEtablissement.setNom(nomet.getText().trim());
                selectedEtablissement.setAdresse(add.getText().trim());
                selectedEtablissement.setNum_contact(Integer.parseInt(num.getText().trim()));
                SEtablissement service = new SEtablissement();
                service.modifier(selectedEtablissement);
                refreshDisplay();
                showAlert("Succès", "L'établissement a été mis à jour avec succès.");
            } catch (NumberFormatException e) {
                showAlert("Erreur", "Le numéro de contact doit être un nombre.");
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur de mise à jour: " + e.getMessage());
            }
        } else {
            showAlert("Erreur", "Aucun établissement sélectionné.");
        }
    }

    @FXML
    void supptr(ActionEvent event) {
        if (selectedEtablissement != null) {
            try {
                SEtablissement service = new SEtablissement();
                service.supprimer(selectedEtablissement.getId());
                vboxTerrain.getChildren().removeIf(node -> node.getUserData() == selectedEtablissement);
                showAlert("Succès", "L'établissement a été supprimé avec succès.");
                clearInputFields();
                selectedEtablissement = null;
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la suppression: " + e.getMessage());
            }
        } else {
            showAlert("Erreur", "Aucun établissement sélectionné pour la suppression.");
        }
    }

    private HBox createEtablissementHBox(Etablissement etablissement) {
        HBox hbox = new HBox(10);
        hbox.setStyle("-fx-background-color: #6F73D2; -fx-padding: 10; -fx-border-color: #4c4177; -fx-border-width: 0 0 1 0;");
        Label nameLabel = new Label(etablissement.getNom());
        Label addressLabel = new Label(etablissement.getAdresse());
        Label contactLabel = new Label(String.valueOf(etablissement.getNum_contact()));
        hbox.getChildren().addAll(nameLabel, addressLabel, contactLabel);
        hbox.setUserData(etablissement);
        hbox.setOnMouseClicked(this::handleHBoxClick);
        return hbox;
    }

    private void handleHBoxClick(MouseEvent event) {
        HBox hbox = (HBox) event.getSource();
        selectedEtablissement = (Etablissement) hbox.getUserData();
        nomet.setText(selectedEtablissement.getNom());
        add.setText(selectedEtablissement.getAdresse());
        num.setText(String.valueOf(selectedEtablissement.getNum_contact()));
        try {
            Image qrCode = generateQRCode(selectedEtablissement.getAdresse());
            qrCodeImage.setImage(qrCode);
        } catch (WriterException | IOException e) {
            showAlert("Erreur", "Erreur lors de la génération du QR Code: " + e.getMessage());
        }
    }

    private void refreshDisplay() {
        vboxTerrain.getChildren().clear();
        SEtablissement service = new SEtablissement();
        try {
            ObservableList<Etablissement> etablissements = FXCollections.observableArrayList(service.afficher());
            for (Etablissement etablissement : etablissements) {
                vboxTerrain.getChildren().add(createEtablissementHBox(etablissement));
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des établissements: " + e.getMessage());
        }
    }

    private void clearInputFields() {
        nomet.clear();
        add.clear();
        num.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Image generateQRCode(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200, hints);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return SwingFXUtils.toFXImage(javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(pngData)), null);
    }

    @FXML
    void pdf(ActionEvent event) {
        // Créer une instance de la classe Pdf
        Pdf pdfGenerator = new Pdf();

        try {
            // Appeler la méthode generatePdf en passant le nom du fichier PDF
            pdfGenerator.generatePdfetablissement("Etablissement");
            showAlert("Succès", "Le PDF a été généré avec succès.");
        } catch (IOException | SQLException e) {
            showAlert("Erreur", "Une erreur est survenue lors de la génération du PDF : " + e.getMessage());
        }
    }


    @FXML
    private void search() {
        String searchText = Searchfield.getText().toLowerCase().trim();
        vboxTerrain.getChildren().clear(); // Effacer tous les éléments actuels

        SEtablissement service = new SEtablissement();
        try {
            ObservableList<Etablissement> etablissements = FXCollections.observableArrayList(service.afficher());
            for (Etablissement etablissement : etablissements) {
                if (etablissement.getNom().toLowerCase().contains(searchText) ||
                        etablissement.getAdresse().toLowerCase().contains(searchText) ||
                        String.valueOf(etablissement.getNum_contact()).contains(searchText)) {
                    vboxTerrain.getChildren().add(createEtablissementHBox(etablissement));
                }
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des établissements: " + e.getMessage());
        }
    }






    @FXML
    void initialize() {
        refreshDisplay();

        Searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            search();


        });

    }
}
