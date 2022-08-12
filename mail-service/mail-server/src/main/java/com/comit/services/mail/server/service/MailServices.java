package com.comit.services.mail.server.service;

import com.comit.services.mail.server.entity.Mail;

public interface MailServices {
    void sendEmail(Mail mail, String templateName);
}
