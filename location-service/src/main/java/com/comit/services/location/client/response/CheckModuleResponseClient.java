package com.comit.services.location.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckModuleResponseClient extends BaseResponseClient {
    @JsonProperty(value = "is_module")
    private Boolean isModule;
}
