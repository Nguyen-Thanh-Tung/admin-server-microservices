package com.comit.services.camera.client.data;

import com.comit.services.camera.model.dto.BaseModelDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaRestrictionDto extends BaseModelDto {
    private String name;

    private String code;

    @JsonProperty(value = "time_start")
    private String timeStart;

    @JsonProperty(value = "time_end")
    private String timeEnd;

    @JsonProperty(value = "location_id")
    private Integer locationId;
}

