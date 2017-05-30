
package pad;

import Controller.DisplayControl;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Iwan
 */
public class PAD extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        DisplayControl borudo = new DisplayControl();
        borudo.setHomescreen();
        
        primaryStage.setTitle("Borudo Amsta #1");
        primaryStage.setScene(borudo.diplayBorudo());
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
