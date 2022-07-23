package com.comit.services.timeKeeping.middleware;

import com.comit.services.timeKeeping.controller.request.ShiftRequest;

public interface ShiftVerifyRequestServices {
    void verifyAddOrUpdateShiftRequest(ShiftRequest request);
}
