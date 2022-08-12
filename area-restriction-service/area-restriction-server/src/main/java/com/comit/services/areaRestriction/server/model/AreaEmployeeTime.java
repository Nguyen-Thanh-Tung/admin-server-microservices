package com.comit.services.areaRestriction.server.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "area_employee_times")
@Getter
@Setter
public class AreaEmployeeTime extends BaseModel {
    @Column(name = "employee_id")
    private Integer employeeId;

    @ManyToOne
    @JoinColumn(name = "area_restriction_id")
    private AreaRestriction areaRestriction;

    @Column(name = "time_start")
    private String timeStart;

    @Column(name = "time_end")
    private String timeEnd;
}
