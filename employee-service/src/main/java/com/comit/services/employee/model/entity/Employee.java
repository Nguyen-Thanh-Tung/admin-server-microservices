package com.comit.services.employee.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "employees", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code", "location_id"})
})
@Getter
@Setter
public class Employee extends BaseModel {
    @Column(name = "name")
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(50) default 'active'")
    private String status;

    @Column(name = "embedding_id")
    private Integer embeddingId;

    @Column(name = "image_id")
    private Integer imageId;

    @Column(name = "manager_id")
    private Integer managerId;

    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "shift_ids")
    private String shiftIds;
}
