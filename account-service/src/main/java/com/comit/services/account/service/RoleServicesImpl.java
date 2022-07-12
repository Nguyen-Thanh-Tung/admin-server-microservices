package com.comit.services.account.service;

import com.comit.services.account.constant.Const;
import com.comit.services.account.model.entity.Role;
import com.comit.services.account.model.entity.User;
import com.comit.services.account.repository.RoleRepository;
import com.comit.services.account.util.RequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RoleServicesImpl implements RoleServices {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RequestHelper requestHelper;

    @Value("${system.supperAdmin.username}")
    private String superAdminUsername;

    public boolean existsByName(String role) {
        return roleRepository.existsByName(role);
    }

    public List<Role> getAllRole() {
        Iterable<Role> roleIterable = roleRepository.findAll();
        List<Role> roles = new ArrayList<>();
        roleIterable.forEach(roles::add);
        return roles;
    }

    public Role findRoleByName(String role) {
        return roleRepository.findByName(role);
    }

    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    public void saveRoles(List<Role> roles) {
        roleRepository.saveAll(roles);
    }

    /**
     * Check permission manage role
     *
     * @param roleName name of role want check
     * @return boolean
     */

    public boolean hasPermissionManageRole(User currentUser, String roleName) {
        if (requestHelper.hasRole(Const.ROLE_SUPER_ADMIN)
                || Objects.equals(currentUser.getUsername(), superAdminUsername)) {
            return Objects.equals(roleName, Const.ROLE_TIME_KEEPING_ADMIN)
                    || Objects.equals(roleName, Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN);
        }

        if (isSuperAdminOrganization(currentUser) && requestHelper.hasRole(Const.ROLE_TIME_KEEPING_ADMIN) && requestHelper.hasRole(Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN)) {
            return Objects.equals(roleName, Const.ROLE_TIME_KEEPING_ADMIN) || Objects.equals(roleName, Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN);
        }
        if (isSuperAdminOrganization(currentUser) && requestHelper.hasRole(Const.ROLE_TIME_KEEPING_ADMIN)) {
            return Objects.equals(roleName, Const.ROLE_TIME_KEEPING_ADMIN);
        }

        if (isSuperAdminOrganization(currentUser) && requestHelper.hasRole(Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN)) {
            return Objects.equals(roleName, Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN);
        }

        return (requestHelper.hasRole(Const.ROLE_TIME_KEEPING_ADMIN) && Objects.equals(roleName, Const.ROLE_TIME_KEEPING_USER))
                || (requestHelper.hasRole(Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN) && Objects.equals(roleName, Const.ROLE_AREA_RESTRICTION_CONTROL_USER));
    }

    public boolean isSuperAdminOrganization(User user) {
        if (user.getUsername().equals(superAdminUsername)) return false;
        return Objects.equals(user.getParent().getUsername(), superAdminUsername);
    }
}
