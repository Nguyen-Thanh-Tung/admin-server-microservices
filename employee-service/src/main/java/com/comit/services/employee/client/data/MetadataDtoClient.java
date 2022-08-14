package com.comit.services.employee.client.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetadataDtoClient {
    private Integer id;
    private String md5;
    private String path;
    private String type;
}

