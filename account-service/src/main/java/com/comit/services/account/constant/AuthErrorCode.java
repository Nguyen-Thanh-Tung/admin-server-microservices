package com.comit.services.account.constant;

public enum AuthErrorCode {
    //    Authentication
    MISSING_USERNAME_FIELD(0, "Tên tài khoản là bắt buộc"),
    MISSING_PASSWORD_FIELD(0, "Mật khẩu là bắt buộc"),
    USERNAME_INVALID(0, "Tên tài khoản không hợp lệ"),
    PASSWORD_INVALID(0, "Mật khẩu không hợp lệ, phải chứa ít nhất 8 ký tự trong đó có ít nhất 1 chữ in thường, 1 chữ in hoa, 1 số và 1 ký tự đặc biệt"),
    USER_IS_BLOCK(0, "Người dùng đã bị khóa"),
    MISSING_USER_ID_FIELD(0, "Thiếu id người dùng"),
    MISSING_OLD_PASSWORD_FIELD(0, "Mật khẩu cũ là bắt buộc"),
    MISSING_NEW_PASSWORD_FIELD(0, "Mật khẩu mới là bắt buộc"),
    OLD_PASSWORD_INVALID(0, "Mật khẩu cũ không hợp lệ"),
    NEW_PASSWORD_INVALID(0, "Mật khẩu mới không hợp lệ, phải chứa ít nhất 8 ký tự trong đó có ít nhất 1 chữ in thường, 1 chữ in hoa, 1 số và 1 ký tự đặc biệt"),
    MISSING_OLD_PASSWORD_OR_CODE_FIELD(0, "Phải có mật khẩu cũ hoặc nhập mã"),
    CODE_IS_NOT_VERIFIED(0, "Không thể xác thực mã cho người dùng"),

    PERMISSION_DENIED(0, "Bạn không có quyền thực hiện chức năng này"),
    INTERNAL_ERROR(0, "Có lỗi xảy ra, hãy thử lại sau!"),
    CANT_ADD_ORGANIZATION(0, "Không thể thêm tổ chức"),
    CANT_GET_ORGANIZATION(0, "Không thể lấy thông tin tổ chức");
    private final int code;
    private String message;

    AuthErrorCode(int code, String message) {
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
