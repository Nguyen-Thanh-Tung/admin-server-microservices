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
        LocationResponseClient locationResponseClient = locationClient.getLocation(httpServletRequest.getHeader("token"), locationId).getBody();
        if (locationResponseClient == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return locationResponseClient.getLocationDtoClient();
    }

    @Override
    public LocationDtoClient getLocationOfCurrentUser() {
        UserResponseClient userResponseClient = accountClient.getCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (userResponseClient == null || userResponseClient.getUser() == null || userResponseClient.getUser().getLocationId() == null) {
            return null;
        }
        LocationResponseClient locationResponseClient = locationClient.getLocation(httpServletRequest.getHeader("token"), userResponseClient.getUser().getLocationId()).getBody();
        if (locationResponseClient == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return locationResponseClient.getLocationDtoClient();
    }


    @Override
    public AreaRestrictionDtoClient getAreaRestriction(Integer locationId, int areaRestrictionId) {
        if (locationId == null) {
            return null;
        }
        AreaRestrictionResponseClient areaRestrictionResponseClient = areaRestrictionClient.getAreaRestriction(httpServletRequest.getHeader("token"), areaRestrictionId).getBody();
        if (areaRestrictionResponseClient == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return areaRestrictionResponseClient.getAreaRestriction();
    }

    @Override
    public CameraDtoClient getCamera(Integer cameraId) {
        if (cameraId == null) {
            return null;
        }
        CameraResponseClient cameraResponseClient = cameraClient.getCamera(httpServletRequest.getHeader("token"), cameraId).getBody();
        if (cameraResponseClient == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return cameraResponseClient.getCamera();
    }

    @Override
    public EmployeeDtoClient getEmployee(Integer employeeId) {
        if (employeeId == null) {
            return null;
        }
        EmployeeResponseClient employeeResponseClient = employeeClient.getEmployee(httpServletRequest.getHeader("token"), employeeId).getBody();
        if (employeeResponseClient == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return employeeResponseClient.getEmployee();
    }

    @Override
    public NotificationMethodDtoClient getNotificationMethodOfAreaRestriction(Integer areaRestrictionId) {
        if (areaRestrictionId == null) {
            return null;
        }
        NotificationMethodResponseClient notificationMethodResponseClient = areaRestrictionClient.getNotificationMethodOfAreaRestriction(httpServletRequest.getHeader("token"), areaRestrictionId).getBody();
        if (notificationMethodResponseClient == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return notificationMethodResponseClient.getNotificationMethod();
    }

    @Override
    public MetadataDtoClient getMetadata(Integer imageId) {
        if (imageId == null) {
            return null;
        }

        MetadataResponseClient metadataResponseClient = metadataClient.getMetadata(httpServletRequest.getHeader("token"), imageId).getBody();
        if (metadataResponseClient == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return metadataResponseClient.getMetadata();
    }
}
