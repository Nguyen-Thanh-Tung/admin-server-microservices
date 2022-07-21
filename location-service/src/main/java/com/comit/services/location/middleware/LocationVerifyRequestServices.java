package com.comit.services.location.middleware;

import com.comit.services.location.controller.request.LocationRequest;

public interface LocationVerifyRequestServices {
    void verifyAddOrUpdateLocationRequest(LocationRequest request);
}
