package com.example.gamerguard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


/**
 * <p>HelloApplication class.</p>
 *
 * @author Serene Coders
 * @version 1.0.0
 */
public class HelloApplication extends Application {
    /** Constant <code>TITLE="Gamer Guard"</code> */
    public static final String TITLE = "Gamer Guard";
    /** Constant <code>WIDTH=1080</code> */
    public static final int WIDTH = 1080;
    /** Constant <code>HEIGHT=720</code> */
    public static final int HEIGHT = 720;

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        stage.initStyle(StageStyle.DECORATED); // Change to UNDECORATED if necessary
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     */
    public static void main(String[] args) {
        launch();
    }
}
