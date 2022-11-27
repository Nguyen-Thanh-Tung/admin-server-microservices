package com.comit.services.areaRestriction.middleware;

import com.comit.services.areaRestriction.controller.request.AreaEmployeeTimeListRequest;

public interface AreaEmployeeTimesVerifyRequestServices {
    void verifyAddAreaEmployeeTimes(AreaEmployeeTimeListRequest areaEmployeeTimeListRequest);
}
