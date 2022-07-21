package com.comit.services.location.client.response;

import com.comit.services.location.controller.response.BaseResponse;
import com.comit.services.location.model.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserListResponse extends BaseResponse {
    @JsonProperty(value = "users")
    private List<User> users;

    public UserListResponse(int code, String message, List<User> users) {
        this.code = code;
        this.message = message;
        this.users = users;
    }
}
