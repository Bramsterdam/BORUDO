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
 */
public class Photos {
    
    private SimpleStringProperty photoID;
    private SimpleStringProperty photoTitle;
    private SimpleStringProperty photoSlideshow;
    
    public Photos(String photoID, String photoTitle, String photoSlideshow){
        
        this.photoID = new SimpleStringProperty(photoID);
        this.photoTitle  = new SimpleStringProperty(photoTitle);
        this.photoSlideshow = new SimpleStringProperty(photoSlideshow);
        
    }


    public String getPhotoID() {
        return photoID.get();
    }

    public String getPhotoTitle() {
        return photoTitle.get();
    }

    public String getPhotoSlideshow() {
        return photoSlideshow.get();
    }
    
}
