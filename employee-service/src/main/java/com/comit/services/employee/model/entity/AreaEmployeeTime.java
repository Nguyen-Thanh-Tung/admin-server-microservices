package com.comit.services.employee.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaEmployeeTime {
    private Integer id;
    private Integer employeeId;
    private Integer areaRestrictionId;
    private String timeStart;
    private String timeEnd;
}
