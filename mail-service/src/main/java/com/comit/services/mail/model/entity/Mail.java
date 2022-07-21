package com.comit.services.mail.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class Mail {
    private String mailFrom;

    private String mailTo;

    private String mailCc;

    private String mailBcc;

    private String mailSubject;

    private Map<String, Object> props;

    private String contentType;

    private String[] attachments;

    public Mail() {
        contentType = "text/plain";
    }

    public Date getMailSendDate() {
        return new Date();
    }
}
