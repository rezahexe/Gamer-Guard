package com.example.gamerguard.controller;

import javafx.event.ActionEvent;
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


/**
 * Controller class for sending OTP without a session.
 *
 * @author Serene Coders
 * @version 1.0.0
 */
public class OTPNoSessionSendController {
    public Label otpField;
    public Button verifyButton;
    public Label OTPMessageLabel;
    public String otp = "empty otp";
    public TextField otpTextField;
    public Button sendOTPButton;
    private boolean otpVerified = false;

    Session newSession = null;
    MimeMessage mimeMessage = null;

    private String emailAddress;

    /**
     * Method to set the email address.
     *
     * @param emailAddress Email address to be set.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Method to retrieve the email address.
     *
     * @return The set email address.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Return True so that the next page may be opened.
     *
     * @return True
     */
    public boolean isOtpVerified() {
        return otpVerified;
    }


    /**
     * Checks for correct user OTP input.
     * Otherwise, update message label and inform incorrect OTP.
     *
     * @param actionEvent Button click
     */
    public void verifyButtonOnAction(javafx.event.ActionEvent actionEvent) {
        String userOTP = otpTextField.getText();
        if (userOTP.equals(otp)) {
            otpVerified = true;
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } else{
            OTPMessageLabel.setText("Please enter the correct OTP.");
        }
    }


    /**
     * Sets up email content.
     * Send OTP email to user email.
     * Update labels to inform user of status, successful or unsuccessful.
     *
     * @param actionEvent Button click
     */
    public void sendOTPButtonOnAction(ActionEvent actionEvent) {
        try {
            String emailaddress = getEmailAddress();
            //sendOTPButtonOnAction(actionEvent, emailaddress);
            otp = generateOTP();
            setupServerProperties();
            String emailSubject = "OTP From GamerGuard";
            String emailBody = otp;
            mimeMessage = new MimeMessage(newSession);

            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailaddress));

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


    /**
     * Information of email account that sends OTP email.
     */
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


    /**
     * Generate random 4 digit OTP.
     *
     * @return OTP number
     */
    public static String generateOTP() {
        int randomNum = (int) (Math.random() * 9000) + 1000;
        String otp = String.valueOf(randomNum);
        System.out.println("OTP is " + otp); //Comment this out
        return otp;
    }


    /**
     * Uses javax.mail to send email.
     * Set port 587 for SMTP communication.
     * Enable authenticaion and TLS to true.
     */
    void setupServerProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        newSession = Session.getDefaultInstance(properties,null);
    }

}
