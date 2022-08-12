package com.comit.services.history.client.dto;

import com.comit.services.areaRestriction.client.dto.NotificationMethodDto;
import com.comit.services.camera.client.dto.CameraDto;
import com.comit.services.employee.client.dto.EmployeeDto;
import com.comit.services.metadata.client.dto.MetadataDto;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificationHistoryDto {
    private Integer id;
    private String type;

    private Date time;
    private String status;

    @JsonIncludeProperties({"id", "name", "area_restriction"})
    private CameraDto camera;

    @JsonIncludeProperties({"id", "code", "name", "manager", "image"})
    private EmployeeDto employee;

    @JsonIncludeProperties({"id", "path", "type"})
    private MetadataDto image;

    @JsonIncludeProperties({"use_ott", "use_email", "use_screen", "use_ring"})
    @JsonProperty(value = "notification_method")
    private NotificationMethodDto notificationMethod;
}
