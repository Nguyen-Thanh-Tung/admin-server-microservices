package com.comit.services.areaRestriction.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "area_restrictions")
@Getter
@Setter
public class AreaRestriction extends BaseModel {
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    private String managerIds;

    @Column(name = "time_start")
    private String timeStart;

    @Column(name = "time_end")
    private String timeEnd;

    @Column(name = "location_id")
    private Integer locationId;
}
