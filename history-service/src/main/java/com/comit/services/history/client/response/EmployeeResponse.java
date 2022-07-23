package com.comit.services.history.client.response;

import com.comit.services.history.controller.response.BaseResponse;
import com.comit.services.history.model.entity.Employee;
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
