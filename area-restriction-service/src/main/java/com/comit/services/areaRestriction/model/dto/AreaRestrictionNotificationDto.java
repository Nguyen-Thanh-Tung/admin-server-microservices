package com.comit.services.areaRestriction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AreaRestrictionNotificationDto extends BaseModelDto {

    @JsonProperty(value = "area_restriction")
    private BaseAreaRestrictionDto areaRestrictionDto;

    @JsonProperty(value = "notification_method")
    private NotificationMethodDto notificationMethod;

    @JsonProperty(value = "managers")
    private List<AreaRestrictionManagerNotificationDto> areaRestrictionManagerNotifications;
}
