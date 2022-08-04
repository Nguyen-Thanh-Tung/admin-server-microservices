package com.comit.services.history.model.dto;

import com.comit.services.history.client.data.CameraDto;
import com.comit.services.history.client.data.EmployeeDto;
import com.comit.services.history.client.data.MetadataDto;
import com.comit.services.history.client.data.NotificationMethodDto;
import com.comit.services.history.model.entity.InOutHistory;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Getter
@Setter
public class InOutHistoryDto extends BaseModelDto {
    private String type;

    private Date time;

    @JsonIncludeProperties({"id", "name"})
    private CameraDto camera;

    @JsonIncludeProperties({"id", "code", "name", "manager"})
    private EmployeeDto employee;

    @JsonIncludeProperties({"id", "path", "type"})
    private MetadataDto image;
}
