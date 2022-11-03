package com.comit.services.timeKeeping.service;

import com.comit.services.timeKeeping.client.data.LocationDtoClient;

public interface TimeKeepingServices {
    LocationDtoClient getLocationOfCurrentUser();
}
