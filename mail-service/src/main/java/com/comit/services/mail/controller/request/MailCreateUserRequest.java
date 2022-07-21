package com.comit.services.mail.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MailCreateUserRequest {
    private String email;
    private String fullName;
    private int id;
    private String code;
}
