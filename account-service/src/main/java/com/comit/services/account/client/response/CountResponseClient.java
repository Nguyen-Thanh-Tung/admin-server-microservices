package com.comit.services.account.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CountResponseClient extends BaseResponseClient {
    @JsonProperty("number")
    private int number;

    public CountResponseClient(
            int code,
            String message,
            int number) {
        this.code = code;
        this.message = message;
        this.number = number;
    }
}
