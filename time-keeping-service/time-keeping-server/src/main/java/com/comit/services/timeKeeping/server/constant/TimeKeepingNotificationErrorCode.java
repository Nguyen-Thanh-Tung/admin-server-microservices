package com.comit.services.timeKeeping.server.constant;

public enum TimeKeepingNotificationErrorCode {
    TIME_KEEPING_NOTIFICATION_NOT_EXIST(0, "Time keeping notification not exist"),
    MISSING_LATE_TIME_FIELD(0, "Thiếu số lần tối đa được phép đi muộn"),
    MISSING_LATE_IN_WEEK_FIELD(0, "Thiếu số lần đi muộn trong tuần thì bị cảnh báo"),
    MISSING_LATE_IN_MONTH_FIELD(0, "Thiếu số lần đi muộn trong tháng thì bị cảnh báo"),
    MISSING_LATE_IN_QUARTER_FIELD(0, "Thiếu số lần đi muộn trong quý thì bị cảnh báo"),
    MISSING_USE_OTT_FIELD(0, "Missing useOTT"),
    MISSING_USE_EMAIL_FIELD(0, "Missing useEmail"),
    MISSING_USE_SCREEN_FIELD(0, "Missing useScreen"),
    MISSING_USE_RING_FIELD(0, "Missing useRing");

    private final int code;
    private String message;

    TimeKeepingNotificationErrorCode(int code, String message) {
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
