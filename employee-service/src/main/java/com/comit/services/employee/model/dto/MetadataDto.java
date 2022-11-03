package com.comit.services.employee.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetadataDto {
    private Integer id;
    private String md5;
    private String path;
    private String type;
}
