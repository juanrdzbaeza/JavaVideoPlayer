package com.juanrdzbaeza.javavideoplayer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import java.io.File;

public class VideoPlayerController {

    @FXML
    private StackPane mediaViewContainer;

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

    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean isLooping = false;
    private boolean isMute = false;

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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mkv", "*.avi"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            // Actualizar el slider de progreso según la duración del video
            mediaPlayer.setOnReady(() -> {
                mediaSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
            });

            // Sincronizar el slider de progreso con el tiempo actual del video
            mediaPlayer.currentTimeProperty().addListener((observable, oldTime, newTime) -> {
                mediaSlider.setValue(newTime.toSeconds());
            });

            mediaPlayer.play();

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