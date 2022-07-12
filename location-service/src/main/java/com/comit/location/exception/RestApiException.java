package com.comit.location.exception;

import com.comit.location.constant.LocationErrorCode;
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

    public RestApiException(String debugInfo) {
        code = LocationErrorCode.INTERNAL_ERROR.getCode();
        this.message = LocationErrorCode.INTERNAL_ERROR.getMessage();
        this.debugInfo = debugInfo;
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
