package com.comit.services.history.model.dto;

import com.comit.services.history.model.entity.Camera;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class CameraDto extends BaseModelDto {
    @JsonProperty(value = "ip_address")
    private String ipAddress;
    private String name;
    private String type;
    private String status;
    private String taken;

    // For area restriction module
    @JsonIncludeProperties(value = {"id", "name", "code"})
    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDto areaRestriction;

    public static CameraDto convertCameraToCameraDto(Camera camera) {
        if (camera == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(camera, CameraDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}

