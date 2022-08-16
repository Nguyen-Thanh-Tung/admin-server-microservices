package com.comit.services.account.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MailRequestClient {
    String email;
    @JsonProperty("fullname")
    String fullName;
    int id;
    String code;
}
