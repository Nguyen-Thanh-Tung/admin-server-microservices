package com.comit.services.history.constant;

public enum HistoryErrorCode {
    NOT_EXIST_NOTIFICATION_HISTORY(0, "Lịch sử cảnh báo không tồn tại"),
    SUCCESS(1, "Thành công"),
    INTERNAL_ERROR(0, "Có lỗi xảy ra, hãy thử lại sau!"),
    FAIL(0, "Không thành công"),
    TIME_INVALID(0, "Trường time không đúng định dạng dd/MM/yyyy HH:mm:ss"),
    MISSING_TIME_FIELD(0, "Trường time là bắt buộc"),
    IMAGE_NOT_EXIST(0, "Image không tồn tại"),
    PERMISSION_DENIED(0, "Bạn không có quyền thực hiện chức năng này");
    private final int code;
    private String message;

    HistoryErrorCode(int code, String message) {
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
