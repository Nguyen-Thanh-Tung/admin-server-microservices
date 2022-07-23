package com.comit.services.history.controller.request;

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
    private Integer cameraId;
    private Integer employeeId;
    private Integer imageId;
}
