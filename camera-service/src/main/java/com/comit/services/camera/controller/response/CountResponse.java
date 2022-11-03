package com.comit.services.camera.controller.response;

import com.comit.services.camera.constant.CameraErrorCode;
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

    public CountResponse(CameraErrorCode errorCode, int number) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.number = number;
    }
}
