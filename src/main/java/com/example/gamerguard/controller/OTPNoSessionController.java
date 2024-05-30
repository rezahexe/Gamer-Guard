package com.example.gamerguard.controller;

import com.example.gamerguard.HelloApplication;
import com.example.gamerguard.controller.Settings.ChangePasswordNoSessionController;
import com.example.gamerguard.model.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Controller class for handling OTP verification without an active session.
 *
 * @author Serene Coders
 * @version 1.0.0
 */
public class OTPNoSessionController {

    @FXML
    private Label otpMessageLabel;

    @FXML
    private TextField otpTextField;

    @FXML
    private Label otpField;

    @FXML
    private Button verifyButton;

    /**
     * Checks for correct user OTP input.
     * Otherwise, update message label and inform incorrect OTP.
     *
     * @param actionEvent Button click
     */
    public void verifyButtonOnAction(ActionEvent actionEvent) {
        Connection connectDB = DatabaseConnection.getInstance();
        String emailaddress = otpTextField.getText();
        String checkEmailQuery = "SELECT COUNT(*) FROM user_account WHERE emailaddress = ?";
        try (PreparedStatement checkEmailStatement = connectDB.prepareStatement(checkEmailQuery)) {
            checkEmailStatement.setString(1, emailaddress);
            ResultSet resultSet = checkEmailStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                try {
                    OTPNoSessionSendController emailInstance = new OTPNoSessionSendController();

                    // Get the email address
                    String emailAddress = otpTextField.getText();

                    // Pass the email address to class B
                    emailInstance.setEmailAddress(emailAddress);

                    // Now you can retrieve the email address from class B whenever you need it
                    String retrievedEmailAddress = emailInstance.getEmailAddress();

                    // Pass the retrieved email address to OTPController
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("otpnosessionsend.fxml"));
                    Stage otpStage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    otpStage.setScene(scene);

                    OTPNoSessionSendController controller = fxmlLoader.getController();
                    controller.setEmailAddress(retrievedEmailAddress);
                    otpStage.showAndWait();
                    if (controller.isOtpVerified()) {
                        FXMLLoader passwordLoader = new FXMLLoader(HelloApplication.class.getResource("Settings_fxmls/change-passwordnosession.fxml"));
                        Stage passwordStage = new Stage();
                        Scene passwordScene = new Scene(passwordLoader.load(), 600, 400);
                        passwordStage.setScene(passwordScene);
                        passwordStage.show();

                        ChangePasswordNoSessionController controller2 = passwordLoader.getController();
                        controller2.setEmailAddress(retrievedEmailAddress);

                        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        currentStage.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else{
                otpField.setText("No account with this email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
