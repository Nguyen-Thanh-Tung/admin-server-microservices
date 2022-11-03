package com.comit.services.timeKeeping.controller.response;

import com.comit.services.timeKeeping.constant.TimeKeepingErrorCode;
import com.comit.services.timeKeeping.model.dto.TimeKeepingNotificationDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeKeepingNotificationResponse extends BaseResponse {
    @JsonProperty(value = "time_keeping_notification")
    private TimeKeepingNotificationDto timeKeepingNotificationDto;

    public TimeKeepingNotificationResponse(TimeKeepingErrorCode errorCode, TimeKeepingNotificationDto timeKeepingNotificationDto) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.timeKeepingNotificationDto = timeKeepingNotificationDto;
    }
}
