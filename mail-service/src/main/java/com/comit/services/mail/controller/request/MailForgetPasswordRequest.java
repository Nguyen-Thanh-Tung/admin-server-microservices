package com.comit.services.mail.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailForgetPasswordRequest {
    private String mailTo;
    private String fullname;
    private int userId;
    private String code;
}
