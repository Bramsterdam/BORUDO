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
import javafx.scene.input.MouseEvent;

/**
 *
 * @author $Iwan Snapper
 */
public class IdleScreen {

    public static boolean playing = false;

    public static Stage idleStage = new Stage();

    private static final StackPane idleScreen = new StackPane();
    private static final Timeline idleTimeline = new Timeline(new KeyFrame(
            Duration.millis(20000),
            ae -> setIdleScreen()));
    public static Scene idleScene = new Scene(idleScreen, 3500, 3500);

    IdleScreen() {
        idleStage.setMaximized(true);
        idleStage.setResizable(false);
        idleStage.setAlwaysOnTop(true);
        idleScreen.prefHeightProperty().bind(idleStage.heightProperty());
        idleScreen.prefWidthProperty().bind(idleStage.widthProperty());
        idleStage.setTitle("Second stage");
        idleStage.setScene(idleScene);

        //Set action everytime the mouse is moved over the screen.
        idleScene.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                IdleScreen.restartIdleTimer();
            }
        });
    }

    public static void setIdleScreen() {
        idleStage.setMaximized(true);
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
