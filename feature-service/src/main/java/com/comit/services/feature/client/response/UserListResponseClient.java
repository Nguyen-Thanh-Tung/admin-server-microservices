package com.comit.services.feature.client.response;

import com.comit.services.feature.client.data.UserDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserListResponseClient extends BaseResponseClient {
    @JsonProperty(value = "users")
    private List<UserDtoClient> userDtoClients;

    public UserListResponseClient(int code, String message, List<UserDtoClient> userDtoClients) {
        this.code = code;
        this.message = message;
        this.userDtoClients = userDtoClients;
    }
}
