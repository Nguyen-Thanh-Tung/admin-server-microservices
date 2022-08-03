package com.comit.services.camera.controller;

import com.comit.services.camera.business.CameraBusiness;
import com.comit.services.camera.constant.CameraErrorCode;
import com.comit.services.camera.constant.Const;
import com.comit.services.camera.controller.request.CameraPolygonsRequest;
import com.comit.services.camera.controller.request.CameraRequest;
import com.comit.services.camera.controller.response.BaseResponse;
import com.comit.services.camera.controller.response.CameraListResponse;
import com.comit.services.camera.controller.response.CameraResponse;
import com.comit.services.camera.controller.response.CountResponse;
import com.comit.services.camera.model.dto.CameraDto;
import com.comit.services.camera.model.entity.Camera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cameras")
public class CameraController {
    @Autowired
    private CameraBusiness cameraBusiness;

    /**
     * Get all cameras
     *
     * @return CameraListResponse
     */
    @GetMapping(value = "")
    public ResponseEntity<BaseResponse> getAllCameras(
            @RequestParam(required = false, value = "location_id") Integer locationId,
            @RequestParam(required = false, value = "area_restriction_id") Integer areaRestrictionId,
            @RequestParam(required = false, value = "camera_ids") String cameraIds,
            @RequestParam(required = false, value = "status") String status,
            @RequestParam(defaultValue = Const.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Const.DEFAULT_SIZE_PAGE) int size,
            @RequestParam(required = false) String search
    ) {
        Page<Camera> cameraPage;
        if (areaRestrictionId != null) {
            cameraPage = cameraBusiness.getCameraPage(locationId, areaRestrictionId, status, page, size, search);
        } else {
            cameraPage = cameraBusiness.getCameraPage(locationId, cameraIds, status, page, size, search);
        }
        List<CameraDto> cameraDtos = new ArrayList<>();
        int currentPage = 0;
        long totalItems = 0;
        int totalPages = 0;
        if (cameraPage != null) {
            cameraDtos = cameraBusiness.getAllCamera(cameraPage.getContent());
            currentPage = cameraPage.getNumber();
            totalItems = cameraPage.getTotalElements();
            totalPages = cameraPage.getTotalPages();
        }
        return new ResponseEntity<>(new CameraListResponse(CameraErrorCode.SUCCESS, cameraDtos, currentPage, totalItems, totalPages), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> getCamera(@PathVariable int id, @RequestParam(required = false) String status) {
        CameraDto cameraDto = cameraBusiness.getCamera(id, status);
        return new ResponseEntity<>(new CameraResponse(CameraErrorCode.SUCCESS, cameraDto), HttpStatus.OK);
    }

    /**
     * Add camera
     *
     * @param cameraRequest CameraRequest
     * @return CameraResponse
     */
    @PostMapping(value = "")
    public ResponseEntity<BaseResponse> addCamera(@RequestBody CameraRequest cameraRequest) {
        CameraDto cameraDto = cameraBusiness.addCamera(cameraRequest);
        return new ResponseEntity<>(new CameraResponse(CameraErrorCode.SUCCESS, cameraDto), HttpStatus.OK);
    }

    /**
     * Update camera
     *
     * @param cameraRequest CameraRequest
     * @return CameraResponse
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> updateCamera(@PathVariable int id, @RequestBody CameraRequest cameraRequest) {
        CameraDto cameraDto = cameraBusiness.updateCamera(id, cameraRequest);
        return new ResponseEntity<>(new CameraResponse(CameraErrorCode.SUCCESS, cameraDto), HttpStatus.OK);
    }

    /**
     * Update camera polygons
     *
     * @param cameraRequest CameraRequest
     * @return CameraResponse
     */
    @PutMapping(value = "/{id}/polygons")
    public ResponseEntity<BaseResponse> updatePolygonsCamera(@PathVariable int id, @RequestBody CameraPolygonsRequest cameraRequest) {
        boolean updateSuccess = cameraBusiness.updatePolygonCamera(id, cameraRequest);
        if (updateSuccess) {
            return new ResponseEntity<>(new BaseResponse(CameraErrorCode.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(CameraErrorCode.CAN_NOT_UPDATE_POLYGON_CAMERA), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> deleteCamera(@PathVariable int id) {
        boolean deleteSuccess = cameraBusiness.deleteCamera(id);
        if (deleteSuccess) {
            return new ResponseEntity<>(new BaseResponse(CameraErrorCode.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(CameraErrorCode.CAN_NOT_DELETE_CAMERA), HttpStatus.OK);
    }

    @GetMapping(value = "/location/{locationId}")
    public ResponseEntity<BaseResponse> getNumberCameraOfLocation(@PathVariable int locationId) {
        int numberCameraOfLocation = cameraBusiness.getNumberCameraOfLocation(locationId);
        return new ResponseEntity<>(new CountResponse(CameraErrorCode.SUCCESS, numberCameraOfLocation), HttpStatus.OK);
    }
}
