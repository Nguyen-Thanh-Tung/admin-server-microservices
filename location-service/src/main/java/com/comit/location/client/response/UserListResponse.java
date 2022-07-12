package com.comit.location.client.response;

import com.comit.location.controller.response.BaseResponse;
import com.comit.location.model.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserListResponse extends BaseResponse {
    @JsonProperty(value = "users")
    private List<User> users;

}
