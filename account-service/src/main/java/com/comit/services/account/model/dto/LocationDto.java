package com.comit.services.account.model.dto;

import com.comit.services.account.model.entity.Location;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class LocationDto extends BaseModelDto {
    private String name;
    private String code;
    private String type;

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
