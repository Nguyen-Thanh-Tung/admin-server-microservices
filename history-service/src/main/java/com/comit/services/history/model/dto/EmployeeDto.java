package com.comit.services.history.model.dto;

import com.comit.services.history.model.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto extends BaseModelDto {
    private String code;
    private String name;
    private String email;
    private String phone;
    private String status;

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

