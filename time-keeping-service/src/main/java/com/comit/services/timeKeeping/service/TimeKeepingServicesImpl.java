package com.comit.services.timeKeeping.service;

import com.comit.services.timeKeeping.client.AccountClient;
import com.comit.services.timeKeeping.client.LocationClient;
import com.comit.services.timeKeeping.client.data.LocationDtoClient;
import com.comit.services.timeKeeping.client.response.LocationResponseClient;
import com.comit.services.timeKeeping.client.response.UserResponseClient;
import com.comit.services.timeKeeping.constant.TimeKeepingErrorCode;
import com.comit.services.timeKeeping.exception.TimeKeepingCommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TimeKeepingServicesImpl implements TimeKeepingServices {
    @Autowired
    private AccountClient accountClient;
    @Autowired
    private LocationClient locationClient;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public LocationDtoClient getLocationOfCurrentUser() {
        UserResponseClient userResponseClient = accountClient.getCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (userResponseClient == null || userResponseClient.getUser() == null || userResponseClient.getUser().getLocationId() == null) {
            return null;
        }
        LocationResponseClient locationResponseClient = locationClient.getLocationById(httpServletRequest.getHeader("token"), userResponseClient.getUser().getLocationId()).getBody();
        if (locationResponseClient == null) {
            throw new TimeKeepingCommonException(TimeKeepingErrorCode.INTERNAL_ERROR);
        }
        return locationResponseClient.getLocation();
    }
}
