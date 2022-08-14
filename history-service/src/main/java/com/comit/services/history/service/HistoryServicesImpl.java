package com.comit.services.history.service;

import com.comit.services.history.client.*;
import com.comit.services.history.client.data.*;
import com.comit.services.history.client.response.*;
import com.comit.services.history.constant.HistoryErrorCode;
import com.comit.services.history.exception.RestApiException;
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
    public LocationDtoClient getLocation(Integer locationId) {
        if (locationId == null) {
            return null;
        }
        LocationResponse locationResponse = locationClient.getLocation(httpServletRequest.getHeader("token"), locationId).getBody();
        if (locationResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return locationResponse.getLocationDtoClient();
    }

    @Override
    public LocationDtoClient getLocationOfCurrentUser() {
        UserResponseClient userResponseClient = accountClient.getCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (userResponseClient == null || userResponseClient.getUser() == null || userResponseClient.getUser().getLocationId() == null) {
            return null;
        }
        LocationResponse locationResponse = locationClient.getLocation(httpServletRequest.getHeader("token"), userResponseClient.getUser().getLocationId()).getBody();
        if (locationResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return locationResponse.getLocationDtoClient();
    }


    @Override
    public AreaRestrictionDtoClient getAreaRestriction(Integer locationId, int areaRestrictionId) {
        if (locationId == null) {
            return null;
        }
        AreaRestrictionResponse areaRestrictionResponse = areaRestrictionClient.getAreaRestriction(httpServletRequest.getHeader("token"), areaRestrictionId).getBody();
        if (areaRestrictionResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return areaRestrictionResponse.getAreaRestriction();
    }

    @Override
    public CameraDtoClient getCamera(Integer cameraId) {
        if (cameraId == null) {
            return null;
        }
        CameraResponse cameraResponse = cameraClient.getCamera(httpServletRequest.getHeader("token"), cameraId).getBody();
        if (cameraResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return cameraResponse.getCamera();
    }

    @Override
    public EmployeeDtoClient getEmployee(Integer employeeId) {
        if (employeeId == null) {
            return null;
        }
        EmployeeResponse employeeResponse = employeeClient.getEmployee(httpServletRequest.getHeader("token"), employeeId).getBody();
        if (employeeResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return employeeResponse.getEmployee();
    }

    @Override
    public NotificationMethodDtoClient getNotificationMethodOfAreaRestriction(Integer areaRestrictionId) {
        if (areaRestrictionId == null) {
            return null;
        }
        NotificationMethodResponse notificationMethodResponse = areaRestrictionClient.getNotificationMethodOfAreaRestriction(httpServletRequest.getHeader("token"), areaRestrictionId).getBody();
        if (notificationMethodResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return notificationMethodResponse.getNotificationMethod();
    }

    @Override
    public MetadataDtoClient getMetadata(Integer imageId) {
        if (imageId == null) {
            return null;
        }

        MetadataResponse metadataResponse = metadataClient.getMetadata(httpServletRequest.getHeader("token"), imageId).getBody();
        if (metadataResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return metadataResponse.getMetadata();
    }
}
