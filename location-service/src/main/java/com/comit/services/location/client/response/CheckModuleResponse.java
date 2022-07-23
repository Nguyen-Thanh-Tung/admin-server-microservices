package com.comit.services.location.client.response;

import com.comit.services.location.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckModuleResponse extends BaseResponse {
    @JsonProperty(value = "is_module")
    private Boolean isModule;
}
