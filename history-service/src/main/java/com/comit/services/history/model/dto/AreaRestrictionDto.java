package com.comit.services.history.model.dto;

import com.comit.services.history.model.entity.AreaRestriction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;

import java.util.Date;

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
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(areaRestriction, AreaRestrictionDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}

