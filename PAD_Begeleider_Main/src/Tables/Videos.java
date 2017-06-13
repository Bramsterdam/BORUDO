/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Iwan
 * 
 * Set up info for insertion into a tableview
 */
public class Videos {
    
    private SimpleStringProperty videoID;
    private SimpleStringProperty videoTitle;
    private SimpleStringProperty videoDescription;

    public Videos(String videoID, String videoTitle, String videoDescription) {

        this.videoID = new SimpleStringProperty(videoID);
        this.videoTitle = new SimpleStringProperty(videoTitle);
        this.videoDescription = new SimpleStringProperty(videoDescription);
    }

    public String getVideoID() {
        return videoID.get();
    }

    public String getVideoTitle() {
        return videoTitle.get();
    }

    public String getVideoDescription() {
        return videoDescription.get();
    }

    
}
