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
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

/**
 *
 * @author $Iwan Snapper
 */
public class IdleScreen {
    
    private static int timeBeforeIdle = 15;
    
    private static final Timeline idleTimeline = new Timeline(new KeyFrame(
            Duration.seconds(timeBeforeIdle),
            ae -> setIdleScreen()));

    public static boolean playing = false;

    private StackPane idlePane = new StackPane();
    public Scene idleScene = new Scene(idlePane, 5000, 3500);
    public static Stage idleStage = new Stage();

    Label test = new Label("Klik ergens op het scherm om terug te gaan naar Borudo");

    public IdleScreen() {

         
        
        // Add Label to StackPane
        Label label = new Label("U bevind zich op het Idle scherm ");
        Label label2 = new Label("Druk op het scherm om verder te gaan waar u gebleven was");
        Label label1 = new Label("");
        Image image = new Image("file:src/Resources/amsta.png");
        label1.setGraphic(new ImageView(image));
        label1.setTextFill(Color.web("#0076a3"));
        
        label.setStyle("-fx-background-color:orange");
        label.setPadding(new Insets(100,100,-370,100));
        label2.setPadding(new Insets(100,100,-500,100));
        label.setFont(new Font("Arial", 55));
        label2.setFont(new Font("Arial", 55));
        label1.setPadding(new Insets(100,100,400,100));
        idlePane.getChildren().add(label);
        idlePane.getChildren().add(label2);
        idlePane.getChildren().add(label1);
        
        
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
    public static boolean getPlaying(){
        return playing;
    }
}
