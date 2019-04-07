import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.EventListener;
import java.util.Properties;
import java.awt.*;
import java.util.Random;

public class MailSender {
    private Session session;
    private int verificationCode;


    public MailSender(){
        // Credentials for mailClient
        final String username="reminderservice00@gmail.com";
        final String password = "mailreminder123";

        // Set props of the mail client.
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth" , "true");
        props.put("mail.smtp.host" , "smtp.gmail.com");
        props.put("mail.smtp.port","587");

        // Authenticate credentials
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        };

        // Initialize mail client session.
        session = Session.getDefaultInstance(props , authenticator);

    }

    /** **
     *
     * @param mail mail of the recipient.
     * @param subject subject of the mail.
     * @param content content of the mail.
     */
    public void sendMail(String mail , String subject , String content){
        Message mailMessage = new MimeMessage(session);
        try {
            mailMessage.setRecipients(Message.RecipientType.TO , InternetAddress.parse(mail));
            mailMessage.setFrom(new InternetAddress("kubilayorcun5@gmail.com"));
            mailMessage.setSubject(subject);
            mailMessage.setContent(content , "text/html; charset=utf-8");
            Transport.send(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param recipient mail receiver's mail address.
     */
    public void sendConfirmationMail(String recipient) {
        // Create a one time password.(OTP)
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

    public String getVerificationCode() {
        return Integer.toString(verificationCode);
    }


}
