package com.comit.services.history.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "in_out_histories")
@Getter
@Setter
public class InOutHistory extends BaseModel {
    @Column(name = "type")
    private String type;

    @Column(name = "time")
    private Date time;

    @Column(name = "camera_id")
    private Integer cameraId;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "image_id")
    private Integer imageId;

    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "area_restriction_id")
    private Integer areaRestrictionId;
}
