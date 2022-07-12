package com.comit.services.account.constant;

public enum RoleErrorCode {
    SUCCESS(1, "Success");
    private final int code;
    private String message;

    RoleErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
