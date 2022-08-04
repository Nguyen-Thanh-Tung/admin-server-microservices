package com.comit.services.account.client.data;

import com.comit.services.account.model.dto.BaseModelDto;
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
