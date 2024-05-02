package com.example.gamerguard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
//import java.security.PublicKey;

public class HelloApplication extends Application {
    public static final String TITLE = "Gamer Guard";
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Testing AM I SECOND?, yes ");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("notifi-settings.fxml"));
        stage.initStyle(StageStyle.DECORATED); // Change to "UNDECORATED" to remove title bar.
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Testing IF THIS RUNS");
        launch();
    }
}