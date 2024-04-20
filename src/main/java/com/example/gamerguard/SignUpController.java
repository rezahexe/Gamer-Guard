package com.example.gamerguard;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;


import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;


public class SignUpController implements Initializable {

    @FXML
    private ImageView logoImageView;
    @FXML
    private Button cancelButton;
    @FXML
    private Label registerMessageLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField firstameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;





    public  void initialize (URL url, ResourceBundle resourceBundle) {
        File logoFile = new File("Images/GAMER_GUARD_LOGO.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);
    }

    public void registerButtonOnAction (ActionEvent event) {

        if (setPasswordField.getText().equals(confirmPasswordField.getText())) {
            registerUser();
        } else {
            registerMessageLabel.setText("Password does not match!");
        }

    }

    public void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstameTextField.getText();
        String lastname = lastnameTextField.getText();
        String emailaddress = emailTextField.getText();
        String password = setPasswordField.getText();

        String insertFields = "";
        String insertValues = "";
        String insertToRegister = insertFields = insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            registerMessageLabel.setText("Registered successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }



    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

}
