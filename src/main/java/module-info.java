module com.juanrdzbaeza.javavideoplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    
    requires org.kordamp.bootstrapfx.core;

    opens com.juanrdzbaeza.javavideoplayer to javafx.fxml;
    exports com.juanrdzbaeza.javavideoplayer;
}