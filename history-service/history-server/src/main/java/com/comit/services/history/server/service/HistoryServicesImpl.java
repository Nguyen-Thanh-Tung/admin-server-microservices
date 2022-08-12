package com.comit.services.history.server.service;

import com.comit.services.account.client.AccountClient;
import com.comit.services.account.client.response.UserResponse;
import com.comit.services.areaRestriction.client.AreaRestrictionClient;
import com.comit.services.areaRestriction.client.dto.AreaRestrictionDto;
import com.comit.services.areaRestriction.client.dto.NotificationMethodDto;
import com.comit.services.areaRestriction.client.response.AreaRestrictionResponse;
import com.comit.services.areaRestriction.client.response.NotificationMethodResponse;
import com.comit.services.camera.client.CameraClient;
import com.comit.services.camera.client.dto.CameraDto;
import com.comit.services.camera.client.response.CameraResponse;
import com.comit.services.employee.client.EmployeeClient;
import com.comit.services.employee.client.dto.EmployeeDto;
import com.comit.services.employee.client.response.EmployeeResponse;
import com.comit.services.history.server.constant.HistoryErrorCode;
import com.comit.services.history.server.exception.RestApiException;
import com.comit.services.location.client.LocationClient;
import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.location.client.response.LocationResponse;
import com.comit.services.metadata.client.MetadataClient;
import com.comit.services.metadata.client.dto.MetadataDto;
import com.comit.services.metadata.client.response.MetadataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class HistoryServicesImpl implements HistoryServices {
    @Autowired
    private AccountClient accountClient;
    @Autowired
    private LocationClient locationClient;
    @Autowired
    private CameraClient cameraClient;
    @Autowired
    private AreaRestrictionClient areaRestrictionClient;
    @Autowired
    private EmployeeClient employeeClient;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private MetadataClient metadataClient;

    @Override
    public LocationDto getLocation(Integer locationId) {
        if (locationId == null) {
            return null;
        }
        LocationResponse locationResponse = locationClient.getLocationById(httpServletRequest.getHeader("token"), locationId);
        if (locationResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return locationResponse.getLocation();
    }

    @Override
    public LocationDto getLocationOfCurrentUser() {
        UserResponse userResponse = accountClient.getCurrentUser(httpServletRequest.getHeader("token"));
        LocationResponse locationResponse = locationClient.getLocationById(httpServletRequest.getHeader("token"), userResponse.getUserDto().getLocationId());
        if (locationResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return locationResponse.getLocation();
    }


    @Override
    public AreaRestrictionDto getAreaRestriction(Integer locationId, int areaRestrictionId) {
        if (locationId == null) {
            return null;
        }
        AreaRestrictionResponse areaRestrictionResponse = areaRestrictionClient.getAreaRestriction(httpServletRequest.getHeader("token"), areaRestrictionId);
        if (areaRestrictionResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return areaRestrictionResponse.getAreaRestriction();
    }

    @Override
    public CameraDto getCamera(Integer cameraId) {
        if (cameraId == null) {
            return null;
        }
        CameraResponse cameraResponse = cameraClient.getCamera(httpServletRequest.getHeader("token"), cameraId);
        if (cameraResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return cameraResponse.getCamera();
    }

    @Override
    public EmployeeDto getEmployee(Integer employeeId) {
        if (employeeId == null) {
            return null;
        }
        EmployeeResponse employeeResponse = employeeClient.getEmployee(httpServletRequest.getHeader("token"), employeeId);
        if (employeeResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return employeeResponse.getEmployee();
    }

    @Override
    public NotificationMethodDto getNotificationMethodOfAreaRestriction(Integer areaRestrictionId) {
        if (areaRestrictionId == null) {
            return null;
        }
        NotificationMethodResponse notificationMethodResponse = areaRestrictionClient.getNotificationMethodOfAreaRestriction(httpServletRequest.getHeader("token"), areaRestrictionId);
        if (notificationMethodResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return notificationMethodResponse.getNotificationMethod();
    }

    @Override
    public MetadataDto getMetadata(Integer imageId) {
        if (imageId == null) {
            return null;
        }

        MetadataResponse metadataResponse = metadataClient.getMetadata(httpServletRequest.getHeader("token"), imageId);
        if (metadataResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return metadataResponse.getMetadata();
    }
}
