package com.comit.services.history.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationHistoryRequest {
    private String type;
    private String time;
    @JsonProperty("location_id")
    private Integer locationId;
    @JsonProperty("camera_id")
    private Integer cameraId;
    @JsonProperty("employee_id")
    private Integer employeeId;
    @JsonProperty("area_restriction_id")
    private Integer areaRestrictionId;
    @JsonProperty("image_id")
    private Integer imageId;
}
