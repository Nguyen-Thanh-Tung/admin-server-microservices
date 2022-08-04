package com.comit.services.employee.client.data;

import com.comit.services.employee.model.dto.BaseModelDto;
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

    @JsonProperty(value = "number_employee_allow")
    private int numberEmployeeAllow;

    @JsonProperty(value = "number_camera")
    private int numberCamera;

    @JsonProperty(value = "number_notification")
    private int numberNotification;
}

