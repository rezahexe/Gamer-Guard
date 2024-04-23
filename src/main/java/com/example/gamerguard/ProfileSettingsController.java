package com.example.gamerguard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.net.URL;

public class ProfileSettingsController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;


    @FXML
    private ImageView logoImageView;
    @FXML
    private ImageView button_back;
    @FXML
    private ImageView logoImageView1;
    @FXML
    private ImageView logoImageView2;


    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordPasswordField;


    /**
     * Initializes the controller with the Gamer Guard logo image.
     * Loads the logo image from the specified file path and sets it
     * as the image source for the logoImageView.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle that contains localized objects for the controller, or null if there is no resource bundle.
     */
    public  void initialize (URL url, ResourceBundle resourceBundle) {
        File logoFile = new File("Images/defaultprofile.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);

        File logoFile0 = new File("Images/button_back.png");
        Image logoImage0 = new Image(logoFile0.toURI().toString());
        button_back.setImage(logoImage0);

        File logoFile1 = new File("Images/icon_steam.png");
        Image logoImage1 = new Image(logoFile1.toURI().toString());
        logoImageView1.setImage(logoImage1);

        File logoFile2 = new File("Images/icon_spotify.png");
        Image logoImage2 = new Image(logoFile2.toURI().toString());
        logoImageView2.setImage(logoImage2);
    }

    /**
     * Handles the action event when the login button is clicked.
     * Validates whether the email and password fields are filled.
     * If both fields are filled, calls the validateLogin() method.
     * If any field is empty, updates the loginMessageLabel to prompt
     * the user to enter both email and password.
     *
     * @param event The ActionEvent triggered by clicking the login button.
     */
    public void loginButtonOnAction(ActionEvent event) {
        if (emailTextField.getText().isBlank() == false && passwordPasswordField.getText().isBlank() == false)  {
            validateLogin();

        } else {
            loginMessageLabel.setText("Please enter a username and password");

        }
    }

    /**
     * Handles the action event when the cancel button is clicked.
     * Retrieves the stage associated with the cancel button's scene
     * and closes it, effectively canceling the current operation.
     *
     * @param event The ActionEvent triggered by clicking the cancel button.
     */
    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void backButtonOnAction() {
        Stage stage = (Stage) button_back.getScene().getWindow();
        stage.close();
    }

    // Will be used in the future with loginButtonOnAction and SQL. Going to sleep now lol.
    public void validateLogin() {
        DatabaseConnection connectNow =  new DatabaseConnection();
        Connection connectDB =  connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_account WHERE emailaddress = '" + emailTextField.getText() + "' AND password = '" + passwordPasswordField.getText() + "'" ;

        try {

            Statement statement =  connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()) {
                if(queryResult.getInt(1) == 1){
//                    loginMessageLabel.setText("Welcome to Gamer Guard!");
                    createAccountForm();

                } else {
                    loginMessageLabel.setText("Invalid login. Please try again");
                }
            }

        } catch ( Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createAccountForm() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SignUp.fxml"));
            Stage createAccountStage = new Stage();
            createAccountStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            createAccountStage.setScene(scene);
            createAccountStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}

