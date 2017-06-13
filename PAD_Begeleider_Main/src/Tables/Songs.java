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
public class Songs {
    
    private SimpleStringProperty songID;
    private SimpleStringProperty songTitle;
    private SimpleStringProperty songPlaylist;
    private SimpleStringProperty songArtist;

    public Songs(String songID, String songTitle, String songPlaylist, String songArtist) {

        this.songID = new SimpleStringProperty(songID);
        this.songTitle = new SimpleStringProperty(songTitle);
        this.songPlaylist = new SimpleStringProperty(songPlaylist);
        this.songArtist = new SimpleStringProperty(songArtist);

    }

    public String getSongID() {
        return songID.get();
    }

    public String getSongTitle() {
        return songTitle.get();
    }

    public String getSongPlaylist() {
        return songPlaylist.get();
    }

    public String getSongArtist() {
        return songArtist.get();
    }
}

