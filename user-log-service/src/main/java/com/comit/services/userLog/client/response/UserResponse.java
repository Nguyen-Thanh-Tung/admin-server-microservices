package com.comit.services.userLog.client.response;

import com.comit.services.userLog.controller.response.BaseResponse;
import com.comit.services.userLog.model.entity.User;
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
