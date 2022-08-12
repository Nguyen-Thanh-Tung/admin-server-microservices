package com.comit.services.account.client.dto;

import com.comit.services.account.client.helper.Helper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
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
public class UserDto {
    private Integer id;
    private String username;
    private String fullname;
    private String email;
    private String status;

    @JsonProperty("parent_id")
    private Integer parentId;
    @JsonProperty("avatar_id")
    private Integer avatarId;
    @JsonProperty("organization_id")
    private Integer organizationId;
    @JsonProperty("location_id")
    private Integer locationId;
    
    public static UserDto convertJsonToObject(JsonObject jsonObject) {
        try {
            Integer id = Helper.isNull(jsonObject, "id") ? null : jsonObject.get("id").getAsInt();
            String username = Helper.isNull(jsonObject, "username") ? null : jsonObject.get("username").getAsString();
            String fullname = Helper.isNull(jsonObject, "fullname") ? null : jsonObject.get("fullname").getAsString();
            String email = Helper.isNull(jsonObject, "email") ? null : jsonObject.get("email").getAsString();
            String status = Helper.isNull(jsonObject, "status") ? null : jsonObject.get("status").getAsString();
            Integer parentId = Helper.isNull(jsonObject, "parent_id") ? null : jsonObject.get("parent_id").getAsInt();
            Integer avatarId = Helper.isNull(jsonObject, "avatar_id") ? null : jsonObject.get("avatar_id").getAsInt();
            Integer organizationId = Helper.isNull(jsonObject, "organization_id") ? null : jsonObject.get("organization_id").getAsInt();
            Integer locationId = Helper.isNull(jsonObject, "location_id") ? null : jsonObject.get("location_id").getAsInt();
            return new UserDto(id, username, fullname, email, status, parentId, avatarId, organizationId, locationId);
        } catch (Exception e) {
            log.error("Error UserDto: " + e.getMessage());
            return null;
        }
    }
}

