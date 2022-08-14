package com.comit.services.organization.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountUserResponse extends BaseResponseClient {
    private int number;

    public CountUserResponse(int errorCode, String errorMessage, int number) {
        this.code = errorCode;
        this.message = errorMessage;
        this.number = number;
    }
}

