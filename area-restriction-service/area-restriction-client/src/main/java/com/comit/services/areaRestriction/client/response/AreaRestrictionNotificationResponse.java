package com.comit.services.areaRestriction.client.response;

import com.comit.services.areaRestriction.client.dto.AreaRestrictionNotificationDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaRestrictionNotificationResponse extends BaseResponse {
    @JsonProperty(value = "area_restriction_notification")
    private AreaRestrictionNotificationDto areaRestrictionNotification;

    public AreaRestrictionNotificationResponse(int errorCode, String errorMessage, AreaRestrictionNotificationDto areaRestrictionNotification) {
        this.setCode(errorCode);
        this.setMessage(errorMessage);
        this.areaRestrictionNotification = areaRestrictionNotification;
    }
}
