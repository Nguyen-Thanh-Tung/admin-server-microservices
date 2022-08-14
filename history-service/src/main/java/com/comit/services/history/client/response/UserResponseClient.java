package com.comit.services.history.client.response;

import com.comit.services.history.client.data.UserDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseClient extends BaseResponseClient {
    @JsonProperty(value = "user")
    private UserDtoClient user;

    public UserResponseClient(int errorCode, String errorMessage, UserDtoClient user) {
        this.code = errorCode;
        this.message = errorMessage;
        this.user = user;
    }
}
