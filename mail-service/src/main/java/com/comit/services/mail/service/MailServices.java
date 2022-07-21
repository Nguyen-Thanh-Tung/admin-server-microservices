package com.comit.services.mail.service;

import com.comit.services.mail.model.entity.Mail;

public interface MailServices {
    void sendEmail(Mail mail, String templateName);
}
