package com.comit.services.account.client.response;

import com.comit.services.account.client.dto.RoleDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class RoleListResponse extends BaseResponse {
    @JsonProperty("roles")
    List<RoleDto> roleDtos;

    public RoleListResponse(int code, String message, List<RoleDto> roleDtos) {
        this.code = code;
        this.message = message;
        this.roleDtos = roleDtos;
    }

    public static RoleListResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            List<RoleDto> roleDtos = new ArrayList<>();
            jsonObject.get("roles").getAsJsonArray().forEach(item -> {
                roleDtos.add(RoleDto.convertJsonToObject(item.getAsJsonObject()));
            });
            return new RoleListResponse(code, message, roleDtos);
        } catch (Exception e) {
            log.error("Error RoleListResponse: " + e.getMessage());
            return null;
        }
    }
}
