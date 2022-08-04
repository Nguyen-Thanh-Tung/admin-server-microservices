package com.comit.services.employee.client.response;

import com.comit.services.employee.client.data.ShiftDto;
import com.comit.services.employee.constant.EmployeeErrorCode;
import com.comit.services.employee.controller.response.BaseResponse;
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
    private ShiftDto shift;

    public ShiftResponse(EmployeeErrorCode errorCode, ShiftDto shift) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.shift = shift;
    }
}

