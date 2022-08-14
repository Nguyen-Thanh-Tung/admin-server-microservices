package com.comit.services.history.client.response;

import com.comit.services.history.client.data.CameraDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CameraResponse extends BaseResponseClient {
    @JsonProperty(value = "camera")
    private CameraDtoClient camera;

    public CameraResponse(int code, String message, CameraDtoClient camera) {
        this.code = code;
        this.message = message;
        this.camera = camera;
    }
}
