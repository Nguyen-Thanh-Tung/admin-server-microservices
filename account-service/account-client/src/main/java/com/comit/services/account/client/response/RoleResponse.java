package com.comit.services.account.client.response;

import com.comit.services.account.client.dto.RoleDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class RoleResponse extends BaseResponse {
    @JsonProperty("role")
    private RoleDto roleDto;

    public RoleResponse(int code, String message, RoleDto roleDto) {
        this.code = code;
        this.message = message;
        this.roleDto = roleDto;
    }

    public static RoleResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            RoleDto roleDto = RoleDto.convertJsonToObject(jsonObject.get("role").getAsJsonObject());
            return new RoleResponse(code, message, roleDto);
        } catch (Exception e) {
            log.error("Error RoleResponse: " + e.getMessage());
            return null;
        }
    }
}
