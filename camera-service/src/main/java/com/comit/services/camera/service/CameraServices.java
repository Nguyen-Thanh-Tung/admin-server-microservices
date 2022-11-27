package com.comit.services.camera.service;

import com.comit.services.camera.client.data.AreaRestrictionDtoClient;
import com.comit.services.camera.client.data.LocationDtoClient;
import com.comit.services.camera.client.data.OrganizationDtoClient;
import com.comit.services.camera.client.response.RoleListResponseClient;
import com.comit.services.camera.controller.request.CameraPolygonsRequest;
import com.comit.services.camera.model.entity.Camera;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CameraServices {
    Page<Camera> getAllCamera(List<Integer> locations, List<Integer> cameraIds, String status, String search, Pageable paging);

    Page<Camera> getAllCamera(Integer areaRestrictionId, String status, String search, Pageable paging);

    Page<Camera> findByLocation(Integer locationId, List<Integer> cameraIds, String status, String search, Pageable paging);

    Camera saveCamera(Camera camera);

    Camera getCamera(int id, String status);

    boolean deleteCamera(int id);

    boolean isLiveCame(String ipAddress);

    Camera getCameraByNameAndLocation(String name, Integer locationId);

    boolean isTimeKeepingModule();

    boolean isAreaRestrictionModule();

    boolean isBehaviorModule();

    boolean isMatchRolesAndModule(RoleListResponseClient rolesClient);

    boolean isMatchLocationTypeAndModule(int locationId);

    OrganizationDtoClient getOrganizationOfCurrentUser();

    LocationDtoClient getLocationOfCurrentUser();

    AreaRestrictionDtoClient getAreaRestriction(Integer locationId, Integer areaRestrictionId);

    List<LocationDtoClient> getLocationListByOrganizationId(Integer organizationId);

    LocationDtoClient getLocation(Integer organizationId, Integer locationId);

    LocationDtoClient getLocationById(Integer locationId);

    int getNumberCameraOfLocation(int locationId);

    int getNumberCameraOfAreaRestriction(int areaRestrictionId);

    boolean hasPermissionViewCamera(Camera camera);

    boolean hasRole(String roleNeedCheck);

    boolean hasPermissionDeleteCamera(Camera camera);

    RoleListResponseClient getAllRoleOfCurrent();

    boolean hasPermissionUpdatePolygons(int id, CameraPolygonsRequest request);
}
