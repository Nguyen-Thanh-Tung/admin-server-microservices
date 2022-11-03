package com.comit.services.account.controller.response;

import com.comit.services.account.constant.UserErrorCode;
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

    public CountResponse(UserErrorCode errorCode, int number) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.number = number;
    }
}
