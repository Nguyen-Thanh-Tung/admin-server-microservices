package com.comit.services.areaRestriction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseAreaRestrictionDto extends BaseModelDto {
    private String name;

    private String code;

    @JsonProperty(value = "time_start")
    private String timeStart;

    @JsonProperty(value = "time_end")
    private String timeEnd;

    @JsonProperty(value = "location_id")
    private Integer locationId;

    @JsonProperty(value = "manager_ids")
    private String managerIds;
}
