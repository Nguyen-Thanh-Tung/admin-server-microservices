package com.comit.services.camera.service;

import com.comit.services.camera.client.AccountClient;
import com.comit.services.camera.client.AreaRestrictionClient;
import com.comit.services.camera.client.LocationClient;
import com.comit.services.camera.client.response.AreaRestrictionResponse;
import com.comit.services.camera.client.response.LocationListResponse;
import com.comit.services.camera.client.response.LocationResponse;
import com.comit.services.camera.client.response.OrganizationResponse;
import com.comit.services.camera.constant.CameraErrorCode;
import com.comit.services.camera.constant.Const;
import com.comit.services.camera.exception.RestApiException;
import com.comit.services.camera.model.entity.AreaRestriction;
import com.comit.services.camera.model.entity.Camera;
import com.comit.services.camera.model.entity.Location;
import com.comit.services.camera.model.entity.Organization;
import com.comit.services.camera.repository.CameraRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class CameraServicesImpl implements CameraServices {

    @Autowired
    private CameraRepository cameraRepository;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private LocationClient locationClient;
    @Autowired
    private AreaRestrictionClient areaRestrictionClient;
    @Autowired
    private AccountClient accountClient;

    @Value("${core.api.check-camera}")
    private String checkCameraUrl;

    @Override
    public Page<Camera> getAllCamera(List<Integer> locationIds, List<Integer> cameraIds, String status, String search, Pageable pageable) {
        if (search != null && !search.trim().isEmpty()) {
            if (cameraIds.size() == 0) {
                if (status != null) {
                    return cameraRepository.findByLocationIdInAndStatusAndNameContainingOrderByIdDesc(locationIds, status, search, pageable);
                }
                return cameraRepository.findByLocationIdInAndNameContainingOrderByStatusAscIdDesc(locationIds, search, pageable);
            } else {
                if (status != null) {
                    return cameraRepository.findByLocationIdInAndIdInAndStatusAndNameContainingOrderByStatusAscIdDesc(locationIds, cameraIds, status, search, pageable);
                }
                return cameraRepository.findByLocationIdInAndIdInAndNameContainingOrderByStatusAscIdDesc(locationIds, cameraIds, search, pageable);
            }
        } else if (cameraIds.size() == 0) {
            if (status != null) {
                return cameraRepository.findByLocationIdInAndStatusOrderByIdDesc(locationIds, status, pageable);
            }
            return cameraRepository.findByLocationIdInOrderByStatusAscIdDesc(locationIds, pageable);
        } else {
            if (status != null) {
                return cameraRepository.findByLocationIdInAndIdInAndStatusOrderByStatusAscIdDesc(locationIds, cameraIds, status, pageable);
            }
            return cameraRepository.findByLocationIdInAndIdInOrderByStatusAscIdDesc(locationIds, cameraIds, pageable);
        }

    }

    @Override
    public Page<Camera> getAllCamera(Integer areaRestrictionId, String status, String search, Pageable paging) {
        if (search != null && !search.trim().isEmpty()) {
            if (status != null) {
                return cameraRepository.findByAreaRestrictionIdAndStatusAndNameContainingOrderByStatusAscIdDesc(areaRestrictionId, status, search, paging);
            }
            return cameraRepository.findByAreaRestrictionIdAndNameContainingOrderByStatusAscIdDesc(areaRestrictionId, search, paging);
        } else {
            if (status != null) {
                return cameraRepository.findByAreaRestrictionIdAndStatusOrderByStatusAscIdDesc(areaRestrictionId, status, paging);
            }
            return cameraRepository.findByAreaRestrictionIdOrderByStatusAscIdDesc(areaRestrictionId, paging);
        }
    }

    @Override
    public Page<Camera> findByUser(Integer userId, String status, Pageable pageable) {
        return null;
    }


    @Override
    public Page<Camera> findByLocation(Integer locationId, List<Integer> cameraIds, String status, String search, Pageable pageable) {
        if (search != null && !search.trim().isEmpty()) {
            if (cameraIds.size() == 0) {
                if (status != null) {
                    return cameraRepository.findByLocationIdInAndStatusAndNameContainingOrderByStatusAscIdDesc(List.of(locationId), status, search, pageable);
                }
                return cameraRepository.findByLocationIdInAndNameContainingOrderByStatusAscIdDesc(List.of(locationId), search, pageable);
            } else {
                if (status != null) {
                    return cameraRepository.findByLocationIdInAndIdInAndStatusAndNameContainingOrderByStatusAscIdDesc(List.of(locationId), cameraIds, status, search, pageable);
                }
                return cameraRepository.findByLocationIdInAndIdInAndNameContainingOrderByStatusAscIdDesc(List.of(locationId), cameraIds, search, pageable);
            }
        } else if (cameraIds.size() == 0) {
            if (status != null) {
                return cameraRepository.findByLocationIdInAndStatusOrderByStatusAscIdDesc(List.of(locationId), status, pageable);
            }
            return cameraRepository.findByLocationIdInOrderByStatusAscIdDesc(List.of(locationId), pageable);
        } else {
            if (status != null) {
                return cameraRepository.findByLocationIdInAndIdInAndStatusOrderByStatusAscIdDesc(List.of(locationId), cameraIds, status, pageable);
            }
            return cameraRepository.findByLocationIdInAndIdInOrderByStatusAscIdDesc(List.of(locationId), cameraIds, pageable);
        }
    }

    @Override
    public Camera saveCamera(Camera camera) {
        try {
            return cameraRepository.save(camera);
        } catch (Exception e) {
            throw new RestApiException(CameraErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public Camera getCamera(int id, String status) {
        if (status != null) {
            return cameraRepository.findByIdAndStatus(id, status);
        }
        return cameraRepository.findById(id);
    }

    @Override
    public boolean deleteCamera(int id) {
        try {
            cameraRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isLiveCame(String ipAddress) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        String checkCamBody = "{\"rtsp\": \"" +
                ipAddress +
                "\"}";
        RequestBody body = RequestBody.create(JSON, checkCamBody);
        Request.Builder requestBuilder = new Request.Builder();

        Request request = requestBuilder
                .url(checkCameraUrl)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.body() != null) {
                String responseBody = response.body().string();
                JsonObject obj = new JsonParser().parse(responseBody).getAsJsonObject();
                if (obj.has("code")) {
                    int code = obj.get("code").getAsInt();
                    return code == 200;
                }
            }
//            return "{\"code\": 200, \"data\": {\"embedding_id\":5, \"location_id\": 5, \"image_path\": \"test\"}}";
            return false;
        } catch (Exception e) {
            throw new RestApiException(CameraErrorCode.CAN_NOT_CHECK_CAMERA);
        }
    }

    @Override
    public Camera getCameraByNameAndLocation(String name, Integer locationId) {
        return cameraRepository.findByLocationIdAndName(locationId, name);
    }

    @Override
    public boolean isTimeKeepingModule() {
        String moduleName = httpServletRequest.getHeader(Const.HEADER_MODULE);
        return Objects.equals(moduleName, Const.TIME_KEEPING_HEADER_MODULE);
    }

    @Override
    public boolean isAreaRestrictionModule() {
        String moduleName = httpServletRequest.getHeader(Const.HEADER_MODULE);
        return Objects.equals(moduleName, Const.AREA_RESTRICTION_HEADER_MODULE);
    }

    @Override
    public boolean isBehaviorModule() {
        String moduleName = httpServletRequest.getHeader(Const.HEADER_MODULE);
        return Objects.equals(moduleName, Const.BEHAVIOR_HEADER_MODULE);
    }

    @Override
    public Organization getOrganizationOfCurrentUser() {
        OrganizationResponse organizationResponse = accountClient.getOrganizationOfCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (organizationResponse == null) {
            throw new RestApiException(CameraErrorCode.INTERNAL_ERROR);
        }

        return organizationResponse.getOrganization();
    }

    @Override
    public Location getLocationOfCurrentUser() {
        LocationResponse locationResponse = accountClient.getLocationOfCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (locationResponse == null) {
            throw new RestApiException(CameraErrorCode.INTERNAL_ERROR);
        }
        return locationResponse.getLocation();
    }

    @Override
    public AreaRestriction getAreaRestriction(Integer locationId, Integer areaRestrictionId) {
        AreaRestrictionResponse areaRestrictionResponse = areaRestrictionClient.getAreaRestriction(httpServletRequest.getHeader("token"), areaRestrictionId).getBody();
        if (areaRestrictionResponse == null) {
            throw new RestApiException(CameraErrorCode.INTERNAL_ERROR);
        }
        if (Objects.equals(areaRestrictionResponse.getAreaRestriction().getLocationId(), locationId)) {
            return areaRestrictionResponse.getAreaRestriction();
        }
        return null;
    }

    @Override
    public List<Location> getLocationListByOrganizationId(Integer organizationId) {
        LocationListResponse locationListResponse = locationClient.getLocationsByOrganizationId(httpServletRequest.getHeader("token"), organizationId).getBody();
        if (locationListResponse == null) {
            throw new RestApiException(CameraErrorCode.INTERNAL_ERROR);
        }
        return locationListResponse.getLocations();
    }

    @Override
    public Location getLocation(Integer organizationId, Integer locationId) {
        LocationResponse locationResponse = locationClient.getLocationById(httpServletRequest.getHeader("token"), locationId).getBody();
        if (locationResponse == null) {
            throw new RestApiException(CameraErrorCode.INTERNAL_ERROR);
        }

        if (!Objects.equals(locationResponse.getLocation().getOrganizationId(), organizationId)) {
            return null;
        }
        return locationResponse.getLocation();
    }

    @Override
    public Location getLocationById(Integer locationId) {
        LocationResponse locationResponse = locationClient.getLocationById(httpServletRequest.getHeader("token"), locationId).getBody();
        if (locationResponse == null) {
            throw new RestApiException(CameraErrorCode.INTERNAL_ERROR);
        }
        return locationResponse.getLocation();
    }

    @Override
    public int getNumberCameraOfLocation(int locationId) {
        return cameraRepository.countByLocationIdAndStatus(locationId, Const.ACTIVE);
    }
}
