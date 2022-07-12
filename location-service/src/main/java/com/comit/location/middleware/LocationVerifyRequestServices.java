package com.comit.location.middleware;

import com.comit.location.controller.request.LocationRequest;

public interface LocationVerifyRequestServices {
    void verifyAddOrUpdateLocationRequest(LocationRequest request);
}
