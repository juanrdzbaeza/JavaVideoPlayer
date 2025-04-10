# JavaFX Video Player

A simple video player application built using JavaFX. This application allows users to open, play, pause, and stop video files.

## Features

- Open video files (`.mp4`, `.mkv`, `.avi`) using a file chooser.
- Play, pause, and stop video playback.
- User-friendly interface with basic controls.

## Prerequisites

- **Java Development Kit (JDK)**: Version 17 or higher.
- **Maven**: Ensure Maven is installed and configured.
- **JavaFX**: The project uses JavaFX 20.0.2.

## Setup Instructions

1. Clone the repository:
```bash
   git clone https://github.com/juanrdzbaeza/javafx-video-player.git
   cd javafx-video-player
```

2. Add JavaFX dependencies to your `pom.xml`:
```xml
   <dependencies>
       <dependency>
           <groupId>org.openjfx</groupId>
           <artifactId>javafx-controls</artifactId>
           <version>20.0.2</version>
       </dependency>
       <dependency>
           <groupId>org.openjfx</groupId>
           <artifactId>javafx-fxml</artifactId>
           <version>20.0.2</version>
       </dependency>
       <dependency>
           <groupId>org.openjfx</groupId>
           <artifactId>javafx-media</artifactId>
           <version>20.0.2</version>
       </dependency>
   </dependencies>
```

3. Build and run the project:
```bash
   mvn clean install
   mvn javafx:run
```

## Project Structure

- `src/main/java/com/juanrdzbaeza/javavideoplayer/VideoPlayerApplication.java`: Main application entry point.
- `src/main/java/com/juanrdzbaeza/javavideoplayer/VideoPlayerController.java`: Controller for handling UI interactions.
- `src/main/resources/com/juanrdzbaeza/javavideoplayer/video-player-view.fxml`: FXML file defining the UI layout.

## Usage

1. Launch the application.
2. Click the "Open File" button to select a video file.
3. Use the "Play", "Pause", and "Stop" buttons to control playback.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
