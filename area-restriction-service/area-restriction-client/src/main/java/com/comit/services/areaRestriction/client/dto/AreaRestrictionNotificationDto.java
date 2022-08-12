package com.comit.services.areaRestriction.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AreaRestrictionNotificationDto {
    private Integer id;

    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDto areaRestrictionDto;

    @JsonProperty(value = "notification_method")
    private NotificationMethodDto notificationMethod;

    @JsonProperty(value = "managers")
    private List<AreaRestrictionManagerNotificationDto> areaRestrictionManagerNotifications;
}
