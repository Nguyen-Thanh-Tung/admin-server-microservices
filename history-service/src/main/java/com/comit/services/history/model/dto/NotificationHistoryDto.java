package com.comit.services.history.model.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificationHistoryDto extends BaseModelDto {
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
