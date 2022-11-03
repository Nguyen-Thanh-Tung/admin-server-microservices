package com.comit.services.timeKeeping.client.response;

import com.comit.services.timeKeeping.client.data.UserDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseClient extends BaseResponseClient {
    @JsonProperty(value = "user")
    private UserDtoClient user;

    public UserResponseClient(int code, String message, UserDtoClient userDtoClient) {
        this.code = code;
        this.message = message;
        this.user = userDtoClient;
    }
}
