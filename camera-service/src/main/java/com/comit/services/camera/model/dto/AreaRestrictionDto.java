package com.comit.services.camera.model.dto;

import com.comit.services.camera.model.entity.AreaRestriction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class AreaRestrictionDto extends BaseModelDto {
    private String name;

    private String code;

    @JsonProperty(value = "time_start")
    private String timeStart;

    @JsonProperty(value = "time_end")
    private String timeEnd;

    public static AreaRestrictionDto convertAreaRestrictionToAreaRestrictionDto(AreaRestriction areaRestriction) {
        if (areaRestriction == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(areaRestriction, AreaRestrictionDto.class);
    }
}

