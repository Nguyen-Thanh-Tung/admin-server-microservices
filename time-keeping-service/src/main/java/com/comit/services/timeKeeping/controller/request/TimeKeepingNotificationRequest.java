package com.comit.services.timeKeeping.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeKeepingNotificationRequest {
    @JsonProperty(value = "late_time")
    private Integer lateTime;

    @JsonProperty(value = "late_in_week")
    private Integer lateInWeek;

    @JsonProperty(value = "late_in_month")
    private Integer lateInMonth;

    @JsonProperty(value = "late_in_quarter")
    private Integer lateInQuarter;

    @JsonProperty(value = "start_day_of_week")
    private Integer startDayOfWeek;

    @JsonProperty(value = "end_day_of_week")
    private Integer endDayOfWeek;

    @JsonProperty(value = "use_ott")
    private Boolean useOTT;

    @JsonProperty(value = "use_email")
    private Boolean useEmail;

    @JsonProperty(value = "use_screen")
    private Boolean useScreen;

    @JsonProperty(value = "use_ring")
    private Boolean useRing;
}
