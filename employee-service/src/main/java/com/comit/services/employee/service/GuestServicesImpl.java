package com.comit.services.employee.service;

import com.comit.services.employee.model.entity.Guest;
import com.comit.services.employee.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestServicesImpl implements GuestServices {
    @Autowired
    private GuestRepository guestRepository;

    @Override
    public Guest addGuest(Guest guest) {
        return guestRepository.save(guest);
    }
}
