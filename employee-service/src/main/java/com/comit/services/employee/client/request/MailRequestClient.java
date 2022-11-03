package com.comit.services.employee.client.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MailRequestClient {
    String mailTo;
    String fullname;
    String employeeCode;
    String organizationName;
    String locationName;
    String locationCode;
}
