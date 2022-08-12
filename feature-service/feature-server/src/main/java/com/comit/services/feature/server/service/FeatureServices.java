package com.comit.services.feature.server.service;

import com.comit.services.account.client.dto.RoleDto;
import com.comit.services.account.client.dto.UserDto;
import com.comit.services.feature.server.model.Feature;

import java.util.List;

public interface FeatureServices {
    Feature getFeature(int id);

    Feature getFeature(String name);

    List<Feature> getAllFeature();

    Feature saveFeature(Feature feature);

    boolean currentUserIsSuperAdmin();

    List<RoleDto> getRolesOfCurrentUser();

    List<Feature> getFeaturesOfRole(Integer roleId);

    List<UserDto> getUsersOfRole(Integer roleId);

    RoleDto findRoleByName(String roleTimeKeepingAdmin);
}
