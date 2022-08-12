package com.comit.services.organization.client.dto;

import com.comit.services.organization.client.helper.Helper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class OrganizationDto {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String description;
    @JsonProperty("number_user")
    private Integer numberUser;

    public static OrganizationDto convertJsonToObject(JsonObject jsonObject) {
        try {
            Integer id = Helper.isNull(jsonObject, "id") ? null : jsonObject.get("id").getAsInt();
            String name = Helper.isNull(jsonObject, "name") ? null : jsonObject.get("name").getAsString();
            String email = Helper.isNull(jsonObject, "email") ? null : jsonObject.get("email").getAsString();
            String phone = Helper.isNull(jsonObject, "phone") ? null : jsonObject.get("phone").getAsString();
            String address = Helper.isNull(jsonObject, "address") ? null : jsonObject.get("address").getAsString();
            String description = Helper.isNull(jsonObject, "description") ? null : jsonObject.get("description").getAsString();
            Integer numberUser = Helper.isNull(jsonObject, "number_user") ? null : jsonObject.get("number_user").getAsInt();
            return new OrganizationDto(id, name, email, phone, address, description, numberUser);
        } catch (Exception e) {
            log.error("Error OrganizationDto: " + e.getMessage());
            return null;
        }
    }
}

