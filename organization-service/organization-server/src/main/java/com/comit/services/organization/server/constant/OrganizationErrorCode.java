package com.comit.services.organization.server.constant;

public enum OrganizationErrorCode {
    MISSING_NAME_FIELD(0, "Tên là bắt buộc"),
    EMAIL_INVALID(0, "Đại chỉ email không hợp lệ"),
    PHONE_INVALID(0, "Số điện thoại không hợp lệ"),
    ORGANIZATION_NOT_EXIST(0, "Tổ chức không tồn tại"),
    ORGANIZATION_EXISTED(0, "Tổ chức đã tồn tại"),
    USER_BELONG_OTHER_ORGANIZATION(0, "Người dùng thuộc tổ chức khác"),
    USER_NOT_BELONG_ORGANIZATION(0, "Người dùng không thuộc tổ chức"),
    CAN_DELETE_ORGANIZATION_HAS_USER(0, "Không thể xóa tổ chức có thành viên"),
    CAN_DELETE_ORGANIZATION_HAS_LOCATION(0, "Không thể xóa tổ chức có chi nhánh"),
    CAN_NOT_IMPORT_DATA(0, "Không thể import dữ liệu"),

    INTERNAL_ERROR(0, "Có lỗi xảy ra, hãy thử lại sau!"),
    USER_NOT_EXIST(0, "Người dùng không tồn tại"),
    SUCCESS(1, "Thành công"),
    FAIL(0, "Không thành công"),
    MISSING_FILE_FIELD(0, "File là bắt buộc"),
    UN_SUPPORT_FILE_UPLOAD(0, "Không hỗ trợ kiểu file");
    private final int code;
    private String message;

    OrganizationErrorCode(int code, String message) {
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
