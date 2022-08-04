package com.comit.services.timeKeeping.service;

import com.comit.services.timeKeeping.client.AccountClient;
import com.comit.services.timeKeeping.client.data.LocationDto;
import com.comit.services.timeKeeping.client.response.LocationResponse;
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
    private HttpServletRequest httpServletRequest;

    @Override
    public LocationDto getLocationOfCurrentUser() {
        LocationResponse locationResponse = accountClient.getLocationOfCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (locationResponse == null) {
            throw new TimeKeepingCommonException(TimeKeepingErrorCode.INTERNAL_ERROR);
        }
        return locationResponse.getLocationDto();
    }
}
