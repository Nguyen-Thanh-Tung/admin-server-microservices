package com.comit.services.location.server.middleware;

import com.comit.services.location.client.request.LocationRequest;

public interface LocationVerifyRequestServices {
    void verifyAddOrUpdateLocationRequest(LocationRequest request);
}
