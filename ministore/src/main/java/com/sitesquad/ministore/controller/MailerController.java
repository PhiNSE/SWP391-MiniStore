package com.sitesquad.ministore.controller;

import com.sitesquad.ministore.dto.EmailRequest;
import com.sitesquad.ministore.service.MailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailerController {

    private final MailerService mailerService;

    @Autowired
    public MailerController(MailerService mailerService) {
        this.mailerService = mailerService;
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        mailerService.send(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getMessage());
        return "Email sent successfully";
    }
}
