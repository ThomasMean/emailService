package com.tmean.email.controller;

import com.tmean.email.models.ContactInput;
import com.tmean.email.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@Controller
public class EmailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    @PostMapping("/api/email/send")
    public ResponseEntity<Boolean> sendEmail(@RequestBody ContactInput input){
        try {
            if ( emailService.sendEmail(input)) {
            return ResponseEntity.ok(Boolean.TRUE);
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }
}
