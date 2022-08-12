package com.comit.services.camera.client.response;


import com.comit.services.camera.client.dto.CameraDto;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class CameraResponse extends BaseResponse {
    private Integer code;
    private String message;
    private CameraDto camera;

    public CameraResponse(int code, String message, CameraDto camera) {
        this.code = code;
        this.message = message;
        this.camera = camera;
    }

    public static CameraResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            CameraDto cameraDto = CameraDto.convertJsonToObject(jsonObject.get("camera").getAsJsonObject());
            return new CameraResponse(code, message, cameraDto);
        } catch (Exception e) {
            log.error("Error CameraResponse: " + e.getMessage());
            return null;
        }
    }
}

