
package Controller;

import GUI.Homescreen;
import GUI.Quickbar;
import Selectionscreens.MusicScreen;
import Selectionscreens.VideoScreen;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author Iwan
 * 
 * This class manages the whats display get access at any point. And can be accessed by other classes when there has to be navigated between different displays 
 */
public class DisplayControl {
    private static Homescreen homescreen = new Homescreen();
    private static BorderPane UserInterface = new BorderPane ();
    private static Quickbar quickbar = new Quickbar();
    private static VideoScreen videoscreen = new VideoScreen();
    private static MusicScreen musicscreen = new MusicScreen();
    
    
    
    Scene borudoDisplay = new Scene(UserInterface,3500,3500);
    
    
    /**
     * Returns to the home screen
     */
    public static void setHomescreen (){
        UserInterface.setRight(null);
        UserInterface.setCenter(homescreen.getHomescreen());
        
        turnOffMedia();
    }
    
    /**
     * Access the photo functionality
     */
    public static void setPhotoMenu (){
        
        
        UserInterface.setRight(quickbar.getQuickbar());
        
        turnOffMedia();
    }
    
    /**
     * Access the video functionality
     */
    public static void setVideoMenu () {
        
        UserInterface.setRight(quickbar.getQuickbar());
        UserInterface.setCenter(videoscreen.getVideoScreen());
           
        turnOffMedia();
    }
    public static void playVideo (){
        
        UserInterface.setCenter(videoscreen.getVideoPlayer());
        videoscreen.playVideo();
    }
    
    
    /**
     * Access the game functionality
     */
    public static void setMusicMenu () {
        
        UserInterface.setRight(quickbar.getQuickbar());
        UserInterface.setCenter(musicscreen.getMusicScreen());
        
        turnOffMedia();
    }
    
    public static void playMusic (){
        
        UserInterface.setCenter(musicscreen.getMusicPlayer());
        musicscreen.playMusic();
    }
    
    public static void turnOffMedia() {
        
        musicscreen.stopMusic();
        videoscreen.stopVideo();
    }
    
    /**
     * returns a scene to be executed
     * @return 
     */
    public Scene diplayBorudo() {
        return borudoDisplay;
    }
}
