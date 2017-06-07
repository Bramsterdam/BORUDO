package pad;

import Controller.DisplayControl;
import GUI.IdleScreen;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Iwan
 */
public class PAD extends Application {

    private static Stage stage= new Stage();
    
    @Override
    public void start(Stage primaryStage) {
        DisplayControl borudo = new DisplayControl();
        borudo.setHomescreen();
        IdleScreen idlescreen = new IdleScreen();
        
        this.stage = primaryStage;
        
        primaryStage.setTitle("Borudo Amsta #1");
        primaryStage.setScene(borudo.diplayBorudo());
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.F));
        primaryStage.setResizable(false);
        primaryStage.show();
        setFullscreen();

    }
 
    public static void setFullscreen(){
        stage.setFullScreen(true);
    }

/**
 * @param args the command line arguments
 */
public static void main(String[] args) {
        launch(args);
        
    }
    
    
    
    
}
