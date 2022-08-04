package com.comit.services.history.controller.response;

import com.comit.services.history.constant.HistoryErrorCode;
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

    public CountResponse(HistoryErrorCode errorCode, int number) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.number = number;
    }
}

