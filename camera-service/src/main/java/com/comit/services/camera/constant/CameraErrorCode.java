package com.comit.services.camera.constant;

public enum CameraErrorCode {
    MISSING_NAME_FIELD(0, "Tên là bắt buộc"),
    MISSING_IP_ADDRESS_FIELD(0, "Trường địa chỉ IP là bắt buộc"),
    MISSING_LOCATION_ID_FIELD(0, "Chi nhánh là bắt buộc"),
    INVALID_TYPE_FIELD_TK(0, "Loại camera phải là Check in/Check out"),
    INVALID_TYPE_FIELD_AREA(0, "Loại camera phải là Giám sát KVHC"),
    MISSING_POLYGONS_FIELD(0, "Mảng vị trí polygons là bắt buộc và không được để trống"),
    INVALID_POLYGONS_FIELD(0, "Trường polygons không đúng định dạng"),
    CAMERA_NOT_EXIST(0, "Camera không tồn tại"),
    MISSING_AREA_RESTRICTION_ID_FIELD(0, "Khu vực hạn chế là bắt buộc"),
    CAN_NOT_DELETE_CAMERA(0, "Không thể xóa camera"),
    CAN_NOT_UPDATE_POLYGON_CAMERA(0, "Không thể cập nhật vùng hạn chế"),
    CAMERA_NOT_WORKING(0, "Không thể kết nối đến link camera, hãy thử lại"),
    CAN_NOT_CHECK_CAMERA(0, "Không thể kiểm tra link camera, hãy thử lại sau"),
    SUCCESS(1, "Thành công"),
    AREA_RESTRICTION_NOT_EXIST(0, "Khu vực hạn chế không tồn tại"),
    LOCATION_NOT_EXIST(0, "Chi nhánh không tồn tại"),
    MODULE_NOT_FOUND(0, "Chức năng không tìm thấy"),
    INTERNAL_ERROR(0, "Có lỗi xảy ra, hãy thử lại!"),
    EXISTED_CAMERA_BY_NAME(0, "Tên camera đã được sử dụng"),
    PERMISSION_DENIED(0, "Bạn không có quyền thực hiện chức năng này"),
    LOCATION_NOT_IN_ORGANIZATION(0, "Chi nhánh không thuộc tổ chức"),
    INVALID_LOCATION_ID_FIELD(0, "Chi nhánh không hợp lệ"),
    INVALID_AREA_RESTRICTION_ID_FIELD(0, "Khu vực không hợp lệ");
    private final int code;
    private String message;

    CameraErrorCode(int code, String message) {
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
