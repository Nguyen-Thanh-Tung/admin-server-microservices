package com.comit.services.areaRestriction.model.dto;

import com.comit.services.areaRestriction.model.entity.AreaRestriction;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;

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

    public static AreaRestrictionDto convertAreaRestrictionToAreaRestrictionDto(AreaRestriction areaRestriction) {
        if (areaRestriction == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        AreaRestrictionDto areaRestrictionDto = modelMapper.map(areaRestriction, AreaRestrictionDto.class);
        return areaRestrictionDto;
    }
}
