package com.example.gamerguard.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

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
}
