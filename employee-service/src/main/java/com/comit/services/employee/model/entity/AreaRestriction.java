package com.comit.services.employee.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AreaRestriction {
    private Integer id;
    private String name;
    private String code;
    private Set<Employee> managers;
    private String timeStart;
    private String timeEnd;
}
