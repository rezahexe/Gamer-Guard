package com.example.gamerguard.controller;

import com.example.gamerguard.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    public Button cancelButton;
    Session newSession = null;
    MimeMessage mimeMessage = null;
    public void verifyButtonOnAction(javafx.event.ActionEvent actionEvent) {
        try {

            String otp = generateOTP();
            System.out.println(otp);
            System.out.println(">:3 PLZZZ...");
            setupServerProperties();


//            emailContents();
            System.out.println(">:3 2. Draft email running...");

            String[] emailReceipients = {"phoebehoo123@gmail.com"};  //Enter list of email recepients
            String emailSubject = "OTP From GamerGuard";
            String emailBody = otp;
            mimeMessage = new MimeMessage(newSession);

            for (String emailReceipient : emailReceipients) {
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipient));
            }
            mimeMessage.setSubject(emailSubject);

            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(emailBody);
            MimeMultipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(bodyPart);
            mimeMessage.setContent(multiPart);


            sendEmail();

            System.out.println("Go to password changeegeg >:2---------------");

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
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelButtonOnAction(ActionEvent actionEvent) {
    }


    void sendEmail() {
        System.out.println(">:3 3. Send email running...");

        String fromUser = "phoebecode@gmail.com";  //Enter sender email id
        String fromUserPassword = "uihb qius vzzx gvwg";  //Enter sender gmail password , this will be authenticated by gmail smtp server
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


//    MimeMessage emailContents() throws AddressException, MessagingException, IOException {
//        System.out.println(">:3 2. Draft email running...");
//
//        String[] emailReceipients = {"phoebehoo123@gmail.com"};  //Enter list of email recepients
//        String emailSubject = "OTP From GamerGuard";
//        String emailBody = otp;
//        mimeMessage = new MimeMessage(newSession);
//
//        for (String emailReceipient : emailReceipients) {
//            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipient));
//        }
//        mimeMessage.setSubject(emailSubject);
//
//        MimeBodyPart bodyPart = new MimeBodyPart();
//        bodyPart.setContent(emailBody,"html/text");
//        MimeMultipart multiPart = new MimeMultipart();
//        multiPart.addBodyPart(bodyPart);
//        mimeMessage.setContent(multiPart);
//        return mimeMessage;
//    }

    public static String generateOTP() {
        int randomNum = (int) (Math.random() * 9000) + 1000;
        String otp = String.valueOf(randomNum);
        System.out.println("OTP is " + otp);
        return otp;
    }

    void setupServerProperties() {
        System.out.println(">:3 1. Server properties running...");
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        newSession = Session.getDefaultInstance(properties,null);
    }
}
