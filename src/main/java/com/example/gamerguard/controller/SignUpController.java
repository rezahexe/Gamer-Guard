package com.example.gamerguard.controller;

import com.example.gamerguard.model.DatabaseConnection;
import com.example.gamerguard.HelloApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;


public class SignUpController implements Initializable {

    @FXML
    private ImageView logoImageView;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label registerMessageLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Hyperlink termsAndConditionsHyperlink;
    @FXML
    private Hyperlink loginHyperlink;

    public static Button registerButtonStatic;





    public  void initialize (URL url, ResourceBundle resourceBundle) {
        registerButtonStatic = registerButton;
        File logoFile = new File("Images/GAMER_GUARD_LOGO.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);
    }



    public void registerUser() {
        Connection connectDB = DatabaseConnection.getInstance();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String emailaddress = emailTextField.getText();
        String password = setPasswordField.getText();

        String insertFields = "INSERT INTO user_account(firstname, lastname, emailaddress, password) VALUES ('";
        String insertValues = firstname + "','"+ lastname + "','" + emailaddress + "','" + password + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            registerMessageLabel.setText("Registered successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


    public void termsAndConditionsHyperlinkOnAction(ActionEvent event) throws IOException {
//        Stage stage = (Stage) termsAndConditionsHyperlink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("termsandconditions.fxml"));
        Stage termsAndConditionsStage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        termsAndConditionsStage.setScene(scene);

        TermsAndConditionsController controller = fxmlLoader.getController();
        controller.setRegisterButton(registerButton);
        termsAndConditionsStage.show();
    }

    public void registerButtonOnAction (ActionEvent event) {

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String emailaddress = emailTextField.getText();
        String password = setPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!firstname.isEmpty() && !lastname.isEmpty() && !emailaddress.isEmpty() && !password.isEmpty()) {
            if (password.equals(confirmPassword)) {
            registerUser();

            try {
                openDashboard();
            } catch (IOException e) {
                e.printStackTrace();
                e.getCause();
            }

        } else {
                registerMessageLabel.setText("Password does not match!");
            }
        } else {
            registerMessageLabel.setText("All fields must be filled");
        }

    }

    public void loginHyperlinkOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) loginHyperlink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    public void openDashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.setScene(scene);
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

}


