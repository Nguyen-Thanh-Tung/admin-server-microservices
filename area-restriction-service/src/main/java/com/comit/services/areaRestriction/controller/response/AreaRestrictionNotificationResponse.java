package com.comit.services.areaRestriction.controller.response;

import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.model.dto.AreaRestrictionNotificationDto;
import com.comit.services.areaRestriction.model.dto.BaseModelDto;
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
    private BaseModelDto areaRestrictionNotification;

    public AreaRestrictionNotificationResponse(AreaRestrictionErrorCode errorCode, BaseModelDto areaRestrictionNotification) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.areaRestrictionNotification = areaRestrictionNotification;
    }
}
