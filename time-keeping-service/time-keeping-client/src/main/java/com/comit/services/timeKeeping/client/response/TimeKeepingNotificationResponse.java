package com.comit.services.timeKeeping.client.response;

import com.comit.services.timeKeeping.client.dto.TimeKeepingNotificationDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
public class TimeKeepingNotificationResponse extends BaseResponse {
    @JsonProperty(value = "time_keeping_notification")
    private TimeKeepingNotificationDto timeKeepingNotificationDto;

    public TimeKeepingNotificationResponse(int errorCode, String errorMessage, TimeKeepingNotificationDto timeKeepingNotificationDto) {
        this.setCode(errorCode);
        this.setMessage(errorMessage);
        this.timeKeepingNotificationDto = timeKeepingNotificationDto;
    }

    public static TimeKeepingNotificationResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            TimeKeepingNotificationDto timeKeepingNotificationDto = TimeKeepingNotificationDto.convertJsonToObject(jsonObject.get("time_keeping_notification").getAsJsonObject());
            return new TimeKeepingNotificationResponse(code, message, timeKeepingNotificationDto);
        } catch (Exception e) {
            log.error("Error TimeKeepingNotificationResponse: " + e.getMessage());
            return null;
        }
    }
}
