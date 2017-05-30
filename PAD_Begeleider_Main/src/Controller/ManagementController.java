/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import FileManagement.MusicManagement;
import FileManagement.PhotoManagement;
import FileManagement.VideoManagement;
import SQL_Queries.SQL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author $Iwan Snapper
 */
public class ManagementController {

    private static MusicManagement musicManagement = new MusicManagement();
    private static VideoManagement videoManagement = new VideoManagement();
    private static PhotoManagement photoManagement = new PhotoManagement();

    public static BorderPane managementPane = new BorderPane();
    public static HBox managementQuickbar = new HBox();
    
    public static Button setMusicButton = new Button("Muziek");
    public static Button setVideoButton = new Button("Video's");
    public static Button setPhotoButton = new Button("Foto's");

    private Scene managementApplication = new Scene(managementPane, 3500, 3500);

    public ManagementController() {

        //Music navigationbutton - navigates to music management
        setMusicButton.setOnAction((ActionEvent event) -> {
            SetMusicManagement();
            musicManagement.Reset();
        });

        //Video navigationButton - navigate to video management
        setVideoButton.setOnAction((ActionEvent event) -> {
            SetVideoManagement();
            
            videoManagement.reset();
        });

        //Photo management - navigates to video management
        setPhotoButton.setOnAction((ActionEvent event) -> {
            SetPhotoManagement();
            photoManagement.reset();
        });

        //Set spacing for button is quickbar
        setMusicButton.prefWidthProperty().bind(managementQuickbar.widthProperty().divide(3));
        setVideoButton.prefWidthProperty().bind(managementQuickbar.widthProperty().divide(3));
        setPhotoButton.prefWidthProperty().bind(managementQuickbar.widthProperty().divide(3));
        
        managementQuickbar.getChildren().addAll(setMusicButton, setVideoButton, setPhotoButton);
        managementQuickbar.setPadding(new Insets(30, 0, 30, 0));
        managementPane.setTop(managementQuickbar);

    }
    
    /**
     * Opens up the part of the application from which music can be added or removed
     * Set to center to leave room for navigation buttons
     */
    public void SetMusicManagement() {
        managementPane.setLeft(musicManagement.getMusicOverview());
        managementPane.setCenter(musicManagement.getAddMusicPane());
        musicManagement.Reset();
    }

    /**
     * Opens up the part of the application from which videos can be added or removed
     * Set to center to leave room for navigation buttons
     */
    public void SetVideoManagement() {
        managementPane.setLeft(videoManagement.getVideoOverview());
        managementPane.setCenter(videoManagement.getAddVideoPane());
        videoManagement.reset();
    }

    /**
     * Opens up the part of the application from which photos can be added or removed
     * Set to center to leave room for navigation buttons
     */
    public void SetPhotoManagement() {
        managementPane.setLeft(photoManagement.getPhotoOverview());
        managementPane.setCenter(photoManagement.getAddPhotoPane());
        photoManagement.reset();

    }

    /**
     * Complete application
     * @return 
     */
    public Scene getManagementApplication() {
        return managementApplication;
    }
}
    
