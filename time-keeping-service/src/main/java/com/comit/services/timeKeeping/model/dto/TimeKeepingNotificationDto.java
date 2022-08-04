package com.comit.services.timeKeeping.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeKeepingNotificationDto extends BaseModelDto {
    @JsonProperty(value = "late_time")
    private int lateTime;

    @JsonProperty(value = "late_in_week")
    private int lateInWeek;

    @JsonProperty(value = "late_in_month")
    private int lateInMonth;

    @JsonProperty(value = "late_in_quarter")
    private int lateInQuarter;

    @JsonProperty(value = "start_day_of_week")
    private int startDayOfWeek;

    @JsonProperty(value = "end_day_of_week")
    private int endDayOfWeek;

    @JsonProperty(value = "notification_method")
    private NotificationMethodDto notificationMethod;
}
