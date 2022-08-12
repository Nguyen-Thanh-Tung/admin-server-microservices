package com.comit.services.timeKeeping.client.dto;

import com.comit.services.timeKeeping.client.helper.Helper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class TimeKeepingNotificationDto {
    private Integer id;

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

    public static TimeKeepingNotificationDto convertJsonToObject(JsonObject jsonObject) {
        try {
            Integer id = Helper.isNull(jsonObject, "id") ? null : jsonObject.get("id").getAsInt();
            int lateTime = Helper.isNull(jsonObject, "late_time") ? 0 : jsonObject.get("late_time").getAsInt();
            int lateInWeek = Helper.isNull(jsonObject, "late_in_week") ? 0 : jsonObject.get("late_in_week").getAsInt();
            int lateInMonth = Helper.isNull(jsonObject, "late_in_month") ? 0 : jsonObject.get("late_in_month").getAsInt();
            int lateInQuarter = Helper.isNull(jsonObject, "late_in_quarter") ? 0 : jsonObject.get("late_in_quarter").getAsInt();
            int startDayOfWeek = Helper.isNull(jsonObject, "start_day_of_week") ? 0 : jsonObject.get("start_day_of_week").getAsInt();
            int endDayOfWeek = Helper.isNull(jsonObject, "end_day_of_week") ? 0 : jsonObject.get("end_day_of_week").getAsInt();
            NotificationMethodDto notificationMethodDto = Helper.isNull(jsonObject, "notification_method") ? null : NotificationMethodDto.convertJsonToObject(jsonObject.get("notification_method").getAsJsonObject());
            return new TimeKeepingNotificationDto(id, lateTime, lateInWeek, lateInMonth, lateInQuarter, startDayOfWeek, endDayOfWeek, notificationMethodDto);
        } catch (Exception e) {
            log.error("Error TimeKeepingNotificationDto: " + e.getMessage());
            return null;
        }
    }
}
