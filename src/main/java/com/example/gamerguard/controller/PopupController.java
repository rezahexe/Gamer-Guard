package com.example.gamerguard.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PopupController {

    @FXML
    private Button closeButton;

    @FXML
    private Button settingsButton;

    // This method gets called when the 'Close' button is pressed
    @FXML
    private void handleClose() {
        // Get the current stage from the close button and close it
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    // This method gets called when the 'Settings' button is pressed
    @FXML
    private void handleSettings() {
        // Implement your settings handling logic here
        System.out.println("Settings button clicked");
    }

    // Initialization method if needed
    @FXML
    public void initialize() {
        // Initialization logic here, if necessary
    }
}
