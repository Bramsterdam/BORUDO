
package GUI;

import Controller.DisplayControl;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author Iwan
 * 
 * This class creates a VBox that contains several buttons that allow for easy navigation between menu's without having to 
 * return to a main screen.
 */

public class Quickbar {
    
    //Buttons for switching between functionalities.
    public VBox quickbar = new VBox(40);
    private Button quickHome = new Button();
    private Button quickPhoto = new Button();
    private Button quickVideo = new Button();
    private Button quickMusic = new Button();
    
    public Quickbar() {
        
        quickbar.setAlignment(Pos.CENTER);
        quickbar.setPadding(new Insets(30,30,30,30));
        quickbar.setStyle("-fx-background-color:#A0A0A0");
        
        // Button to access the home screen
        createQuickbarButton(quickHome, "file:src/Resources/Homeicon.png");
        quickHome.setOnAction((ActionEvent event) -> {
            DisplayControl.setHomescreen();
        });
        
        // Button to access the photo menu
        createQuickbarButton(quickPhoto, "file:src/Resources/Photo.jpg");
        quickPhoto.setOnAction((ActionEvent event) -> {
         DisplayControl.setPhotoMenu();   
        });
        
        // Button to access the video menu
        createQuickbarButton(quickVideo, "file:src/Resources/Clapperboard.png");
        quickVideo.setOnAction((ActionEvent event) -> {
            DisplayControl.setVideoMenu();
        });
        
        //Button to access the game menu
        createQuickbarButton(quickMusic, "file:src/Resources/Carddeck.jpg");
        quickMusic.setOnAction((ActionEvent event) -> {
            DisplayControl.setMusicMenu();
        });
    
        // add all buttons to the quickbar for easy access
        quickbar.getChildren().addAll(quickHome,quickPhoto,quickVideo,quickMusic);
    }
    
    /** 
     * When given the location of a picture this class will give the button the correct dimension for the home screen.
     * 
     * @param button the button that needs configuration
     * @param iconPath The path to the image that needs te be used
     */
    private void createQuickbarButton (Button button, String iconPath){
        Image icon = new Image(iconPath, 100,100, false,false);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setGraphic(new ImageView(icon));   
    }
    
    //Returns the quickbar as a VBox so it can be placed on either side of the applciation.
    public VBox getQuickbar () {
        return quickbar;
    }
}
