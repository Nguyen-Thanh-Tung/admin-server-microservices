package com.comit.services.employee.client.data;

import com.comit.services.employee.model.dto.BaseModelDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShiftDto extends BaseModelDto {
    private String name;

    @JsonProperty(value = "time_start")
    private String timeStart;

    @JsonProperty(value = "time_end")
    private String timeEnd;
}

