package com.comit.services.camera.client.data;

import com.comit.services.camera.model.dto.BaseModelDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDto extends BaseModelDto {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String description;
}
