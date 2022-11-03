package com.comit.services.employee.business;

import com.comit.services.employee.controller.request.GuestRequest;
import com.comit.services.employee.loging.model.CommonLogger;
import com.comit.services.employee.middleware.GuestVerifyRequestServices;
import com.comit.services.employee.model.dto.GuestDto;
import com.comit.services.employee.model.entity.Guest;
import com.comit.services.employee.service.GuestServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestBusinessImpl implements GuestBusiness {
    @Autowired
    GuestVerifyRequestServices guestVerifyRequestServices;
    @Autowired
    GuestServices guestServices;

    public GuestDto addGuest(GuestRequest request) {
        guestVerifyRequestServices.verifyAddGuestRequest(request);

        Guest guest = new Guest();
        guest.setName(request.getName());
        guest.setEmail(request.getEmail());
        guest.setPhone(request.getPhone());
        guest.setAreaRestrictionId(request.getAreaRestrictionId());
        guest.setLocationId(request.getLocationId());
        Guest newGuest = guestServices.addGuest(guest);
        return convertGuestToGuestDto(newGuest);
    }

    private GuestDto convertGuestToGuestDto(Guest guest) {
        if (guest == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(guest, GuestDto.class);
        } catch (Exception e) {
            CommonLogger.error("Error convert guest: " + e.getMessage());
            return null;
        }
    }
}
