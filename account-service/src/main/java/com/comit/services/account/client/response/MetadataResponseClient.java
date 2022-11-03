package com.comit.services.account.client.response;

import com.comit.services.account.client.data.MetadataDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetadataResponseClient extends BaseResponseClient {
    @JsonProperty(value = "metadata")
    private MetadataDtoClient metadata;
}
