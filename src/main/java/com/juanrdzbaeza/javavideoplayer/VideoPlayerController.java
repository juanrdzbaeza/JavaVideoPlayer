package com.juanrdzbaeza.javavideoplayer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import java.io.File;

public class VideoPlayerController {
    @FXML
    private MediaView mediaView;

    @FXML
    private Button playButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button stopButton;

    private MediaPlayer mediaPlayer;

    @FXML
    protected void onOpenFileClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mkv", "*.avi"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
        }
    }

    @FXML
    protected void onPlayClick() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    @FXML
    protected void onPauseClick() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @FXML
    protected void onStopClick() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}