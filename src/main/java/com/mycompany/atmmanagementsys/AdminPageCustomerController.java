package com.mycompany.atmmanagementsys;


import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;

import entitie.Pdf;
import entitie.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import services.ServiceUser;
import utils.DB;

public class AdminPageCustomerController {


    @FXML
    private ImageView image_id;
    private String imagePath;
    private Image image;
    String AdminID;
    @FXML
    private AnchorPane principal;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label welcome;

    @FXML
    private TextField nomm;

    @FXML
    private ImageView adminimage;

    @FXML
    private Label adminname;

    @FXML
    private Label adminid;

    @FXML
    private ListView<User> listTerrain;

    @FXML
    private TextField Searchfield;

    @FXML
    private Button search;
    @FXML
    private Button pdf;

   

    @FXML
    private TableView<CustomerData> customertable;
    @FXML
    private TableColumn<CustomerData,String> cusname;
    @FXML
    private TableColumn<CustomerData,String> cusaddress;
    @FXML
    private TableColumn<CustomerData,String> cusemail;
    @FXML
    private TableColumn<CustomerData,String> cusphone;
    @FXML
    private TableColumn<CustomerData,Integer> cusbalance;
    private ObservableList<CustomerData>data;




    public void GetAdminID(String id) throws SQLException, FileNotFoundException, IOException{
        AdminID = id;
        Connection con = DB.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM admin WHERE id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            InputStream is = rs.getBinaryStream("image");
            OutputStream os = new FileOutputStream(new File("adminimage.jpeg"));
            byte[] content = new byte[1024];
            int s = 0;
            while((s= is.read(content))!= -1){
                os.write(content, 0, s);
            }
            Image image = new Image("file:adminimage.jpeg");
            adminimage.setImage(image);
            adminimage.setFitWidth(248);
            adminimage.setFitHeight(186);
            Circle clip = new Circle(93,93,93);
            adminimage.setClip(clip);
        }
        ps.close();
        rs.close();
       // con.close();
    }


    @FXML
    void ajt(ActionEvent event) {
        try {
            // Charge le FXML pour la vue ajoutEvent
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddCustomer.fxml"));
            Parent root = loader.load();

            // Obtient la scène du bouton actuel et y place le nouveau root
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            Scene scene = new Scene(root);

            scene.getStylesheets().add("/styles/AddCustomer.css");

            // Met à jour le titre de la fenêtre, si désiré
            stage.setTitle("Ajout d'un Nouvel Association");

            // Affiche la nouvelle vue
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Log l'exception
            showAlert("Erreur de Chargement", "Impossible de charger la vue d'ajout d'association.");
        }
    }



    @FXML
    void moditr(ActionEvent event) throws IOException {
        User selectedParticipation = listTerrain.getSelectionModel().getSelectedItem();
        if (selectedParticipation != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Editcust.fxml"));
            Parent root = loader.load();

            EditCustomerController modifierController = loader.getController();
            modifierController.setParticipationData(selectedParticipation);

            nomm.getScene().setRoot(root); // Assuming nomm is a component in your current scene
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a Participation to modify.");
            alert.showAndWait();
        }
    }

    @FXML
    void supptr(ActionEvent event) {
        User selectedAssociation = listTerrain.getSelectionModel().getSelectedItem(); // Make sure 'listAssociations' is your ListView for associations
        if (selectedAssociation != null) {
            ServiceUser sa = new ServiceUser();
            try {
                // Calling the method to delete the association, assuming there is a method like deleteAssociation
                sa.supprimer(selectedAssociation.getId()); // Use getId or your current method to obtain the ID

                // Remove the selected item from the ListView
                listTerrain.getItems().remove(selectedAssociation);

                // Clear the selection
                listTerrain.getSelectionModel().clearSelection();

                // Clear text fields if necessary
                nomm.clear();


                // Display a confirmation alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Customer a été supprimée avec succès.");
                alert.showAndWait();
            } catch (SQLException e) {
                // Display an error alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Échec de la suppression de Customer");
                alert.setContentText("Une erreur est survenue : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Display a warning alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une Customer à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    public void LoadCustomerData(ActionEvent event) throws SQLException{
        Connection con = DB.getInstance().getConnection();
        data = FXCollections.observableArrayList();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM user");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            data.add(new CustomerData(rs.getInt("id"),rs.getString("name"),rs.getString("address"),rs.getString("email"),rs.getString("Phone"),rs.getInt("balance")));
        }
        cusname.setCellValueFactory(new PropertyValueFactory<CustomerData,String>("CustomerName"));
        cusaddress.setCellValueFactory(new PropertyValueFactory<CustomerData,String>("CustomerAddress"));
        cusemail.setCellValueFactory(new PropertyValueFactory<CustomerData,String>("CustomerEmail"));
        cusphone.setCellValueFactory(new PropertyValueFactory<CustomerData,String>("CustomerPhone"));
        cusbalance.setCellValueFactory(new PropertyValueFactory<CustomerData,Integer>("CustomerBalance"));
        customertable.setItems(null);
        customertable.setItems(data);
        ps.close();
        rs.close();
       // con.close();
    }

    @FXML
    public void ResetPassword(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/PasswordReset.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/PasswordReset.css");
        Image icon = new Image("/icons/Password.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Passwor Reset Page");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void search(ActionEvent event) {
        String keyword = Searchfield.getText().toLowerCase().trim();

        ObservableList<User> allItems = listTerrain.getItems();
        ObservableList<User> filteredList = FXCollections.observableArrayList();

        // Vérifier si le champ de recherche n'est pas vide
        if (!keyword.isEmpty()) {
            for (User terrain : allItems) {
                if (terrain.getName().toLowerCase().contains(keyword)) {
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
        ServiceUser service = new ServiceUser();
        try {
            ObservableList<User> relevers = FXCollections.observableArrayList(service.afficher());
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
            pdfGenerator.generatePdf("Users");
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



    @FXML
    void initialize() {
        assert listTerrain != null : "fx:id=\"listTerrain\" was not injected: check your FXML file 'AdminProjects.fxml'.";

        ServiceUser serviceUser = new ServiceUser();
        try {
            ObservableList<User> utilisateurs = FXCollections.observableArrayList(serviceUser.afficher());
            listTerrain.setItems(utilisateurs);

            listTerrain.setCellFactory(listView -> new ListCell<User>() {
                @Override
                public void updateItem(User utilisateur, boolean empty) {
                    super.updateItem(utilisateur, empty);
                    if (empty || utilisateur == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(10); // HBox with spacing of 10
                        Label nomLabel = new Label(utilisateur.getName());
                        nomLabel.setMinWidth(100); // Set minimum width for the label
                        Label adresseLabel = new Label(utilisateur.getAddress());
                        adresseLabel.setMinWidth(100); // Set minimum width for the label
                        Label phoneLabel = new Label(utilisateur.getPhone());
                        phoneLabel.setMinWidth(100); // Set minimum width for the label
                        hbox.getChildren().addAll(nomLabel, adresseLabel, phoneLabel);
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
                User selectedItem = listTerrain.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    nomm.setText(selectedItem.getName());


                    if (selectedItem.getImage() != null && !selectedItem.getImage().isEmpty()) {
                        File file = new File(selectedItem.getImage());
                        if (file.exists()) {
                            imagePath = selectedItem.getImage();
                            image_id.setImage(new Image(file.toURI().toString()));
                        } else {
                            // Fichier image introuvable
                            System.out.println("Fichier image introuvable.");
                        }
                    } else {
                        imagePath = null;
                        image_id.setImage(null);  // Aucune image disponible pour cet événement
                    }



                }
            }
        });
    }

}



