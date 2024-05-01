package com.example.gamerguard.controller;

import com.example.gamerguard.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class OTPController {
    public Label otpField;
    public Button verifyButton;
    public Label OTPMessageLabel;
    public String otp = "empty otp";
    public TextField otpTextField;

    Session newSession = null;
    MimeMessage mimeMessage = null;

    public void verifyButtonOnAction(javafx.event.ActionEvent actionEvent) {
        String userOTP = otpTextField.getText();
        if (userOTP.equals(otp)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("change-password.fxml"));
                Stage passwordStage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                passwordStage.setScene(scene);
                ChangePasswordController controller = fxmlLoader.getController();

                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();
                passwordStage.show();
            } catch (IOException ex) {
                // Handle the IOException
                ex.printStackTrace();
            }
        } else{
            OTPMessageLabel.setText("Please enter the correct OTP.");
        }
    }

    public void sendOTPButtonOnAction(javafx.event.ActionEvent actionEvent) {
        OTPMessageLabel.setText("OTP sending, please wait a moment...");
        try {
            setupServerProperties();

            String userEmail = SessionInfo.getUserEmail();
            otp = generateOTP(); // Update the class-level otp variable
            String[] emailRecipient = {userEmail};
            String emailSubject = "OTP From GamerGuard";
            String emailBody = otp;
            mimeMessage = new MimeMessage(newSession);

            for (String emailReceipient : emailRecipient) {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipient));
            }
            mimeMessage.setSubject(emailSubject);

            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(emailBody);
            MimeMultipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(bodyPart);
            mimeMessage.setContent(multiPart);

            sendEmail();

            OTPMessageLabel.setText("OTP sent, please check your inbox.");

        } catch (MessagingException e) {
            OTPMessageLabel.setText("Mailing error...");
            throw new RuntimeException(e);
        }
    }



    void sendEmail() {
        String fromUser = "phoebecode@gmail.com";  //new throwaway account created for this assignment
        String fromUserPassword = "uihb qius vzzx gvwg";  //app password
        String emailHost = "smtp.gmail.com";
        Transport transport = null;

        try {
            transport = newSession.getTransport("smtp");
            transport.connect(emailHost, fromUser, fromUserPassword);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            System.out.println("Email successfully sent!!!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error sending email: " + e.getMessage());
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                    System.err.println("Error closing transport: " + e.getMessage());
                }
            }
        }
    }


    public static String generateOTP() {
        int randomNum = (int) (Math.random() * 9000) + 1000;
        String otp = String.valueOf(randomNum);
        System.out.println("OTP is " + otp);
        return otp;
    }


    void setupServerProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        newSession = Session.getDefaultInstance(properties,null);
    }
}
