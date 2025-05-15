package com.juanrdzbaeza.javavideoplayer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class VideoPlayerController {

    @FXML
    private BorderPane borderPane;
    @FXML
    private MediaView mediaView;
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button repeatButton;
    @FXML
    private Button muteButton;
    @FXML
    private Slider mediaSlider;
    @FXML
    private Slider volumeSlider;
    @FXML
    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean isLooping = false;
    private boolean isMute = false;
    private List<File> files;
    private Queue<File> playlist = new LinkedList<>();
    private double initialMediaViewHeight;

    @FXML
    public void initialize() {

        System.out.println("VideoPlayerController initialized");

        // Configurar el slider de volumen
        volumeSlider.setMin(0);
        volumeSlider.setMax(1); // El volumen en MediaPlayer va de 0.0 a 1.0
        volumeSlider.setValue(0.5); // Volumen inicial al 50%
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue());
            }
        });

        // Configurar el slider de progreso
        mediaSlider.setMin(0);
        mediaSlider.setValue(0);
        mediaSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null && Math.abs(newValue.doubleValue() - mediaPlayer.getCurrentTime().toSeconds()) > 0.5) {
                mediaPlayer.seek(javafx.util.Duration.seconds(newValue.doubleValue()));
            }
        });

    }

    @FXML
    protected void onOpenFileClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.MP4"));

        this.files = fileChooser.showOpenMultipleDialog(new Stage());

        if (files != null) {
            playlist.addAll(files);
            playNextVideo();
        }
    }

    private void playNextVideo() {
        if (!playlist.isEmpty()) {
            File nextFile = playlist.poll();
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            media = new Media(nextFile.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            mediaPlayer.setOnReady(() -> {
                mediaSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
                mediaPlayer.play();
                hideControls(); // Ocultar los controles al empezar la reproducción
            });

            mediaPlayer.currentTimeProperty().addListener((observable, oldTime, newTime) -> {
                mediaSlider.setValue(newTime.toSeconds());
            });

            mediaPlayer.setOnEndOfMedia(() -> {
                playNextVideo();
            });
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

    @FXML
    protected void onRepeatClick() {
        if (mediaPlayer != null) {
            isLooping = !isLooping; // Alternar el estado de bucle
            mediaPlayer.setCycleCount(isLooping ? MediaPlayer.INDEFINITE : 1);
            repeatButton.setText(isLooping ? "\uD83D\uDD03: ON" : "\uD83D\uDD03: OFF");
        }
    }

    @FXML
    protected void onMuteClick() {
        if (mediaPlayer != null) {
            isMute = !isMute; // Alternar el estado de silencio
            mediaPlayer.setMute(isMute);
            muteButton.setText(isMute ? "🔇" : "🔊");
        }
    }

}