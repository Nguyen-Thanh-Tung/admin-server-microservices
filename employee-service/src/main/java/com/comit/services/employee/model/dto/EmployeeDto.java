package com.comit.services.employee.model.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto extends BaseModelDto {
    private String code;
    private String name;

    private String email;
    private String phone;
    private String status;

    @JsonProperty("location")
    private LocationDto location;

    @JsonProperty("embedding_id")
    private Integer embeddingId;

    @JsonIncludeProperties(value = {"id", "code", "name", "image", "email", "phone", "status", "shifts"})
    private List<EmployeeDto> employees;

    @JsonProperty("manager")
    @JsonIncludeProperties(value = {"id", "code", "name", "image", "email", "phone", "status"})
    private EmployeeDto manager;

    @JsonProperty("image")
    @JsonIncludeProperties(value = {"id", "path", "type"})
    private MetadataDto image;

    @JsonProperty("shifts")
    @JsonIncludeProperties(value = {"id", "name", "time_start", "time_end"})
    private List<ShiftDto> shifts;

    @JsonIncludeProperties(value = {"id", "area_restriction", "time_start", "time_end"})
    @JsonProperty(value = "area_employees")
    private List<AreaEmployeeTimeDto> areaEmployeeTimes;
}
