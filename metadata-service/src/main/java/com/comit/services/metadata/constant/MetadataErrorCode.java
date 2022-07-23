package com.comit.services.metadata.constant;

public enum MetadataErrorCode {
    INTERNAL_ERROR(0, "Có lỗi xảy ra, hãy thử lại sau!"),
    CANT_GEN_ID(0, "Cant gen id, after 1000 times"),
    SUCCESS(1, "Thành công"),
    NOT_EXIST_METADATA(0, "Không tồn tại dữ liệu"), FAIL(0, "Không thành công");
    private final int code;
    private String message;

    MetadataErrorCode(int code, String message) {
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
