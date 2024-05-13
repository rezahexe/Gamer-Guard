package com.example.gamerguard.controller;

import com.example.gamerguard.HelloApplication;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NotifiSettingsController implements Initializable {

    @FXML
    private ImageView BackButton;
    @FXML
    private CheckBox hydrationCheckbox;
    @FXML
    private CheckBox breakReminderCheckbox;
    @FXML
    private CheckBox studyReminderCheckbox;
    @FXML
    private CheckBox notificationSoundCheckbox;


    private PauseTransition hydrationTimer = new PauseTransition(Duration.seconds(5));
    private PauseTransition breakTimer = new PauseTransition(Duration.seconds(5));
    private PauseTransition studyTimer = new PauseTransition(Duration.seconds(5));
    private PauseTransition soundTimer = new PauseTransition(Duration.seconds(5));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up the back button image
        File logoFile = new File("Images/button_back.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        BackButton.setImage(logoImage);

        // Set up hydration reminder
        setupReminder(hydrationCheckbox, hydrationTimer,  "Hydration Reminder","Need to take a break!");
        setupReminder(breakReminderCheckbox, breakTimer,  "Break Reminder","Time for a short break!");
        setupReminder(studyReminderCheckbox, studyTimer,  "Study Reminder","Time to focus on studies!");
        setupReminder(notificationSoundCheckbox, soundTimer, "Sound Notification","Sound notification activated!");
    }

    private void setupReminder(CheckBox checkBox, PauseTransition timer, String title, String message) {
        timer.setOnFinished(event -> {
            if (checkBox.isSelected()) {
                // Correctly capture checkBox and message in the lambda
                Platform.runLater(() -> showReminderPopup(checkBox, timer, title, message));
            }
        });

        checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                timer.playFromStart();
            } else {
                timer.stop();
            }
        });
    }

    private void showReminderPopup(CheckBox checkBox, PauseTransition timer, String title,String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait(); // Blocks until the alert is dismissed

        // Restart the timer here after the alert is closed, if the checkbox is still checked
        if (checkBox.isSelected()) {
            timer.playFromStart();
        }
    }
    @FXML
    public void BackOnAction(MouseEvent event) throws IOException {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }
}
