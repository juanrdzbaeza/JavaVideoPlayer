package com.juanrdzbaeza.javavideoplayer;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
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
    private StackPane stackPane;
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
    private HBox controlsBar;
    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private RepeatMode repeatMode = RepeatMode.NONE;
    private boolean isMute = false;
    private List<File> files;
    private Queue<File> playlist = new LinkedList<>();
    private double initialMediaViewHeight;

    private enum RepeatMode {
        NONE,
        CURRENT,
        ALL
    }

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

        // Resize the window and assign an event after the scene is loaded
        javafx.application.Platform.runLater(() -> {
            // Obtener el Stage actual
            javafx.stage.Stage stage = (javafx.stage.Stage) mediaView.getScene().getWindow();

            // Agregar un listener para imprimir el tamaño de la ventana al redimensionar
            stage.widthProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Ancho de la ventana: " + newValue);
                mediaView.setFitWidth((Double) newValue);
            });

            stage.heightProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Alto de la ventana: " + newValue);
                mediaView.setFitHeight((Double) newValue);
            });

            // Ocultar la barra de controles al inicio
            hideControls();

            // Mostrar la barra de controles cuando el ratón se mueve cerca del borde superior
            stackPane.setOnMouseMoved(event -> {
                if (event.getY() <= 30) { // Ajusta este valor según la altura de tu barra de controles
                    showControls();
                } else {
                    hideControls();
                }
            });

            // Guarda la altura inicial del MediaView
            initialMediaViewHeight = mediaView.getFitHeight();
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
        if (playlist.isEmpty() && repeatMode == RepeatMode.NONE) {
            return;
        }

        if (playlist.isEmpty() && repeatMode == RepeatMode.ALL) {
            playlist.addAll(files);
        }

        File nextFile = playlist.poll();

        if (nextFile == null && repeatMode == RepeatMode.CURRENT) {
            nextFile = file;
        }

        if (nextFile != null) {
            file = nextFile;
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
                if (repeatMode == RepeatMode.CURRENT) {
                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.play();
                } else {
                    playNextVideo();
                }
            });
        }
    }

    @FXML
    protected void onPlayClick() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
            hideControls(); // Ocultar los controles al hacer clic en reproducir
        }
    }

    @FXML
    protected void onPauseClick() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            showControls(); // Mostrar los controles al hacer clic en pausa
        }
    }

    @FXML
    protected void onStopClick() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            showControls(); // Mostrar los controles al hacer clic en detener
        }
    }

    @FXML
    protected void onRepeatClick() {
        switch (repeatMode) {
            case NONE:
                repeatMode = RepeatMode.CURRENT;
                repeatButton.setText("Repeat Current");
                break;
            case CURRENT:
                repeatMode = RepeatMode.ALL;
                repeatButton.setText("Repeat All");
                break;
            case ALL:
                repeatMode = RepeatMode.NONE;
                repeatButton.setText("Repeat: Off");
                break;
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

    private void showControls() {
        controlsBar.setVisible(true);
        controlsBar.setManaged(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), controlsBar);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    private void hideControls() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), controlsBar);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> {
            controlsBar.setVisible(false);
            controlsBar.setManaged(false);
        });
        fadeTransition.play();
    }
}