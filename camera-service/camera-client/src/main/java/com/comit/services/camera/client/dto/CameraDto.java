package com.comit.services.camera.client.dto;

import com.comit.services.areaRestriction.client.dto.AreaRestrictionDto;
import com.comit.services.camera.client.helper.Helper;
import com.comit.services.location.client.dto.LocationDto;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
public class CameraDto {
    private Integer id;
    @JsonProperty(value = "ip_address")
    private String ipAddress;
    private String name;
    private String type;
    private String status;
    private String taken;

    // For time keeping module
    @JsonIncludeProperties(value = {"id", "name", "code", "type"})
    private LocationDto location;

    // For area restriction module
    @JsonIncludeProperties(value = {"id", "name", "code"})
    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDto areaRestriction;

    public static CameraDto convertJsonToObject(JsonObject jsonObject) {
        try {
            Integer id = Helper.isNull(jsonObject,"id") ? null : jsonObject.get("id").getAsInt();
            String ipAddress = Helper.isNull(jsonObject,"ip_address") ? null : jsonObject.get("ip_address").getAsString();
            String name = Helper.isNull(jsonObject,"name") ? null : jsonObject.get("name").getAsString();
            String type = Helper.isNull(jsonObject,"type") ? null : jsonObject.get("type").getAsString();
            String status = Helper.isNull(jsonObject,"status") ? null : jsonObject.get("status").getAsString();
            String taken = Helper.isNull(jsonObject,"taken") ? null : jsonObject.get("taken").getAsString();
            LocationDto locationDto = Helper.isNull(jsonObject,"location") ? null : LocationDto.convertJsonToObject(jsonObject.get("location").getAsJsonObject());
            AreaRestrictionDto areaRestrictionDto = Helper.isNull(jsonObject,"area_restriction") ? null : AreaRestrictionDto.convertJsonToObject(jsonObject.get("area_restriction").getAsJsonObject());
            return new CameraDto(id, ipAddress, name, type, status, taken, locationDto, areaRestrictionDto);
        } catch (Exception e) {
            log.error("Error CameraDto: " + e.getMessage());
            return null;
        }
    }
}

