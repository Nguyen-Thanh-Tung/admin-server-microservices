package com.comit.services.feature.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String status;
    private String code;
    private Integer organizationId;
}
