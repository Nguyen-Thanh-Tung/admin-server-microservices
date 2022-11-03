package com.comit.services.employee.client.response;

import com.comit.services.employee.client.data.MetadataDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetadataResponseClient extends BaseResponseClient {
    @JsonProperty(value = "metadata")
    private MetadataDtoClient metadata;

    public MetadataResponseClient(
            int code,
            String message,
            MetadataDtoClient metadata) {
        this.code = code;
        this.message = message;
        this.metadata = metadata;
    }
}
