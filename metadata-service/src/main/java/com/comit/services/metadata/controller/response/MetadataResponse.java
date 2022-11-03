package com.comit.services.metadata.controller.response;

import com.comit.services.metadata.constant.MetadataErrorCode;
import com.comit.services.metadata.model.dto.MetadataDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetadataResponse extends BaseResponse {
    @JsonProperty(value = "metadata")
    private MetadataDto metadataDto;

    public MetadataResponse(
            MetadataErrorCode metadataErrorCode,
            MetadataDto metadataDto) {
        this.code = metadataErrorCode.getCode();
        this.message = metadataErrorCode.getMessage();
        this.metadataDto = metadataDto;
    }
}
