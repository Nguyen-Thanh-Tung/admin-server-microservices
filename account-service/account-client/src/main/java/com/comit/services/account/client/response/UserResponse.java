package com.comit.services.account.client.response;

import com.comit.services.account.client.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserResponse extends BaseResponse {
    @JsonProperty(value = "user")
    private UserDto userDto;

    public UserResponse(int errorCode, String errorMessage, UserDto userDto) {
        this.code = errorCode;
        this.message = errorMessage;
        this.userDto = userDto;
    }

    public static UserResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            UserDto userDto = jsonObject.get("user").isJsonNull() ? null : UserDto.convertJsonToObject(jsonObject.get("user").getAsJsonObject());
            return new UserResponse(code, message, userDto);
        } catch (Exception e) {
            log.error("Error UserResponse: " + e.getMessage());
            return null;
        }
    }
}
