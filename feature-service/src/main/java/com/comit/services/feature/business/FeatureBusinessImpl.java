package com.comit.services.feature.business;

import com.comit.services.feature.constant.Const;
import com.comit.services.feature.constant.FeatureErrorCode;
import com.comit.services.feature.controller.request.FeatureRequest;
import com.comit.services.feature.exception.RestApiException;
import com.comit.services.feature.middleware.FeatureVerifyRequestServices;
import com.comit.services.feature.model.dto.FeatureDto;
import com.comit.services.feature.model.entity.Feature;
import com.comit.services.feature.model.entity.Role;
import com.comit.services.feature.model.entity.User;
import com.comit.services.feature.service.FeatureServices;
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
            Role roleTimeKeepingAdmin = featureServices.findRoleByName(Const.ROLE_TIME_KEEPING_ADMIN);
            if (roleTimeKeepingAdmin != null) {
                featureRoleStrs += roleTimeKeepingAdmin.getId() + ",";
            }
            Role roleTimeKeepingUser = featureServices.findRoleByName(Const.ROLE_TIME_KEEPING_USER);
            if (roleTimeKeepingUser != null) {
                featureRoleStrs += roleTimeKeepingUser.getId();
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
            Role roleAreaRestrictionAdmin = featureServices.findRoleByName(Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN);
            if (roleAreaRestrictionAdmin != null) {
                featureRoleStrs += roleAreaRestrictionAdmin.getId() + ",";
            }
            Role roleAreaRestrictionUser = featureServices.findRoleByName(Const.ROLE_AREA_RESTRICTION_CONTROL_USER);
            if (roleAreaRestrictionUser != null) {
                featureRoleStrs += roleAreaRestrictionUser.getId();
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
            Role roleBehaviorAdmin = featureServices.findRoleByName(Const.ROLE_BEHAVIOR_CONTROL_ADMIN);
            if (roleBehaviorAdmin != null) {
                featureRoleStrs += roleBehaviorAdmin.getId() + ",";
            }
            Role roleBehaviorUser = featureServices.findRoleByName(Const.ROLE_BEHAVIOR_CONTROL_USER);
            if (roleBehaviorUser != null) {
                featureRoleStrs += roleBehaviorUser.getId();
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
            Set<User> users = new HashSet<>();
            Set<Integer> organizationIds = new HashSet<>();

            roleIds.forEach(roleId -> {
                featureServices.getUsersOfRole(roleId).forEach(user -> {
                    if (!Objects.equals(user.getStatus(), Const.DELETED)) {
                        users.add(user);
                        organizationIds.add(user.getOrganizationId());
                    }
                });
            });
            featureDto.setNumberOrganization(organizationIds.size());
            featureDto.setNumberAccount(users.size());
            return featureDto;
        } catch (Exception e) {
            return null;
        }
    }
}
