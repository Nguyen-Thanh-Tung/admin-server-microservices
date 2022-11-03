package com.comit.services.history.controller.response;

import com.comit.services.history.constant.HistoryErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeKeepingCountResponse extends BaseResponse {
    @JsonProperty(value = "number_check_in")
    private int numberCheckInCurrenDay;

    @JsonProperty(value = "number_late_time")
    private int numberUserNotificationInCurrenDay;

    @JsonProperty(value = "number_late_in")
    private int numberUserLateInMonth;

    public TimeKeepingCountResponse(
            HistoryErrorCode errorCode,
            int numberCheckInCurrenDay,
            int numberUserNotificationInCurrenDay,
            int numberUserLateInMonth
    ) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.numberCheckInCurrenDay = numberCheckInCurrenDay;
        this.numberUserNotificationInCurrenDay = numberUserNotificationInCurrenDay;
        this.numberUserLateInMonth = numberUserLateInMonth;
    }
}
