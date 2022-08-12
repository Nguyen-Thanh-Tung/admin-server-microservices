package com.example.userlog.server.constant;

public enum UserLogErrorCode {
    RATE_LIMIT_EXCEED(205, "Request rate limitation EXCEED!."),

    SUCCESS(1, "Success."),
    FAIL(0, "Fail"),
    INTERNAL_ERROR(0, "Có lỗi xảy ra, liên hệ admin để được hỗ trợ."),
    PERMISSION_DENIED(0, "Bạn không có quyền thực hiện chức năng này!"),


    //    Authentication
    MISSING_FILE_FIELD(0, "File tải lên bị thiếu"),
    UN_SUPPORT_FILE_UPLOAD(0, "File tải lên không được hỗ trợ"),

    MODULE_NOT_FOUND(0, "Module không tồn tại");
    private final int code;
    private String message;

    UserLogErrorCode(int code, String message) {
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
