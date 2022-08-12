package com.comit.services.timeKeeping.server.service;

import com.comit.services.location.client.dto.LocationDto;

public interface TimeKeepingServices {
    LocationDto getLocationOfCurrentUser();
}
