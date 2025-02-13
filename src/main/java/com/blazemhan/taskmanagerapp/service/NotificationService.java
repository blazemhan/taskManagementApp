package com.blazemhan.taskmanagerapp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTaskNotification(String recipientEmail, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setFrom("phronesis212@gmail.com");
            helper.setText(message, true);
            mailSender.send(mimeMessage);
            System.out.println("📩 Email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException("❌ Failed to send email: " + e.getMessage());
        }
    }

}
