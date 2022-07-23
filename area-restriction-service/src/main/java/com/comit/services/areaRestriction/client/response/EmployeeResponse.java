package com.comit.services.areaRestriction.client.response;

import com.comit.services.areaRestriction.controller.response.BaseResponse;
import com.comit.services.areaRestriction.model.entity.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse extends BaseResponse {
    @JsonProperty(value = "employee")
    private Employee employee;

    public EmployeeResponse(int code, String message, Employee employee) {
        this.code = code;
        this.message = message;
        this.employee = employee;
    }
}
