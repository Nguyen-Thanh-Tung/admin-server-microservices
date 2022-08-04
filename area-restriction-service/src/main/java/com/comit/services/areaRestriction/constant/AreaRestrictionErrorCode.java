package com.comit.services.areaRestriction.constant;

public enum AreaRestrictionErrorCode {
    MISSING_AREA_RESTRICTION_NAME_FIELD(0, "Tên khu vực hạn chế là bắt buộc"),
    MISSING_AREA_RESTRICTION_CODE_FIELD(0, "Mã khu vực hạn chế là bắt buộc"),
    MISSING_AREA_RESTRICTION_MANAGER_IDS_FIELD(0, "Danh sách người phụ trách là bắt buộc"),
    MISSING_AREA_RESTRICTION_TIME_START_FIELD(0, "Thời gian bắt đầu là bắt buộc"),
    MISSING_AREA_RESTRICTION_TIME_END_FIELD(0, "Thời gian kết thúc là bắt buộc"),
    AREA_RESTRICTION_NOT_EXIST(0, "Khu vực hạn chế không tồn tại"),
    AREA_RESTRICTION_IS_EXISTED(0, "Khu vực hạn chế đã tồn tại"),
    AREA_RESTRICTION_NOTIFICATION_NOT_EXIST(0, "Cài đặt cho Khu vực hạn chế không tồn tại"),

    MANAGERS_NOT_FOUND(0, "Không có người phụ trách thỏa mãn"),
    CAN_NOT_DELETE_AREA_RESTRICTION(0, "Không thể xóa khu vực hạn chế"),
    SUCCESS(1, "Thành công"),
    INTERNAL_ERROR(0, "Có lỗi xảy ra, hãy thử lại sau!"), FAIL(0, "Không thành công");

    private final int code;
    private String message;

    AreaRestrictionErrorCode(int code, String message) {
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
