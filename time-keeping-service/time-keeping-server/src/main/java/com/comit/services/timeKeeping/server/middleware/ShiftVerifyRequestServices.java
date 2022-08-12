package com.comit.services.timeKeeping.server.middleware;


import com.comit.services.timeKeeping.client.request.ShiftRequest;

public interface ShiftVerifyRequestServices {
    void verifyAddOrUpdateShiftRequest(ShiftRequest request);
}
