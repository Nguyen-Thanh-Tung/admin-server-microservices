package com.comit.services.employee.client.dto;

//import com.comit.services.areaRestriction.client.dto.AreaEmployeeTimeDto;
import com.comit.services.employee.client.helper.Helper;
import com.comit.services.metadata.client.dto.MetadataDto;
import com.comit.services.timeKeeping.client.dto.ShiftDto;
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
public class EmployeeDto {
    private Integer id;
    private String code;
    private String name;
    private String email;
    private String phone;
    private String status;
    @JsonProperty("location_id")
    private Integer locationId;
    @JsonProperty("embedding_id")
    private Integer embeddingId;

    @JsonIncludeProperties(value = {"id", "path", "type"})
    private MetadataDto image;
    @JsonIncludeProperties(value = {"id", "code", "name", "image", "email", "phone", "status"})
    private List<EmployeeDto> employees;

    @JsonIncludeProperties(value = {"id", "code", "name", "image", "email", "phone", "status"})
    private EmployeeDto manager;

    @JsonIncludeProperties(value = {"id", "name", "time_start", "time_end"})
    private List<ShiftDto> shifts;

//    @JsonIncludeProperties(value = {"id", "area_restriction", "time_start", "time_end"})
//    @JsonProperty(value = "area_employees")
//    private List<AreaEmployeeTimeDto> areaEmployeeTimes;

    public static EmployeeDto convertJsonToObject(JsonObject jsonObject) {
        try {
            Integer id = Helper.isNull(jsonObject,  "id") ? null : jsonObject.get("id").getAsInt();
            String code = Helper.isNull(jsonObject, "code") ? null : jsonObject.get("code").getAsString();
            String name = Helper.isNull(jsonObject, "name") ? null : jsonObject.get("name").getAsString();
            String email = Helper.isNull(jsonObject, "email") ? null : jsonObject.get("email").getAsString();
            String phone = Helper.isNull(jsonObject, "phone") ? null : jsonObject.get("phone").getAsString();
            String status = Helper.isNull(jsonObject, "status") ? null : jsonObject.get("status").getAsString();
            Integer locationId = Helper.isNull(jsonObject, "location_id") ? null : jsonObject.get("location_id").getAsInt();
            Integer embeddingId = Helper.isNull(jsonObject, "embedding_id") ? null : jsonObject.get("embedding_id").getAsInt();

            MetadataDto metadataDto = Helper.isNull(jsonObject, "image") ? null : MetadataDto.convertJsonToObject(jsonObject.get("image").getAsJsonObject());
            List<EmployeeDto> employeeDtos = new ArrayList<>();
            if (!jsonObject.get("employees").isJsonNull()) {
                jsonObject.get("employees").getAsJsonArray().forEach(item -> {
                    employeeDtos.add(EmployeeDto.convertJsonToObject(item.getAsJsonObject()));
                });
            }
            EmployeeDto employeeDto = jsonObject.get("manager").isJsonNull() ? null : EmployeeDto.convertJsonToObject(jsonObject.get("manager").getAsJsonObject());
            List<ShiftDto> shiftDtos = new ArrayList<>();
            if (!jsonObject.get("shifts").isJsonNull()) {
                jsonObject.get("shifts").getAsJsonArray().forEach(item -> {
                    shiftDtos.add(ShiftDto.convertJsonToObject(item.getAsJsonObject()));
                });
            }

            return new EmployeeDto(id, code, name, email, phone, status, locationId, embeddingId, metadataDto, employeeDtos, employeeDto, shiftDtos);
        } catch (Exception e) {
            log.error("Error EmployeeDto: " + e.getMessage());
            return null;
        }
    }
}
