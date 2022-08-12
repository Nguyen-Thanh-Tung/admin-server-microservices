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
public class SignUpResponse extends BaseResponse {
    @JsonProperty(value = "user")
    private UserDto userDto;

    public SignUpResponse(int errorCode, String errorMessage, UserDto userDto) {
        this.code = errorCode;
        this.message = errorMessage;
        this.userDto = userDto;
    }

    public static SignUpResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            UserDto userDto = UserDto.convertJsonToObject(jsonObject.get("has_role").getAsJsonObject());
            return new SignUpResponse(code, message, userDto);
        } catch (Exception e) {
            log.error("Error SignUpResponse: " + e.getMessage());
            return null;
        }
    }
}
