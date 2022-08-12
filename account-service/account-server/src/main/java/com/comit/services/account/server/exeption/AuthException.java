package com.comit.services.account.server.exeption;

import com.comit.services.account.server.constant.AuthErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthException extends RuntimeException {
    private int code;
    private String message;
    private Exception originEx;
    private String debugInfo;

    public AuthException(AuthErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public AuthException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public AuthException(String debugInfo) {
        code = AuthErrorCode.INTERNAL_ERROR.getCode();
        this.message = AuthErrorCode.INTERNAL_ERROR.getMessage();
        this.debugInfo = debugInfo;
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
