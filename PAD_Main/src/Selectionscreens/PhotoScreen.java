/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Selectionscreens;

import Controller.DisplayControl;
import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author $Iwan Snapper
 */
public class PhotoScreen implements SelectionMenu {

    Connection connection;

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

    GridPane photoSelectionPane = new GridPane();
    StackPane photoPane = new StackPane();

    public PhotoScreen() {

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
                    System.out.println(selectedPath);
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
        System.out.println(cover);
        Image thumbnail = new Image(cover, 400, 400, true, false);
        ImageView thumbnailView = new ImageView(thumbnail);

        //Changes the size of the button and makes it display the coverimage over the button
        button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setMaxSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        
        
        button.setGraphic(thumbnailView);
        thumbnailView.setImage(thumbnail);
        //Adds file info to the button
        label.setText(playlistTitle);

        //Loads the correct video into the video players, then proceed to play the video
        thumbnailView.fitHeightProperty().bind(pickSlideShow1.heightProperty());

        button.setOnAction((ActionEvent event) -> {

            
            
            
            
        });

    }

    /**
     * Returns the selecting screen and randomize everytime it's called upon
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
    public StackPane getPhotoPlayer() {

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
