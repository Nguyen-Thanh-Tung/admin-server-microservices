package com.comit.services.timeKeeping.server.exception;

import com.comit.services.timeKeeping.server.constant.ShiftErrorCode;
import com.comit.services.timeKeeping.server.constant.TimeKeepingErrorCode;
import com.comit.services.timeKeeping.server.constant.TimeKeepingNotificationErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeKeepingCommonException extends RuntimeException {
    private int code;
    private String message;
    private Exception originEx;
    private String debugInfo;

    public TimeKeepingCommonException(TimeKeepingErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public TimeKeepingCommonException(TimeKeepingNotificationErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public TimeKeepingCommonException(ShiftErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public TimeKeepingCommonException(int code, String message) {
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
