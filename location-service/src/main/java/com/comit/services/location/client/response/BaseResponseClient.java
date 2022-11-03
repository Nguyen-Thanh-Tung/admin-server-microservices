package com.comit.services.location.client.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponseClient {
    protected Integer code;
    protected String message;
}
