package com.ewm.card.ewmhealthchecktool.mail.core;

import com.ewm.card.ewmhealthchecktool.mail.model.EmailMessage;

import javax.mail.MessagingException;

public interface SimpleEmailSender {
    void sendEmail(EmailMessage emailMessage) throws MessagingException;
}
