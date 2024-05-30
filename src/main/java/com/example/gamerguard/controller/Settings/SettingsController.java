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


/**
 * Controller class for managing settings UI.
 *
 * @author Serene Coders
 * @version 1.0.0
 */
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
    @FXML
    private ImageView BackButton;


    /**
     * {@inheritDoc}
     *
     * Initializes the controller after its root element has been completely processed.
     */
    public void initialize(URL location, ResourceBundle resources) {
        File logoFile0 = new File("Images/button_back.png");
        Image logoImage0 = new Image(logoFile0.toURI().toString());
        BackButton.setImage(logoImage0);
    }


    /**
     * Handles the back button action to navigate to the dashboard.
     *
     * @param event The mouse event triggered by clicking the back button.
     * @throws java.io.IOException If an error occurs while loading the dashboard FXML file.
     */
    @FXML
    public void BackOnAction(MouseEvent event) throws IOException {
        Stage stage = (Stage) AppearanceThemeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }


    /**
     * Handles the appearance theme button action to navigate to appearance theme.
     *
     * @param event The mouse event triggered by clicking the appearance theme button.
     * @throws IOException If an error occurs while loading the appearance theme FXML file.
     */
    @FXML
    private void AppearanceThemeButtonOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) AppearanceThemeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Appearance-theme.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }


    /**
     * Handles the notifications button action to navigate to notification settings.
     *
     * @param event The mouse event triggered by clicking the notifications button.
     * @throws IOException If an error occurs while loading the notifi-settings FXML file.
     */
    @FXML
    private void NotificationsOnAction(ActionEvent event) throws IOException {
         // Load the FXML file
         Stage stage = (Stage) NotificationsButton.getScene().getWindow();
         FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/notifi-settings.fxml"));
         Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
         stage.setScene(scene);
    }


    /**
     * Handles the Accessibility button action to navigate to Accessibility settings.
     *
     * @param event The mouse event triggered by clicking the accessibility button.
     * @throws IOException If an error occurs while loading the accessibility FXML file.
     */
    @FXML
    private void AccessibilityOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) AccessibilityButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Accessibility.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }


    /**
     * Handles the privacy and security button action to navigate to privacy and security settings.
     *
     * @param event The mouse event triggered by clicking the privacy and security button.
     * @throws IOException If an error occurs while loading the Privacyand-and-security FXML file.
     */
    @FXML
    private void PrivacyandSecurityOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) PrivacyandSecurityButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Privacy-and-security.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }


    /**
     * Handles the display button action to navigate to display settings.
     *
     * @param event The mouse event triggered by clicking the display button.
     * @throws IOException If an error occurs while loading the Display FXML file.
     */
    @FXML
    private void DisplayOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) DisplayButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Display.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }


    /**
     * Handles the language button action to navigate to language settings.
     *
     * @param event The mouse event triggered by clicking the language button.
     * @throws IOException If an error occurs while loading the Language FXML file.
     */
    @FXML
    private void LanguageOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) LanguageButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Language.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }


    /**
     * Handles the help and support button action to navigate to help and support settings.
     *
     * @param event The mouse event triggered by clicking the help and support button.
     * @throws IOException If an error occurs while loading the help-and-support FXML file.
     */
    @FXML
    private void HelpandSupportOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) HelpandSupportButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Help-and-support.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }


    /**
     * Handles the about button action to navigate to about settings.
     *
     * @param event The mouse event triggered by clicking the about button.
     * @throws IOException If an error occurs while loading the About FXML file.
     */
    @FXML
    private void AboutOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) AboutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/About.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

}
