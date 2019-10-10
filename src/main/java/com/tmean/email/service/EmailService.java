package com.tmean.email.service;

import com.tmean.email.models.ContactInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.from}")
    private String emailFrom;


    public Boolean sendEmail(ContactInput input) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(emailFrom);
            msg.setSubject(generateSubject(input.getName(), input.getSubject()));
            msg.setText(generateContent(input.getEmail(), input.getName(), input.getContent()));
            javaMailSender.send(msg);
            return true;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public String generateContent(String fromEmail, String name, String message) {
        return String.format("Hi Tom!\n" +
                "%s has sent you a message. \n" +
                "Their email is %s . \n" +
                "The message is %s", name, fromEmail, message);
    }

    public String generateSubject(String name, String subject) {
        return String.format("New Message from %s - %s", name, subject);
    }
}
