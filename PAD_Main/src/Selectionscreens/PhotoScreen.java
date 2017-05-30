/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Selectionscreens;

import Controller.DisplayControl;
import java.io.File;
import java.sql.Connection;
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

/**
 *
 * @author $Iwan Snapper
 */
public class PhotoScreen implements SelectionMenu {

    Connection connection;

    final int BUTTON_WIDTH = 500;
    final int BUTTON_HEIGHT = 430;

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
        photoSelectionPane.setHgap(100);
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

        ArrayList<String> slideshowFiles = new ArrayList<>();

        String selectedID = "";
        String selectedPath = "";
        String selectedTitle = "Geen titel";

        try {

            for (int i = 0; i < photoButtons.length; i++) {

                Statement state = connection.createStatement();
                ResultSet resultSet1 = state.executeQuery("select ptheme from PTheme ORDER BY RAND() LIMIT 1");
                while (resultSet1.next()) {
                    selectedID = resultSet1.getString("ptheme");
                    System.out.println(selectedID);
                }
                resultSet1 = state.executeQuery("select PhotoPath from photos where PTheme = '" + selectedID + "' ;");
                while (resultSet1.next()) {
                    selectedPath = resultSet1.getString(1);
                    slideshowFiles.add(selectedPath);

                    designButton(photoButtons[i], photoLabels[i], selectedID, slideshowFiles);
                }
            }

        } catch (SQLException ex) {
            System.out.println("Failed");
        }

        
    }

    /**
     * Creates button with the proper picture and title of the category.
     *
     * @param button
     */
    public void designButton(Button button, Label label, String playlistTitle, ArrayList<String> slideshow) {

        //Creates and image for the coverpicture to place over the button
        Image thumbnail = new Image(slideshow.get(0), 500, 500, true, false);
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

}
