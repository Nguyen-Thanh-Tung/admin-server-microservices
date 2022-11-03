package com.comit.services.metadata.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "metadatas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Metadata extends BaseModel {
    @Column(name = "metadata_id")
    private String metadataId;

    @Column(name = "md5")
    private String md5;

    @Column(name = "path")
    private String path;

    @Column(name = "type")
    private String type;
}
