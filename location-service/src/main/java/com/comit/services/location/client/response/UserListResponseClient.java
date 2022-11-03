package com.comit.services.location.client.response;

import com.comit.services.location.client.data.UserDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserListResponseClient extends BaseResponseClient {
    @JsonProperty(value = "users")
    private List<UserDtoClient> users;

    public UserListResponseClient(int code, String message, List<UserDtoClient> userDtoClients) {
        this.code = code;
        this.message = message;
        this.users = userDtoClients;
    }
}
