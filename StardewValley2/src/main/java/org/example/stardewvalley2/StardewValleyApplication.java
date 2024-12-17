package org.example.stardewvalley2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.stardewvalley2.control.HelloController;

import java.io.IOException;

public class StardewValleyApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        openWindow("hello-view.fxml");
    }

    public static void openWindow(String fxml){

        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(StardewValleyApplication.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
            Stage stage = new Stage();
            stage.setTitle("Stardew Valley 2");
            stage.setScene(scene);
            stage.setResizable(false);


            Object controller = fxmlLoader.getController();
            if (controller instanceof HelloController helloController) {

                stage.setOnCloseRequest(event -> {
                    helloController.setRunning();
                    Platform.exit();
                });
            }

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}