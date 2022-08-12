package com.comit.services.account.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(50) default 'pending'")
    private String status;

    @Column(name = "code")
    private String code;

    @Column(name = "avatar_id")
    private Integer avatarId;

    @ManyToMany(fetch = FetchType.EAGER, cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    @Column(name = "organization_id")
    private Integer organizationId;

    @Column(name = "location_id")
    private Integer locationId;

    @OneToOne
    @JoinColumn(name = "parent_user_id")
    private User parent;
}
