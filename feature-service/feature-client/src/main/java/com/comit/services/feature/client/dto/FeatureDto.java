package com.comit.services.feature.client.dto;

import com.comit.services.feature.client.helper.Helper;
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
public class FeatureDto {
    private Integer id;
    private String name;
    private String description;

    @JsonProperty(value = "number_account")
    private int numberAccount;

    @JsonProperty(value = "number_organization")
    private int numberOrganization;

    public static FeatureDto convertJsonToObject(JsonObject jsonObject) {
        try {
            Integer id = Helper.isNull(jsonObject, "id") ? null : jsonObject.get("id").getAsInt();
            String name = Helper.isNull(jsonObject, "name") ? null : jsonObject.get("name").getAsString();
            String description = Helper.isNull(jsonObject, "description") ? null : jsonObject.get("description").getAsString();
            int numberAccount = Helper.isNull(jsonObject, "number_account") ? 0 : jsonObject.get("number_account").getAsInt();
            int numberOrganization = Helper.isNull(jsonObject, "number_organization") ? 0 : jsonObject.get("number_organization").getAsInt();
            return new FeatureDto(id, name, description, numberAccount, numberOrganization);
        } catch (Exception e) {
            log.error("Error FeatureDto: " + e.getMessage());
            return null;
        }
    }
}
