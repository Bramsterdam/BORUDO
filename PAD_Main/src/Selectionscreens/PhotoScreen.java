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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import pad.PAD;

/**
 * This class creates a selection menu from which a user can select a category
 * Based on this category a slideshow is displayed based on the photo related to that category
 * @author $Iwan Snapper
 */
public class PhotoScreen implements SelectionMenu {

    Connection connection;

    //Timeline with how long every picture stays on screen
    Timeline tl;
    int waitTime = 20;
    
    //Set button size
    final int BUTTON_WIDTH = 500;
    final int BUTTON_HEIGHT = 430;
    final int NAVIGATE_SLIDESHOW = 150;

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

    //Initialize photoplayer
    Image testphoto = new Image("file:src/Resources/Music/cover/Anberlin.png", 500, 500, true, true);
    ImageView photoViewer = new ImageView(testphoto);

    //Create pane for the photoviewer
    GridPane photoSelectionPane = new GridPane();
    BorderPane photoPane = new BorderPane();
    StackPane photoPlayer = new StackPane();
    AnchorPane backOnePane = new AnchorPane();
    AnchorPane forwardOnePane = new AnchorPane();
    
    //Buttons for navigating within a photo album
    Button backOne = new Button();
    Button forwardOne = new Button();

    public PhotoScreen() {
        
        // Initialization
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

        //Create and design button for navigating back in the slideshow
        Image navigateBack = new Image("file:src/Resources/Navigateback.png");
        ImageView navigateBackView = new ImageView(navigateBack);
        backOne.setGraphic(navigateBackView);
        backOnePane.setTopAnchor(backOne, 0.0);
        backOnePane.setBottomAnchor(backOne, 0.0);
        backOne.setMinWidth(NAVIGATE_SLIDESHOW);
        backOne.setMaxWidth(NAVIGATE_SLIDESHOW);
        backOne.setMinHeight(1000);
        backOnePane.setLeftAnchor(navigateBackView, 0.0);
        backOnePane.setRightAnchor(navigateBackView, 0.0);
        backOnePane.setTopAnchor(navigateBackView, 0.0);
        backOnePane.setBottomAnchor(navigateBackView,0.0);
        navigateBackView.fitWidthProperty().bind(backOne.widthProperty());
        
        //Create and design button for navigating foward in the slideshow
        Image navigateForward = new Image("file:src/Resources/Navigateforward.png");
        ImageView navigateForwardView = new ImageView(navigateForward);
        forwardOne.setGraphic(navigateForwardView);
        forwardOnePane.setTopAnchor(forwardOne, 0.0);
        forwardOnePane.setBottomAnchor(forwardOne, 0.0);
        forwardOne.setMinWidth(NAVIGATE_SLIDESHOW);
        forwardOne.setMaxWidth(NAVIGATE_SLIDESHOW);
        forwardOne.setMinHeight(1000);
        forwardOnePane.setLeftAnchor(navigateForwardView, 0.0);
        forwardOnePane.setRightAnchor(navigateForwardView, 0.0);
        forwardOnePane.setTopAnchor(navigateForwardView, 0.0);
        forwardOnePane.setBottomAnchor(navigateForwardView,0.0);
        navigateForwardView.fitWidthProperty().bind(forwardOne.widthProperty());

        //Change size of the photoviewer
        photoViewer.fitWidthProperty().bind(photoPlayer.widthProperty());
        photoViewer.fitHeightProperty().bind(photoPlayer.heightProperty());
        photoViewer.prefWidth(BUTTON_HEIGHT);
        photoViewer.setPreserveRatio(true);

        //Create photoviewer
        photoPlayer.getChildren().add(photoViewer);
        backOnePane.getChildren().add(backOne);
        forwardOnePane.getChildren().add(forwardOne);
        photoPane.setLeft(backOnePane);
        photoPane.setCenter(photoPlayer);
        photoPane.setRight(forwardOnePane);
        
        photoPlayer.setAlignment(Pos.CENTER);
        
        Randomize();
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
                }

                //Obtains all files from corresponding theme. in random order
                resultSet1 = state.executeQuery("select PhotoPath from photos where PTheme = '" + selectedID + "' ORDER BY RAND() ;");
                while (resultSet1.next()) {
                    selectedPath = resultSet1.getString("PhotoPath");
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
        String cover = slideshow.get(0);
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

    /**
     * Plays a slideshow based on the given arraylist of images
     * @param playlist arraylist containng all filepaths
     * @param photoNr arraylist index
     */
    public void playSlideshow(ArrayList<String> playlist, int photoNr) {
        
        //Create current and next image index
        int currentNr = photoNr;
        int nextNr = (photoNr + 1);

        //Load images
        String selectedPath = playlist.get(photoNr);
        Image selectedPhoto = new Image(selectedPath,1500,1000, true,true);
        photoViewer.setImage(selectedPhoto);
        photoViewer.fitWidthProperty().bind(photoPlayer.widthProperty());

        //Set next photo to be played after a certain amount of time has passed.
        //If there is a next photo, else return to home menu 
        this.tl = new Timeline(new KeyFrame(Duration.seconds(waitTime), (ActionEvent event) -> { 
        if (nextNr < playlist.size()) {
            playSlideshow(playlist, nextNr);
        } else {
            DisplayControl.turnOffMedia();
            DisplayControl.setHomescreen();
        }
        }));
        
        
        tl.play();

        //Display previous image after pressing button
        backOne.setOnAction((ActionEvent event) -> {
            if (currentNr > 0) {
                tl.stop();
                playSlideshow(playlist, (currentNr - 1));
            }

        });

        //Display next image after pressing button, if there is one, else turn off and return to home menu
        forwardOne.setOnAction((ActionEvent event) -> {
            tl.stop();
            if (nextNr < playlist.size()) {
                playSlideshow(playlist, nextNr);
            } else {
                DisplayControl.turnOffMedia();
                DisplayControl.setHomescreen();
            }

        });
    }

    /**
     * Stop slideshow and timer to stop the imageviewer from restarting
     */
    public void stopSlideshow() {
        if(IdleScreen.getPlaying()== true){
        tl.stop();
        }
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
    public BorderPane getPhotoPlayer() {

        return photoPane;
    }

    public void initializeDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/borudo", "amsta1", "appel123");

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Class not found");
        }
    }

}
