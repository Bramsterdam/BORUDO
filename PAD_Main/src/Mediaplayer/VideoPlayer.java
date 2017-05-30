/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mediaplayer;

import java.io.File;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;




/**
 *
 * @author Iwan
 */
public class VideoPlayer  {
    
    
    String videoPath;
    Media media = new Media(new File("file:src/Resources/Video/Test(1).mp4").toURI().toString());
    
    MediaPlayer mp = new MediaPlayer(media);
    MediaView mv = new MediaView(mp);
    
    StackPane videoPane = new StackPane(mv);
    
    public VideoPlayer(){
    videoPane.getChildren().add(mv);
    
            }
    
    public void playVideoPlayer(){
        mp.play();
    }
    
    public StackPane getVideoPlayer() {
        return videoPane;
    }
    
}
