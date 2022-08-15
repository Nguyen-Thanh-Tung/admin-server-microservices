package com.comit.services.camera.controller.response;

import com.comit.services.camera.constant.CameraErrorCode;
import com.comit.services.camera.model.dto.BaseModelDto;
import com.comit.services.camera.model.dto.CameraDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CameraResponse extends BaseResponse {
    @JsonProperty(value = "camera")
    private BaseModelDto cameraDto;

    public CameraResponse(CameraErrorCode errorCode, BaseModelDto cameraDto) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.cameraDto = cameraDto;
    }
}
