package com.comit.services.employee.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "guest")
@Getter
@Setter
public class Guest extends BaseModel {
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "embedding_id")
    private Integer embeddingId;

    @Column(name = "image")
    private String image;

    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "area_restriction_id")
    private Integer areaRestrictionId;

    @Column(name = "time_start")
    private Date timeStart;

    @Column(name = "time_end")
    private Date timeEnd;
}
