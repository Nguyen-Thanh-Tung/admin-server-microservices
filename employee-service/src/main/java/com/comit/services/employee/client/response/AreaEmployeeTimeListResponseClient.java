package com.comit.services.employee.client.response;

import com.comit.services.employee.client.data.AreaEmployeeTimeDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AreaEmployeeTimeListResponseClient extends BaseResponseClient {
    @JsonProperty(value = "area_employee_times")
    private List<AreaEmployeeTimeDtoClient> areaEmployeeTimes;

    public AreaEmployeeTimeListResponseClient(
            int code,
            String message,
            List<AreaEmployeeTimeDtoClient> areaEmployeeTimes
    ) {
        this.code = code;
        this.message = message;
        this.areaEmployeeTimes = areaEmployeeTimes;
    }
}
