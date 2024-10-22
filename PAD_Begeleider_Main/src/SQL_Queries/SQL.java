/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL_Queries;

import Controller.ManagementController;
import FileManagement.MusicManagement;
import Tables.Photos;
import Tables.Songs;
import Tables.Videos;
import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;

/**
 * This class contains all SQL queries used by the application
 *
 * @author $Iwan Snapper
 */
public class SQL {

    //Section: Selecting file from computer
    private static FileChooser photoChooser = new FileChooser();
    private static File file;
    private final Desktop desktop = Desktop.getDesktop();
    private static String pathFile;
    private static StackPane test = new StackPane();

    private static Connection connection;
    private static Statement stmnt;
    private static PreparedStatement pst;
    
    /**
     * Adds a song to the database based on given information also checks if the
     * playlist or artist already exists
     *
     * @param location : file location
     * @param title: song title
     * @param playlist; playlist name
     * @param artist: artist name
     */
    public static void AddMusic(String location, String title, String playlist, String artist) {

        photoChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
    
        
        initializeDB();
        location = location.replace("\\", "\\\\");

        /**
         * Check if Category exists else
         */
        try {
            Statement state = connection.createStatement();
            ResultSet rs1 = state.executeQuery("Select * from Playlist where playlistName = '" + playlist + "'");

            //If the query returns the playlist already exists if not creates it
            if (rs1.next()) {
                System.out.println("Playlist bestaat niet");
            } else {
                AddMusicPlaylist(playlist);
            }

        } catch (SQLException ex) {

            System.out.println("fail playlist");
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * Check if Artist exists
         */
        try {

            Statement state = connection.createStatement();
            ResultSet rs2 = state.executeQuery("Select * from Artist where ArtistName ='" + artist + "'");
            //If the query returns the Artistt already exists if not creates it
            if (rs2.next()) {
                System.out.println("Playlist bestaat niet");
            } else {
                AddMusicArtist(artist);
            }

        } catch (SQLException ex) {
            System.out.println("fail artist");
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * after confirming the existance of the correct playlist and artist it
         * add the data.
         */
        try {

            Statement state = connection.createStatement();
            state.executeUpdate("insert into music (musicPath, musicTitle,mArtist,mPlaylist) VALUES ('" + location + "','" + title + "','" + artist + "','" + playlist + "')");
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Adds a video to the database based on given information 
     * @param location File paths
     * @param title video title
     * @param description video description
     */
    public static void AddVideo(String location, String title, String description) {

        initializeDB();

        //Database remove the \ escape character
        location = location.replace("\\", "\\\\");

        //add a video with given information
        try {

            Statement state = connection.createStatement();

            state.executeUpdate("insert into videos (VideoTitle, VideoPath, VideoDescription) VALUES ('" + title + "','" + location + "','" + description + "')");

        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void AddPhoto(String location, String title, String category) {

        initializeDB();

        //Database remove the \ escape character
        location = location.replace("\\", "\\\\");

        /*
        Check whether the theme already exists
         */
        try {

            Statement state = connection.createStatement();
            ResultSet rs1 = state.executeQuery("Select * from pTheme where PTheme ='" + category + "'");

            //If the query returns the theme already exists if not creates it
            if (rs1.next()) {
                System.out.println("Playlist bestaat al");
            } else {
                AddPhotoCategory(category);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);

        }

        /**
         * after confirming the existance of the correct theme add the data.
         */
        try {
            Statement state = connection.createStatement();
            state.executeUpdate("insert into photos (PhotoPath, PhotoTitle, PTheme) VALUES (' file:" + location + "','" + title + "','" + category + "')");

        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add Music playlist to the database
     * @param playlistName Playlist name
     */
    public static void AddMusicPlaylist(String playlistName) {

        //placeholder cover
        String newPlaylistThumbnail = "src\\\\resources\\\\NoImage.png";

        initializeDB();

        try {
            //Select cover image
            photoChooser.setTitle("Selecteer een plaatjes dat bij de playlist "+ playlistName + " past");
            file = photoChooser.showOpenDialog(ManagementController.managementPane.getScene().getWindow());
            if (file != null) {
                pathFile = file.getAbsolutePath();
                newPlaylistThumbnail = pathFile;
            }
            
            //Database remove the \ escape character
            
                newPlaylistThumbnail = newPlaylistThumbnail.replace("\\", "\\\\");

            //Insert into the database
            Statement state = connection.createStatement();
            state.executeUpdate("insert into playlist (PlaylistName, PlaylistThumbnail) VALUES ('" + playlistName + "','file:" + newPlaylistThumbnail + "')");

        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add Artist to the database
     * @param artistName Artist Name
     */
    public static void AddMusicArtist(String artistName) {

        //Placeholder image
        String newArtistThumbnail = "src\\\\resources\\\\NoImage.png";

        initializeDB();

        
        
        try {
            
            photoChooser.setTitle("Selecteer een plaatjes dat bij de Artiest: "+ artistName + " past");
        file = photoChooser.showOpenDialog(ManagementController.setMusicButton.getScene().getWindow());
        if (file != null) {
            pathFile = file.getAbsolutePath();
            newArtistThumbnail = pathFile;
        }
            //Database removes \ escape character 
            newArtistThumbnail = newArtistThumbnail.replace("\\", "\\\\");

            //add new artist to the database
            Statement state = connection.createStatement();
           
            state.executeUpdate("insert into artist (artistName, ArtistThumbnail) VALUES ('" + artistName + "', 'file:" + newArtistThumbnail + "')");

        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add photo thema to the database
     * @param categoryName Thema name
     */
    public static void AddPhotoCategory(String categoryName) {

        initializeDB();

        try {

            //Creates new photo theme
            Statement state = connection.createStatement();
            state.executeUpdate("insert into ptheme (PTheme) VALUES ('" + categoryName + "')");
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Remove song from database
     * @param song Song ID
     */
    public static void RemoveMusic(Songs song) {

        String usedArtist = "";
        String usedPlaylist = "";

        try {
            initializeDB();

            Statement state = connection.createStatement();
            ResultSet rs1 = state.executeQuery("Select martist, mplaylist from Music where idMusic = " + song.getSongID());
            if (rs1.next()) {
                usedArtist = rs1.getString(1);
                usedPlaylist = rs1.getString(2);
            }

            PreparedStatement removeMusic = connection.prepareStatement("DELETE FROM `borudo`.`Music` WHERE `idMusic`= ? ;");

            if (song.getSongID() != null) {
                removeMusic.setInt(1, Integer.parseInt(song.getSongID()));
                removeMusic.executeUpdate();
            }

            ResultSet rs2 = state.executeQuery("Select Count(*) from music where mArtist = '" + usedArtist + "';");

            if (rs2.next()) {
                if (rs2.getInt(1) == 0) {
                    PreparedStatement removeArtist = connection.prepareStatement("DELETE FROM `borudo`.`Artist` WHERE `ArtistName`= ? ;");
                    removeArtist.setString(1, usedArtist);
                    removeArtist.executeUpdate();
                }
            }
            ResultSet rs3 = state.executeQuery("Select Count(*) from music where mPlaylist = '" + usedPlaylist + "';");

            if (rs3.next()) {
                if (rs3.getInt(1) == 0) {
                    PreparedStatement removePlaylist = connection.prepareStatement("DELETE FROM `borudo`.`Playlist` WHERE `PlaylistName`= ? ;");
                    removePlaylist.setString(1, usedPlaylist);
                    removePlaylist.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Remove video from database
     * @param video video ID
     */
    public static void RemoveVideo(Videos video) {

        try {
            initializeDB();
            PreparedStatement removeMusic = connection.prepareStatement("DELETE FROM `borudo`.`Videos` WHERE `idVideo`= ? ;");

            if (video.getVideoID() != null) {
                removeMusic.setInt(1, Integer.parseInt(video.getVideoID()));
                removeMusic.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Remove image from database
     * @param photo image ID
     */
    public static void RemovePhoto(Photos photo) {

        String usedTheme = "";
        try {

            Statement state = connection.createStatement();
            ResultSet rs1 = state.executeQuery("Select PTheme from Photos where idPhotos = " + photo.getPhotoID());
            if (rs1.next()) {
                usedTheme = rs1.getString(1);
            }

            initializeDB();
            PreparedStatement removeMusic = connection.prepareStatement("DELETE FROM `borudo`.`photos` WHERE `idPhotos`= ? ;");

            if (photo.getPhotoID() != null) {
                removeMusic.setInt(1, Integer.parseInt(photo.getPhotoID()));
                removeMusic.executeUpdate();
            }

            ResultSet rs2 = state.executeQuery("Select Count(*) from photos where PTheme  = '" + usedTheme + "';");

            if (rs2.next()) {
                if (rs2.getInt(1) == 0) {
                    PreparedStatement removeTheme = connection.prepareStatement("DELETE FROM `borudo`.`PTheme` WHERE `PTheme`= ? ;");
                    removeTheme.setString(1, usedTheme);
                    removeTheme.executeUpdate();
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Load existing playlist titles
     * @param cb combobox filled from database
     */
    public static void fillPlaylistCB(ComboBox cb) {

        initializeDB();
        List<String> allPlaylists = new ArrayList();

        try {

            //obtain data from database
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery("Select PlaylistName from Playlist");

            //add all data to a list
            while (rs.next()) {
                allPlaylists.add(rs.getString("PlaylistName"));

            }

            //add to combobox
            cb.setItems(FXCollections.observableList(allPlaylists));

        } catch (SQLException ex) {
            System.out.println("Class not found");
        }

    }

    /**
     * Load existing artist names
     * @param cb combobox filled from database 
     */
    public static void fillArtistCB(ComboBox cb) {
        initializeDB();

        List<String> allArtists = new ArrayList();

        try {

            //obtain data from database
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery("Select ArtistName from Artist");

            //add all data to a list
            while (rs.next()) {
                allArtists.add(rs.getString("ArtistName"));

            }

            //add to combobox
            cb.setItems(FXCollections.observableList(allArtists));

        } catch (SQLException ex) {
            System.out.println("Class not found");
        }
    }

    /**
     * load existing theme names
     * @param cb combobox filled from database
     */
    public static void fillThemeCB(ComboBox cb) {
        initializeDB();

        List<String> allThemes = new ArrayList();

        try {
            //obtain data from database
            Statement state = connection.createStatement();
            ResultSet rs = state.executeQuery("Select ptheme from Ptheme");

            //add all data to a list
            while (rs.next()) {
                allThemes.add(rs.getString("PTheme"));

            }

            //add to combobox
            cb.setItems(FXCollections.observableList(allThemes));

        } catch (SQLException ex) {
            System.out.println("Class not found");
        }
    }

    public static Connection initializeDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/borudo", "amsta1", "appel123");
            System.out.println("Database connected");

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Class not found");
        }
        return connection;
    }
}
