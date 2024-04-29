package com.example.gamerguard;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class SettingsController {

        @FXML
        private void handleAppearanceTheme() {
            try {
                // Load the FXML file
                Parent root = FXMLLoader.load(getClass().getResource("Appearance-theme.fxml"));

                // Create a new stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        @FXML
        private void handleNotifications () {
            try {
                // Load the FXML file
                Parent root = FXMLLoader.load(getClass().getResource("Notifications.fxml"));

                // Create a new stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            }
        }

        @FXML
        private void handleAccessibility () {
            try {
                // Load the FXML file
                Parent root = FXMLLoader.load(getClass().getResource("Accessibility.fxml"));

                // Create a new stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            }
        }

    @FXML
    private void handlePrivacyandSettings () {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("Privacy-and-security.fxml"));

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    @FXML
    private void handleDisplay () {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("Display.fxml"));

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    @FXML
    private void handleLanguage () {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("Language.fxml"));

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    @FXML
    private void handleHelpandSupport () {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("Help-and-support.fxml"));

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

}

