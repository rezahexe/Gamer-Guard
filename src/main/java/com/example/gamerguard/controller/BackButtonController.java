package com.example.gamerguard.controller;

import com.example.gamerguard.HelloApplication;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller class for handling actions related to the back button and profile settings button.
 */
public class BackButtonController implements Initializable {

    @FXML
    private ImageView BackButton;

    @FXML
    private Button ProfileSettingsButton;


    /**
     * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File logoFile0 = new File("Images/button_back.png");
        Image logoImage0 = new Image(logoFile0.toURI().toString());
        BackButton.setImage(logoImage0);
    }


    /**
     * Handles the action event for the back button. Loads the settings scene when the back button is clicked.
     *
     * @param event The mouse event triggered by clicking the back button.
     * @throws IOException If there is an error loading the FXML file.
     */
    @FXML
    public void BackOnAction(MouseEvent event) throws IOException {
        Stage stage = (Stage) BackButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/Settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Handles the action event for the profile settings button. Loads the profile settings scene when the profile settings button is clicked.
     *
     * @param event The action event triggered by clicking the profile settings button.
     * @throws IOException If there is an error loading the FXML file.
     */
    @FXML
    private void ProfileSettingsOnAction(ActionEvent event) throws IOException {
        // Load the FXML file
        Stage stage = (Stage) ProfileSettingsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/profile-settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }
}

