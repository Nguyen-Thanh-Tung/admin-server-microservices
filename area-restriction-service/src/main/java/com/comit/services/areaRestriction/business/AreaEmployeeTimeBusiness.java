package com.comit.services.areaRestriction.business;


import com.comit.services.areaRestriction.model.entity.AreaEmployeeTime;
import com.comit.services.areaRestriction.model.entity.Employee;

import java.util.List;

public interface AreaEmployeeTimeBusiness {
    List<AreaEmployeeTime> saveEmployeeAreaRestrictionList(String employeeAreaRestrictionStr, Employee employee);

    boolean deleteEmployeeAreaRestrictionList(Employee employee);
}
