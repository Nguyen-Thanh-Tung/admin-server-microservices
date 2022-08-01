package com.comit.services.feature.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "features")
@Getter
@Setter
@NoArgsConstructor
public class Feature extends BaseModel {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "role_ids")
    private String roleIds;
}
