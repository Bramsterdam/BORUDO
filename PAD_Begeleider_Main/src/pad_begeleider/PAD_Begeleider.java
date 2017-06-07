/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pad_begeleider;

import Controller.ManagementController;
import FileManagement.ManagementScreen;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Iwan Snapper <your.name at your.org>
 */
public class PAD_Begeleider extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        
        ManagementController managementApplication = new ManagementController();
       
        
        primaryStage.setTitle("Amsta begeleidersapplicatie voor bestandsbeheer");
        
        primaryStage.setScene(managementApplication.getManagementApplication());
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
