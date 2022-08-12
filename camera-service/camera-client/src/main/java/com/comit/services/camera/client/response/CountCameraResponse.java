package com.comit.services.camera.client.response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class CountCameraResponse extends BaseResponse {
    private Integer code;
    private String message;
    private int number;

    public CountCameraResponse(int errorCode, String errorMessage, int number) {
        this.code = errorCode;
        this.message = errorMessage;
        this.number = number;
    }

    public static CountCameraResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            int number = jsonObject.get("number").getAsInt();
            return new CountCameraResponse(code, message, number);
        } catch (Exception e) {
            log.error("Error CountCameraResponse: " + e.getMessage());
            return null;
        }
    }
}
