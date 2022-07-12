package com.comit.location.model.dto;

import com.comit.location.model.entity.Location;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class LocationDto extends BaseModelDto {
    private String name;
    private String code;
    private String type;

    @JsonProperty(value = "number_camera")
    private Integer numberCamera;

    @JsonProperty(value = "number_employee")
    private Integer numberEmployees;

    public static LocationDto convertLocationToLocationDto(Location location) {
        if (location == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(location, LocationDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}
