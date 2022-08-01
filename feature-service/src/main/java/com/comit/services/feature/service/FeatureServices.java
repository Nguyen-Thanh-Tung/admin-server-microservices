package com.comit.services.feature.service;

import com.comit.services.feature.model.entity.Feature;
import com.comit.services.feature.model.entity.Role;
import com.comit.services.feature.model.entity.User;

import java.util.List;

public interface FeatureServices {
    Feature getFeature(int id);

    Feature getFeature(String name);

    List<Feature> getAllFeature();

    Feature saveFeature(Feature feature);

    boolean currentUserIsSuperAdmin();

    List<Role> getRolesOfCurrentUser();

    List<Feature> getFeaturesOfRole(Integer roleId);

    List<User> getUsersOfRole(Integer roleId);

    Role findRoleByName(String roleTimeKeepingAdmin);
}
