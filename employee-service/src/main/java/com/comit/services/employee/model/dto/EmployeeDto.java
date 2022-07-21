package com.comit.services.employee.model.dto;

import com.comit.services.employee.model.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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

    public static EmployeeDto convertEmployeeToEmployeeDto(Employee employee) {
        if (employee == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(employee, EmployeeDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}
