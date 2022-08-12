package com.comit.services.account.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    @JsonProperty(value = "user_id")
    private Integer userId;

    @JsonProperty(value = "old_password")
    private String oldPassword;

    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value = "new_password")
    private String newPassword;
}
