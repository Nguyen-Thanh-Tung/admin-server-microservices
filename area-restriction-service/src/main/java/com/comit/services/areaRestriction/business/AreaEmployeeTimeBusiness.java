package com.comit.services.areaRestriction.business;


import com.comit.services.areaRestriction.controller.request.AreaEmployeeTimeListRequest;
import com.comit.services.areaRestriction.model.dto.AreaEmployeeTimeDto;

import java.util.List;

public interface AreaEmployeeTimeBusiness {
    boolean deleteEmployeeAreaRestrictionList(Integer employeeId);

    List<AreaEmployeeTimeDto> saveAreaEmployeeTimeList(AreaEmployeeTimeListRequest areaEmployeeTimeListRequest);

    List<AreaEmployeeTimeDto> getAreaEmployeeTimeListOfEmployee(Integer employeeId);
}
