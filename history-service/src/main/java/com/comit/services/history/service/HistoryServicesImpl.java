package com.comit.services.history.service;

import com.comit.services.history.client.*;
import com.comit.services.history.client.response.*;
import com.comit.services.history.constant.HistoryErrorCode;
import com.comit.services.history.exception.RestApiException;
import com.comit.services.history.model.entity.AreaRestriction;
import com.comit.services.history.model.entity.Camera;
import com.comit.services.history.model.entity.Employee;
import com.comit.services.history.model.entity.Location;
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

    @Override
    public Location getLocation(Integer locationId) {
        LocationResponse locationResponse = locationClient.getLocation(httpServletRequest.getHeader("token"), locationId).getBody();
        if (locationResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return locationResponse.getLocation();
    }

    @Override
    public Location getLocationOfCurrentUser() {
        LocationResponse locationResponse = accountClient.getLocationOfCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (locationResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return locationResponse.getLocation();
    }


    @Override
    public AreaRestriction getAreaRestriction(Integer locationId, int areaRestrictionId) {
        AreaRestrictionResponse areaRestrictionResponse = areaRestrictionClient.getAreaRestriction(httpServletRequest.getHeader("token"), areaRestrictionId).getBody();
        if (areaRestrictionResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return areaRestrictionResponse.getAreaRestriction();
    }

    @Override
    public Camera getCamera(Integer cameraId) {
        CameraResponse cameraResponse = cameraClient.getCamera(httpServletRequest.getHeader("token"), cameraId).getBody();
        if (cameraResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return cameraResponse.getCamera();
    }

    @Override
    public Employee getEmployee(Integer employeeId) {
        EmployeeResponse employeeResponse = employeeClient.getEmployee(httpServletRequest.getHeader("token"), employeeId).getBody();
        if (employeeResponse == null) {
            throw new RestApiException(HistoryErrorCode.INTERNAL_ERROR);
        }
        return employeeResponse.getEmployee();
    }
}
