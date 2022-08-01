package com.comit.services.account.model.dto;

import com.comit.services.account.model.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

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

    public static OrganizationDto convertOrganizationToOrganizationDto(Organization organization) {
        if (organization == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        try {
            return modelMapper.map(organization, OrganizationDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}
