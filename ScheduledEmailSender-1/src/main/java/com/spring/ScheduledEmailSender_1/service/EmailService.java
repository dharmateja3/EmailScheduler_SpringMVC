package com.spring.ScheduledEmailSender_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.spring.ScheduledEmailSender_1.entity.EmailRecipient;
import com.spring.ScheduledEmailSender_1.repository.EmailRecipientRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailRecipientRepository emailRecipientRepository;

    @Autowired
    private TaskScheduler taskScheduler;

    public void scheduleEmail(String dateTime, String subject, String body) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime scheduledTime = LocalDateTime.parse(dateTime, formatter);

        taskScheduler.schedule(() -> sendEmails(subject, stripHtmlTags(body)),
                scheduledTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private void sendEmails(String subject, String body) {
        List<EmailRecipient> recipients = emailRecipientRepository.findAll();
        for (EmailRecipient recipient : recipients) {
            sendEmail(recipient.getEmail(), subject, body);
        }
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
    
    private String stripHtmlTags(String html) {
        return HtmlUtils.htmlEscape(html.replaceAll("\\<.*?\\>", ""));
    }
}

