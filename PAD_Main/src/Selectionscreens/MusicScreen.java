/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Selectionscreens;

import Controller.DisplayControl;
import Selectionscreens.SelectionMenu;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javax.swing.text.StyledEditorKit;

/**
 *
 * @author $Iwan Snapper
 */
public class MusicScreen implements SelectionMenu {

    Connection connection;

    final int BUTTON_WIDTH = 500;
    final int BUTTON_HEIGHT = 430;
    boolean playing = false;

    //Create selection buttons and labels
    Button pickPlaylist1 = new Button();
    Button pickPlaylist2 = new Button();
    Button pickPlaylist3 = new Button();
    Button pickPlaylist4 = new Button();

    Label titlePlaylist1 = new Label("Playlist title");
    Label titlePlaylist2 = new Label("Playlist title");
    Label titlePlaylist3 = new Label("Playlist title");
    Label titlePlaylist4 = new Label("Playlist title");

    //Temporarily add cover images and music path for the lack of a database
    String coverPlaylist1 = "file:src/Resources/Music/Test (1).jpg";
    String music1 = "src/Resources/Music/Anberlin (1).mp3";

    //Creates an array to hold the random generated number, selection buttons and info labels
    int[] RandomizerID = {0, 1, 2, 3};
    Boolean[] RandomizerIsArtist = {true, false, true, false};
    Button[] musicButtons = {pickPlaylist1, pickPlaylist2, pickPlaylist3, pickPlaylist4};
    Label[] musicLabels = {titlePlaylist1, titlePlaylist2, titlePlaylist3, titlePlaylist4};

    //Creates the mediaplayer that plays all music.
    Media queuedVideo = new Media(new File(music1).toURI().toString());
    MediaPlayer musicPlayer = new MediaPlayer(queuedVideo);
    MediaView mediaView = new MediaView(musicPlayer);

    //Create image view with a sample image
    Image coverImage = new Image("file:src/Resources/Carddeck.jpg");
    ImageView imageView = new ImageView(coverImage);

    //Panes to be added to a scene
    GridPane musicSelectionPane = new GridPane();
    StackPane musicPane = new StackPane();

    /**
     *
     */
    public MusicScreen() {

        //Sets spacing for the selection menu
        musicSelectionPane.setAlignment(Pos.CENTER);
        musicSelectionPane.setHgap(100);
        musicSelectionPane.setVgap(40);

        //Buttons with an titel label placed on top
        musicSelectionPane.add(pickPlaylist1, 1, 1, 1, 2);
        musicSelectionPane.add(titlePlaylist1, 1, 1);

        musicSelectionPane.add(pickPlaylist2, 3, 1, 1, 2);
        musicSelectionPane.add(titlePlaylist2, 3, 1);

        musicSelectionPane.add(pickPlaylist3, 1, 3, 1, 2);
        musicSelectionPane.add(titlePlaylist3, 1, 3);

        musicSelectionPane.add(pickPlaylist4, 3, 3, 1, 2);
        musicSelectionPane.add(titlePlaylist4, 3, 3);

        musicSelectionPane.setStyle("-fx-background-color:#FFB266");

        musicPane.setStyle("-fx-background-color:#000000");

        //Stretched the musicplay to fit the screen and set the correct positioning
        mediaView.fitHeightProperty().bind(musicPane.heightProperty());
        imageView.fitHeightProperty().bind(musicPane.heightProperty());
        imageView.setPreserveRatio(true);

        musicPane.setAlignment(Pos.CENTER_RIGHT);

        //Add nodes to the correct Panes
        musicPane.setMargin(imageView, new Insets(0, 100, 0, 0));
        musicPane.getChildren().add(mediaView);
        musicPane.getChildren().add(imageView);

        //Causes the music to play when called upon
        musicPlayer.setAutoPlay(true);

        //Initialize selection menu
        Randomize();

    }

    /**
     * Randomizes all the buttons so that it shows a different playlist ever
     * time the selection menu is opened.
     */
    @Override
    public void Randomize() {
        
      
        ArrayList<String> slideshowFiles = new ArrayList<>();

        String selectedID = "";
        String selectedPath = "";
        String selectedTitle = "Geen titel";
        String selectedThunbnail = "";
        
        initializeDB();
        //Create random numbers that decide what playlist gets queued
        for (int i = 0; i < musicButtons.length; i++) {

            int randomNumberIsArtist = (int) (Math.random() * (1));

            if (randomNumberIsArtist == 0) {

                try {
                    
                    Statement state = connection.createStatement();
                    ResultSet resultSet1 = state.executeQuery("select ArtistName, ArtistThumbnail from artist ORDER BY RAND() LIMIT 1");
                    while (resultSet1.next()) {
                        selectedID = resultSet1.getString("ArtistName");
                        selectedThunbnail = resultSet1.getString("ArtistThumbnail");
                    }
                    resultSet1 = state.executeQuery("select musicPath from music where mArtist = '" + selectedID + "' ;");
                    while (resultSet1.next()) {
                        selectedPath = resultSet1.getString("musicPath");
                        slideshowFiles.add(selectedPath);

                        designButton(musicButtons[i], musicLabels[i], selectedID, slideshowFiles, selectedThunbnail);
                    }

                } catch (SQLException ex) {
                    System.out.println("Failed");
                }

            } else {

                try {
                    Statement state = connection.createStatement();
                    ResultSet resultSet1 = state.executeQuery("select PlaylistName,PlaylistThumbnail from playlist ORDER BY RAND() LIMIT 1");
                    while (resultSet1.next()) {
                        selectedID = resultSet1.getString("PlaylistName");
                        System.out.println(selectedID);
                        selectedThunbnail = resultSet1.getString("PlatlistThumbnail");
                    }
                    resultSet1 = state.executeQuery("select musicPath from music where mPlaylist = '" + selectedID + "' ;");
                    while (resultSet1.next()) {
                        selectedPath = resultSet1.getString("musicPath");
                        slideshowFiles.add(selectedPath);

                        designButton(musicButtons[i], musicLabels[i], selectedID, slideshowFiles, selectedThunbnail);
                    }

                } catch (SQLException ex) {
                    System.out.println("Failed");
                }
            }
        }
    }

    /**
     * Creates button with the proper picture and title of the category.
     *
     * @param button : Button that needs to be changed
     * @param label : Label that shows info about the file
     * @param playlistTitle
     * @param playlist : the playlist containing paths to all the files
     * @param cover
     * @param image : Coverimage that shows what playlist it is.
     */
    public void designButton(Button button, Label label, String playlistTitle, ArrayList<String> playlist, String cover) {

        //Creates and image for the coverpicture to place over the button
        Image thumbnail = new Image(cover, 500, 500, true, false);
        ImageView thumbnailView = new ImageView(thumbnail);

        //Changes the size of the button and makes it display the coverimage over the button
        button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setMaxSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        button.setGraphic(thumbnailView);
        thumbnailView.setImage(thumbnail);

        //Adds file info to the button
        label.setText(playlistTitle);

        //Loads the correct video into the video players, then proceed to play the video
        thumbnailView.fitHeightProperty().bind(pickPlaylist1.heightProperty());

        button.setOnAction((ActionEvent event) -> {

            //Displays the musicPlayer
            imageView.setImage(thumbnail);
            Media media = new Media(new File(playlist.get(0)).toURI().toString());
            musicPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(musicPlayer);

            //start playing
            DisplayControl.playMusic();

        });

    }

    /**
     * Start playing the music
     */
    public void playMusic() {
        musicPlayer.play();
    }

    /**
     * Stop playing the music
     */
    public void stopMusic() {
        musicPlayer.stop();
    }

    /**
     *
     * @return
     */
    public GridPane getMusicScreen() {

        Randomize();
        return musicSelectionPane;
    }

    /**
     * Returns a video player on which the user can display a video
     *
     * @return
     */
    public StackPane getMusicPlayer() {

        return musicPane;
    }

    private void initializeDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/borudo", "teamrocket", "rickchardet");
            System.out.println("Database connected");

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Class not found");
        }

        System.out.println("Gelukt");
    }

}
    

