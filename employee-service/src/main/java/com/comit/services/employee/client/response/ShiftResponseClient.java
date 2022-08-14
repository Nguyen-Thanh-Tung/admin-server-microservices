package com.comit.services.employee.client.response;

import com.comit.services.employee.client.data.ShiftDtoClient;
import com.comit.services.employee.constant.EmployeeErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShiftResponseClient extends BaseResponseClient {
    @JsonProperty(value = "shift")
    private ShiftDtoClient shift;

    public ShiftResponseClient(EmployeeErrorCode errorCode, ShiftDtoClient shift) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.shift = shift;
    }
}

