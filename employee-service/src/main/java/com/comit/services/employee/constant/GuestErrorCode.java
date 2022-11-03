package com.comit.services.employee.constant;

import lombok.Getter;

@Getter
public enum GuestErrorCode {
    EMAIL_IS_INVALID(0, "Địa chỉ email là không hợp lệ"),
    PHONE_IS_INVALID(0, "Số điện thoại là không hợp lệ"),
    MISSING_NAME_FIELD(0, "Tên là bắt buộc"),
    CAN_NOT_ADD_GUEST(0, "Không thể thêm nhân viên, hãy thử lại sau!"),
    CAN_NOT_UPDATE_GUEST(0, "Không thể cập nhật nhân viên, hãy thử lại sau!"),
    CAN_NOT_DELETE_GUEST(0, "Không thể xóa nhân viên, hãy thử lại sau!"),
    SUCCESS(1, "Thành công"),
    FAIL(0, "Không thành công"),
    INTERNAL_ERROR(0, "Có lỗi xảy ra, vui lòng thử lại sau!"),
    PERMISSION_DENIED(0, "Bạn không có quyền thực hiện chức năng này"),
    MISSING_IMAGE_FIELD(0, "Thiếu hình ảnh khách");

    private final int code;
    private final String message;

    GuestErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
