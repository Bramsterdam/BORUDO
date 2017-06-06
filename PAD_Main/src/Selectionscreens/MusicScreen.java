/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Selectionscreens;

import Controller.DisplayControl;
import GUI.IdleScreen;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 *
 * @author $Iwan Snapper
 */
public class MusicScreen implements SelectionMenu {

    Connection connection;

    //Control Buttons for video in video (full)screen mode
    private String playUnicode = "▶";
    private String pauseUnicode = "❚❚";

    Button play = new Button(playUnicode);
    Button pause = new Button(pauseUnicode);
    Slider volumeSlider = new Slider();

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
    String coverPlaylist1 = "file:src/Resources/Music/cover/Anberlin.jpg";
    String music1 = "src/Resources/Music/A Day Late.mp3";

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
    HBox playButtons = new HBox();

    /**
     *
     */
    public MusicScreen() {
        imageView.setImage(coverImage);
        //Sets spacing for the selection menu
        musicSelectionPane.setAlignment(Pos.CENTER);
        musicSelectionPane.setHgap(100);
        musicSelectionPane.setVgap(40);

        //sets the button to full volume, instead of 0 volume
        volumeSlider.setValue(100);

        //event for playing video
        play.setOnAction(e -> {
            musicPlayer.play();
        });

        //event for stopping video
        pause.setOnAction(e -> {
            musicPlayer.pause();
        });

        //event for changing volume value
        volumeSlider.valueProperty().addListener((Observable ov) -> {
            if (volumeSlider.isValueChanging()) {
                musicPlayer.setVolume(volumeSlider.getValue() / 100.0);
            }
        });

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

        //HBox for the play buttons
        playButtons.setStyle("-fx-background-color:#000000");
        playButtons.setSpacing(10);
        playButtons.getChildren().addAll(play, pause, volumeSlider);

        //sets the correct alignment for the image and play/volume buttons
        musicPane.setAlignment(Pos.CENTER_RIGHT);
        playButtons.setAlignment(Pos.BOTTOM_LEFT);

        //Add nodes to the correct Panes
        musicPane.setMargin(imageView, new Insets(0, 100, 0, 0));
        musicPane.getChildren().addAll((playButtons), (mediaView), (imageView));

        //Initialize selection menu
        Randomize();

    }

    /**
     * Randomizes all the buttons so that it shows a different playlist ever
     * time the selection menu is opened.
     */
    @Override
    public void Randomize() {

        initializeDB();

        //For every button a new playlist is linked
        for (int i = 0; i < musicButtons.length; i++) {

            ArrayList<String> slideshowFiles = new ArrayList<>();
            Random random = new Random();
            String selectedID = "";
            String selectedPath = "";
            String selectedThumbnail = "";

            // when true the queue is based on the artist, if false based on album name
            if (random.nextBoolean() == true) {
                try {
                    //Obtain the data that is only relevant once per cycle from the database
                    Statement state = connection.createStatement();
                    ResultSet resultSet1 = state.executeQuery("select ArtistName, ArtistThumbnail from artist ORDER BY RAND() LIMIT 1");
                    while (resultSet1.next()) {
                        selectedID = resultSet1.getString("ArtistName");
                        selectedThumbnail = resultSet1.getString("ArtistThumbnail");
                    }

                    //Adds all paths of the songs to an array.
                    ResultSet resultSet2 = state.executeQuery("select musicPath from music where mArtist = '" + selectedID + "' ;");
                    while (resultSet2.next()) {
                        selectedPath = resultSet2.getString("musicPath");
                        slideshowFiles.add(selectedPath);

                    }
                    //Uses all the obtained info to setup the corresponding button
                    designButton(musicButtons[i], musicLabels[i], selectedID, slideshowFiles, selectedThumbnail);

                } catch (SQLException ex) {
                    System.out.println("Failed");
                }

            } else {

                //if album name is picked
                try {

                    //Obtain the data that is only relevant once per cycle from the database
                    Statement state = connection.createStatement();
                    ResultSet resultSet1 = state.executeQuery("select PlaylistName,PlaylistThumbnail from playlist ORDER BY RAND() LIMIT 1");
                    while (resultSet1.next()) {
                        selectedID = resultSet1.getString("PlaylistName");
                        selectedThumbnail = resultSet1.getString("PlaylistThumbnail");
                    }
                    ResultSet resultSet2 = state.executeQuery("select musicPath from music where mPlaylist = '" + selectedID + "'ORDER BY RAND() ;");

                    //Adds all paths of the songs to an array.
                    while (resultSet2.next()) {
                        selectedPath = resultSet2.getString("musicPath");
                        slideshowFiles.add(selectedPath);

                    }

                    //Uses all the obtained info to setup the corresponding button
                    designButton(musicButtons[i], musicLabels[i], selectedID, slideshowFiles, selectedThumbnail);
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
     * @param playlistTitle : little of the playlist or performing artist
     * @param playlist : the playlist containing paths to all the files
     * @param cover : cover image
     */
    public void designButton(Button button, Label label, String playlistTitle, ArrayList<String> playlist, String cover) {

        //Creates and image for the coverpicture to place over the button
        System.out.println(cover);
        Image thumbnail = new Image(cover, 500, 500, true, false);
        ImageView thumbnailView = new ImageView(thumbnail);
        System.out.println(thumbnail);

        //Changes the size of the button and makes it display the coverimage over the button
        button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setMaxSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        button.setGraphic(thumbnailView);
        thumbnailView.setImage(thumbnail);

        //Adds file info to the button
        label.setText(playlistTitle);

        //Loads the correct video into the video players, then proceed to play the video
        thumbnailView.fitHeightProperty().bind(pickPlaylist1.heightProperty());
        System.out.println("Nog niet gefaald");
        button.setOnAction((ActionEvent event) -> {

            IdleScreen.stopIdleTimer();
            IdleScreen.setPlayingTrue();

            //Displays the musicPlayer
            imageView.setImage(thumbnail);
            DisplayControl.playMusic();
            playNextMusic(playlist, 0);

        });
    }

    /**
     * Start playing the music
     */
    public void playNextMusic(ArrayList<String> playlist, int songnr) {

        System.out.println(songnr);

        Media media = new Media(new File(playlist.get(songnr)).toURI().toString());
        musicPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(musicPlayer);

        musicPlayer.setAutoPlay(true);

        int nextSong = songnr + 1;
        System.out.println(nextSong);

        if (nextSong < playlist.size()) {
            musicPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    if (IdleScreen.playing == true) {
                        playNextMusic(playlist, nextSong);

                    } else {
                        DisplayControl.turnOffMedia();
                        DisplayControl.setHomescreen();
                    }
                }
            });

        } else {
            musicPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    DisplayControl.turnOffMedia();
                    DisplayControl.setHomescreen();
                }
            });
        }

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
            connection = DriverManager.getConnection("jdbc:mysql://localhost/borudo", "amsta1", "appel123");
            System.out.println("Database connected");

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Class not found");
        }

        System.out.println("Gelukt");
    }

}
