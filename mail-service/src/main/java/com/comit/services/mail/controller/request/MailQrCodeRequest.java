package com.comit.services.mail.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MailQrCodeRequest {
    String mailTo;
    String fullname;
    String employeeCode;
    String organizationName;
    String locationName;
    String locationCode;
    String type;
}
