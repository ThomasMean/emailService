package com.tmean.email.service;

import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.tmean.email.models.ContactInput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@RunWith(SpringJUnit4ClassRunner.class)
public class EmailServiceTest {

    private static final String SUBJECT = "Test Subject";
    private static final String NAME = "Test Name";
    private static final String FROM_EMAIL = "Test Email";
    private static final String CONTENT = "Test Content";

    @InjectMocks
    private EmailService emailService;

    private EmailService mockEmailService;

    @Before
    public void setup() throws IOException {
        mockEmailService = spy(emailService);
        doReturn(new Response()).when(mockEmailService).send(any(Request.class));
    }

    @Test
    public void sendEmailShouldReturnTrue() {
        assertThat(mockEmailService.sendEmail(generateInput())).isTrue();
    }

    @Test
    public void sendEmailShouldReturnFalseIfExceptionThrownInSendGrid() throws IOException {
        doThrow(new RuntimeException()).when(mockEmailService).send(any(Request.class));
        assertThat(mockEmailService.sendEmail(generateInput())).isFalse();
    }

    @Test
    public void generateSubjectShouldGenerateSubjectCorrectly() {
        assertThat(emailService.generateSubject(NAME, SUBJECT)).isEqualTo("New Message from Test Name - Test Subject");
    }

    @Test
    public void generateContentShouldGenerateContentCorrectly() {
        String contentResp = "Hi Tom!\n" +
                "Test Name has sent you a message. \n" +
                "Their email is Test Email . \n" +
                "The message is Test Content";
        assertThat(emailService.generateContent(FROM_EMAIL, NAME, CONTENT)).isEqualTo(contentResp);
    }

    private ContactInput generateInput() {
        ContactInput input = new ContactInput();
        input.setContent(CONTENT);
        input.setEmail(FROM_EMAIL);
        input.setName(NAME);
        input.setSubject(SUBJECT);
        return input;
    }

}