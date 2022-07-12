package com.comit.services.account.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Metadata {
    private Integer id;
    private String metadataId;
    private String md5;
    private String path;
    private String type;
}
