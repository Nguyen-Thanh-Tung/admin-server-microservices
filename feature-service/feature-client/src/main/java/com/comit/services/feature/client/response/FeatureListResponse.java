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
public class FeatureListResponse {
    private Integer code;
    private String message;
    @JsonProperty("features")
    private List<FeatureDto> featureDtos;


    public FeatureListResponse(
            int errorCode,
            String errorMessage,
            List<FeatureDto> featureDtos
    ) {
        this.code = errorCode;
        this.message = errorMessage;
        this.featureDtos = featureDtos;
    }

    public static FeatureListResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            List<FeatureDto> featureDtos = new ArrayList<>();
            jsonObject.get("users").getAsJsonArray().forEach(item -> {
                featureDtos.add(FeatureDto.convertJsonToObject(item.getAsJsonObject()));
            });
            return new FeatureListResponse(code, message, featureDtos);
        } catch (Exception e) {
            log.error("Error FeatureListResponse: " + e.getMessage());
            return null;
        }
    }
}
