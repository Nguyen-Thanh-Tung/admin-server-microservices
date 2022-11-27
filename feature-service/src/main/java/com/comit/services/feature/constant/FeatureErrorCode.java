package com.comit.services.feature.constant;

public enum FeatureErrorCode {
    MISSING_NAME_FIELD(0, "Tên là bắt buộc"),
    FEATURE_NOT_EXIST(0, "Tính năng không tồn tại"),
    INTERNAL_ERROR(0, "Có lỗi xảy ra, hãy thử lại sau!"),
    SUCCESS(1, "Thành công"),
    PERMISSION_DENIED(0, "Bạn không có quyền thực hiện chức năng này");
    private final int code;
    private String message;

    FeatureErrorCode(int code, String message) {
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
