package com.comit.services.feature.controller.response;

import com.comit.services.feature.constant.FeatureErrorCode;
import com.comit.services.feature.model.dto.FeatureDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeatureListResponse extends BaseResponse {
    @JsonProperty("features")
    private List<FeatureDto> featureDtos;


    public FeatureListResponse(
            FeatureErrorCode errorCode,
            List<FeatureDto> featureDtos
    ) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.featureDtos = featureDtos;
    }
}
