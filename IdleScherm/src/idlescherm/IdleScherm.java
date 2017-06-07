/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idlescherm;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;



 
public class IdleScherm extends Application {
 
    private StackPane stackPane;
 
    @Override
    public void start(Stage primaryStage) throws Exception {
 
        VBox root = new VBox();
 
        // StackPane
        stackPane = new StackPane();
         
        
        // Add Label to StackPane
        Label label = new Label("U bevind zich op het Idle scherm ");
        Label label2 = new Label("Beweeg met u muis om verder te gaan waar u gebleven was");
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
        stackPane.getChildren().add(label);
        stackPane.getChildren().add(label2);
        stackPane.getChildren().add(label1);
        
        
        //
        stackPane.setPrefSize(1080,1920);
       
        stackPane.setStyle("-fx-background-color: white;-fx-border-color: orange;");
        
        //
        root.getChildren().add(stackPane);
 
           // new Image(url)

      
        //
 
        Scene scene = new Scene(root,700 , 400);
 
        primaryStage.setTitle("Idle Scherm");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
 
     {
 
        primaryStage.show();
    }
 
    {
   
        }
    }
 
    public static void main(String[] args) {
        launch(args);
    }
 
}

