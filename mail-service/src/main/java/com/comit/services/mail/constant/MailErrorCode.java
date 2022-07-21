package com.comit.services.mail.constant;

public enum MailErrorCode {
    EXISTED_CAMERA_BY_NAME(0, "Tên camera đã được sử dụng trong chi nhánh"), SUCCESS(1, ""), FAIL(1, "");
    private final int code;
    private String message;

    MailErrorCode(int code, String message) {
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
