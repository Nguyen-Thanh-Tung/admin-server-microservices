package com.comit.services.timeKeeping.service;

import com.comit.services.timeKeeping.model.entity.Location;

public interface TimeKeepingServices {
    Location getLocationOfCurrentUser();
}
