package com.comit.services.feature.server.business;

import com.comit.services.account.client.dto.RoleDto;
import com.comit.services.account.client.dto.UserDto;
import com.comit.services.feature.client.dto.FeatureDto;
import com.comit.services.feature.client.request.FeatureRequest;
import com.comit.services.feature.server.constant.Const;
import com.comit.services.feature.server.constant.FeatureErrorCode;
import com.comit.services.feature.server.exception.RestApiException;
import com.comit.services.feature.server.middleware.FeatureVerifyRequestServices;
import com.comit.services.feature.server.model.Feature;
import com.comit.services.feature.server.service.FeatureServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FeatureBusinessImpl implements FeatureBusiness {
    @Autowired
    private FeatureVerifyRequestServices verifyRequestServices;
    @Autowired
    private FeatureServices featureServices;

    @Override
    public List<FeatureDto> getAllFeature() {
        List<FeatureDto> featureDtos = new ArrayList<>();
        Set<Feature> features = new HashSet<>();
        if (featureServices.currentUserIsSuperAdmin()) {
            features.addAll(featureServices.getAllFeature());
        } else {
            featureServices.getRolesOfCurrentUser().forEach(role -> {
                features.addAll(featureServices.getFeaturesOfRole(role.getId()));
            });
        }
        features.forEach(feature -> {
            featureDtos.add(convertFeatureToFeatureDto(feature));
        });
        return featureDtos;
    }

    @Override
    public FeatureDto addFeature(FeatureRequest request) {
        if (Objects.equals(request.getName(), Const.TIME_KEEPING_MODULE)) {
            String featureRoleStrs = "";
            RoleDto roleDtoTimeKeepingAdmin = featureServices.findRoleByName(Const.ROLE_TIME_KEEPING_ADMIN);
            if (roleDtoTimeKeepingAdmin != null) {
                featureRoleStrs += roleDtoTimeKeepingAdmin.getId() + ",";
            }
            RoleDto roleDtoTimeKeepingUser = featureServices.findRoleByName(Const.ROLE_TIME_KEEPING_USER);
            if (roleDtoTimeKeepingUser != null) {
                featureRoleStrs += roleDtoTimeKeepingUser.getId();
            }
            Feature feature = featureServices.getFeature(Const.TIME_KEEPING_MODULE);
            if (feature == null) {
                feature = new Feature();
            }
            feature.setName(Const.TIME_KEEPING_MODULE);
            feature.setDescription(Const.TIME_KEEPING_MODULE);
            Feature newFeature = featureServices.saveFeature(feature);
            newFeature.setRoleIds(featureRoleStrs);
            featureServices.saveFeature(newFeature);
            return convertFeatureToFeatureDto(newFeature);
        } else if (Objects.equals(request.getName(), Const.AREA_RESTRICTION_CONTROL_MODULE)) {
            String featureRoleStrs = "";
            RoleDto roleDtoAreaRestrictionAdmin = featureServices.findRoleByName(Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN);
            if (roleDtoAreaRestrictionAdmin != null) {
                featureRoleStrs += roleDtoAreaRestrictionAdmin.getId() + ",";
            }
            RoleDto roleDtoAreaRestrictionUser = featureServices.findRoleByName(Const.ROLE_AREA_RESTRICTION_CONTROL_USER);
            if (roleDtoAreaRestrictionUser != null) {
                featureRoleStrs += roleDtoAreaRestrictionUser.getId();
            }
            Feature feature = featureServices.getFeature(Const.AREA_RESTRICTION_CONTROL_MODULE);
            if (feature == null) {
                feature = new Feature();
            }
            feature.setName(Const.AREA_RESTRICTION_CONTROL_MODULE);
            feature.setDescription(Const.AREA_RESTRICTION_CONTROL_MODULE);
            Feature newFeature = featureServices.saveFeature(feature);
            newFeature.setRoleIds(featureRoleStrs);
            featureServices.saveFeature(newFeature);
            return convertFeatureToFeatureDto(newFeature);
        } else if (Objects.equals(request.getName(), Const.BEHAVIOR_CONTROL_MODULE)) {
            String featureRoleStrs = "";
            RoleDto roleDtoBehaviorAdmin = featureServices.findRoleByName(Const.ROLE_BEHAVIOR_CONTROL_ADMIN);
            if (roleDtoBehaviorAdmin != null) {
                featureRoleStrs += roleDtoBehaviorAdmin.getId() + ",";
            }
            RoleDto roleDtoBehaviorUser = featureServices.findRoleByName(Const.ROLE_BEHAVIOR_CONTROL_USER);
            if (roleDtoBehaviorUser != null) {
                featureRoleStrs += roleDtoBehaviorUser.getId();
            }
            Feature feature = featureServices.getFeature(Const.BEHAVIOR_CONTROL_MODULE);
            if (feature == null) {
                feature = new Feature();
            }
            feature.setName(Const.BEHAVIOR_CONTROL_MODULE);
            feature.setDescription(Const.BEHAVIOR_CONTROL_MODULE);
            Feature newFeature = featureServices.saveFeature(feature);
            newFeature.setRoleIds(featureRoleStrs);
            featureServices.saveFeature(newFeature);
            return convertFeatureToFeatureDto(newFeature);
        } else {
            verifyRequestServices.verifyAddFeatureRequest(request);
            Feature feature = featureServices.getFeature(request.getName());
            if (feature == null) {
                feature = new Feature();
            }
            feature.setName(request.getName());
            feature.setDescription(request.getDescription());
            Feature newFeature = featureServices.saveFeature(feature);
            return convertFeatureToFeatureDto(newFeature);
        }

    }

    @Override
    public FeatureDto getFeature(int id) {
        Feature feature = featureServices.getFeature(id);
        if (feature == null) {
            throw new RestApiException(FeatureErrorCode.FEATURE_NOT_EXIST);
        }

        return convertFeatureToFeatureDto(feature);
    }

    public FeatureDto convertFeatureToFeatureDto(Feature feature) {
        if (feature == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            FeatureDto featureDto = modelMapper.map(feature, FeatureDto.class);
            Set<Integer> roleIds = new HashSet<>();
            for (String s : feature.getRoleIds().split(",")) {
                roleIds.add(Integer.parseInt(s));
            }
            Set<UserDto> userDtos = new HashSet<>();
            Set<Integer> organizationIds = new HashSet<>();

            roleIds.forEach(roleId -> {
                featureServices.getUsersOfRole(roleId).forEach(userDto -> {
                    if (!Objects.equals(userDto.getStatus(), Const.DELETED)) {
                        userDtos.add(userDto);
                        organizationIds.add(userDto.getOrganizationId());
                    }
                });
            });
            featureDto.setNumberOrganization(organizationIds.size());
            featureDto.setNumberAccount(userDtos.size());
            return featureDto;
        } catch (Exception e) {
            return null;
        }
    }
}
