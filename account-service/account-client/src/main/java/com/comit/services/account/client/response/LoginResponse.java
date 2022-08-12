package com.comit.services.account.client.response;

import com.comit.services.account.client.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class LoginResponse extends BaseResponse {
    @JsonProperty(value = "user")
    private UserDto userDto;

    private String token;

    public LoginResponse(int errorCode, String errorMessage, UserDto userDto, String token) {
        this.code = errorCode;
        this.message = errorMessage;
        this.userDto = userDto;
        this.token = token;
    }

    public static LoginResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            String token = jsonObject.get("token").getAsString();
            UserDto userDto = UserDto.convertJsonToObject(jsonObject.get("user").getAsJsonObject());
            return new LoginResponse(code, message, userDto, token);
        } catch (Exception e) {
            log.error("Error LoginResponse: " + e.getMessage());
            return null;
        }
    }
}
