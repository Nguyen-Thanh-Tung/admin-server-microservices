package com.comit.services.account.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgetPasswordRequest {
    @JsonProperty(value = "email")
    private String email;
}
