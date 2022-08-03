package com.comit.services.employee.model.dto;

import com.comit.services.employee.model.entity.Shift;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ShiftDto extends BaseModelDto {
    private String name;

    @JsonProperty(value = "time_start")
    private String timeStart;

    @JsonProperty(value = "time_end")
    private String timeEnd;

    public static ShiftDto convertShiftToShiftDto(Shift shift) {
        if (shift == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(shift, ShiftDto.class);
    }
}

