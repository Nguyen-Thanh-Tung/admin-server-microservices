package com.comit.services.account.client.response;

import com.comit.services.account.client.data.MetadataDto;
import com.comit.services.account.controller.response.BaseResponse;
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
    private MetadataDto metadata;
}
