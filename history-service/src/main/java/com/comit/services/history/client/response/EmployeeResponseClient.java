package com.comit.services.history.client.response;

import com.comit.services.history.client.data.EmployeeDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseClient extends BaseResponseClient {
    @JsonProperty(value = "employee")
    private EmployeeDtoClient employee;

    public EmployeeResponseClient(int code, String message, EmployeeDtoClient employee) {
        this.code = code;
        this.message = message;
        this.employee = employee;
    }
}
