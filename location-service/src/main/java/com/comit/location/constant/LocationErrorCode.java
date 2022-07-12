package com.comit.location.constant;

public enum LocationErrorCode {
    MISSING_NAME_FIELD(0, "Tên là bắt buộc"),
    MISSING_LOCATION_CODE_FIELD(0, "Mã chi nhánh là bắt buộc"),
    MISSING_LOCATION_TYPE_FIELD(0, "Kiểu chi nhánh là bắt buộc"),
    LOCATION_NOT_EXIST(0, "Chi nhánh không tồn tại"),
    LOCATION_CODE_EXIST(0, "Mã chi nhánh đã tồn tại"),
    CAN_NOT_DELETE_LOCATION(0, "Không thể xóa chi nhánh đã có thành viên"),
    ORGANIZATION_NOT_EXIST(0, ""),
    INTERNAL_ERROR(0, ""),
    SUCCESS(1, ""),
    PERMISSION_DENIED(0, "");
    private final int code;
    private String message;

    LocationErrorCode(int code, String message) {
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
