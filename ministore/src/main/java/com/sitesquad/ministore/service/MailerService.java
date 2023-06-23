package com.sitesquad.ministore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailerService {

//    @Autowired
//    private final JavaMailSender javaMailSender;
//
//    @Value("${spring.mail.username}")
//    private String from;
//
//
//
//    public void send(String to, String subject, String message) {
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(message);
//
//            javaMailSender.send(mimeMessage);
//            System.out.println("Message sent successfully");
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
