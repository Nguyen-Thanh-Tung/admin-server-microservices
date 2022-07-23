package com.comit.services.areaRestriction.controller.response;

import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.model.dto.NotificationMethodDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMethodResponse extends BaseResponse {
    @JsonProperty("notification_method")
    private NotificationMethodDto notificationMethodDto;

    public NotificationMethodResponse(AreaRestrictionErrorCode areaRestrictionErrorCode, NotificationMethodDto notificationMethodDto) {
        this.code = areaRestrictionErrorCode.getCode();
        this.message =areaRestrictionErrorCode.getMessage();
        this.notificationMethodDto = notificationMethodDto;
    }
}
