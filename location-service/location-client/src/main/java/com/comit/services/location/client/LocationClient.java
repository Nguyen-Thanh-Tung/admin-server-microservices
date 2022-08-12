package com.comit.services.location.client;

import com.comit.services.location.client.response.LocationListResponse;
import com.comit.services.location.client.response.LocationResponse;

public interface LocationClient {
    LocationListResponse getLocationsByOrganizationId(String token, int organizationId);

    LocationResponse getLocationById(String token, Integer locationId);
}
