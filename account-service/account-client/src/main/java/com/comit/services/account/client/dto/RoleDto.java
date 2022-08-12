package com.comit.services.account.client.dto;

import com.comit.services.account.client.helper.Helper;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class RoleDto {
    private Integer id;
    private String name;

    @JsonIncludeProperties({"id", "username"})
    private Set<UserDto> users;

    public static RoleDto convertJsonToObject(JsonObject jsonObject) {
        try {
            Integer id = Helper.isNull(jsonObject, "id") ? null : jsonObject.get("id").getAsInt();
            String name = Helper.isNull(jsonObject, "name") ? null : jsonObject.get("name").getAsString();
            Set<UserDto> userDtos = new HashSet<>();
            if (jsonObject.has("users") && jsonObject.get("users").isJsonArray()) {
                jsonObject.get("users").getAsJsonArray().forEach(item -> {
                    userDtos.add(UserDto.convertJsonToObject(item.getAsJsonObject()));
                });
            }
            return new RoleDto(id, name, userDtos);
        } catch (Exception e) {
            log.error("Error RoleDto: " + e.getMessage());
            return null;
        }
    }
}
