package com.comit.services.employee.controller.response;

import com.comit.services.employee.constant.EmployeeErrorCode;
import com.comit.services.employee.model.dto.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse extends BaseResponse {
    @JsonProperty(value = "employee")
    private EmployeeDto employeeDto;

    public EmployeeResponse(EmployeeErrorCode errorCode, EmployeeDto employeeDto) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.employeeDto = employeeDto;
    }
}
