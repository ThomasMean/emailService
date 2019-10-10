package com.tmean.email.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmean.email.models.ContactInput;
import com.tmean.email.service.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmailControllerTest {

    private static final String SUBJECT = "Test Subject";
    private static final String NAME = "Test Name";
    private static final String FROM_EMAIL = "Test Email";
    private static final String CONTENT = "Test Content";

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();
    }


    @Test
    public void shouldReturn200OkIfEmailSentSuccessfully() throws Exception {
        when(emailService.sendEmail(any(ContactInput.class))).thenReturn(Boolean.TRUE);
        String json = mapper.writeValueAsString(generateInput());
        mockMvc.perform(post("/api/email/send").contentType(APPLICATION_JSON_UTF8).content(json)).andExpect(status().isOk());
    }

    @Test
    public void shouldReturn400BadRequestIfEmailSendReturnsFalse() throws Exception {
        when(emailService.sendEmail(any(ContactInput.class))).thenReturn(Boolean.FALSE);
        String json = mapper.writeValueAsString(generateInput());
        mockMvc.perform(post("/api/email/send").contentType(APPLICATION_JSON_UTF8).content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn400BadRequestIfEmailSendThrowsException() throws Exception {
        when(emailService.sendEmail(any(ContactInput.class))).thenThrow(new RuntimeException());
        String json = mapper.writeValueAsString(generateInput());
        mockMvc.perform(post("/api/email/send").contentType(APPLICATION_JSON_UTF8).content(json)).andExpect(status().isBadRequest());
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