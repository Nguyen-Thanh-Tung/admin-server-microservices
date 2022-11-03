package com.comit.services.employee.business;

import com.comit.services.employee.controller.request.GuestRequest;
import com.comit.services.employee.model.dto.GuestDto;

public interface GuestBusiness {
    GuestDto addGuest(GuestRequest request);
}
