package com.comit.services.account.controller.response;

import com.comit.services.account.model.entity.Metadata;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetadataResponse extends BaseResponse {
    @JsonProperty(value = "metadata")
    private Metadata metadata;

    public MetadataResponse(int code, String message) {
        super(code, message);
    }
}
