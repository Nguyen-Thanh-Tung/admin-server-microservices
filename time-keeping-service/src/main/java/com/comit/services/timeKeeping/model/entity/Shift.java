package com.comit.services.timeKeeping.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "shifts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shift extends BaseModel {
    @Column(name = "name")
    private String name;

    @Column(name = "time_start")
    private String timeStart;

    @Column(name = "time_end")
    private String timeEnd;

    @Column(name = "location_id")
    private Integer locationId;

    public Shift(String name, String timeStart, String timeEnd) {
        this.name = name;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }
}
