package com.farras.sdit.perpussditanakshalihbogorabsensi.utils;

import com.digitalpersona.uareu.Reader;

import java.net.URL;
import java.util.Objects;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundNotification {
    private Media media;
    private MediaPlayer mediaPlayer;

    public enum SoundType {
        SUCCESS,
        DONE,
        FAILED
    }

    public SoundNotification (SoundType soundType){
        String pathString = "";
        switch (soundType){
            case  SUCCESS:
                pathString = "/sound/true.mp3";
                break;
            case DONE:
                pathString = "/sound/done.mp3";
                break;
            case FAILED:
                pathString = "/sound/fail.mp3";
                break;
        }

        this.media = new Media(Objects.requireNonNull(getClass().getResource(pathString)).toExternalForm());
        this.mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnEndOfMedia(this::turnOf);
    }

    public void turnOn (){
        this.mediaPlayer.play();
    }

    private void turnOf(){
        this.mediaPlayer.stop();
    }
}
