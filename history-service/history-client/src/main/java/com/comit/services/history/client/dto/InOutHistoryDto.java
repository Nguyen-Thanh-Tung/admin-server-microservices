package com.comit.services.history.client.dto;

import com.comit.services.camera.client.dto.CameraDto;
import com.comit.services.employee.client.dto.EmployeeDto;
import com.comit.services.metadata.client.dto.MetadataDto;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InOutHistoryDto {
    private Integer id;
    private String type;
    private Date time;

    @JsonIncludeProperties({"id", "name"})
    private CameraDto camera;

    @JsonIncludeProperties({"id", "code", "name", "manager"})
    private EmployeeDto employee;

    @JsonIncludeProperties({"id", "path", "type"})
    private MetadataDto image;
}
