package com.comit.services.employee.controller.response;

import com.comit.services.employee.constant.EmployeeErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountResponse extends BaseResponse {
    private int number;

    public CountResponse(EmployeeErrorCode errorCode, int number) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.number = number;
    }
}
