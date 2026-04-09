package com.ordermanagement.system.service;

import com.ordermanagement.system.entity.Products;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${app.mail.from:${spring.mail.username:}}")
    private String fromEmail;

    @Value("${app.report.email.to:${spring.mail.username:}}")
    private String reportToEmail;

    public void sendLowStockAlert(Products product) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(this.reportToEmail);
            message.setSubject("Low Stock Alert \ud83d\udea8");
            message.setText("Product Id : " + product.getId() + "\nProduct Name : " + product.getProductName() + "\nAvailable Stock : " + product.getStock());
            message.setFrom(this.fromEmail);
            this.javaMailSender.send(message);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmailWithAttachment(String toEmail, String subject, String body, String filePath) {
        try {
            MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);
            helper.setFrom(this.fromEmail);
            FileSystemResource file = new FileSystemResource(filePath);
            helper.addAttachment(file.getFilename(), (InputStreamSource)file);
            this.javaMailSender.send(mimeMessage);
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
