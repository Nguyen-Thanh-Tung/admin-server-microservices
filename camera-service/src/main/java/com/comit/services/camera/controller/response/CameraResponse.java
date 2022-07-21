package com.comit.services.camera.controller.response;

import com.comit.services.camera.constant.CameraErrorCode;
import com.comit.services.camera.model.dto.CameraDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CameraResponse extends BaseResponse {
    @JsonProperty(value = "camera")
    private CameraDto cameraDto;

    public CameraResponse(CameraErrorCode errorCode, CameraDto cameraDto) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.cameraDto = cameraDto;
    }
}
