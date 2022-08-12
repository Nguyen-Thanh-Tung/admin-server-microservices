package com.comit.services.account.client.response;

import com.comit.services.account.client.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class UserListResponse extends BaseResponse {
    @JsonProperty(value = "users")
    private List<UserDto> userDtos;

    public UserListResponse(Integer errorCode, String errorMessage, List<UserDto> userDtos) {
        this.code = errorCode;
        this.message = errorMessage;
        this.userDtos = userDtos;
    }

    public static UserListResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            List<UserDto> userDtos = new ArrayList<>();
            if (jsonObject.get("users").isJsonArray()) {
                jsonObject.get("users").getAsJsonArray().forEach(item -> {
                    userDtos.add(UserDto.convertJsonToObject(item.getAsJsonObject()));
                });
            }
            return new UserListResponse(code, message, userDtos);
        } catch (Exception e) {
            log.error("Error UserListResponse: " + e.getMessage());
            return null;
        }
    }
}
