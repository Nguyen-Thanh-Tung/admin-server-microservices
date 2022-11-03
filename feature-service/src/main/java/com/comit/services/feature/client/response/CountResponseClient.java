package com.comit.services.feature.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountResponseClient extends BaseResponseClient {
    private int number;

    public CountResponseClient(int errorCode, String errorMessage, int number) {
        this.code = errorCode;
        this.message = errorMessage;
        this.number = number;
    }
}

