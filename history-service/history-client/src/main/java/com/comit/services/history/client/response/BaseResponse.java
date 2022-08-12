package com.comit.services.history.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.context.annotation.RequestScope;

import java.io.Serializable;

@Getter
@Setter
@RequestScope
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class BaseResponse implements Serializable {
    protected Integer code;
    protected String message;

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
