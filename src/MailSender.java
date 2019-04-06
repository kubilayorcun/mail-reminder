import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.EventListener;
import java.util.Properties;
import java.awt.*;
import java.util.Random;

public class MailSender {
    Session session;
    int verificationCode;


    public MailSender(){

        final String username="kubilayorcun5@gmail.com";
        final String password = "anfbghjsrgty";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth" , "true");
        props.put("mail.smtp.host" , "smtp.gmail.com");
        props.put("mail.smtp.port","587");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        };
        session = Session.getDefaultInstance(props , authenticator);

    }

    public void sendMail(String mail , String subject , String content){
        Message mailMessage = new MimeMessage(session);
        try {
            mailMessage.setRecipients(Message.RecipientType.TO , InternetAddress.parse(mail));
            mailMessage.setFrom(new InternetAddress(mail));
            mailMessage.setSubject(subject);
            mailMessage.setContent(content , "text/html; charset=utf-8");
            Transport.send(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendConfirmationMail(String recipient) {
        verificationCode = (int)(Math.random() * (9999-1000)) + 1;

        Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress("kubilayorcun5@gmail.com"));
            message.setRecipients(Message.RecipientType.TO , InternetAddress.parse(recipient));
            message.setSubject("Confirmation Mail");
            message.setContent("Your verification code is: " + verificationCode, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public int getVerificationCode() {
        return verificationCode;
    }


}
