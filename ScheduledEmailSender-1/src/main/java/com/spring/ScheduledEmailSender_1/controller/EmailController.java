package com.spring.ScheduledEmailSender_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.ScheduledEmailSender_1.service.EmailService;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public String showForm(Model model) {
        return "scheduleEmail";
    }

    @PostMapping("/schedule")
    public String scheduleEmail(@RequestParam String dateTime,
                                @RequestParam String subject,
                                @RequestParam String body) {
        emailService.scheduleEmail(dateTime, subject, body);
        return "redirect:/success";
    }
    
    @GetMapping("/success")
    public String showSuccessPage() {
        return "success";
    }
}


