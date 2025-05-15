# JavaFX Video Player

<img src="src/main/resources/com/juanrdzbaeza/javavideoplayer/icon.png" alt="Icon" width="256" height="256">


A simple video player application built using JavaFX. This application allows users to open, play, pause, stop, repeat and mute video files. It also includes a volume slider and a media slider to control the playback progress.

## Features

-   Open multiple video files (`.mp4`) using a file chooser and play them in a playlist.
-   Play, pause, and stop video playback.
-   Adjust volume using a volume slider.
-   Control playback progress using a media slider.
-   Toggle repeat mode.
-   Toggle mute mode.
-   The controls are displayed on top of the video with a transparent background and fade in/out on mouse movement.

## Prerequisites

-   **Java Development Kit (JDK)**: Version 17 or higher.
-   **Maven**: Ensure Maven is installed and configured.
-   **JavaFX**: The project uses JavaFX.

## Setup Instructions

1.  Clone the repository:

    ```bash
    git clone https://github.com/juanrdzbaeza/javafx-video-player.git
    cd javafx-video-player
    ```
2.  Build and run the project:

    ```bash
    mvn clean install
    mvn javafx:run
    ```

## Project Structure

-   `src/main/java/com/juanrdzbaeza/javavideoplayer/VideoPlayerApplication.java`: Main application entry point.
-   `src/main/java/com/juanrdzbaeza/javavideoplayer/VideoPlayerController.java`: Controller for handling UI interactions.
-   `src/main/resources/com/juanrdzbaeza/javavideoplayer/video-player-view.fxml`: FXML file defining the UI layout.

## Usage

1.  Launch the application.
2.  Click the "Open File" button to select one or more video files.
3.  Use the "Play", "Pause", and "Stop" buttons to control playback.
4.  Use the "Repeat" button to toggle repeat mode.
5.  Use the "Mute" button to toggle mute mode.
6.  Use the volume slider to adjust the volume.
7.  Use the media slider to control the playback progress.
8.  Move the mouse near the top of the window to show/hide the controls.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.