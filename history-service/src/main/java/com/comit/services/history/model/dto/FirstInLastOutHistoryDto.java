package com.comit.services.history.model.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FirstInLastOutHistoryDto extends BaseModelDto {
    private String type;

    private Date time;

    @JsonIncludeProperties({"id", "name"})
    private CameraDto camera;

    @JsonIncludeProperties({"id", "code", "name", "manager"})
    private EmployeeDto employee;

    @JsonIncludeProperties({"id", "path", "type"})
    private MetadataDto image;
}
