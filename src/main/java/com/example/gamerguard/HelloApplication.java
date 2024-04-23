package com.example.gamerguard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profile-settings.fxml"));
        stage.initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
//        stage.setTitle("Welcome to GamerGuard!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}