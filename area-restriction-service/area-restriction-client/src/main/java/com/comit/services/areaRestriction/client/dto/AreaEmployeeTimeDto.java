package com.comit.services.areaRestriction.client.dto;

import com.comit.services.areaRestriction.client.helper.Helper;
import com.comit.services.employee.client.dto.EmployeeDto;
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
public class AreaEmployeeTimeDto {
    private Integer id;

    private EmployeeDto employee;

    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDto areaRestriction;

    @JsonProperty(value = "time_start")
    private String timeStart;

    @JsonProperty(value = "time_end")
    private String timeEnd;

    public static AreaEmployeeTimeDto convertJsonToObject(JsonObject jsonObject) {
        try {
            Integer id = Helper.isNull(jsonObject, "id") ? null : jsonObject.get("id").getAsInt();
            EmployeeDto employeeDto = Helper.isNull(jsonObject, "employee") ? null : EmployeeDto.convertJsonToObject(jsonObject.get("employee").getAsJsonObject());
            AreaRestrictionDto areaRestrictionDto = Helper.isNull(jsonObject, "area_restriction") ? null : AreaRestrictionDto.convertJsonToObject(jsonObject.get("area_restriction").getAsJsonObject());
            String timeStart = Helper.isNull(jsonObject, "time_start") ? null : jsonObject.get("time_start").getAsString();
            String timeEnd = Helper.isNull(jsonObject, "time_end") ? null : jsonObject.get("time_end").getAsString();
            return new AreaEmployeeTimeDto(id, employeeDto, areaRestrictionDto, timeStart, timeEnd);
        } catch (Exception e) {
            log.error("Error AreaEmployeeTimeDto: " + e.getMessage());
            return null;
        }
    }
}
