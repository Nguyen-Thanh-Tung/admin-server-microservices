package com.comit.services.organization.controller.response;

import com.comit.services.organization.constant.OrganizationErrorCode;
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
        this.code = OrganizationErrorCode.SUCCESS.getCode();
        this.message = OrganizationErrorCode.SUCCESS.getMessage();
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(OrganizationErrorCode errorCode) {
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
