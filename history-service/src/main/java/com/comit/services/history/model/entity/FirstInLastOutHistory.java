package com.comit.services.history.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "first_in_last_out_histories")
@Getter
@Setter
public class FirstInLastOutHistory extends BaseModel {
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
}
