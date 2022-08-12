package com.comit.services.feature.client.response;

import com.comit.services.feature.client.dto.FeatureDto;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class BaseResponse implements Serializable {
    protected Integer code;
    protected String message;

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static BaseResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            return new BaseResponse(code, message);
        } catch (Exception e) {
            log.error("Error BaseResponse Feature: " + e.getMessage());
            return null;
        }
    }
}
