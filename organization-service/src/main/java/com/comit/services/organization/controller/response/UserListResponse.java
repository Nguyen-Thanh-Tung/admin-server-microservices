package com.comit.services.organization.controller.response;

import com.comit.services.organization.model.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserListResponse extends BaseResponse {
    @JsonProperty(value = "users")
    private List<User> users;

    public UserListResponse(int code, String message) {
        super(code, message);
    }
}
