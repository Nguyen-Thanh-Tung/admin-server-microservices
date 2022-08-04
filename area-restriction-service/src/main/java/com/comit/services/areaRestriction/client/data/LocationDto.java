package com.comit.services.areaRestriction.client.data;

import com.comit.services.areaRestriction.model.dto.BaseModelDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto extends BaseModelDto {
    private String name;
    private String code;
    private String type;
    @JsonProperty("organization_id")
    private Integer organizationId;
}
