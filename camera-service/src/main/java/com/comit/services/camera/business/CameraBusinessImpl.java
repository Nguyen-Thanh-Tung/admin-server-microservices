package com.comit.services.camera.business;

import com.comit.services.camera.constant.CameraErrorCode;
import com.comit.services.camera.constant.Const;
import com.comit.services.camera.controller.request.CameraPolygonsRequest;
import com.comit.services.camera.controller.request.CameraRequest;
import com.comit.services.camera.exception.RestApiException;
import com.comit.services.camera.middleware.CameraVerifyRequestServices;
import com.comit.services.camera.model.dto.AreaRestrictionDto;
import com.comit.services.camera.model.dto.CameraDto;
import com.comit.services.camera.model.dto.LocationDto;
import com.comit.services.camera.model.entity.AreaRestriction;
import com.comit.services.camera.model.entity.Camera;
import com.comit.services.camera.model.entity.Location;
import com.comit.services.camera.model.entity.Organization;
import com.comit.services.camera.service.CameraServices;
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
        Organization organization = cameraServices.getOrganizationOfCurrentUser();
        Location locationOfCurrentUser = cameraServices.getLocationOfCurrentUser();
        if (areaRestrictionId != null) {
            AreaRestriction areaRestriction = cameraServices.getAreaRestriction(locationOfCurrentUser.getId(), areaRestrictionId);
            if (areaRestriction == null) {
                throw new RestApiException(CameraErrorCode.AREA_RESTRICTION_NOT_EXIST);
            }
            cameraPage = cameraServices.getAllCamera(areaRestriction.getId(), status, search, paging);
        } else if (locationId == null) {
            List<Integer> locationIds = new ArrayList<>();
            if (locationOfCurrentUser == null) {
                List<Location> locations = cameraServices.getLocationListByOrganizationId(organization.getId());
                for (Location tmp : locations) {
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
            Location location = cameraServices.getLocation(organization.getId(), locationId);
            if (location == null) {
                throw new RestApiException(CameraErrorCode.LOCATION_NOT_EXIST);
            }
            cameraPage = cameraServices.findByLocation(location.getId(), new ArrayList<>(), status, search, paging);
        }

        return cameraPage;
    }

    @Override
    public Page<Camera> getCameraPage(Integer locationId, String cameraIdStrs, String status, int page, int size, String search) {
        Pageable paging = PageRequest.of(page, size);

        Page<Camera> cameraPage;
        Organization organization = cameraServices.getOrganizationOfCurrentUser();
        List<Integer> cameraIds = new ArrayList<>();
        if (cameraIdStrs != null && !cameraIdStrs.trim().isEmpty()) {
            for (String cameraId : cameraIdStrs.split(",")) {
                cameraIds.add(Integer.parseInt(cameraId));
            }
        }
        if (locationId == null) {
            List<Integer> locationIds = new ArrayList<>();
            Location locationOfCurrentUser = cameraServices.getLocationOfCurrentUser();
            if (locationOfCurrentUser == null) {
                List<Location> locations = cameraServices.getLocationListByOrganizationId(organization.getId());
                for (Location tmp : locations) {
                    if ((cameraServices.isTimeKeepingModule() && Objects.equals(tmp.getType(), Const.TIME_KEEPING_TYPE)) ||
                            (cameraServices.isAreaRestrictionModule() && Objects.equals(tmp.getType(), Const.AREA_RESTRICTION_TYPE)) ||
                            (cameraServices.isBehaviorModule() && Objects.equals(tmp.getType(), Const.BEHAVIOR_TYPE))) {
                        locationIds.add(tmp.getId());
                    }
                }
            } else {
                locationIds = List.of(locationOfCurrentUser.getId());
            }
            cameraPage = cameraServices.getAllCamera(locationIds, cameraIds, status, search, paging);
        } else {
            Location location = cameraServices.getLocation(organization.getId(), locationId);
            if (location == null) {
                throw new RestApiException(CameraErrorCode.LOCATION_NOT_EXIST);
            }
            cameraPage = cameraServices.findByLocation(location.getId(), cameraIds, status, search, paging);
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
        Organization organization = cameraServices.getOrganizationOfCurrentUser();
        Location location;
        Camera camera = new Camera();
        if (cameraServices.isTimeKeepingModule()) {
            location = cameraServices.getLocation(organization.getId(), request.getLocationId());
            if (location == null) {
                throw new RestApiException(CameraErrorCode.LOCATION_NOT_EXIST);
            }
            camera.setType(request.getType());
        } else if (cameraServices.isAreaRestrictionModule()) {
            location = cameraServices.getLocationOfCurrentUser();
            AreaRestriction areaRestriction = cameraServices.getAreaRestriction(location.getId(), request.getAreaRestrictionId());
            if (areaRestriction == null) {
                throw new RestApiException(CameraErrorCode.AREA_RESTRICTION_NOT_EXIST);
            }
            camera.setAreaRestrictionId(areaRestriction.getId());
            camera.setType("Giám sát KVHC");
        } else if (cameraServices.isBehaviorModule()) {
            location = cameraServices.getLocationOfCurrentUser();
            AreaRestriction areaRestriction = cameraServices.getAreaRestriction(location.getId(), request.getAreaRestrictionId());
            if (areaRestriction == null) {
                throw new RestApiException(CameraErrorCode.AREA_RESTRICTION_NOT_EXIST);
            }
            camera.setAreaRestrictionId(areaRestriction.getId());
            camera.setType("Kiểm soát hành vi");
        } else {
            throw new RestApiException(CameraErrorCode.MODULE_NOT_FOUND);
        }
        // Camera name is used
        if (cameraServices.getCameraByNameAndLocation(request.getName(), location.getId()) != null) {
            throw new RestApiException(CameraErrorCode.EXISTED_CAMERA_BY_NAME);
        }
        camera.setIpAddress(request.getIpAddress());
        camera.setLocationId(location.getId());
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
        Organization organization = cameraServices.getOrganizationOfCurrentUser();
        Location location;
        if (cameraServices.isTimeKeepingModule()) {
            location = cameraServices.getLocation(organization.getId(), request.getLocationId());
            if (location == null) {
                throw new RestApiException(CameraErrorCode.LOCATION_NOT_EXIST);
            }
            camera.setType(request.getType());
        } else if (cameraServices.isAreaRestrictionModule()) {
            location = cameraServices.getLocationOfCurrentUser();
            AreaRestriction areaRestriction = cameraServices.getAreaRestriction(location.getId(), request.getAreaRestrictionId());
            if (areaRestriction == null) {
                throw new RestApiException(CameraErrorCode.AREA_RESTRICTION_NOT_EXIST);
            }
            camera.setAreaRestrictionId(areaRestriction.getId());
            camera.setType("Giám sát KVHC");
        } else if (cameraServices.isBehaviorModule()) {
            location = cameraServices.getLocationOfCurrentUser();
            AreaRestriction areaRestriction = cameraServices.getAreaRestriction(location.getId(), request.getAreaRestrictionId());
            if (areaRestriction == null) {
                throw new RestApiException(CameraErrorCode.AREA_RESTRICTION_NOT_EXIST);
            }
            camera.setAreaRestrictionId(areaRestriction.getId());
            camera.setType("Kiểm soát hành vi");
        } else {
            throw new RestApiException(CameraErrorCode.MODULE_NOT_FOUND);
        }

        // Camera name is used
        if (!Objects.equals(camera.getName(), request.getName()) && cameraServices.getCameraByNameAndLocation(request.getName(), location.getId()) != null) {
            throw new RestApiException(CameraErrorCode.EXISTED_CAMERA_BY_NAME);
        }

        camera.setIpAddress(request.getIpAddress());
        camera.setLocationId(location.getId());
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

    public CameraDto convertCameraToCameraDto(Camera camera) {
        if (camera == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            CameraDto cameraDto = modelMapper.map(camera, CameraDto.class);
            if (camera.getLocationId() != null) {
                Location location = cameraServices.getLocationById(camera.getLocationId());
                if (location != null) {
                    cameraDto.setLocation(LocationDto.convertLocationToLocationDto(location));
                }
                if (camera.getAreaRestrictionId() != null) {
                    AreaRestriction areaRestriction = cameraServices.getAreaRestriction(camera.getLocationId(), camera.getAreaRestrictionId());
                    if (areaRestriction != null){
                        cameraDto.setAreaRestriction(AreaRestrictionDto.convertAreaRestrictionToAreaRestrictionDto(areaRestriction));
                    }
                }
            }
            return cameraDto;
        } catch (Exception e) {
            return null;
        }
    }
}
