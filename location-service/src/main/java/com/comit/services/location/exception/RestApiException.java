package com.comit.services.location.exception;

import com.comit.services.location.constant.LocationErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiException extends RuntimeException {
    private int code;
    private String message;
    private Exception originEx;
    private String debugInfo;

    public RestApiException(LocationErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public RestApiException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getResponseMessage() {
        return this.message;
    }

    @Override
    public String getMessage() {
        if (originEx != null) {
            return originEx.getMessage();
        } else if (debugInfo != null) {
            return debugInfo;
        }
        return super.getMessage();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        if (originEx != null) {
            return originEx.getStackTrace();
        }
        return super.getStackTrace();
    }
}
