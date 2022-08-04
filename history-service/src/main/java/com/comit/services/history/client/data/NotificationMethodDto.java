package com.comit.services.history.client.data;

import com.comit.services.history.model.dto.BaseModelDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private boolean areaRestrictionId;
}

