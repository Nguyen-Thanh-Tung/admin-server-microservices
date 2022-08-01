package com.comit.services.userLog.controller.response;

import com.comit.services.userLog.constant.UserLogErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.context.annotation.RequestScope;

import java.io.Serializable;

@Getter
@Setter
@RequestScope
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse implements Serializable {
    protected Integer code;
    protected String message;
    @JsonProperty(value = "request_id")
    protected String requestID;
    @JsonProperty(value = "request_time")
    protected String requestTime;
    @JsonProperty(value = "response_time")
    protected String responseTime;

    public BaseResponse() {
        this.code = UserLogErrorCode.SUCCESS.getCode();
        this.message = UserLogErrorCode.SUCCESS.getMessage();
    }

    public BaseResponse(UserLogErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseBase{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", requestID='" + requestID + '\'' +
                '}';
    }
}
