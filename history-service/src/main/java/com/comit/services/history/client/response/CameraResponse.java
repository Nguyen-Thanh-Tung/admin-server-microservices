package com.comit.services.history.client.response;

import com.comit.services.history.client.data.CameraDto;
import com.comit.services.history.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CameraResponse extends BaseResponse {
    @JsonProperty(value = "camera")
    private CameraDto camera;

    public CameraResponse(int code, String message, CameraDto camera) {
        this.code = code;
        this.message = message;
        this.camera = camera;
    }
}
