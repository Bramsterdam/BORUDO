/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManagement;

import Controller.ManagementController;
import SQL_Queries.SQL;
import Tables.Songs;
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
public class MusicManagement extends ManagementScreen {

    Connection connection;
    
    private FileChooser fileChooser;
    private File file;
    private final Desktop desktop = Desktop.getDesktop();
    private String pathFile = "";

    private static Statement stmnt;
    private static PreparedStatement pst;

    VBox musicOverview = new VBox();
    GridPane addMusicPane = new GridPane();

    //Bestand zoeken
    Label lbMusicManagement = new Label("Muziek toevoegen");
    Label infoLocation = new Label("Selecteer het bestand om toe te voegen");
    Button searchButton = new Button("Bestand zoeken");
    String defaultNoFile = "Geen bestand geselecteerd";
    Label lbLocation = new Label(defaultNoFile);
    String selectedFile = "";

    //Bestand Toevoegen
    Label lbTitle = new Label("Titel:");
    TextField tfTitle = new TextField();
    String defaultTfTitle = "Geef het lied een titel";

    Label lbArtist = new Label("Artiest:");
    ComboBox cbArtist = new ComboBox();
    String defaultCbArtis = "Geef de naam van de artiest";

    Label lbPlaylist = new Label("Afspeellijst:");
    ComboBox cbPlaylist = new ComboBox();
    String defaultCbPlaylist = "Selecteer een bestaande playlist of creeer een nieuwe";
    Button addButton = new Button("Toevoegen");

    //Listview aanpassen en selecteren
    public static ObservableList<Songs> olMusic = FXCollections.observableArrayList();
    TableView<Songs> musicTableView = new TableView<>();
    Button removeButton = new Button("Selectie verwijderen");
   

    public MusicManagement() {

        //List that shows all music
        musicOverview.getChildren().addAll(musicTableView, removeButton);

        removeButton.prefWidthProperty().bind(musicTableView.widthProperty());
        TableViewSetup();

        addMusicPane.setAlignment(Pos.CENTER);
        addMusicPane.setVgap(5);
        addMusicPane.setPadding(new Insets(30, 30, 30, 30));

        addMusicPane.add(lbMusicManagement, 0, 1, 3, 1);
        addMusicPane.add(infoLocation, 0, 3);

        addMusicPane.add(lbLocation, 0, 4, 3, 1);
        addMusicPane.add(searchButton, 1, 5);

        //Bestand Toevoegen
        addMusicPane.add(lbTitle, 0, 7);
        addMusicPane.add(tfTitle, 0, 8, 3, 1);
        addMusicPane.add(lbArtist, 0, 10);
        addMusicPane.add(cbArtist, 0, 11, 3, 1);
        addMusicPane.add(lbPlaylist, 0, 12);
        addMusicPane.add(cbPlaylist, 0, 13, 3, 1);
        addMusicPane.add(addButton, 2, 14);

        lbMusicManagement.setAlignment(Pos.CENTER);
        searchButton.setAlignment(Pos.CENTER_RIGHT);
        addButton.setAlignment(Pos.CENTER_RIGHT);

        lbMusicManagement.setFont(Font.font("Cambria", 32));

        tfTitle.prefWidthProperty().bind(addMusicPane.widthProperty());
        tfTitle.setPromptText(defaultTfTitle);

        cbArtist.prefWidthProperty().bind(addMusicPane.widthProperty());
        cbArtist.setEditable(true);
        cbArtist.setPromptText(defaultCbArtis);

        cbPlaylist.setEditable(true);
        cbPlaylist.prefWidthProperty().bind(addMusicPane.widthProperty());
        cbPlaylist.setPromptText(defaultCbPlaylist);
        
        //button beschrijving
         removeButton.setFont(new Font("Arial", 25));

        //Creates the pop-up window to choose a file, sets the type of file also
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3")
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

                SQL.AddMusic(lbLocation.getText(),
                        tfTitle.getText(),
                        cbPlaylist.getSelectionModel().getSelectedItem().toString(),
                        cbArtist.getSelectionModel().getSelectedItem().toString());
                
                    Reset();
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

                SQL.RemoveMusic(musicTableView.getSelectionModel().getSelectedItem());
                olMusic.removeAll(olMusic);

                    Reset();
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Item is verwijderd \n");
                    alert.showAndWait();    
            }
        });
    }

    public VBox getMusicOverview() {
        musicTableView.prefWidthProperty().bind(ManagementController.managementQuickbar.widthProperty().divide(1.5));
        musicTableView.prefHeightProperty().bind(musicOverview.heightProperty());
        return musicOverview;
    }

    public GridPane getAddMusicPane() {
        return addMusicPane;
    }

    public void TableViewSetup() {

        TableColumn idColumn = new TableColumn("ID");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("songID"));
        TableColumn songTitleColumn = new TableColumn("Title");
        songTitleColumn.prefWidthProperty().bind(musicTableView.widthProperty().divide(2.5));
        songTitleColumn.setCellValueFactory(
                new PropertyValueFactory<>("songTitle"));

        TableColumn playlistColumn = new TableColumn("Playlist");
        playlistColumn.prefWidthProperty().bind(musicTableView.widthProperty().divide(4));
        playlistColumn.setCellValueFactory(
                new PropertyValueFactory<>("songPlaylist"));

        TableColumn artistColumn = new TableColumn("Artiest");
        artistColumn.prefWidthProperty().bind(musicTableView.widthProperty().divide(4));
        artistColumn.setCellValueFactory(
                new PropertyValueFactory<>("songArtist"));

        musicTableView.getColumns().addAll(idColumn, songTitleColumn, playlistColumn, artistColumn);
        musicTableView.setItems(olMusic);

    }

    public void loadSongs(){

        olMusic.removeAll(olMusic);
        try {
            

            initializeDB();
            Statement state = connection.createStatement();
            ResultSet resultSet1 = state.executeQuery("select idMusic,musicTitle,mPlaylist,mArtist from Music");

            while (resultSet1.next()) {
                olMusic.add(new Songs(resultSet1.getString("idMusic"), resultSet1.getString("musicTitle"), resultSet1.getString("mPlaylist"), resultSet1.getString("mArtist")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(SQL.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void Reset(){
        
        loadSongs();
        SQL.fillPlaylistCB(cbPlaylist);
        SQL.fillArtistCB(cbArtist);
        selectedFile = defaultNoFile;
        lbLocation.setText(defaultNoFile);
        tfTitle.setPromptText(defaultTfTitle);
        tfTitle.clear();
        cbArtist.setPromptText(defaultCbArtis);
        cbArtist.setValue(null);
        cbPlaylist.setPromptText(defaultCbPlaylist);
        cbPlaylist.setValue(null);
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
