package org.plu.rest.services;

import javax.websocket.Session;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;

import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class MailSender {

    public MailSender() {
        // TODO Auto-generated constructor stub
    }

    public void sendMail(String username,String password,String firstname,String lastname) {

        String to = username;

        String message = String.format("http://localhost:8080/user/new/"+firstname+"/"+lastname+"/"+username+"/"+password+"/USER");
        final String userr = "thecomicbookstore276@gmail.com";

        final String pass = "PPeerroo";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        //	props.put("mail.debug", "true"); // linija koja sluzi za debug mail-a



        javax.mail.Session session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userr, pass);
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage mm = new MimeMessage(session);

            // Set From: header field of the header.
            mm.setFrom(new InternetAddress(userr));

            // Set To: header field of the header.
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            mm.setSubject("REGISTRATION");

            // Now set the actual message
            mm.setText(message);

            // Send message
            Transport.send(mm);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            System.out.println(mex);
        }

    }



}
