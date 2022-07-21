package com.comit.services.camera.model.dto;

import com.comit.services.camera.model.entity.Camera;
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
