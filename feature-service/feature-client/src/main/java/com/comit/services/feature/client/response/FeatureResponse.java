package com.comit.services.feature.client.response;

import com.comit.services.feature.client.dto.FeatureDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class FeatureResponse {
    private Integer code;
    private String message;
    @JsonProperty(value = "feature")
    private FeatureDto featureDto;

    public FeatureResponse(int errorCode, String errorMessage, FeatureDto featureDto) {
        this.code = errorCode;
        this.message = errorMessage;
        this.featureDto = featureDto;
    }

    public static FeatureResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            FeatureDto featureDto = jsonObject.get("feature").isJsonNull() ? null : FeatureDto.convertJsonToObject(jsonObject.get("feature").getAsJsonObject());

            return new FeatureResponse(code, message, featureDto);
        } catch (Exception e) {
            log.error("Error FeatureResponse: " + e.getMessage());
            return null;
        }
    }
}
