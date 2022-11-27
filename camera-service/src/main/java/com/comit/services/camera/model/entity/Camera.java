package com.comit.services.camera.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "cameras", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "location_id"})
})
@Getter
@Setter
public class Camera extends BaseModel {
    @Column(name = "name")
    private String name;

    @Column(name = "ip_address")
    private String ipAddress;

    // For core use
    @Column(name = "stream_state", columnDefinition = "varchar(32) default 'on'")
    private String streamState;

    @Column(name = "taken_by")
    private String taken;

    @Column(name = "use_case")
    private String useCase;

    @Column(name = "type")
    private String type;

    @Column(name = "polygons", length = 5000)
    private String polygons;

    @Column(name = "is_updated", columnDefinition = "boolean default false")
    private boolean isUpdated;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(50) default 'active'")
    private String status;

    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "area_restriction_id")
    private Integer areaRestrictionId;
}
