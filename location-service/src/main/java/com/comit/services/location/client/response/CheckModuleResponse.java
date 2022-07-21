package com.comit.services.location.client.response;

import com.comit.services.location.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckModuleResponse extends BaseResponse {
    @JsonProperty(value = "is_module")
    private Boolean isModule;

    public CheckModuleResponse(int code, String message, Boolean isModule) {
        super(code, message);
        this.isModule = isModule;
    }
}
