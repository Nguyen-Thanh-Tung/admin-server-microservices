package com.comit.services.areaRestriction.client.dto;

import com.comit.services.areaRestriction.client.helper.Helper;
import com.comit.services.employee.client.dto.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AreaRestrictionDto {
    private Integer id;
    private String name;

    private String code;

    @JsonProperty(value = "time_start")
    private String timeStart;

    @JsonProperty(value = "time_end")
    private String timeEnd;

    @JsonProperty(value = "location_id")
    private Integer locationId;

    @JsonIncludeProperties(value = {"id", "code", "name"})
    private List<EmployeeDto> managers;

    @JsonProperty(value = "number_employee_allow")
    private int numberEmployeeAllow;

    @JsonProperty(value = "number_camera")
    private int numberCamera;

    @JsonProperty(value = "number_notification")
    private int numberNotification;

    public static AreaRestrictionDto convertJsonToObject(JsonObject jsonObject) {
        try {
            Integer id = Helper.isNull(jsonObject, "id") ? null : jsonObject.get("id").getAsInt();
            String name = Helper.isNull(jsonObject,"name") ? null : jsonObject.get("name").getAsString();
            String code = Helper.isNull(jsonObject,"code") ? null : jsonObject.get("code").getAsString();
            String timeStart = Helper.isNull(jsonObject,"time_start") ? null : jsonObject.get("time_start").getAsString();
            String timeEnd = Helper.isNull(jsonObject,"time_end") ? null : jsonObject.get("time_end").getAsString();
            Integer locationId = Helper.isNull(jsonObject,"location_id") ? null : jsonObject.get("location_id").getAsInt();
            List<EmployeeDto> employeeDtos = new ArrayList<>();
            if (jsonObject.has("managers") && jsonObject.get("managers").isJsonArray()) {
                jsonObject.get("managers").getAsJsonArray().forEach(item -> {
                    employeeDtos.add(EmployeeDto.convertJsonToObject(item.getAsJsonObject()));
                });
            }
            int numberEmployeeAllow = Helper.isNull(jsonObject,"number_employee_allow") ? 0 : jsonObject.get("number_employee_allow").getAsInt();
            int numberCamera = Helper.isNull(jsonObject,"number_camera") ? 0 : jsonObject.get("number_camera").getAsInt();
            int numberNotification = Helper.isNull(jsonObject,"number_notification") ? 0 : jsonObject.get("number_notification").getAsInt();
            return new AreaRestrictionDto(id, name, code, timeStart, timeEnd, locationId, employeeDtos, numberEmployeeAllow, numberCamera, numberNotification);
        } catch (Exception e) {
            log.error("Error AreaRestrictionDto: " + e.getMessage());
            return null;
        }
    }
}

