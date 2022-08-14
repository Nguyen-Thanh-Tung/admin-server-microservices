package com.comit.services.history.client.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDtoClient {
    private Integer id;
    private String code;
    private String name;
    private String email;
    private String phone;
    private String status;
    private EmployeeDtoClient manager;
    private MetadataDtoClient image;
}

