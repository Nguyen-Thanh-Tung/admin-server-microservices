package com.comit.services.feature.server.service;

import com.comit.services.account.client.AccountClient;
import com.comit.services.account.client.dto.RoleDto;
import com.comit.services.account.client.dto.UserDto;
import com.comit.services.account.client.response.CheckRoleResponse;
import com.comit.services.account.client.response.RoleListResponse;
import com.comit.services.account.client.response.RoleResponse;
import com.comit.services.account.client.response.UserListResponse;
import com.comit.services.feature.server.constant.FeatureErrorCode;
import com.comit.services.feature.server.exception.RestApiException;
import com.comit.services.feature.server.model.Feature;
import com.comit.services.feature.server.repository.FeatureRepository;
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
        CheckRoleResponse checkRoleResponse = accountClient.isCurrentUserSuperAdmin(httpServletRequest.getHeader("token"));
        if (checkRoleResponse == null) {
            throw new RestApiException(FeatureErrorCode.INTERNAL_ERROR);
        }
        return checkRoleResponse.isHasRole();
    }

    @Override
    public List<RoleDto> getRolesOfCurrentUser() {
        RoleListResponse roleListResponse = accountClient.getRolesOfCurrentUser(httpServletRequest.getHeader("token"));
        if (roleListResponse == null) {
            throw new RestApiException(FeatureErrorCode.INTERNAL_ERROR);
        }
        return roleListResponse.getRoleDtos();
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
    public List<UserDto> getUsersOfRole(Integer roleId) {
        UserListResponse userListResponse = accountClient.getUsersOfRole(httpServletRequest.getHeader("token"), roleId);
        if (userListResponse == null) {
            throw new RestApiException(FeatureErrorCode.INTERNAL_ERROR);
        }
        return userListResponse.getUserDtos();
    }

    @Override
    public RoleDto findRoleByName(String roleName) {
        RoleResponse roleResponse = accountClient.getRoleByName(httpServletRequest.getHeader("token"), roleName);
        if (roleResponse == null) {
            throw new RestApiException(FeatureErrorCode.INTERNAL_ERROR);
        }
        return roleResponse.getRoleDto();
    }
}
