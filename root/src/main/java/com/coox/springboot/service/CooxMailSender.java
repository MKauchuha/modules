package com.coox.springboot.service;

import com.coox.springboot.dto.MailSendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class CooxMailSender {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender javaMailSender;
    private final MailProperties mailProps;

    public void sendEmail(MailSendRequest request) {
        System.out.println(mailProps);
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, "UTF-8");
            messageHelper.setFrom("noreply@visaburkina.bf");
            messageHelper.setTo(request.recipient());
            messageHelper.setSubject("X Performance Data Not Imported");
            messageHelper.setText(request.body(), true);
            javaMailSender.send(mailMessage);
        } catch (MessagingException ex) {
            throw new RuntimeException("Failed to send message", ex);
        }
    }

    public void sendEVanilla(MailSendRequest request) {
        Properties properties = new Properties();

        // Setup mail server
//        properties.putAll(this.mailProps.getProperties());
//        properties.setProperty("mail.smtp.host", this.mailProps.getHost());
//        properties.setProperty("mail.smtp.port", this.mailProps.getPort().toString());
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.host", "smtp.zeptomail.com");
        properties.put("mail.smtp.auth", "true");

        String username = "emailapikey";
        String password = "wSsVR610qxL3X/15mTT8JO9skVwHAwikQEx80Qeh6Xb9T/rE8cczkBfLVlXzH6QWFWM/EjBD8LovmUtR0zUMh9h5nw0BXiiF9mqRe1U4J3x17qnvhDzIWWhZkBaJLYIKwgVsmGdnE8si+g==";

        // compose the message
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("noreply@visaburkina.bf"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(request.recipient()));
            message.setSubject("X Performance Data Not Imported");
            message.setText(request.body());

            Transport.send(message);         //send Message
            System.out.println("Done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
