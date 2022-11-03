package com.comit.services.employee.middleware;

import com.comit.services.employee.controller.request.GuestRequest;

public interface GuestVerifyRequestServices {
    void verifyAddGuestRequest(GuestRequest request);
}
