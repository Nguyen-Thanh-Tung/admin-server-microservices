package com.comit.services.history.client.request;

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
