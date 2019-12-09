package com.tmean.email.service;

import com.sendgrid.*;
import com.tmean.email.models.ContactInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Value("${email.from}")
    private String emailFrom;

    @Value("${email.key}")
    private String emailKey;


    public Boolean sendEmail(ContactInput input) {
        Email from = new Email(emailFrom);
        String subject = generateSubject(input.getName(), input.getSubject());
        Email to = new Email(emailFrom);
        Content content = new Content("text/plain", generateContent(input.getEmail(), input.getName(), input.getContent()));
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            send(request);
            return true;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    protected Response send(Request request) throws IOException {
        SendGrid sendGrid = new SendGrid(emailKey);
        return sendGrid.api(request);
    }

    protected String generateContent(String fromEmail, String name, String message) {
        return String.format("Hi Tom!\n" +
                "%s has sent you a message. \n" +
                "Their email is %s . \n" +
                "The message is %s", name, fromEmail, message);
    }

    protected String generateSubject(String name, String subject) {
        return String.format("New Message from %s - %s", name, subject);
    }
}
