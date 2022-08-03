package com.comit.services.employee.model.dto;

import com.comit.services.employee.model.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

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

    @JsonIncludeProperties(value = {"id", "code", "name", "image", "email", "phone", "status"})
    private List<EmployeeDto> employees;

    @JsonIncludeProperties(value = {"id", "code", "name", "image", "email", "phone", "status"})
    private EmployeeDto manager;

    @JsonIncludeProperties(value = {"id", "path", "type"})
    private MetadataDto image;

    @JsonIncludeProperties(value = {"id", "name", "time_start", "time_end"})
    private List<ShiftDto> shifts;

    @JsonIncludeProperties(value = {"id", "area_restriction", "time_start", "time_end"})
    @JsonProperty(value = "area_employees")
    private List<AreaEmployeeTimeDto> areaEmployeeTimes;
}
