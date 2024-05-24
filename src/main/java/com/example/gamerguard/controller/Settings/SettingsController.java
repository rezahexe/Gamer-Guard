package com.example.gamerguard.controller.Settings;

import com.example.gamerguard.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
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
        Stage stage = (Stage) AppearanceThemeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void AppearanceThemeButtonOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) AppearanceThemeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Appearance-theme.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void NotificationsOnAction(ActionEvent event) throws IOException {
         // Load the FXML file
         Stage stage = (Stage) NotificationsButton.getScene().getWindow();
         FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/notifi-settings.fxml"));
         Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
         stage.setScene(scene);
    }

    @FXML
    private void AccessibilityOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) AccessibilityButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Accessibility.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void PrivacyandSecurityOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) PrivacyandSecurityButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Privacy-and-security.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void DisplayOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) DisplayButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Display.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void LanguageOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) LanguageButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Language.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void HelpandSupportOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) HelpandSupportButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Help-and-support.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void AboutOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) AboutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/About.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

}