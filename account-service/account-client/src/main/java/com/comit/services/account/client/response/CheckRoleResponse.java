package com.comit.services.account.client.response;

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
public class CheckRoleResponse extends BaseResponse {
    @JsonProperty("has_role")
    private boolean hasRole;

    public CheckRoleResponse(int code, String message, boolean hasRole) {
        this.code = code;
        this.message = message;
        this.hasRole = hasRole;
    }

    public static CheckRoleResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            boolean hasRole = jsonObject.get("has_role").getAsBoolean();
            return new CheckRoleResponse(code, message, hasRole);
        } catch (Exception e) {
            log.error("Error CheckRoleResponse: " + e.getMessage());
            return null;
        }
    }
}
