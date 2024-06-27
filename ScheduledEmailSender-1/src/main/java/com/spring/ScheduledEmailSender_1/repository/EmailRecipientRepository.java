package com.spring.ScheduledEmailSender_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.ScheduledEmailSender_1.entity.EmailRecipient;

@Repository
public interface EmailRecipientRepository extends JpaRepository<EmailRecipient, Long> {
}

