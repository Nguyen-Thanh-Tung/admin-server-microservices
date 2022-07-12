package com.comit.services.account.model.dto;

import com.comit.services.account.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseModelDto {
    private String username;
    private String fullname;
    private String email;
    private String status;

    @JsonIncludeProperties({"id", "name"})
    private Set<RoleDto> roles;

    @JsonProperty("parent_user")
    @JsonIncludeProperties({"id", "fullname", "roles"})
    private UserDto parent;

    public static UserDto convertUserToUserDto(User user) throws IOException {
        if (user == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        try {
            return modelMapper.map(user, UserDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}
