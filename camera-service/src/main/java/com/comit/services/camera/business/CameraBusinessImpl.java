package com.comit.services.camera.business;

import com.comit.services.camera.client.data.AreaRestrictionDtoClient;
import com.comit.services.camera.client.data.LocationDtoClient;
import com.comit.services.camera.client.data.OrganizationDtoClient;
import com.comit.services.camera.constant.CameraErrorCode;
import com.comit.services.camera.constant.Const;
import com.comit.services.camera.controller.request.CameraPolygonsRequest;
import com.comit.services.camera.controller.request.CameraRequest;
import com.comit.services.camera.exception.RestApiException;
import com.comit.services.camera.loging.model.CommonLogger;
import com.comit.services.camera.middleware.CameraVerifyRequestServices;
import com.comit.services.camera.model.dto.AreaRestrictionDto;
import com.comit.services.camera.model.dto.BaseCameraDto;
import com.comit.services.camera.model.dto.CameraDto;
import com.comit.services.camera.model.dto.LocationDto;
import com.comit.services.camera.model.entity.Camera;
import com.comit.services.camera.service.CameraServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CameraBusinessImpl implements CameraBusiness {
    @Autowired
    private CameraVerifyRequestServices verifyRequestServices;
    @Autowired
    private CameraServices cameraServices;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Value("${app.internalToken}")
    private String internalToken;

    @Override
    public Page<Camera> getCameraPage(Integer locationId, Integer areaRestrictionId, String status, int page, int size, String search) {
        Pageable paging = PageRequest.of(page, size);

        Page<Camera> cameraPage;
        OrganizationDtoClient organizationDtoClient = cameraServices.getOrganizationOfCurrentUser();
        LocationDtoClient locationOfCurrentUser = cameraServices.getLocationOfCurrentUser();
        if (areaRestrictionId != null) {
            AreaRestrictionDtoClient areaRestrictionDtoClient = cameraServices.getAreaRestriction(locationOfCurrentUser.getId(), areaRestrictionId);
            if (areaRestrictionDtoClient == null) {
                throw new RestApiException(CameraErrorCode.AREA_RESTRICTION_NOT_EXIST);
            }
            cameraPage = cameraServices.getAllCamera(areaRestrictionDtoClient.getId(), status, search, paging);
        } else if (locationId == null) {
            List<Integer> locationIds = new ArrayList<>();
            if (locationOfCurrentUser == null) {
                List<LocationDtoClient> locationDtoClients = cameraServices.getLocationListByOrganizationId(organizationDtoClient.getId());
                for (LocationDtoClient tmp : locationDtoClients) {
                    if ((cameraServices.isTimeKeepingModule() && Objects.equals(tmp.getType(), Const.TIME_KEEPING_TYPE)) ||
                            (cameraServices.isAreaRestrictionModule() && Objects.equals(tmp.getType(), Const.AREA_RESTRICTION_TYPE)) ||
                            (cameraServices.isBehaviorModule() && Objects.equals(tmp.getType(), Const.BEHAVIOR_TYPE))) {
                        locationIds.add(tmp.getId());
                    }
                }
            } else {
                locationIds = List.of(locationOfCurrentUser.getId());
            }
            cameraPage = cameraServices.getAllCamera(locationIds, new ArrayList<>(), status, search, paging);
        } else {
            LocationDtoClient locationDtoClient = cameraServices.getLocation(organizationDtoClient.getId(), locationId);
            if (locationDtoClient == null) {
                throw new RestApiException(CameraErrorCode.LOCATION_NOT_EXIST);
            }
            cameraPage = cameraServices.findByLocation(locationDtoClient.getId(), new ArrayList<>(), status, search, paging);
        }

        return cameraPage;
    }

    @Override
    public Page<Camera> getCameraPage(Integer locationId, String cameraIdStrs, String status, int page, int size, String search) {
        Pageable paging = PageRequest.of(page, size);

        Page<Camera> cameraPage;
        OrganizationDtoClient organizationDtoClient = cameraServices.getOrganizationOfCurrentUser();
        List<Integer> cameraIds = new ArrayList<>();
        if (cameraIdStrs != null && !cameraIdStrs.trim().isEmpty()) {
            for (String cameraId : cameraIdStrs.split(",")) {
                cameraIds.add(Integer.parseInt(cameraId));
            }
        }
        if (locationId == null) {
            List<Integer> locationIds = new ArrayList<>();
            LocationDtoClient locationOfCurrentUserDto = cameraServices.getLocationOfCurrentUser();
            if (locationOfCurrentUserDto == null) {
                List<LocationDtoClient> locations = cameraServices.getLocationListByOrganizationId(organizationDtoClient.getId());
                for (LocationDtoClient tmp : locations) {
                    if ((cameraServices.isTimeKeepingModule() && Objects.equals(tmp.getType(), Const.TIME_KEEPING_TYPE)) ||
                            (cameraServices.isAreaRestrictionModule() && Objects.equals(tmp.getType(), Const.AREA_RESTRICTION_TYPE)) ||
                            (cameraServices.isBehaviorModule() && Objects.equals(tmp.getType(), Const.BEHAVIOR_TYPE))) {
                        locationIds.add(tmp.getId());
                    }
                }
            } else {
                locationIds = List.of(locationOfCurrentUserDto.getId());
            }
            cameraPage = cameraServices.getAllCamera(locationIds, cameraIds, status, search, paging);
        } else {
            LocationDtoClient locationDtoClient = cameraServices.getLocation(organizationDtoClient.getId(), locationId);
            if (locationDtoClient == null) {
                throw new RestApiException(CameraErrorCode.LOCATION_NOT_EXIST);
            }
            cameraPage = cameraServices.findByLocation(locationDtoClient.getId(), cameraIds, status, search, paging);
        }

        return cameraPage;
    }

    @Override
    public List<CameraDto> getAllCamera(List<Camera> cameras) {
        List<CameraDto> cameraDtos = new ArrayList<>();
        cameras.forEach(camera -> {
            cameraDtos.add(convertCameraToCameraDto(camera));
        });
        return cameraDtos;
    }

    @Override
    public CameraDto addCamera(CameraRequest request) {
        // Verify input
        verifyRequestServices.verifyAddOrUpdateCameraRequest(request);

        // Check role add camera, is existed camera?
        OrganizationDtoClient organizationDtoClient = cameraServices.getOrganizationOfCurrentUser();
        if (organizationDtoClient == null) {
            CommonLogger.error(CameraErrorCode.INTERNAL_ERROR.getMessage() + ": Get organization of current user error");
            throw new RestApiException(CameraErrorCode.INTERNAL_ERROR);
        }
        LocationDtoClient locationDtoClient;
        Camera camera = new Camera();
        if (cameraServices.isTimeKeepingModule()) {
            if (!cameraServices.hasRole(Const.ROLE_TIME_KEEPING_ADMIN)) {
                CommonLogger.error(CameraErrorCode.PERMISSION_DENIED.getMessage() + ": module-time_keeping, hasRoleTKA-false");
                throw new RestApiException(CameraErrorCode.PERMISSION_DENIED);
            }
            locationDtoClient = cameraServices.getLocation(organizationDtoClient.getId(), request.getLocationId());
            if (locationDtoClient == null) {
                throw new RestApiException(CameraErrorCode.LOCATION_NOT_EXIST);
            }
            camera.setType(request.getType());
        } else if (cameraServices.isAreaRestrictionModule() || cameraServices.isBehaviorModule()) {
            if ((cameraServices.isAreaRestrictionModule() && !cameraServices.hasRole(Const.ROLE_AREA_RESTRICTION_CONTROL_USER))
                    || (cameraServices.isBehaviorModule() && !cameraServices.hasRole(Const.ROLE_BEHAVIOR_CONTROL_USER))) {
                CommonLogger.error(CameraErrorCode.PERMISSION_DENIED.getMessage() + ": hasRoleARU-" + cameraServices.hasRole(Const.ROLE_AREA_RESTRICTION_CONTROL_USER) + ", hasRoleBHU-" + cameraServices.hasRole(Const.ROLE_BEHAVIOR_CONTROL_USER) + ", module-" + httpServletRequest.getHeader("module"));
                throw new RestApiException(CameraErrorCode.PERMISSION_DENIED);
            }
            locationDtoClient = cameraServices.getLocationOfCurrentUser();
            if (locationDtoClient == null) {
                CommonLogger.error(CameraErrorCode.INTERNAL_ERROR.getMessage() + ": Get location of current user error");
                throw new RestApiException(CameraErrorCode.INTERNAL_ERROR);
            } else {
                if (request.getLocationId() != null && !Objects.equals(locationDtoClient.getId(), request.getLocationId())) {
                    throw new RestApiException(CameraErrorCode.INVALID_LOCATION_ID_FIELD);
                }
            }
            AreaRestrictionDtoClient areaRestrictionDtoClient = cameraServices.getAreaRestriction(locationDtoClient.getId(),
                    request.getAreaRestrictionId());
            if (areaRestrictionDtoClient == null) {
                throw new RestApiException(CameraErrorCode.AREA_RESTRICTION_NOT_EXIST);
            }
            camera.setAreaRestrictionId(areaRestrictionDtoClient.getId());
            camera.setType(cameraServices.isAreaRestrictionModule() ? Const.GSKVHC : Const.KSHV);
        } else {
            throw new RestApiException(CameraErrorCode.MODULE_NOT_FOUND);
        }
        // Camera name is used
        if (cameraServices.getCameraByNameAndLocation(request.getName(), locationDtoClient.getId()) != null) {
            throw new RestApiException(CameraErrorCode.EXISTED_CAMERA_BY_NAME);
        }

        // Check camera active or not
        boolean isLiveCam = cameraServices.isLiveCame(request.getIpAddress());
        if (!isLiveCam) {
            throw new RestApiException(CameraErrorCode.CAMERA_NOT_WORKING);
        }

        camera.setIpAddress(request.getIpAddress());
        camera.setLocationId(locationDtoClient.getId());
        camera.setName(request.getName());
        camera.setStreamState(Const.ON);
        camera.setStatus(Const.ACTIVE);
        Camera newCamera = cameraServices.saveCamera(camera);
        return convertCameraToCameraDto(newCamera);
    }

    @Override
    public CameraDto updateCamera(int id, CameraRequest request) {
        // Verify input
        verifyRequestServices.verifyAddOrUpdateCameraRequest(request);

        // is existed camera, check role update camera
        Camera camera = cameraServices.getCamera(id, Const.ACTIVE);
        if (camera == null) {
            throw new RestApiException(CameraErrorCode.CAMERA_NOT_EXIST);
        }

        OrganizationDtoClient organizationDtoClient = cameraServices.getOrganizationOfCurrentUser();
        if (organizationDtoClient == null) {
            CommonLogger.error(CameraErrorCode.INTERNAL_ERROR.getMessage() + ": Get organization of current user error");
            throw new RestApiException(CameraErrorCode.INTERNAL_ERROR);
        }
        LocationDtoClient locationDtoClient;
        if (cameraServices.isTimeKeepingModule()) {
            if (!cameraServices.hasRole(Const.ROLE_TIME_KEEPING_ADMIN)) {
                CommonLogger.error(CameraErrorCode.PERMISSION_DENIED.getMessage() + ": module-time_keeping, hasRoleTKA-false");
                throw new RestApiException(CameraErrorCode.PERMISSION_DENIED);
            }
            locationDtoClient = cameraServices.getLocation(organizationDtoClient.getId(), request.getLocationId());
            if (locationDtoClient == null) {
                throw new RestApiException(CameraErrorCode.LOCATION_NOT_EXIST);
            }
            camera.setType(request.getType());
        } else if (cameraServices.isAreaRestrictionModule() || cameraServices.isBehaviorModule()) {
            if ((cameraServices.isAreaRestrictionModule() && !cameraServices.hasRole(Const.ROLE_AREA_RESTRICTION_CONTROL_USER))
                    || (cameraServices.isBehaviorModule() && !cameraServices.hasRole(Const.ROLE_BEHAVIOR_CONTROL_USER))) {
                CommonLogger.error(CameraErrorCode.PERMISSION_DENIED.getMessage() + ": hasRoleARU-" + cameraServices.hasRole(Const.ROLE_AREA_RESTRICTION_CONTROL_USER) + ", hasRoleBHU-" + cameraServices.hasRole(Const.ROLE_BEHAVIOR_CONTROL_USER) + ", module-" + httpServletRequest.getHeader("module"));
                throw new RestApiException(CameraErrorCode.PERMISSION_DENIED);
            }
            locationDtoClient = cameraServices.getLocationOfCurrentUser();
            if (locationDtoClient == null) {
                CommonLogger.error(CameraErrorCode.INTERNAL_ERROR.getMessage() + ": Get location of current user error");
                throw new RestApiException(CameraErrorCode.INTERNAL_ERROR);
            }
            AreaRestrictionDtoClient areaRestrictionDtoClient = cameraServices.getAreaRestriction(locationDtoClient.getId(), request.getAreaRestrictionId());
            if (areaRestrictionDtoClient == null) {
                throw new RestApiException(CameraErrorCode.AREA_RESTRICTION_NOT_EXIST);
            }
            camera.setAreaRestrictionId(areaRestrictionDtoClient.getId());
            camera.setType(cameraServices.isAreaRestrictionModule() ? Const.GSKVHC : Const.KSHV);
        } else {
            throw new RestApiException(CameraErrorCode.MODULE_NOT_FOUND);
        }

        // Camera name is used
        if (!Objects.equals(camera.getName(), request.getName()) && cameraServices.getCameraByNameAndLocation(request.getName(), locationDtoClient.getId()) != null) {
            throw new RestApiException(CameraErrorCode.EXISTED_CAMERA_BY_NAME);
        }

        // Check camera active or not
        if (!Objects.equals(request.getIpAddress(), camera.getIpAddress())) {
            boolean isLiveCam = cameraServices.isLiveCame(request.getIpAddress());
            if (!isLiveCam) {
                throw new RestApiException(CameraErrorCode.CAMERA_NOT_WORKING);
            }
        }

        camera.setIpAddress(request.getIpAddress());
        camera.setLocationId(locationDtoClient.getId());
        camera.setName(request.getName());
        camera.setUpdated(true);
        Camera newCamera = cameraServices.saveCamera(camera);
        return convertCameraToCameraDto(newCamera);
    }

    @Override
    public boolean deleteCamera(int id) {
        // existed camera
        Camera camera = cameraServices.getCamera(id, Const.ACTIVE);
        if (camera == null) {
            throw new RestApiException(CameraErrorCode.CAMERA_NOT_EXIST);
        }

        // Check role delete camera
        boolean hasPermissionDeleteCamera = cameraServices.hasPermissionDeleteCamera(camera);
        if (!hasPermissionDeleteCamera) {
            throw new RestApiException(CameraErrorCode.PERMISSION_DENIED);
        }
        camera.setStreamState("off");
        camera.setStatus("deleted");
        cameraServices.saveCamera(camera);
        return true;
    }

    @Override
    public CameraDto getCamera(int id, String status) {
        Camera camera = cameraServices.getCamera(id, status);

        if (!cameraServices.hasPermissionViewCamera(camera)) {
            throw new RestApiException(CameraErrorCode.PERMISSION_DENIED);
        }
        if (camera == null) {
            throw new RestApiException(CameraErrorCode.CAMERA_NOT_EXIST);
        }

        return convertCameraToCameraDto(camera);
    }

    @Override
    public BaseCameraDto getCameraBase(int id, String status) {
        if (!isInternalFeature()) throw new RestApiException(CameraErrorCode.PERMISSION_DENIED);
        Camera camera = cameraServices.getCamera(id, status);
        if (camera == null) {
            throw new RestApiException(CameraErrorCode.CAMERA_NOT_EXIST);
        }

        return convertCameraToBaseCameraDto(camera);
    }

    @Override
    public boolean updatePolygonCamera(int id, CameraPolygonsRequest cameraRequest) {
        verifyRequestServices.verifyUpdateCameraPolygons(cameraRequest);
        // check permission
        if (!cameraServices.hasPermissionUpdatePolygons(id, cameraRequest)) {
            return false;
        }
        Camera camera = cameraServices.getCamera(id, Const.ACTIVE);
        if (camera == null) {
            throw new RestApiException(CameraErrorCode.CAMERA_NOT_EXIST);
        }
        try {
            camera.setPolygons(cameraRequest.getPolygons());
            camera.setUpdated(true);
            cameraServices.saveCamera(camera);
            return true;
        } catch (Exception e) {
            CommonLogger.error("Cant update polygon camera: " + e.getMessage());
            return false;
        }
    }

    @Override
    public int getNumberCameraOfLocation(int locationId) {
        if (!isInternalFeature()) throw new RestApiException(CameraErrorCode.PERMISSION_DENIED);
        return cameraServices.getNumberCameraOfLocation(locationId);
    }

    @Override
    public int getNumberCameraOfAreRestriction(int areaRestrictionId) {
        if (!isInternalFeature()) throw new RestApiException(CameraErrorCode.PERMISSION_DENIED);
        return cameraServices.getNumberCameraOfAreaRestriction(areaRestrictionId);
    }

    public BaseCameraDto convertCameraToBaseCameraDto(Camera camera) {
        if (camera == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(camera, BaseCameraDto.class);
        } catch (Exception e) {
            CommonLogger.error(e.getMessage(), e);
            return null;
        }
    }

    public CameraDto convertCameraToCameraDto(Camera camera) {
        if (camera == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            CameraDto cameraDto = modelMapper.map(camera, CameraDto.class);
            if (camera.getLocationId() != null) {
                LocationDtoClient locationDtoClient = cameraServices.getLocationById(camera.getLocationId());
                if (locationDtoClient != null) {
                    cameraDto.setLocation(convertLocationDtoFromClient(locationDtoClient));
                }
                if (camera.getAreaRestrictionId() != null) {
                    AreaRestrictionDtoClient areaRestrictionDtoClient = cameraServices.getAreaRestriction(camera.getLocationId(), camera.getAreaRestrictionId());
                    if (areaRestrictionDtoClient != null) {
                        cameraDto.setAreaRestriction(convertAreaRestrictionDtoFromClient(areaRestrictionDtoClient));
                    }
                }
            }
            return cameraDto;
        } catch (Exception e) {
            CommonLogger.error(e.getMessage(), e);
            return null;
        }
    }

    public LocationDto convertLocationDtoFromClient(LocationDtoClient locationDtoClient) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(locationDtoClient.getId());
        locationDto.setCode(locationDtoClient.getCode());
        locationDto.setName(locationDtoClient.getName());
        locationDto.setType(locationDtoClient.getType());
        return locationDto;
    }

    public AreaRestrictionDto convertAreaRestrictionDtoFromClient(AreaRestrictionDtoClient areaRestrictionDtoClient) {
        AreaRestrictionDto areaRestrictionDto = new AreaRestrictionDto();
        areaRestrictionDto.setId(areaRestrictionDtoClient.getId());
        areaRestrictionDto.setName(areaRestrictionDtoClient.getName());
        areaRestrictionDto.setCode(areaRestrictionDtoClient.getCode());
        areaRestrictionDto.setTimeStart(areaRestrictionDtoClient.getTimeStart());
        areaRestrictionDto.setTimeEnd(areaRestrictionDtoClient.getTimeEnd());
        return areaRestrictionDto;
    }

    public boolean isInternalFeature() {
        return Objects.equals(httpServletRequest.getHeader("token"), internalToken);
    }
}
