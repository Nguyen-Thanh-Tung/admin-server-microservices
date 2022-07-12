package com.comit.location.client.response;

import com.comit.location.controller.response.BaseResponse;
import com.comit.location.model.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends BaseResponse {
    @JsonProperty(value = "user")
    private User user;
}
