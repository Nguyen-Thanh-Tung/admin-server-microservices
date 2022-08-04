package com.comit.services.timeKeeping.service;

import com.comit.services.timeKeeping.client.data.LocationDto;

public interface TimeKeepingServices {
    LocationDto getLocationOfCurrentUser();
}
