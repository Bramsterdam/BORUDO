/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManagement;

import Controller.ManagementController;
import static FileManagement.VideoManagement.olVideo;
import SQL_Queries.SQL;
import Tables.Photos;
import Tables.Videos;
import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale.Category;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

/**
 *
 * @author $Iwan Snapper
 */
public class PhotoManagement extends ManagementScreen {

    Connection connection;

    private FileChooser fileChooser;
    private File file;
    private final Desktop desktop = Desktop.getDesktop();
    private String pathFile = "";

    private static Statement stmnt;
    private static PreparedStatement pst;

    VBox photoOverview = new VBox();
    GridPane addPhotoPane = new GridPane();

    //Bestand zoeken
    Label lbPhotoManagement = new Label("Foto's toevoegen");
    Label infoLocation = new Label("Selecteer het bestand om toe te voegen");
    Button searchButton = new Button("Bestand zoeken");
    String defaultNoFile = "Geen bestand geselecteerd";
    Label lbLocation = new Label(defaultNoFile);
    String selectedFile = "";

    //Add new Categroy
    //Bestan Toevoegen
    Label lbTitle = new Label("Titel");
    TextField tfTitle = new TextField();
    String defaultTfTitle = "Geef de foto een title";
    Label lbCategory = new Label("Categorie:");
    ComboBox cbCategory = new ComboBox();
    String defaultCbCategory = "Voeg toe aan bestaande categorie of voeg nieuwe toe";
    Button addButton = new Button("Toevoegen");

    //Listview aanpassen en selecteren
    ObservableList<Photos> olPhoto = FXCollections.observableArrayList();
    TableView<Photos> photoTableView = new TableView<>();
    Button removeButton = new Button("Selectie verwijderen");

    public PhotoManagement() {

        photoOverview.getChildren().addAll(photoTableView, removeButton);

        removeButton.prefWidthProperty().bind(photoTableView.widthProperty());
        TableViewSetup();

        addPhotoPane.setAlignment(Pos.CENTER);
        addPhotoPane.setVgap(5);
        addPhotoPane.setPadding(new Insets(30, 30, 30, 30));

        addPhotoPane.add(lbPhotoManagement, 0, 1, 3, 1);
        addPhotoPane.add(infoLocation, 0, 3);

        addPhotoPane.add(lbLocation, 0, 4);
        addPhotoPane.add(searchButton, 1, 5);

        //Bestand Toevoegen
        addPhotoPane.add(lbTitle, 0, 7);
        addPhotoPane.add(tfTitle, 0, 8, 3, 1);
        addPhotoPane.add(lbCategory, 0, 10);
        addPhotoPane.add(cbCategory, 0, 11, 3, 1);
        addPhotoPane.add(addButton, 2, 12);

        lbPhotoManagement.setAlignment(Pos.CENTER);
        searchButton.setAlignment(Pos.CENTER_RIGHT);
        addButton.setAlignment(Pos.CENTER_RIGHT);

        lbPhotoManagement.setFont(Font.font("Cambria", 32));
        tfTitle.prefWidthProperty().bind(addPhotoPane.widthProperty());
        tfTitle.setPromptText(defaultTfTitle);
        cbCategory.prefWidthProperty().bind(addPhotoPane.widthProperty());
        cbCategory.setEditable(true);
        cbCategory.setPromptText(defaultCbCategory);

        //Creates the pop-up window to choose a file, sets the type of file also
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );

        searchButton.setOnAction(e -> {
            //button event that triggers fileschooser and retrieves the pathfile
            file = fileChooser.showOpenDialog(searchButton.getScene().getWindow());
            if (file != null) {
                pathFile = file.getAbsolutePath();

                lbLocation.setText(pathFile);
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                /**
                 * Try catch implementeren
                 */
                SQL.AddPhoto(selectedFile,
                        tfTitle.getText(),
                        cbCategory.getSelectionModel().getSelectedItem().toString());

                reset();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Item is toegevoegd \n");
                alert.showAndWait();
            }
        });

        removeButton.setMinHeight(50);
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                SQL.RemovePhoto(photoTableView.getSelectionModel().getSelectedItem());
                olPhoto.removeAll(olPhoto);

                reset();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Item is verwijderd \n");
                alert.showAndWait();    
                
            }
        });
    }

    public VBox getPhotoOverview() {
        photoTableView.prefWidthProperty().bind(ManagementController.managementQuickbar.widthProperty().divide(1.5));
        photoTableView.prefHeightProperty().bind(photoOverview.heightProperty());
        return photoOverview;
    }

    public GridPane getAddPhotoPane() {
        return addPhotoPane;
    }

    public void TableViewSetup() {

        TableColumn idColumn = new TableColumn("ID");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("photoID"));

        TableColumn photoTitleColumn = new TableColumn("Title");
        photoTitleColumn.prefWidthProperty().bind(photoTableView.widthProperty().divide(2.5));
        photoTitleColumn.setCellValueFactory(
                new PropertyValueFactory<>("photoTitle"));

        TableColumn categoryColumn = new TableColumn("Category");
        categoryColumn.prefWidthProperty().bind(photoTableView.widthProperty().divide(4));
        categoryColumn.setCellValueFactory(
                new PropertyValueFactory<>("photoSlideshow"));

        photoTableView.getColumns().addAll(idColumn, photoTitleColumn, categoryColumn);
        photoTableView.setItems(olPhoto);

    }

    public void loadPhotos(){
        olPhoto.removeAll(olPhoto);
        
        initializeDB();
        try {

            Statement state = connection.createStatement();
            ResultSet resultSet1 = state.executeQuery("select idPhotos,PhotoTitle,PTheme from Photos");

            while (resultSet1.next()) {
                olPhoto.add(new Photos(resultSet1.getString("idPhotos"), resultSet1.getString("PhotoTitle"), resultSet1.getString("PTheme")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void reset(){
        
        loadPhotos();
        SQL.fillThemeCB(cbCategory);
        selectedFile = defaultNoFile;
        lbLocation.setText(defaultNoFile);
        tfTitle.setPromptText(defaultTfTitle);
        tfTitle.clear();
        cbCategory.setPromptText(defaultCbCategory);
        cbCategory.setValue(null);
        
    }

    private void initializeDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/borudo", "amsta1", "appel123");
            System.out.println("Database connected");

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Class not found");
        }

        System.out.println("Gelukt");
    }

}
