/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import static GUI.IdleScreen.idleStage;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;

/**
 *
 * @author $Iwan Snapper
 */
public class IdleScreen {
    
    private static int timeBeforeIdle = 15;
    
    private static final Timeline idleTimeline = new Timeline(new KeyFrame(
            Duration.minutes(timeBeforeIdle),
            ae -> setIdleScreen()));

    public static boolean playing = false;

    private StackPane idlePane = new StackPane();
    public Scene idleScene = new Scene(idlePane, 5000, 3500);
    public static Stage idleStage = new Stage();

    Label test = new Label("Hoi");

    public IdleScreen() {

        idleStage.show();
        idleStage.setMaximized(true);
        idleStage.setAlwaysOnTop(true);
        idleStage.setResizable(false);
        idleStage.setFullScreen(true);
        idlePane.getChildren().add(test);
        idleStage.setTitle("Second stage");
        idleStage.setScene(idleScene);


        //Set action everytime the mouse is moved over the screen.
        idleScene.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                IdleScreen.restartIdleTimer();
            }
        });
    }

    public static void setIdleScreen() {
        idleStage.show();
        
    }

    public static void restartIdleTimer() {
        idleStage.hide();
        if (playing == false) {
            idleTimeline.stop();
            idleTimeline.play();
        }
    }

    public static void setPlayingFalse() {
        playing = false;
    }

    public static void setPlayingTrue() {
        playing = true;
    }

    public static void stopIdleTimer() {
        playing = true;
        idleTimeline.stop();
    }
}
