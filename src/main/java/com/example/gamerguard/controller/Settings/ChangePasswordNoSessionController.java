package com.example.gamerguard.controller.Settings;

import com.example.gamerguard.model.DatabaseConnection;
import com.example.gamerguard.other.HashInput;
import com.example.gamerguard.other.SessionInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * Controller class for handling password change without an active session.
 *
 * @author Serene Coders
 * @version 1.0.0
 */
public class ChangePasswordNoSessionController {
    public Button comfirmPasswordButton;
    @FXML
    private Button cancelButton;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    public PasswordField confirmNewPasswordField;
    @FXML
    private Label resetPasswordMessageLabel;
    private String emailAddress;


    /**
     * Sets the email address associated with the account.
     *
     * @param emailAddress The email address to be set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Retrieves the email address associated with the account.
     *
     * @return The email address associated with the account
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Closes change-password page.
     *
     * @param event Button click
     */
    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    /**
     * Connects database and get user ID from session information.
     * Updates password when two password is filled out and is the same.
     *
     * @param actionEvent Button click
     */
    public void confirmPasswordButtonOnAction(ActionEvent actionEvent) {
        Connection connectDB = DatabaseConnection.getInstance();

        //int userId = SessionInfo.getUserId();
        String emailaddress = getEmailAddress();
        String newPassword = newPasswordField.getText();
        String confirmNewPassword = confirmNewPasswordField.getText();

        if (newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            resetPasswordMessageLabel.setText("Fill all the fields to reset your password");
            return;
        }
        try {
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_account WHERE emailaddress = '" +emailaddress + "'");
            if (!newPassword.equals(confirmNewPassword)) {
                resetPasswordMessageLabel.setText("Passwords do not match");
                return;
            }
            String hashedPassword = HashInput.hashInput(newPassword);
            statement.executeUpdate("UPDATE user_account SET password = '" + hashedPassword + "' WHERE emailaddress = '" + emailaddress + "'");
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
