package com.comit.services.history.client.data;

import com.comit.services.history.model.dto.BaseModelDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto extends BaseModelDto {
    private String code;
    private String name;
    private String email;
    private String phone;
    private String status;
    private EmployeeDto manager;
    private MetadataDto image;
}

