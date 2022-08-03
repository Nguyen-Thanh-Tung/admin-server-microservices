package com.comit.services.location.client.response;

import com.comit.services.location.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountResponse extends BaseResponse {
    @JsonProperty("number")
    private int number;


    public CountResponse(
            int code,
            String message,
            int number) {
        this.code = code;
        this.message = message;
        this.number = number;
    }
}
