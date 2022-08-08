package com.comit.services.areaRestriction.model.dto;

import com.comit.services.areaRestriction.model.entity.NotificationMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMethodDto extends BaseModelDto {
    @JsonProperty(value = "use_ott")
    private boolean useOTT;

    @JsonProperty(value = "use_email")
    private boolean useEmail;

    @JsonProperty(value = "use_screen")
    private boolean useScreen;

    @JsonProperty(value = "use_ring")
    private boolean useRing;

    @JsonProperty(value = "area_restriction_id")
    private Integer areaRestrictionId;

    public static NotificationMethodDto convertNotificationMethodToNotificationMethodDto(NotificationMethod notificationMethod) {
        if (notificationMethod == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(notificationMethod, NotificationMethodDto.class);
    }
}
