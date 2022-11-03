package com.comit.services.areaRestriction.service;

import com.comit.services.areaRestriction.model.entity.AreaEmployeeTime;

import java.util.List;

public interface AreaEmployeeTimeService {
    List<AreaEmployeeTime> saveAllAreaEmployeeTime(List<AreaEmployeeTime> areaEmployeeTimeIds);

    boolean deleteAreaEmployeeTime(Integer employeeId);

    int getNumberAreaEmployeeTimeOfAreaRestriction(int areaRestrictionId);

    List<AreaEmployeeTime> getAreaEmployeeTimeListOfEmployee(Integer employeeId);

    List<AreaEmployeeTime> getAreaEmployeeTimeListOfAreaRestriction(Integer areaRestrictionId);
}
