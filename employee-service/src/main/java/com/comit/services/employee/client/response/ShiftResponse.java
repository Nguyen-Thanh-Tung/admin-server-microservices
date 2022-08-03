package com.comit.services.employee.client.response;

import com.comit.services.employee.constant.EmployeeErrorCode;
import com.comit.services.employee.controller.response.BaseResponse;
import com.comit.services.employee.model.entity.Shift;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShiftResponse extends BaseResponse {
    @JsonProperty(value = "shift")
    private Shift shift;

    public ShiftResponse(EmployeeErrorCode errorCode, Shift shift) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.shift = shift;
    }
}

