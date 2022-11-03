package com.comit.services.timeKeeping.constant;

public enum TimeKeepingErrorCode {
    PERMISSION_DENIED(0, "Bạn không có quyền thực hiện chức năng này"),
    SUCCESS(1, "Thành công"),
    INTERNAL_ERROR(0, "Có lỗi xảy ra, hãy thử lại sau!"), FAIL(0, "Không thành công");
    private final int code;
    private String message;

    TimeKeepingErrorCode(int code, String message) {
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
