package com.comit.services.camera.business;


import com.comit.services.camera.controller.request.CameraPolygonsRequest;
import com.comit.services.camera.controller.request.CameraRequest;
import com.comit.services.camera.model.dto.BaseCameraDto;
import com.comit.services.camera.model.dto.CameraDto;
import com.comit.services.camera.model.entity.Camera;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CameraBusiness {
    Page<Camera> getCameraPage(Integer locationId, Integer areaRestrictionId, String status, int page, int size, String search);

    Page<Camera> getCameraPage(Integer locationId, String cameraIds, String status, int page, int size, String search);

    List<CameraDto> getAllCamera(List<Camera> cameras);

    CameraDto addCamera(CameraRequest addCameraRequest);

    CameraDto updateCamera(int id, CameraRequest cameraRequest);

    boolean deleteCamera(int id);

    CameraDto getCamera(int id, String status);

    BaseCameraDto getCameraBase(int id, String status);

    boolean updatePolygonCamera(int id, CameraPolygonsRequest cameraRequest);

    int getNumberCameraOfLocation(int locationId);

    int getNumberCameraOfAreRestriction(int areaRestrictionId);
}
