package com.comit.services.organization.client.response;

import com.comit.services.organization.controller.response.BaseResponse;
import com.comit.services.organization.model.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends BaseResponse {
    @JsonProperty(value = "user")
    private User user;

    public UserResponse(int code, String message, User user) {
        this.code = code;
        this.message = message;
        this.user = user;
    }
}
