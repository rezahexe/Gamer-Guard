package com.example.gamerguard.controller;

import com.example.gamerguard.HelloApplication;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Button profileButton;
    public Button infoButton;
    public Button gamesButton;
    public Button activityButton;
    public Button playlistButton;
    public Button friendsButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Label timerLabel;

    private int secondsElapsed;
    private Timeline timeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization code, if needed
    }

    @FXML
    private void startTimer(ActionEvent event) {
        if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
            return; // Timer already running
        }

        // Create new Timeline for the timer
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsElapsed++;
            timerLabel.setText("Timer: " + secondsElapsed + " seconds");
        }));

        // Set cycle count to indefinite
        timeline.setCycleCount(Animation.INDEFINITE);

        // Start the timer
        timeline.play();
    }

    @FXML
    private void stopTimer(ActionEvent event) {
        if (timeline != null) {
            timeline.stop();
        }
    }

    @FXML
    private void resetTimer(ActionEvent event) {
        stopTimer(event); // Stop the timer
        secondsElapsed = 0;
        timerLabel.setText("Timer: 0 seconds");
    }

    public void gamesButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("display-games.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        Stage stage = (Stage) profileButton.getScene().getWindow();
        stage.setScene(scene);
    }

    public void activityButtonOnAction(ActionEvent event) {
    }

    public void playlistButtonOnAction(ActionEvent event) {
    }

    public void friendsButtonOnAction(ActionEvent event) {
    }

    public void settingsButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        Stage stage = (Stage) settingsButton.getScene().getWindow();
        stage.setScene(scene);
    }

    public void profileButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profile-settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        Stage stage = (Stage) profileButton.getScene().getWindow();
        stage.setScene(scene);
    }

    public void infoButtonOnAction(ActionEvent event) {
    }
}
