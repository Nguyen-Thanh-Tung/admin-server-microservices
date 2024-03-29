package com.comit.services.feature.service;

import com.comit.services.feature.client.data.RoleDtoClient;
import com.comit.services.feature.model.entity.Feature;

import java.util.List;

public interface FeatureServices {
    Feature getFeature(int id);

    Feature getFeature(String name);

    List<Feature> getAllFeature();

    Feature saveFeature(Feature feature);

    boolean currentUserIsSuperAdmin();

    List<RoleDtoClient> getRolesOfCurrentUser();

    List<Feature> getFeaturesOfRole(Integer roleId);

    RoleDtoClient findRoleByName(String roleTimeKeepingAdmin);

    int getNumberOrganizationUsingFeature(String roleIds);

    int getNumberAccountUsingFeature(String roleIds);
}
