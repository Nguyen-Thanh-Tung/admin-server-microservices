package com.comit.services.feature.controller.response;

import com.comit.services.feature.constant.FeatureErrorCode;
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
        this.code = FeatureErrorCode.SUCCESS.getCode();
        this.message = FeatureErrorCode.SUCCESS.getMessage();
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(String message) {
        this.code = FeatureErrorCode.SUCCESS.getCode();
        this.message = message;
    }

    public BaseResponse(FeatureErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
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
