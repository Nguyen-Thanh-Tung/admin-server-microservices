package com.comit.services.account.client.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponseClient {
    protected Integer code;
    protected String message;
}
