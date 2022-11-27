package com.comit.services.account.constant;

public enum UserErrorCode {
    MISSING_FULLNAME_FIELD(0, "Họ và tên là bắt buộc"),
    MISSING_EMAIL_FIELD(0, "Địa chỉ email là bắt buộc"),
    MISSING_LOCATION_FIELD(0, "Id chi nhánh là bắt buộc"),
    MISSING_ORGANIZATION_FIELD(0, "Id tổ chức là bắt buộc"),
    ORGANIZATION_INVALID(0, "Id tổ chức không hợp lệ"),
    LOCATION_INVALID(0, "Id chi nhánh không hợp lệ"),
    LOCATION_NOT_BELONG_ORGANIZATION(0, "Chi nhánh thuộc tổ chức không tồn tại"),
    ADMIN_LOCATION_INVALID(0, "Thêm mới người dùng với quyền admin không cần location_id"),
    EMAIL_INVALID(0, "Địa chỉ email không hợp lệ"),
    EMAIL_EXISTED(0, "Địa chỉ email đã được sử dụng"),
    MISSING_IS_LOCK_FIELD(0, "Trường is_lock là bắt buộc"),
    LIST_ROLE_INVALID(0, "Danh sách quyền không hợp lệ"),
    CAN_NOT_DELETE_USER(0, "Không thể xóa tài khoản"),
    USER_NOT_EXIST(0, "Người dùng không tồn tại"),
    CAN_NOT_ADD_USER(0, "Không thể thêm mới người dùng"),
    ORGANIZATION_NOT_EXIST(0, "Tổ chức không tồn tại"),
    LOCATION_NOT_EXIST(0, "Chi nhánh không tồn tại"),
    PERMISSION_DENIED(0, "Bạn không có quyền thực hiện chức năng này"),
    SUCCESS(1, "Success"),
    FAIL(0, "Fail"),
    CAN_NOT_UPDATE_USER(0, "Không thể cập nhật người dùng"),
    MISSING_FILE_FIELD(0, "File là bắt buộc"),
    MISSING_ROLE_FIELD(0, "Trường role là bắt buộc"),
    UN_SUPPORT_FILE_UPLOAD(0, "File tải lên không được"),
    CAN_NOT_GET_ORGANIZATION(0, "Không thể lấy thông tin tổ chức"),
    INTERNAL_ERROR(0, "Có lỗi xảy ra, hãy thử lại sau!"),
    TOKEN_INVALID(0, "Token không hợp lệ"),
    FULLNAME_IN_VALID(0, "Họ và tên không hợp lệ"),
    CAN_NOT_CHANGE_LIST_ROLE(0, "Bạn không thể thay đổi danh sách quyền của người dùng này"),
    UPDATE_FAIL(0, "Cập nhật không thành công với tài khoản có nhân viên trực thuộc"),
    NOT_IS_MULTIPART(0, "Content-Type phải là multipart/formdata");
    private final int code;
    private String message;

    UserErrorCode(int code, String message) {
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
