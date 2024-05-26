package com.example.gamerguard.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * Controller class for the popup window.
 */
public class PopupController {

    @FXML
    private Button closeButton;

    @FXML
    private Button settingsButton;

    /**
     * Method called when the 'Close' button is pressed.
     * Closes the current stage.
     */
    @FXML
    private void handleClose() {
        // Get the current stage from the close button and close it
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Method called when the 'Settings' button is pressed.
     * Implement your settings handling logic here.
     */
    @FXML
    private void handleSettings() {
    }

    /**
     * Initialization method called after the FXML file is loaded.
     * You can perform any initialization logic here if necessary.
     */
    @FXML
    public void initialize() {
        // Initialization logic here, if necessary
    }
}
