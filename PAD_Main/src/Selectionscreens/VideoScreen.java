package Selectionscreens;

import Controller.DisplayControl;
import GUI.IdleScreen;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.util.Duration;
import pad.PAD;

/**
 *
 * @author Iwan
 *
 * This class display a selection of videos from which the user can make a
 * selection. The options are randomized out of a bigger pool of options
 */
public class VideoScreen implements SelectionMenu {

    Connection connection;

    private static Statement stmnt;
    private static PreparedStatement pst;
    
    //Control Buttons for video in video (full)screen mode
    private String playUnicode = "▶";
    private String pauseUnicode = "❚❚";
        
    Button play = new Button();
    Button pause = new Button();
    Slider volumeSlider = new Slider();

    static boolean playing = false;

    final int BUTTON_WIDTH = 680;
    final int BUTTON_HEIGHT = 430;

    final Label IMPORTANT_INFO = new Label("Klik hieronder op een video");

    //Buttons and labels used to select the video
    Button pickVideo1 = new Button();
    Button pickVideo2 = new Button();
    Button pickVideo3 = new Button();
    Button pickVideo4 = new Button();

    Label titleVideo1 = new Label("Video Info");
    Label titleVideo2 = new Label("Video Info");
    Label titleVideo3 = new Label("Video Info");
    Label titleVideo4 = new Label("Video Info");

    //Placeholder video, are to be removed once a database has been implemented
    String Video1 = "src/Resources/Videos/Test (1).mp4";

    //Contains all the button that are use to select a video
    Button[] videoButtons = {pickVideo1, pickVideo2, pickVideo3, pickVideo4};
    Label[] videoLabels = {titleVideo1, titleVideo2, titleVideo3, titleVideo4};

    //Creates the mediaplayer that shows all videos.
    Media queuedVideo = new Media(new File(Video1).toURI().toString());
    MediaPlayer videoPlayer = new MediaPlayer(queuedVideo);
    MediaView mediaView = new MediaView(videoPlayer);

    //Panes to be added to a scene
    GridPane videoSelectionPane = new GridPane();
    StackPane videoPane = new StackPane();
    BorderPane videoBorderPane = new BorderPane();
    VBox playButtons = new VBox();
    
    public VideoScreen() {

        //Creates the layout of the video Screen
        videoSelectionPane.setAlignment(Pos.CENTER);
        videoSelectionPane.setHgap(100);
        videoSelectionPane.setVgap(40);

        //Sets the width and heigth of the playbuttons
        play.setMinWidth(BUTTON_WIDTH/2);
        pause.setMinWidth(BUTTON_WIDTH/2);
        play.setMaxHeight(40);
        play.setMaxHeight(40);
        Image playImage = new Image("file:src/Resources/play.png");
        Image pauseImage = new Image("file:src/Resources/pause.png");
        play.setGraphic(new ImageView(playImage));
        pause.setGraphic(new ImageView(pauseImage));
                
        //sets the button to full volume, instead of 0 volume
        volumeSlider.setValue(100);
                
        //sets the size and font of the playbuttons
        play.setMinWidth(100);
        pause.setMinWidth(100);
                
        //event for playing video
        play.setOnAction(e ->{
            videoPlayer.play();
        });
        
        //event for stopping video
        pause.setOnAction(e ->{
            videoPlayer.pause();
        });
        
        //event for changing volume value
        volumeSlider.valueProperty().addListener((Observable ov) -> {
            if (volumeSlider.isValueChanging()) {
                videoPlayer.setVolume(volumeSlider.getValue() / 100.0);
            }});
        
        //Shows the user what to do
        IMPORTANT_INFO.setFont(Font.font("Cambria", 32));

        //videoSelectionPane.add(IMPORTANT_INFO, 2, 0, 2, 1);

        //Buttons with an titel label placed on top
        videoSelectionPane.add(pickVideo1, 1, 1, 1, 2);
        videoSelectionPane.add(titleVideo1, 1, 1);

        videoSelectionPane.add(pickVideo2, 3, 1, 1, 2);
        videoSelectionPane.add(titleVideo2, 3, 1);

        videoSelectionPane.add(pickVideo3, 1, 3, 1, 2);
        videoSelectionPane.add(titleVideo3, 1, 3);

        videoSelectionPane.add(pickVideo4, 3, 3, 1, 2);
        videoSelectionPane.add(titleVideo4, 3, 3);

        videoSelectionPane.setStyle("-fx-background-color:#FFB266");

        videoPane.setStyle("-fx-background-color:#000000");
        
        //HBox for the play buttons
        playButtons.setStyle("-fx-background-color:#000000");
        playButtons.setSpacing(15);
        playButtons.getChildren().addAll(play, pause, volumeSlider);
        
        //sets the correct alignment for the image and play/volume buttons
        mediaView.fitWidthProperty().bind(videoPane.widthProperty());
        mediaView.fitHeightProperty().bind(videoPane.heightProperty());
        videoPane.getChildren().add(mediaView);

        //Add nodes to the correct Panes
        playButtons.setAlignment(Pos.CENTER);
        videoBorderPane.setCenter(videoPane);
        videoBorderPane.setLeft(playButtons);
        
        //Initialize selection menu
        Randomize();

    }

    /**
     * Randomizes all the buttons so that it shows a different category ever
     * time the selection menu is opened.
     */
    @Override
    public void Randomize() {

        int selectedID = 1;
        String selectedPath = "";
        String selectedTitle = "Geen titel";

        initializeDB();

        //For every selection button this loop assigns it a specific ID #.
        for (int i = 0; i < videoButtons.length; i++) {

            try {
                //Obtain relevant information from the video
                Statement state = connection.createStatement();
                ResultSet resultSet1 = state.executeQuery("select idVideo from videos ORDER BY RAND() LIMIT 1");
                while (resultSet1.next()) {
                    selectedID = resultSet1.getInt(1);
                    System.out.println(selectedID);
                }
                //Obtain the location of the video file
                resultSet1 = state.executeQuery("select VideoPath, VideoTitle from videos where idVideo = " + selectedID + ";");
                while (resultSet1.next()) {
                    selectedPath = resultSet1.getString(1);
                    System.out.println(selectedPath);
                    selectedTitle = resultSet1.getString(2);
                }

                //Design a button based on the previously obtained buttons
                designButton(videoButtons[i], videoLabels[i], selectedPath, selectedTitle);

            } catch (SQLException ex) {
                System.out.println("Failed");
            }

        }
    }

    /**
     * This method will change the way a button looks and will handle based on
     * the file given
     *
     * @param button : The button that is changing
     * @param label : the corresponding information label for the aforementioned
     * button
     * @param fileLocation  : Location at which the file is found
     * @param fileTitle : Title of the video
     */
    public void designButton(Button button, Label label, String fileLocation, String fileTitle) {

        //Adds a mediaplayer that shows the video as a preview
        Media previewMedia = new Media(new File(fileLocation).toURI().toString());
        MediaPlayer previewPlayer = new MediaPlayer(previewMedia);
        MediaView previewViewer = new MediaView(previewPlayer);

        // changes the preview video to match the button size
        previewViewer.fitWidthProperty().bind(pickVideo1.widthProperty());
        previewViewer.setMediaPlayer(previewPlayer);

        label.setText(fileTitle);

        //Changes the size of the button and makes it play a small preview video
        button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setMaxSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setGraphic(previewViewer);

        //Causes the video to play, whilst being muted
        previewPlayer.setMute(true);
        previewPlayer.setStartTime(Duration.seconds(2));
        previewPlayer.setAutoPlay(false);

        button.setOnAction((ActionEvent event) -> {
            
            
            PAD.setFullscreen();
            
            IdleScreen.setPlayingTrue();
            IdleScreen.stopIdleTimer();
            
            

            //Loads the correct video into the video players, then proceed to play the video
            Media media = new Media(new File(fileLocation).toURI().toString());
            videoPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(videoPlayer);

            DisplayControl.playVideo();

        });

    }

    //Play video
    public void playVideo() {
        videoPlayer.setOnEndOfMedia((new Runnable() {
                @Override
                public void run() {
                    videoPlayer.setAutoPlay(true);
                    DisplayControl.turnOffMedia();
                    DisplayControl.setHomescreen();
                }
            }));
        
        videoPlayer.play();
        IdleScreen.stopIdleTimer();
    }

   /**
    * Stop the video from playing
    */
    public void stopVideo() {
        videoPlayer.stop();
    }

    /**
     * This methode returns the pane from which the user can select a video to
     * play
     *
     * @return a pane with all the selection buttons
     */
    public GridPane getVideoScreen() {

        Randomize();
        return videoSelectionPane;
    }

    /**
     * Returns a video player on which the user can display a video
     *
     * @return a videoplayer
     */
    public BorderPane getVideoPlayer() {

        return videoBorderPane;
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
