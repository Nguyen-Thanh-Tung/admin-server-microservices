package com.comit.services.history.client.response;

import com.comit.services.history.client.data.MetadataDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetadataResponse extends BaseResponseClient {
    @JsonProperty(value = "metadata")
    private MetadataDtoClient metadata;

    public MetadataResponse(
            int code,
            String message,
            MetadataDtoClient metadata) {
        this.code = code;
        this.message = message;
        this.metadata = metadata;
    }
}
