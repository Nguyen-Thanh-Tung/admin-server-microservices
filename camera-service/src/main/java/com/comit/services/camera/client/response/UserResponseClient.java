package com.comit.services.camera.client.response;

import com.comit.services.camera.client.data.UserDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseClient extends BaseResponseClient {
    @JsonProperty(value = "user")
    private UserDtoClient user;

    public UserResponseClient(int code, String message, UserDtoClient user) {
        this.code = code;
        this.message = message;
        this.user = user;
    }
}
