package com.comit.services.timeKeeping.server.service;

import com.comit.services.account.client.AccountClient;
import com.comit.services.account.client.response.UserResponse;
import com.comit.services.location.client.LocationClient;
import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.location.client.response.LocationResponse;
import com.comit.services.timeKeeping.server.constant.TimeKeepingErrorCode;
import com.comit.services.timeKeeping.server.exception.TimeKeepingCommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TimeKeepingServicesImpl implements TimeKeepingServices {
    @Autowired
    private AccountClient accountClient;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private LocationClient locationClient;

    @Override
    public LocationDto getLocationOfCurrentUser() {
        UserResponse userResponse = accountClient.getCurrentUser(httpServletRequest.getHeader("token"));
        LocationResponse locationResponse = locationClient.getLocationById(httpServletRequest.getHeader("token"), userResponse.getUserDto().getLocationId());
        if (locationResponse == null) {
            throw new TimeKeepingCommonException(TimeKeepingErrorCode.INTERNAL_ERROR);
        }
        return locationResponse.getLocation();
    }
}
