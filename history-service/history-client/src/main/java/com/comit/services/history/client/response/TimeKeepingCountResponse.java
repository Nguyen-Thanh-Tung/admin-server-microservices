package com.comit.services.history.client.response;

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
            int errorCode,
            String errorMessage,
            int numberCheckInCurrenDay,
            int numberUserNotificationInCurrenDay,
            int numberUserLateInMonth
    ) {
        this.code = errorCode;
        this.message = errorMessage;
        this.numberCheckInCurrenDay = numberCheckInCurrenDay;
        this.numberUserNotificationInCurrenDay = numberUserNotificationInCurrenDay;
        this.numberUserLateInMonth = numberUserLateInMonth;
    }
}
