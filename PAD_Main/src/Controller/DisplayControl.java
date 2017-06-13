package Controller;

import GUI.Homescreen;
import GUI.IdleScreen;
import GUI.Quickbar;
import Selectionscreens.MusicScreen;
import Selectionscreens.PhotoScreen;
import Selectionscreens.VideoScreen;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import pad.PAD;

/**
 *
 * @author Iwan
 *
 * This class manages the whats display get access at any point. And can be
 * accessed by other classes when there has to be navigated between different
 * displays
 */
public class DisplayControl {

    private static final Homescreen HOMESCREEN = new Homescreen();
    private static final BorderPane USERINTERFACE = new BorderPane();
    private static final Quickbar QUICKBAR = new Quickbar();
    private static final VideoScreen VIDEOSCREEN = new VideoScreen();
    private static final MusicScreen MUSICSCREEN = new MusicScreen();
    private static final PhotoScreen PHOTOSCREEN = new PhotoScreen();

    //Full Application
    public static Scene borudoDisplay = new Scene(USERINTERFACE, 3000, 3000);

    public DisplayControl() {

        //Set action everytime the mouse is moved over the screen.
        borudoDisplay.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                IdleScreen.restartIdleTimer();
            }
        });
        
        //Restarts the Idle time every the user clicks the mouse button.
        borudoDisplay.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                IdleScreen.restartIdleTimer();
            }
        });
    }

    /**
     * Returns to the home screen
     */
    public static void setHomescreen() {
        
        PAD.setFullscreen();
        turnOffMedia();
        
        USERINTERFACE.setRight(null);
        USERINTERFACE.setCenter(HOMESCREEN.getHomescreen());
    }

    /**
     * Access the photo functionality
     */
    public static void setPhotoMenu() {
        PAD.setFullscreen();
        turnOffMedia();

        USERINTERFACE.setRight(QUICKBAR.getQuickbar());
        USERINTERFACE.setCenter(PHOTOSCREEN.getPhotoScreen());

    }

    public static void playPhotoSlideshow() {
        USERINTERFACE.setCenter(PHOTOSCREEN.getPhotoPlayer());

    }

    /**
     * Access the video functionality
     */
    public static void setVideoMenu() {
        turnOffMedia();
        USERINTERFACE.setRight(QUICKBAR.getQuickbar());
        USERINTERFACE.setCenter(VIDEOSCREEN.getVideoScreen());

    }

    //Plays Video
    public static void playVideo() {

        USERINTERFACE.setCenter(VIDEOSCREEN.getVideoPlayer());
        VIDEOSCREEN.playVideo();
    }

    /**
     * Access the music functionality
     */
    public static void setMusicMenu() {
        turnOffMedia();
        USERINTERFACE.setRight(QUICKBAR.getQuickbar());
        USERINTERFACE.setCenter(MUSICSCREEN.getMusicScreen());

        
    }

    //Plays Music
    public static void playMusic() {

        USERINTERFACE.setCenter(MUSICSCREEN.getMusicPlayer());
    }

    //Turns of all media
    public static void turnOffMedia() {

// Restart timer
        IdleScreen.setPlayingFalse();
        IdleScreen.restartIdleTimer();
        //Stop all media
        MUSICSCREEN.stopMusic();
        VIDEOSCREEN.stopVideo();
        PHOTOSCREEN.stopSlideshow();

    }

    /**
     * returns a scene to be executed
     *
     * @return full application
     */
    public Scene diplayBorudo() {
        return borudoDisplay;
    }
}
