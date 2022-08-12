package com.comit.services.camera.server.service;

import com.comit.services.areaRestriction.client.dto.AreaRestrictionDto;
import com.comit.services.camera.server.model.Camera;
import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.organization.client.dto.OrganizationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CameraServices {
    Page<Camera> getAllCamera(List<Integer> locations, List<Integer> cameraIds, String status, String search, Pageable paging);

    Page<Camera> getAllCamera(Integer areaRestrictionId, String status, String search, Pageable paging);

    Page<Camera> findByUser(Integer userId, String status, Pageable paging);

    Page<Camera> findByLocation(Integer locationId, List<Integer> cameraIds, String status, String search, Pageable paging);

    Camera saveCamera(Camera camera);

    Camera getCamera(int id, String status);

    boolean deleteCamera(int id);

    boolean isLiveCame(String ipAddress);

    Camera getCameraByNameAndLocation(String name, Integer locationId);

    boolean isTimeKeepingModule();

    boolean isAreaRestrictionModule();

    boolean isBehaviorModule();

    OrganizationDto getOrganizationOfCurrentUser();

    LocationDto getLocationOfCurrentUser();

    AreaRestrictionDto getAreaRestriction(Integer locationId, Integer areaRestrictionId);

    List<LocationDto> getLocationListByOrganizationId(Integer organizationId);

    LocationDto getLocation(Integer organizationId, Integer locationId);

    LocationDto getLocationById(Integer locationId);

    int getNumberCameraOfLocation(int locationId);

    int getNumberCameraOfAreaRestriction(int areaRestrictionId);
}
