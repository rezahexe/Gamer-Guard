package com.example.gamerguard.controller.Settings;

import com.example.gamerguard.HelloApplication;
import com.example.gamerguard.model.DatabaseConnection;
import com.example.gamerguard.other.HashInput;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ForgotPasswordController {

    @FXML
    private Button resetPasswordButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmNewPasswordField;
    @FXML
    private Label resetPasswordMessageLabel;


    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void resetPasswordButtonOnAction(ActionEvent event) {
        Connection connectDB = DatabaseConnection.getInstance();

        String emailaddress = emailTextField.getText();
        String newPassword = newPasswordField.getText();
        String confirmNewPassword = confirmNewPasswordField.getText();

        if (emailaddress.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            resetPasswordMessageLabel.setText("Fill all the fields to reset your password");
            return;
        }

        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_account WHERE emailaddress = '" + emailaddress + "'");

            if (!resultSet.next()) {
                resetPasswordMessageLabel.setText("Email address does not exist");
                return;
            }

            if (!newPassword.equals(confirmNewPassword)) {
                resetPasswordMessageLabel.setText("Passwords do not match");
                return;
            }
            String hashedPassword = HashInput.hashInput(newPassword);
            statement.executeUpdate("UPDATE user_account SET password = '" + hashedPassword + "' WHERE emailaddress = '" + emailaddress + "'");
            resetPasswordMessageLabel.setText("Password reset successfully! Close page");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            System.out.println("Error occurred during password reset: " + e.getMessage());
        }

    }
}
