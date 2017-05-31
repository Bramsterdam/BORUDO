package GUI;

import Controller.DisplayControl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author Iwan
 *
 * This class creates a home screen for Borudo. From this screen users will have
 * access to all functionalities and allows for a stand-by menu that users can
 * access when they don't intend to use the product straightaway
 */
public class Homescreen {

    
    //Navigation Buttons
    Button goToPhoto = new Button();
    Button goToVideo = new Button();
    Button goToGame = new Button();
    FlowPane homescreen = new FlowPane();

    public Homescreen() {
        
        //Sets layout
        homescreen.setHgap(40);
        homescreen.setAlignment(Pos.CENTER);
        homescreen.setStyle("-fx-background-color:#FFB266");

        // Button to access the photo menu
        createHomescreenButton(goToPhoto, "file:src/Resources/Photo.jpg");
        homescreen.getChildren().add(goToPhoto);
        goToPhoto.setOnAction((ActionEvent event) -> {
            DisplayControl.setPhotoMenu();
        });

        // Button to access the video menu
        createHomescreenButton(goToVideo, "file:src/Resources/Clapperboard.png");

        homescreen.getChildren().add(goToVideo);

        goToVideo.setOnAction((ActionEvent event) -> {
            DisplayControl.setVideoMenu();
        });

        // Button to access the game menu
        createHomescreenButton(goToGame, "file:src/Resources/Carddeck.jpg");
        homescreen.getChildren().add(goToGame);
        goToGame.setOnAction((ActionEvent event) -> {
            DisplayControl.setMusicMenu();
        });

    }

    /**
     * When given the location of a picture this class will give the button the
     * correct dimension for the home screen.
     *
     * @param button the button that needs configuration
     * @param iconPath The path to the image that needs te be used
     */
    private void createHomescreenButton(Button button, String iconPath) {

        Image icon = new Image(iconPath, 500, 500, false, false);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setGraphic(new ImageView(icon));
    }

    /**
     *
     * @return
     */
    public FlowPane getHomescreen() {
        return homescreen;
    }
}
