package com.comit.services.location.client.dto;

import com.comit.services.location.client.helper.Helper;
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
public class LocationDto {
    private Integer id;
    private String name;
    private String code;
    private String type;
    @JsonProperty("organization_id")
    private Integer organizationId;
    @JsonProperty("number_camera")
    private Integer numberCamera;
    @JsonProperty("number_employee")
    private Integer numberEmployee;

    public static LocationDto convertJsonToObject(JsonObject jsonObject) {
        try {
            Integer id = Helper.isNull(jsonObject,  "id") ? null : jsonObject.get("id").getAsInt();
            String name = Helper.isNull(jsonObject, "name") ? null : jsonObject.get("name").getAsString();
            String code = Helper.isNull(jsonObject, "code") ? null : jsonObject.get("code").getAsString();
            String type = Helper.isNull(jsonObject, "type") ? null : jsonObject.get("type").getAsString();
            Integer organizationId = Helper.isNull(jsonObject, "organization_id") ? null : jsonObject.get("organization_id").getAsInt();
            Integer numberCamera = Helper.isNull(jsonObject, "number_camera") ? null : jsonObject.get("number_camera").getAsInt();
            Integer numberEmployee = Helper.isNull(jsonObject, "number_employee") ? null : jsonObject.get("number_employee").getAsInt();
            return new LocationDto(id, name, code, type, organizationId, numberCamera, numberEmployee);
        } catch (Exception e) {
            log.error("Error LocationDto: " + e.getMessage());
            return null;
        }
    }
}

