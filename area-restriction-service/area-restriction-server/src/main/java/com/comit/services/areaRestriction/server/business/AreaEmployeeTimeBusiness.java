package com.comit.services.areaRestriction.server.business;


import com.comit.services.areaRestriction.client.dto.AreaEmployeeTimeDto;
import com.comit.services.areaRestriction.client.request.AreaEmployeeTimeListRequest;

import java.util.List;

public interface AreaEmployeeTimeBusiness {
    boolean deleteEmployeeAreaRestrictionList(Integer employeeId);

    List<AreaEmployeeTimeDto> saveAreaEmployeeTimeList(AreaEmployeeTimeListRequest areaEmployeeTimeListRequest);

    List<AreaEmployeeTimeDto> getAreaEmployeeTimeListOfEmployee(Integer employeeId);

    List<AreaEmployeeTimeDto> getAreaEmployeeTimeListOfAreaRestriction(Integer areaRestrictionId);
}
