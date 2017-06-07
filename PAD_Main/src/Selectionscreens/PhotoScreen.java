/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Selectionscreens;

import Controller.DisplayControl;
import GUI.IdleScreen;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import pad.PAD;

/**
 *
 * @author $Iwan Snapper
 */
public class PhotoScreen implements SelectionMenu {

    Connection connection;

    
    Timeline tl;
    int waitTime = 20;
    
    final int BUTTON_WIDTH = 500;
    final int BUTTON_HEIGHT = 430;

    //Buttons and labels
    Button pickSlideShow1 = new Button();
    Button pickSlideShow2 = new Button();
    Button pickSlideShow3 = new Button();
    Button pickSlideShow4 = new Button();
    Button pickSlideShow5 = new Button();
    Button pickSlideShow6 = new Button();

    Label titleSlideShow1 = new Label("Slideshow Title");
    Label titleSlideShow2 = new Label("Slideshow Title");
    Label titleSlideShow3 = new Label("Slideshow Title");
    Label titleSlideShow4 = new Label("Slideshow Title");
    Label titleSlideShow5 = new Label("Slideshow Title");
    Label titleSlideShow6 = new Label("Slideshow Title");

    //Creates an array to hold the random generated number, selection buttons and info labels
    int[] Randomizer = {0, 1, 2, 3, 4, 5};
    Label[] photoLabels = {titleSlideShow1, titleSlideShow2, titleSlideShow3, titleSlideShow4, titleSlideShow5, titleSlideShow6};
    Button[] photoButtons = {pickSlideShow1, pickSlideShow2, pickSlideShow3, pickSlideShow4, pickSlideShow5, pickSlideShow6};

    Image testphoto = new Image("file:src/Resources/Music/cover/Anberlin.jpg", 500, 500, true, false);
    ImageView photoViewer = new ImageView(testphoto);

    GridPane photoSelectionPane = new GridPane();
    HBox photoPane = new HBox();
    Button backOne = new Button();
    Button fowardOne = new Button();

    public PhotoScreen() {
        photoViewer.setImage(testphoto);

        //Sets spacing for the selection menu
        photoSelectionPane.setAlignment(Pos.CENTER);
        photoSelectionPane.setHgap(35);
        photoSelectionPane.setVgap(40);

        //Buttons with an titel label placed on top
        photoSelectionPane.add(pickSlideShow1, 1, 1, 1, 2);
        photoSelectionPane.add(titleSlideShow1, 1, 1);

        photoSelectionPane.add(pickSlideShow2, 3, 1, 1, 2);
        photoSelectionPane.add(titleSlideShow2, 3, 1);

        photoSelectionPane.add(pickSlideShow3, 5, 1, 1, 2);
        photoSelectionPane.add(titleSlideShow3, 5, 1);

        photoSelectionPane.add(pickSlideShow4, 1, 3, 1, 2);
        photoSelectionPane.add(titleSlideShow4, 1, 3);

        photoSelectionPane.add(pickSlideShow5, 3, 3, 1, 2);
        photoSelectionPane.add(titleSlideShow5, 3, 3);

        photoSelectionPane.add(pickSlideShow6, 5, 3, 1, 2);
        photoSelectionPane.add(titleSlideShow6, 5, 3);

        photoSelectionPane.setStyle("-fx-background-color:#FFB266");

        photoPane.setStyle("-fx-background-color:#000000");

        backOne.prefHeightProperty().bind(photoPane.heightProperty());
        fowardOne.prefHeightProperty().bind(photoPane.heightProperty());

        photoViewer.fitHeightProperty().bind(photoPane.heightProperty());
        photoViewer.prefWidth(BUTTON_HEIGHT);
        photoViewer.setPreserveRatio(true);

        photoPane.setAlignment(Pos.CENTER);
        photoPane.getChildren().addAll(backOne, photoViewer, fowardOne);

    }

    /**
     * Randomizes all the buttons so that it shows a different category ever
     * time the selection menu is opened.
     */
    @Override
    public void Randomize() {

        initializeDB();

        try {

            //for everybutton a corresponding slideshow is extracted from the database
            for (int i = 0; i < photoButtons.length; i++) {

                //containers
                ArrayList<String> slideshowFiles = new ArrayList<>();
                String selectedID = "";
                String selectedPath = "";

                //Obtain info that is only needed once
                Statement state = connection.createStatement();
                ResultSet resultSet1 = state.executeQuery("select ptheme from PTheme ORDER BY RAND() LIMIT 1");
                while (resultSet1.next()) {
                    selectedID = resultSet1.getString("ptheme");
                    System.out.println(selectedID);
                }

                //Obtains all files from corresponding theme. in random order
                resultSet1 = state.executeQuery("select PhotoPath from photos where PTheme = '" + selectedID + "' ORDER BY RAND() ;");
                while (resultSet1.next()) {
                    selectedPath = resultSet1.getString("PhotoPath");
                    System.out.println(selectedPath + "Dit is de playlist");
                    slideshowFiles.add(selectedPath);

                }
                //Use previously obtained info to setup the corresponding button
                designButton(photoButtons[i], photoLabels[i], selectedID, slideshowFiles);
            }

        } catch (SQLException ex) {
            System.out.println("Failed");
        }

    }

    /**
     * Creates button with the proper picture and title of the category.
     *
     * @param button : The selected button
     * @param label : the label paired with the button
     * @param playlistTitle : Title of the slideshow
     * @param slideshow : list containing all files
     */
    public void designButton(Button button, Label label, String playlistTitle, ArrayList<String> slideshow) {

        //Creates and image for the coverpicture to place over the button
        String cover = "file:src\\Resources\\Music\\cover\\Anberlin.jpg";
        System.out.println(cover + "Dit is de cover");
        Image thumbnail = new Image(cover, 500, 500, true, false);
        ImageView thumbnailViewer = new ImageView();
        button.setGraphic(thumbnailViewer);
        thumbnailViewer.setImage(thumbnail);

        //Changes the size of the button and makes it display the coverimage over the button
        button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setMaxSize(BUTTON_WIDTH, BUTTON_HEIGHT);

        thumbnailViewer.fitHeightProperty().bind(pickSlideShow1.heightProperty());

        //Adds file info to the button
        label.setText(playlistTitle);

        //Loads the correct video into the video players, then proceed to play the video
        button.setOnAction((ActionEvent event) -> {

            PAD.setFullscreen();
            DisplayControl.playPhotoSlideshow();

            playSlideshow(slideshow, 0);

            IdleScreen.setPlayingTrue();
            IdleScreen.stopIdleTimer();

        });

    }

    public void playSlideshow(ArrayList<String> playlist, int photoNr) {
        System.out.println("De playlist speelt af");
        int currentNr = photoNr;
        int nextNr = (photoNr + 1);
        System.out.println(nextNr);

        String selectedPath = playlist.get(photoNr);
        System.out.println(selectedPath + "Deze foto wordt afgespeeld.");
        Image selectedPhoto = new Image("file:src/Resources/Music/cover/Anberlin.jpg", 500, 500, true, false);
        System.out.println(selectedPhoto);
        photoViewer.setImage(selectedPhoto);

        this.tl = new Timeline(new KeyFrame(Duration.seconds(waitTime), (ActionEvent event) -> { 
        if (nextNr < playlist.size()) {
            playSlideshow(playlist, nextNr);
        } else {
            DisplayControl.turnOffMedia();
            DisplayControl.setHomescreen();
        }
        }));
        
        
        tl.play();

        backOne.setOnAction((ActionEvent event) -> {
            if (currentNr > 0) {
                tl.stop();
                playSlideshow(playlist, (currentNr - 1));
            }

        });

        fowardOne.setOnAction((ActionEvent event) -> {
            tl.stop();
            if (nextNr < playlist.size()) {
                playSlideshow(playlist, nextNr);
            } else {
                DisplayControl.turnOffMedia();
                DisplayControl.setHomescreen();
            }

        });
    }

    public void stopSlideshow() {

    }

    /**
     * Returns the selecting screen and randomize everytime it's called upon
     *
     * @return selection menu
     */
    public GridPane getPhotoScreen() {

        Randomize();
        return photoSelectionPane;
    }

    /**
     * Returns a video player on which the user can display a video
     *
     * @return slideshow viewer
     */
    public HBox getPhotoPlayer() {

        return photoPane;
    }

    public void initializeDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/borudo", "amsta1", "appel123");
            System.out.println("Database connected");

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Class not found");
        }
    }

}
