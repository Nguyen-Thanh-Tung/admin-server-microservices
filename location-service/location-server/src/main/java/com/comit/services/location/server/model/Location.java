package com.comit.services.location.server.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
@Getter
@Setter
public class Location extends BaseModel {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "type")
    private String type;

    @Column(name = "organization_id")
    private Integer organizationId;
}
