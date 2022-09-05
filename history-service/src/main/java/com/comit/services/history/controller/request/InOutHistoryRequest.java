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
public class InOutHistoryRequest {
    private String time;
    @JsonProperty("camera_id")
    private Integer cameraId;
    @JsonProperty("employee_id")
    private Integer employeeId;
    @JsonProperty("image_id")
    private Integer imageId;
}
