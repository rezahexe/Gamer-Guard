package com.example.gamerguard.controller;

import com.example.gamerguard.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class SettingsController {
    @FXML
    private Button AppearanceThemeButton;
    @FXML
    private Button NotificationsButton;
    @FXML
    private Button AccessibilityButton;
    @FXML
    private Button PrivacyandSecurityButton;
    @FXML
    private Button DisplayButton;
    @FXML
    private Button LanguageButton;
    @FXML
    private Button HelpandSupportButton;
    @FXML
    private Button AboutButton;

    @FXML
    private void AppearanceThemeButtonOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) AppearanceThemeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Appearance-theme.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

        @FXML
        private void NotificationsOnAction(ActionEvent event) throws IOException {
            // Load the FXML file
            Stage stage = (Stage) NotificationsButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Notification.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
            stage.setScene(scene);
        }

    @FXML
    private void AccessibilityOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) AccessibilityButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Accessibility.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void PrivacyandSecurityOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) PrivacyandSecurityButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Privacy-and-security.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void DisplayOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) DisplayButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Display.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void LanguageOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) LanguageButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Language.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void HelpandSupportOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) HelpandSupportButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Help-and-support.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void AboutOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) AboutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("About.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

}
