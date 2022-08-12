package com.comit.services.mail.client.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MailForgetPasswordRequest {
    private String mailTo;
    private String fullname;
    private int userId;
    private String code;
}
