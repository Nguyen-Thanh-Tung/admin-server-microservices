package com.comit.services.timeKeeping.constant;

import lombok.Getter;

@Getter
public enum ShiftErrorCode {
    MISSING_NAME_FIELD(0, "Tên là bắt buộc"),
    MISSING_TIME_START_FIELD(0, "Thiếu thời gian bắt đầu hoặc thời gian bắt đầu là trống"),
    MISSING_TIME_END_FIELD(0, "Thiếu thời gian kết thúc hoặc thời gian kết thúc là trống"),
    TIME_START_IS_INVALID(0, "Thời gian bắt đầu không hợp lệ"),
    TIME_END_IS_INVALID(0, "Thời gian kết thúc không hợp lệ"),
    SHIFT_NOT_EXIST(0, "Ca làm việc không tồn tại"),
    MISSING_SHIFT_IDS_FIELD(0, "Thiếu ca làm việc"),
    SHIFT_IDS_IS_INVALID(0, "Ca làm việc không hợp lệ");

    private final int code;
    private final String message;

    ShiftErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
