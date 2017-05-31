
package Controller;

import GUI.Homescreen;
import GUI.Quickbar;
import Selectionscreens.MusicScreen;
import Selectionscreens.PhotoScreen;
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
    private static PhotoScreen photoscreen = new PhotoScreen();
    
    //Full Application
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
        UserInterface.setCenter(photoscreen.getPhotoScreen());
        
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
    
    //Plays Video
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
    
    
    //Plays Music
    public static void playMusic (){
        
        UserInterface.setCenter(musicscreen.getMusicPlayer());
        musicscreen.playMusic();
    }
    
    //Turns of all media
    public static void turnOffMedia() {
        
        musicscreen.stopMusic();
        videoscreen.stopVideo();
    }
    
    /**
     * returns a scene to be executed
     * @return full application
     */
    public Scene diplayBorudo() {
        return borudoDisplay;
    }
}
