package com.comit.services.employee.server.constant;

import lombok.Getter;

@Getter
public enum EmployeeErrorCode {
    EMPLOYEE_CODE_EXISTED(101, "Nhân viên đã tồn tại"),
    EMPLOYEE_NOT_EXIST(0, "Nhân viên không tồn tại"),
    EMPLOYEE_IMAGE_IS_EXISTED(0, "Hình ảnh nhân viên đã tồn tại"),
    MISSING_EMPLOYEE_CODE_FIELD(0, "Thiếu mã nhân viên"),
    EMAIL_IS_INVALID(0, "Địa chỉ email là không hợp lệ"),
    PHONE_IS_INVALID(0, "Số điện thoại là không hợp lệ"),
    MANAGER_ID_IS_NOT_NUMBER(0, "Manager id is not number"),
    MANAGER_ID_IS_SAME_EMPLOYEE(0, "Người quản lý không hợp lệ"),
    MISSING_NAME_FIELD(0, "Tên là bắt buộc"),
    CAN_NOT_IMPORT_DATA(0, "Không thể import dữ liệu"),
    CAN_NOT_ADD_EMPLOYEE(0, "Không thể thêm nhân viên, hãy thử lại sau!"),
    CAN_NOT_UPDATE_EMPLOYEE(0, "Không thể cập nhật nhân viên, hãy thử lại sau!"),
    CAN_NOT_DELETE_EMPLOYEE(0, "Không thể xóa nhân viên, hãy thử lại sau!"),
    EMPLOYEE_CODE_OF_OTHER_EMPLOYEE(0, "Mã nhân viên thuộc về nhân viên khác"),
    SUCCESS(1, "Thành công"),
    FAIL(0, "Không thành công"),
    MISSING_SHIFT_IDS_FIELD(0, "Danh sách ca làm việc là bắt buộc"),
    SHIFT_IDS_IS_INVALID(0, "Danh sách ca làm việc không hợp lệ"),
    INTERNAL_ERROR(0, "Có lỗi xảy ra, vui lòng thử lại sau!"),
    PERMISSION_DENIED(0, "Bạn không có quyền thực hiện chức năng này"),
    MISSING_FILE_FIELD(0, "File là bắt buộc"),
    UN_SUPPORT_FILE_UPLOAD(0, "Không hỗ trợ kiểu file");

    private final int code;
    private final String message;

    EmployeeErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
