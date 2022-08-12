package com.comit.services.camera.server.business;

import com.comit.services.areaRestriction.client.dto.AreaRestrictionDto;
import com.comit.services.camera.client.dto.CameraDto;
import com.comit.services.camera.client.request.CameraPolygonsRequest;
import com.comit.services.camera.client.request.CameraRequest;
import com.comit.services.camera.server.constant.CameraErrorCode;
import com.comit.services.camera.server.constant.Const;
import com.comit.services.camera.server.exception.RestApiException;
import com.comit.services.camera.server.middleware.CameraVerifyRequestServices;
import com.comit.services.camera.server.model.Camera;
import com.comit.services.camera.server.service.CameraServices;
import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.organization.client.dto.OrganizationDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CameraBusinessImpl implements CameraBusiness {
    @Autowired
    private CameraVerifyRequestServices verifyRequestServices;
    @Autowired
    private CameraServices cameraServices;

    @Override
    public Page<Camera> getCameraPage(Integer locationId, Integer areaRestrictionId, String status, int page, int size, String search) {
        Pageable paging = PageRequest.of(page, size);

        Page<Camera> cameraPage;
        OrganizationDto organizationDto = cameraServices.getOrganizationOfCurrentUser();
        LocationDto locationOfCurrentUser = cameraServices.getLocationOfCurrentUser();
        if (areaRestrictionId != null) {
            AreaRestrictionDto areaRestrictionDto = cameraServices.getAreaRestriction(locationOfCurrentUser.getId(), areaRestrictionId);
            if (areaRestrictionDto == null) {
                throw new RestApiException(CameraErrorCode.AREA_RESTRICTION_NOT_EXIST);
            }
            cameraPage = cameraServices.getAllCamera(areaRestrictionDto.getId(), status, search, paging);
        } else if (locationId == null) {
            List<Integer> locationIds = new ArrayList<>();
            if (locationOfCurrentUser == null) {
                List<LocationDto> locationDtos = cameraServices.getLocationListByOrganizationId(organizationDto.getId());
                for (LocationDto tmp : locationDtos) {
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
            LocationDto locationDto = cameraServices.getLocation(organizationDto.getId(), locationId);
            if (locationDto == null) {
                throw new RestApiException(CameraErrorCode.LOCATION_NOT_EXIST);
            }
            cameraPage = cameraServices.findByLocation(locationDto.getId(), new ArrayList<>(), status, search, paging);
        }

        return cameraPage;
    }

    @Override
    public Page<Camera> getCameraPage(Integer locationId, String cameraIdStrs, String status, int page, int size, String search) {
        Pageable paging = PageRequest.of(page, size);

        Page<Camera> cameraPage;
        OrganizationDto organizationDto = cameraServices.getOrganizationOfCurrentUser();
        List<Integer> cameraIds = new ArrayList<>();
        if (cameraIdStrs != null && !cameraIdStrs.trim().isEmpty()) {
            for (String cameraId : cameraIdStrs.split(",")) {
                cameraIds.add(Integer.parseInt(cameraId));
            }
        }
        if (locationId == null) {
            List<Integer> locationIds = new ArrayList<>();
            LocationDto locationOfCurrentUserDto = cameraServices.getLocationOfCurrentUser();
            if (locationOfCurrentUserDto == null) {
                List<LocationDto> locations = cameraServices.getLocationListByOrganizationId(organizationDto.getId());
                for (LocationDto tmp : locations) {
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
            LocationDto locationDto = cameraServices.getLocation(organizationDto.getId(), locationId);
            if (locationDto == null) {
                throw new RestApiException(CameraErrorCode.LOCATION_NOT_EXIST);
            }
            cameraPage = cameraServices.findByLocation(locationDto.getId(), cameraIds, status, search, paging);
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
        verifyRequestServices.verifyAddOrUpdateCameraRequest(request);
        // Check camera active or not
        boolean isLiveCam = cameraServices.isLiveCame(request.getIpAddress());
        if (!isLiveCam) {
            throw new RestApiException(CameraErrorCode.CAMERA_NOT_WORKING);
        }
        OrganizationDto organizationDto = cameraServices.getOrganizationOfCurrentUser();
        LocationDto locationDto;
        Camera camera = new Camera();
        if (cameraServices.isTimeKeepingModule()) {
            locationDto = cameraServices.getLocation(organizationDto.getId(), request.getLocationId());
            if (locationDto == null) {
                throw new RestApiException(CameraErrorCode.LOCATION_NOT_EXIST);
            }
            camera.setType(request.getType());
        } else if (cameraServices.isAreaRestrictionModule()) {
            locationDto = cameraServices.getLocationOfCurrentUser();
            AreaRestrictionDto areaRestrictionDto = cameraServices.getAreaRestriction(locationDto.getId(), request.getAreaRestrictionId());
            if (areaRestrictionDto == null) {
                throw new RestApiException(CameraErrorCode.AREA_RESTRICTION_NOT_EXIST);
            }
            camera.setAreaRestrictionId(areaRestrictionDto.getId());
            camera.setType("Giám sát KVHC");
        } else if (cameraServices.isBehaviorModule()) {
            locationDto = cameraServices.getLocationOfCurrentUser();
            AreaRestrictionDto areaRestrictionDto = cameraServices.getAreaRestriction(locationDto.getId(), request.getAreaRestrictionId());
            if (areaRestrictionDto == null) {
                throw new RestApiException(CameraErrorCode.AREA_RESTRICTION_NOT_EXIST);
            }
            camera.setAreaRestrictionId(areaRestrictionDto.getId());
            camera.setType("Kiểm soát hành vi");
        } else {
            throw new RestApiException(CameraErrorCode.MODULE_NOT_FOUND);
        }
        // Camera name is used
        if (cameraServices.getCameraByNameAndLocation(request.getName(), locationDto.getId()) != null) {
            throw new RestApiException(CameraErrorCode.EXISTED_CAMERA_BY_NAME);
        }
        camera.setIpAddress(request.getIpAddress());
        camera.setLocationId(locationDto.getId());
        camera.setName(request.getName());
        camera.setStreamState(Const.ON);
        camera.setStatus(Const.ACTIVE);
        Camera newCamera = cameraServices.saveCamera(camera);
        return convertCameraToCameraDto(newCamera);
    }

    @Override
    public CameraDto updateCamera(int id, CameraRequest request) {
        verifyRequestServices.verifyAddOrUpdateCameraRequest(request);
        Camera camera = cameraServices.getCamera(id, Const.ACTIVE);
        if (camera == null) {
            throw new RestApiException(CameraErrorCode.CAMERA_NOT_EXIST);
        }

        // Check camera active or not
        if (!Objects.equals(request.getIpAddress(), camera.getIpAddress())) {
            boolean isLiveCam = cameraServices.isLiveCame(request.getIpAddress());
            if (!isLiveCam) {
                throw new RestApiException(CameraErrorCode.CAMERA_NOT_WORKING);
            }
        }
        OrganizationDto organizationDto = cameraServices.getOrganizationOfCurrentUser();
        LocationDto locationDto;
        if (cameraServices.isTimeKeepingModule()) {
            locationDto = cameraServices.getLocation(organizationDto.getId(), request.getLocationId());
            if (locationDto == null) {
                throw new RestApiException(CameraErrorCode.LOCATION_NOT_EXIST);
            }
            camera.setType(request.getType());
        } else if (cameraServices.isAreaRestrictionModule()) {
            locationDto = cameraServices.getLocationOfCurrentUser();
            AreaRestrictionDto areaRestrictionDto = cameraServices.getAreaRestriction(locationDto.getId(), request.getAreaRestrictionId());
            if (areaRestrictionDto == null) {
                throw new RestApiException(CameraErrorCode.AREA_RESTRICTION_NOT_EXIST);
            }
            camera.setAreaRestrictionId(areaRestrictionDto.getId());
            camera.setType("Giám sát KVHC");
        } else if (cameraServices.isBehaviorModule()) {
            locationDto = cameraServices.getLocationOfCurrentUser();
            AreaRestrictionDto areaRestrictionDto = cameraServices.getAreaRestriction(locationDto.getId(), request.getAreaRestrictionId());
            if (areaRestrictionDto == null) {
                throw new RestApiException(CameraErrorCode.AREA_RESTRICTION_NOT_EXIST);
            }
            camera.setAreaRestrictionId(areaRestrictionDto.getId());
            camera.setType("Kiểm soát hành vi");
        } else {
            throw new RestApiException(CameraErrorCode.MODULE_NOT_FOUND);
        }

        // Camera name is used
        if (!Objects.equals(camera.getName(), request.getName()) && cameraServices.getCameraByNameAndLocation(request.getName(), locationDto.getId()) != null) {
            throw new RestApiException(CameraErrorCode.EXISTED_CAMERA_BY_NAME);
        }

        camera.setIpAddress(request.getIpAddress());
        camera.setLocationId(locationDto.getId());
        camera.setName(request.getName());
        camera.setUpdated(true);
        Camera newCamera = cameraServices.saveCamera(camera);
        return convertCameraToCameraDto(newCamera);
    }

    @Override
    public boolean deleteCamera(int id) {
        Camera camera = cameraServices.getCamera(id, Const.ACTIVE);
        if (camera == null) {
            throw new RestApiException(CameraErrorCode.CAMERA_NOT_EXIST);
        }
        camera.setStreamState("off");
        camera.setStatus("deleted");
        cameraServices.saveCamera(camera);
        return true;
    }

    @Override
    public CameraDto getCamera(int id, String status) {
        Camera camera = cameraServices.getCamera(id, status);
        if (camera == null) {
            throw new RestApiException(CameraErrorCode.CAMERA_NOT_EXIST);
        }

        return convertCameraToCameraDto(camera);
    }

    @Override
    public boolean updatePolygonCamera(int id, CameraPolygonsRequest cameraRequest) {
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
            return false;
        }
    }

    @Override
    public int getNumberCameraOfLocation(int locationId) {
        return cameraServices.getNumberCameraOfLocation(locationId);
    }

    @Override
    public int getNumberCameraOfAreRestriction(int areaRestrictionId) {
        return cameraServices.getNumberCameraOfAreaRestriction(areaRestrictionId);
    }

    public CameraDto convertCameraToCameraDto(Camera camera) {
        if (camera == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            CameraDto cameraDto = modelMapper.map(camera, CameraDto.class);
            if (camera.getLocationId() != null) {
                LocationDto locationDto = cameraServices.getLocationById(camera.getLocationId());
                if (locationDto != null) {
                    cameraDto.setLocation(locationDto);
                }
                if (camera.getAreaRestrictionId() != null) {
                    AreaRestrictionDto areaRestrictionDto = cameraServices.getAreaRestriction(camera.getLocationId(), camera.getAreaRestrictionId());
                    if (areaRestrictionDto != null) {
                        cameraDto.setAreaRestriction(areaRestrictionDto);
                    }
                }
            }
            return cameraDto;
        } catch (Exception e) {
            return null;
        }
    }
}
