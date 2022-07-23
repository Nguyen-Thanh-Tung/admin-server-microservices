package com.comit.services.history.client.response;

import com.comit.services.history.controller.response.BaseResponse;
import com.comit.services.history.model.entity.Camera;
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
