package com.comit.services.areaRestriction.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "area_restriction_manager_notification")
@Getter
@Setter
public class AreaRestrictionManagerNotification extends BaseModel {
    @Column(name = "employee_id")
    private Integer managerId;

    @Column(name = "time_skip")
    private Integer timeSkip;

    @Column(name = "area_restriction_id")
    private Integer areaRestrictionId;
}
