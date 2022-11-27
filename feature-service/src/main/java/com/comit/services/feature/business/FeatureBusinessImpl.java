package com.comit.services.feature.business;

import com.comit.services.feature.client.data.RoleDtoClient;
import com.comit.services.feature.constant.Const;
import com.comit.services.feature.constant.FeatureErrorCode;
import com.comit.services.feature.controller.request.FeatureRequest;
import com.comit.services.feature.exception.RestApiException;
import com.comit.services.feature.loging.model.CommonLogger;
import com.comit.services.feature.middleware.FeatureVerifyRequestServices;
import com.comit.services.feature.model.dto.FeatureDto;
import com.comit.services.feature.model.entity.Feature;
import com.comit.services.feature.service.FeatureServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class FeatureBusinessImpl implements FeatureBusiness {
    @Autowired
    private FeatureVerifyRequestServices verifyRequestServices;
    @Autowired
    private FeatureServices featureServices;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Value("${app.internalToken}")
    private String internalToken;

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
        if (!isInternalFeature()) throw new RestApiException(FeatureErrorCode.PERMISSION_DENIED);
        if (Objects.equals(request.getName(), Const.TIME_KEEPING_MODULE)) {
            String featureRoleStrs = "";
            RoleDtoClient roleDtoClientTimeKeepingAdmin = featureServices.findRoleByName(Const.ROLE_TIME_KEEPING_ADMIN);
            if (roleDtoClientTimeKeepingAdmin != null) {
                featureRoleStrs += roleDtoClientTimeKeepingAdmin.getId() + ",";
            }
            RoleDtoClient roleDtoClientTimeKeepingUser = featureServices.findRoleByName(Const.ROLE_TIME_KEEPING_USER);
            if (roleDtoClientTimeKeepingUser != null) {
                featureRoleStrs += roleDtoClientTimeKeepingUser.getId();
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
            RoleDtoClient roleDtoClientAreaRestrictionAdmin = featureServices.findRoleByName(Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN);
            if (roleDtoClientAreaRestrictionAdmin != null) {
                featureRoleStrs += roleDtoClientAreaRestrictionAdmin.getId() + ",";
            }
            RoleDtoClient roleDtoClientAreaRestrictionUser = featureServices.findRoleByName(Const.ROLE_AREA_RESTRICTION_CONTROL_USER);
            if (roleDtoClientAreaRestrictionUser != null) {
                featureRoleStrs += roleDtoClientAreaRestrictionUser.getId();
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
            RoleDtoClient roleDtoClientBehaviorAdmin = featureServices.findRoleByName(Const.ROLE_BEHAVIOR_CONTROL_ADMIN);
            if (roleDtoClientBehaviorAdmin != null) {
                featureRoleStrs += roleDtoClientBehaviorAdmin.getId() + ",";
            }
            RoleDtoClient roleDtoClientBehaviorUser = featureServices.findRoleByName(Const.ROLE_BEHAVIOR_CONTROL_USER);
            if (roleDtoClientBehaviorUser != null) {
                featureRoleStrs += roleDtoClientBehaviorUser.getId();
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
            if (feature.getRoleIds() == null || Objects.equals(feature.getRoleIds(), "")) {
                featureDto.setNumberOrganization(0);
                featureDto.setNumberAccount(0);
            } else {
                int numberOrganizationUsingFeature = featureServices.getNumberOrganizationUsingFeature(feature.getRoleIds());
                int numberAccountUsingFeature = featureServices.getNumberAccountUsingFeature(feature.getRoleIds());

                featureDto.setNumberOrganization(numberOrganizationUsingFeature);
                featureDto.setNumberAccount(numberAccountUsingFeature);
            }
            return featureDto;
        } catch (Exception e) {
            CommonLogger.error(e.getMessage(), e);
            return null;
        }
    }

    public boolean isInternalFeature() {
        return Objects.equals(httpServletRequest.getHeader("token"), internalToken);
    }
}
