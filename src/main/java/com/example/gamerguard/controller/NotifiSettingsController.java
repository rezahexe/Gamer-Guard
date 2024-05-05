package com.example.gamerguard.controller;

import com.example.gamerguard.HelloApplication;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    //Back button code
    @FXML
    private ImageView BackButton;
    public void initialize(URL location, ResourceBundle resources) {
        File logoFile0 = new File("Images/button_back.png");
        Image logoImage0 = new Image(logoFile0.toURI().toString());
        BackButton.setImage(logoImage0);
    }

    @FXML
    public void BackOnAction(MouseEvent event) throws IOException {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private Button button1;

    private PauseTransition timer = new PauseTransition(Duration.seconds(10));
    private Stage popupStage; // You'll need to create this stage for your pop-up

    @FXML
    private void initialize() {
        setupPopupTimer();
    }

    private void setupPopupTimer() {
        timer.setOnFinished(e -> showPopup());
        timer.play();
    }

    private void showPopup() {
        // This is where you would create/show your pop-up if it's not already showing
        if (popupStage == null) {
            try {
                // Load the pop-up FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("popup.fxml"));
                Parent popupRoot = fxmlLoader.load();

                // Create and initialize the pop-up Stage
                popupStage = new Stage();
                popupStage.setScene(new Scene(popupRoot));
                popupStage.initModality(Modality.APPLICATION_MODAL); // Optional: makes the popup block input to other windows

                // When the pop-up is closed, reset the timer
                popupStage.setOnHiding(event -> timer.playFromStart());
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception as you see fit
                return; // Exit the method if loading failed
            }
        }

        if (!popupStage.isShowing()) {
            popupStage.show();
        }
    }


    // Logic for what should happen when button1 is clicked
    @FXML
    private void handleButton1Click(MouseEvent event) {
        // If you want to trigger the pop-up manually with a button
        showPopup();
    }

    // Other event handling methods and logic for the controller...
}
