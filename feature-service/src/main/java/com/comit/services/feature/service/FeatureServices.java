package com.comit.services.feature.service;

import com.comit.services.feature.client.data.RoleDto;
import com.comit.services.feature.client.data.UserDto;
import com.comit.services.feature.model.entity.Feature;

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
