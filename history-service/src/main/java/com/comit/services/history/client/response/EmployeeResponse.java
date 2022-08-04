package com.comit.services.history.client.response;

import com.comit.services.history.client.data.EmployeeDto;
import com.comit.services.history.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse extends BaseResponse {
    @JsonProperty(value = "employee")
    private EmployeeDto employee;

    public EmployeeResponse(int code, String message, EmployeeDto employee) {
        this.code = code;
        this.message = message;
        this.employee = employee;
    }
}
