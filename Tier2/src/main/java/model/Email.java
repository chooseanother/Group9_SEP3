package model;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author group9
 * @version 1.0
 */

public class Email {
    private String host = "smtp.gmail.com";
    Properties properties;

    /**
     * Creating the email
     */
    public Email() {
        properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
    }

    /**
     * Sending the email
     * @param source source
     * @param to to
     * @param username username
     * @param matchId match id
     */
    public void sendEmail(String source, String to, String username, int matchId){
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(source, "Jj86868686*");

            }

        });
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(source));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("It's your time to make a move!");

            message.setText("Your opponent " + username + " has made a move in the match with id: " + matchId);

            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
