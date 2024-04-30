package com.example.gamerguard.model;


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


public class SendMail
{

    Session newSession = null;
    MimeMessage mimeMessage = null;
    public static void main(String args[]) throws AddressException, MessagingException, IOException
    {
        System.out.println(">:3 PLZZZ...");
        SendMail mail = new SendMail();
        mail.setupServerProperties();
        mail.draftEmail();
        mail.sendEmail();
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


    MimeMessage draftEmail() throws AddressException, MessagingException, IOException {
        System.out.println(">:3 2. Draft email running...");

        String[] emailReceipients = {"phoebehoo123@gmail.com"};  //Enter list of email recepients
        String emailSubject = "FROM GAMERSSS Test Mail";
        String emailBody = "Test Body of my email";
        mimeMessage = new MimeMessage(newSession);

        for (int i =0 ;i<emailReceipients.length;i++)
        {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipients[i]));
        }
        mimeMessage.setSubject(emailSubject);

        // CREATE MIMEMESSAGE
        // CREATE MESSAGE BODY PARTS
        // CREATE MESSAGE MULTIPART
        // ADD MESSAGE BODY PARTS ----> MULTIPART
        // FINALLY ADD MULTIPART TO MESSAGECONTENT i.e. mimeMessage object


        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(emailBody,"html/text");
        MimeMultipart multiPart = new MimeMultipart();
        multiPart.addBodyPart(bodyPart);
        mimeMessage.setContent(multiPart);
        return mimeMessage;
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
