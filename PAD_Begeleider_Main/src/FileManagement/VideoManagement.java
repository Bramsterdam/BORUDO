/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManagement;

import Controller.ManagementController;
import static FileManagement.MusicManagement.olMusic;
import SQL_Queries.SQL;
import Tables.Videos;
import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class VideoManagement extends ManagementScreen {

    //Section: Selecting file from computer
    private FileChooser fileChooser;
    private File file;
    private final Desktop desktop = Desktop.getDesktop();
    private String pathFile;

    //Section: Connecting to database
    Connection connection;
    private static Statement stmnt;
    private static PreparedStatement pst;

    //Creates the panes that that allow for managing the video's  
    VBox videoOverview = new VBox();
    GridPane addVideoPane = new GridPane();

    //Section: Adding
    Label lbVideoManagement = new Label("Video's toevoegen");
    Label infoLocation = new Label("Selecteer het bestand om toe te voegen");
    Button searchButton = new Button("Bestand zoeken");
    String defaultNoFile = "Geen bestand geselecteerd";
    Label lbLocation = new Label(defaultNoFile);
    String selectedVideo = "";

    //Section: File information
    Label lbTitle = new Label("Titel:");
    TextField tfTitle = new TextField();
    String defaultTfTitle = "Geef de video een titel";
    Label lbDescription = new Label("Omschrijving van de video:");
    TextField tfDescription = new TextField();
    String defaultTfDescription = "Geef een korte beschrijving van de video";
    Button addButton = new Button("Toevoegen");

    //TableView that for showing all videos'
    public static ObservableList<Videos> olVideo = FXCollections.observableArrayList();
    TableView<Videos> videoTableView = new TableView<>();

    //Button for removing a file from the database
    Button removeButton = new Button("Selectie verwijderen");
    
    public VideoManagement() {

        //Creates the pop-up window to choose a file, sets the type of file also
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4")
        );

        //Initializes TableView
            loadVideos();

        //Adds all elements to create a list where you can see and remove entries from the database
        videoOverview.getChildren().addAll(videoTableView, removeButton);
        removeButton.prefWidthProperty().bind(videoTableView.widthProperty());
        
        TableViewSetup();

        //Adds all elements to allow the user to add new videos to the database
        addVideoPane.setAlignment(Pos.CENTER);
        addVideoPane.setVgap(5);
        addVideoPane.setPadding(new Insets(30, 30, 30, 30));

        //Section: Adding
        addVideoPane.add(lbVideoManagement, 0, 1, 3, 1);
        addVideoPane.add(infoLocation, 0, 3);
        addVideoPane.add(lbLocation, 0, 4, 3, 1);
        addVideoPane.add(searchButton, 1, 5);
        
        // remover
         removeButton.setFont(new Font("Arial", 25));

        //Section: File information
        addVideoPane.add(lbTitle, 0, 8);
        addVideoPane.add(tfTitle, 0, 9, 3, 1);
        addVideoPane.add(lbDescription, 0, 10);
        addVideoPane.add(tfDescription, 0, 11, 3, 1);
        addVideoPane.add(addButton, 2, 12);

        //Section: Spacing
        lbVideoManagement.setAlignment(Pos.CENTER);
        searchButton.setAlignment(Pos.CENTER_RIGHT);
        addButton.setAlignment(Pos.CENTER_RIGHT);

        //Section: Small changes
        lbVideoManagement.setFont(Font.font("Cambria", 32));
        tfTitle.prefWidthProperty().bind(addVideoPane.widthProperty());
        tfTitle.setPromptText(defaultTfTitle);
        tfDescription.setPromptText(defaultTfDescription);

        //Section: File selector
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4")
        );
        
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                file = fileChooser.showOpenDialog(searchButton.getScene().getWindow());
                if (file != null) {
                    pathFile = file.getAbsolutePath();
                    selectedVideo = pathFile;
                    System.out.println(pathFile);
                    System.out.println(selectedVideo);
                    lbLocation.setText(selectedVideo);
                }
            }
        });

        //Actions of addButton when clicked. Adds Video to the database
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SQL.AddVideo(selectedVideo,
                        tfTitle.getText(),
                        tfDescription.getText());
                
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
                
                SQL.RemoveVideo(videoTableView.getSelectionModel().getSelectedItem());
                olVideo.removeAll(olVideo);
                
                    reset();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Item is verwijderd \n");
                    alert.showAndWait();    
                
            }
        });
        
    }
    
    public VBox getVideoOverview() {
        videoTableView.prefWidthProperty().bind(ManagementController.managementQuickbar.widthProperty().divide(1.5));
        videoTableView.prefHeightProperty().bind(videoOverview.heightProperty());
        return videoOverview;
    }
    
    public GridPane getAddVideoPane() {
        return addVideoPane;
    }
    
    public void TableViewSetup() {
        
        TableColumn idColumn = new TableColumn("ID");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("videoID"));
        TableColumn videoTitleColumn = new TableColumn("Title");
        videoTitleColumn.prefWidthProperty().bind(videoTableView.widthProperty().divide(3));
        videoTitleColumn.setCellValueFactory(
                new PropertyValueFactory<>("videoTitle"));
        
        TableColumn descriptionColumn = new TableColumn("Beschrijving");
        descriptionColumn.prefWidthProperty().bind(videoTableView.widthProperty().divide(2));
        descriptionColumn.setCellValueFactory(
                new PropertyValueFactory<>("videoDescription"));
        
        videoTableView.getColumns().addAll(idColumn, videoTitleColumn, descriptionColumn);
        videoTableView.setItems(olVideo);
        
    }
    
    public void loadVideos() {
        olVideo.removeAll(olVideo);
        
        try {
            
            initializeDB();
            Statement state = connection.createStatement();
            ResultSet resultSet1 = state.executeQuery("SELECT idVideo, VideoTitle, VideoDescription from videos");
            
            while (resultSet1.next()) {
                olVideo.add(new Videos(resultSet1.getString(1), resultSet1.getString(2), resultSet1.getString(3)));
            }
        } catch (NullPointerException | SQLException ex) {
            Logger.getLogger(SQL.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        
        videoTableView.setItems(olVideo);
    }
    
    public void reset(){
        
        loadVideos();
        selectedVideo = defaultNoFile;
        lbLocation.setText(defaultNoFile);
        tfTitle.setPromptText(defaultTfTitle);
        tfTitle.clear();
        tfDescription.setPromptText(defaultTfDescription);
        tfDescription.clear();
        
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
