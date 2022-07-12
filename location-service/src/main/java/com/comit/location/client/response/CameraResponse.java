package com.comit.location.client.response;

import com.comit.location.controller.response.BaseResponse;
import com.comit.location.model.entity.Camera;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CameraResponse extends BaseResponse {
    @JsonProperty(value = "camera")
    private Camera camera;

    public CameraResponse(int code, String message, Camera camera) {
        this.code = code;
        this.message = message;
        this.camera = camera;
    }
}
