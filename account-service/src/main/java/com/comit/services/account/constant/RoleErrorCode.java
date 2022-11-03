package com.comit.services.account.constant;

public enum RoleErrorCode {
    SUCCESS(1, "Success"),
    NOT_EXIST_ROLE(0, "Quyền không tồn tại"),
    INTERNAL_ERROR(0, "Có lỗi xảy ra, hãy thử lại sau");
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
