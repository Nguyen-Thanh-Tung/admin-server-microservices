package com.comit.services.areaRestriction.server.exception;

import com.comit.services.areaRestriction.server.constant.AreaRestrictionErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaRestrictionCommonException extends RuntimeException {
    private int code;
    private String message;
    private Exception originEx;
    private String debugInfo;

    public AreaRestrictionCommonException(AreaRestrictionErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public AreaRestrictionCommonException(int code, String message) {
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
