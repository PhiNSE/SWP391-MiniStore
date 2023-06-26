package com.sitesquad.ministore.repository;

import org.springframework.web.multipart.MultipartFile;

public interface EmailService {
    String sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body);

    String sendMailWithOutFile(String to, String[] cc, String subject, String body);
}
