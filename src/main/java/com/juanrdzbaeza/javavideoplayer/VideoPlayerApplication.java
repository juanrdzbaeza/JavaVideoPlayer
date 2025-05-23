package com.juanrdzbaeza.javavideoplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class VideoPlayerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(VideoPlayerApplication.class.getResource("video-player-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Video Player");
        // Set the application icon
        try {
            stage.getIcons().add(new Image(Objects.requireNonNull(VideoPlayerApplication.class.getResourceAsStream("icon.png"))));
        } catch (NullPointerException e) {
            System.err.println("Icon not found: icon.png");
        }
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}