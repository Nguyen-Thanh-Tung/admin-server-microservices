package com.comit.services.account.client.data;

import com.comit.services.account.model.dto.BaseModelDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto extends BaseModelDto {
    private String name;
    private String code;
    private String type;
    @JsonProperty(value = "organization_id")
    private Integer organizationId;
}
