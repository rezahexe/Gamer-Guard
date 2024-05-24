package com.example.gamerguard.controller;

import com.example.gamerguard.other.SessionInfo;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class OTPNoSessionController {
    public Label otpField;
    public Button verifyButton;
    public Label OTPMessageLabel;
    public TextField otpTextField;


    /**
     * Checks for correct user OTP input.
     * Otherwise, update message label and inform incorrect OTP.
     * @param actionEvent Button click
     */
    public void verifyButtonOnAction(javafx.event.ActionEvent actionEvent) {
        OTPMessageLabel.setText("Meow meow testing");
//        String userOTP = otpTextField.getText();
//        if (userOTP.equals(otp)) {
//            otpVerified = true;
//            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//            currentStage.close();
//        } else{
//            OTPMessageLabel.setText("Please enter the correct OTP.");
//        }
    }
}