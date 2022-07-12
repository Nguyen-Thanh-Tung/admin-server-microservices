package com.comit.services.account.client.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MailRequest {
    String email;
    String fullName;
    int id;
    String code;
}
