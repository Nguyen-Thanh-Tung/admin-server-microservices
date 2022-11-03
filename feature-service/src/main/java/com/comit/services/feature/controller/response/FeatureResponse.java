package com.comit.services.feature.controller.response;

import com.comit.services.feature.constant.FeatureErrorCode;
import com.comit.services.feature.model.dto.FeatureDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeatureResponse extends BaseResponse {
    @JsonProperty(value = "feature")
    private FeatureDto featureDto;

    public FeatureResponse(FeatureErrorCode errorCode, FeatureDto featureDto) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.featureDto = featureDto;
    }
}
