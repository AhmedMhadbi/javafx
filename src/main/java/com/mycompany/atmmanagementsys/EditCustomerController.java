package com.mycompany.atmmanagementsys;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entitie.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import services.ServiceUser;

public class EditCustomerController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField uname;

    @FXML
    private TextField ph;

    @FXML
    private TextField eml;

    @FXML
    private TextField bal;

    @FXML
    private TextField pass;

    @FXML
    private Label addcusconfirm;

    @FXML
    private TextField add;

    @FXML
    private ImageView image_id;

    @FXML
    private Button Import_btn;

    @FXML
    private Button moditr;

    private String imagePath;
    private Image image;
    @FXML
    private AnchorPane principal;

    private User currentParticipation;


    public void setParticipationData(User participation) {

        this.currentParticipation = participation;

        if (participation != null) {
            pass.setText(participation.getPassword());
            uname.setText(participation.getName());
            add.setText(participation.getAddress());
            eml.setText(participation.getEmail());
            ph.setText(participation.getPhone());
            bal.setText(String.valueOf(participation.getBalance()));
        }
    }




    @FXML
    public void Import(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null); // Changed from principal.getScene().getWindow() to null for simplicity
        if (file != null) {
            imagePath = file.getAbsolutePath(); // Obtenez le chemin absolu du fichier
            image = new Image(file.toURI().toString());
            image_id.setImage(image);
        }
    }

    @FXML
    void moditr(ActionEvent event) {
        try {
            currentParticipation.setPassword(pass.getText());
            currentParticipation.setName(uname.getText());
            currentParticipation.setAddress(add.getText());
            currentParticipation.setEmail(eml.getText());
            currentParticipation.setPhone(ph.getText());

            currentParticipation.setBalance(Double.parseDouble(bal.getText()));
            currentParticipation.setImage(imagePath); // Assuming you handle images similarly

            ServiceUser servicePartc = new ServiceUser();
            servicePartc.modifier(currentParticipation);

            returnToParticipationListView();
        } catch (SQLException e) {
            e.printStackTrace();  // Log the exception, or handle it appropriately
        } catch (NumberFormatException e) {
            // This could happen when converting 'tele.getText()' to an integer
            System.out.println("Error in number formatting: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();  // Handle issues with loading the FXML
        }
    }


    private void returnToParticipationListView() throws IOException {
        // Charger le FXML pour votre vue de liste et l'appliquer
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminPageCustomer.fxml"));
        Parent root = loader.load();
        uname.getScene().setRoot(root);  // 'nom' est un champ existant dans le formulaire actuel, utilisé pour accéder à la scène
    }

    @FXML
    void initialize() {
        assert uname != null : "fx:id=\"uname\" was not injected: check your FXML file 'EditCustomer.fxml'.";
        assert ph != null : "fx:id=\"ph\" was not injected: check your FXML file 'EditCustomer.fxml'.";
        assert eml != null : "fx:id=\"eml\" was not injected: check your FXML file 'EditCustomer.fxml'.";
        assert bal != null : "fx:id=\"bal\" was not injected: check your FXML file 'EditCustomer.fxml'.";
        assert pass != null : "fx:id=\"pass\" was not injected: check your FXML file 'EditCustomer.fxml'.";
        assert addcusconfirm != null : "fx:id=\"addcusconfirm\" was not injected: check your FXML file 'EditCustomer.fxml'.";
        assert add != null : "fx:id=\"add\" was not injected: check your FXML file 'EditCustomer.fxml'.";
        assert image_id != null : "fx:id=\"image_id\" was not injected: check your FXML file 'EditCustomer.fxml'.";
        assert Import_btn != null : "fx:id=\"Import_btn\" was not injected: check your FXML file 'EditCustomer.fxml'.";

    }
}
