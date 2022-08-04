package com.comit.services.employee.client.response;

import com.comit.services.employee.client.data.MetadataDto;
import com.comit.services.employee.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetadataResponse extends BaseResponse {
    @JsonProperty(value = "metadata")
    private MetadataDto metadata;

    public MetadataResponse(
            int code,
            String message,
            MetadataDto metadata) {
        this.code = code;
        this.message = message;
        this.metadata = metadata;
    }
}
