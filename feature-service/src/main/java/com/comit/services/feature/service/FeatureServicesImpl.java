package com.comit.services.feature.service;

import com.comit.services.feature.client.AccountClient;
import com.comit.services.feature.client.data.RoleDtoClient;
import com.comit.services.feature.client.data.UserDtoClient;
import com.comit.services.feature.client.response.CheckRoleResponseClient;
import com.comit.services.feature.client.response.RoleListResponseClient;
import com.comit.services.feature.client.response.RoleResponseClient;
import com.comit.services.feature.client.response.UserListResponseClient;
import com.comit.services.feature.constant.FeatureErrorCode;
import com.comit.services.feature.exception.RestApiException;
import com.comit.services.feature.model.entity.Feature;
import com.comit.services.feature.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FeatureServicesImpl implements FeatureServices {

    @Autowired
    private FeatureRepository featureRepository;
    @Autowired
    private AccountClient accountClient;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Feature getFeature(int id) {
        return featureRepository.findById(id);
    }

    @Override
    public Feature getFeature(String name) {
        return featureRepository.findByName(name);
    }

    @Override
    public List<Feature> getAllFeature() {
        return featureRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Feature saveFeature(Feature feature) {
        return featureRepository.save(feature);
    }

    @Override
    public boolean currentUserIsSuperAdmin() {
        CheckRoleResponseClient checkRoleResponseClient = accountClient.isCurrentUserSuperAdmin(httpServletRequest.getHeader("token")).getBody();
        if (checkRoleResponseClient == null) {
            throw new RestApiException(FeatureErrorCode.INTERNAL_ERROR);
        }
        return checkRoleResponseClient.isSuperAdmin();
    }

    @Override
    public List<RoleDtoClient> getRolesOfCurrentUser() {
        RoleListResponseClient roleListResponseClient = accountClient.getRolesOfCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (roleListResponseClient == null) {
            throw new RestApiException(FeatureErrorCode.INTERNAL_ERROR);
        }
        return roleListResponseClient.getRoleDtoClients();
    }

    @Override
    public List<Feature> getFeaturesOfRole(Integer roleId) {
        List<Feature> features = (List<Feature>) featureRepository.findAll();
        List<Feature> result = new ArrayList<>();
        features.forEach(feature -> {
            String[] roleIdStrs = feature.getRoleIds().split(",");
            if (Arrays.asList(roleIdStrs).contains(roleId.toString())) {
                result.add(feature);
            }
        });
        return result;
    }

    @Override
    public List<UserDtoClient> getUsersOfRole(Integer roleId) {
        UserListResponseClient userListResponseClient = accountClient.getUsersOfRole(httpServletRequest.getHeader("token"), roleId).getBody();
        if (userListResponseClient == null) {
            throw new RestApiException(FeatureErrorCode.INTERNAL_ERROR);
        }
        return userListResponseClient.getUserDtoClients();
    }

    @Override
    public RoleDtoClient findRoleByName(String roleName) {
        RoleResponseClient roleResponseClient = accountClient.getRoleByName(httpServletRequest.getHeader("token"), roleName).getBody();
        if (roleResponseClient == null) {
            throw new RestApiException(FeatureErrorCode.INTERNAL_ERROR);
        }
        return roleResponseClient.getRoleDtoClient();
    }
}
