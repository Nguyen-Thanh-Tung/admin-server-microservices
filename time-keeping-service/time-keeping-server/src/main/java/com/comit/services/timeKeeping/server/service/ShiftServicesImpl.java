package com.comit.services.timeKeeping.server.service;

import com.comit.services.timeKeeping.server.model.Shift;
import com.comit.services.timeKeeping.server.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftServicesImpl implements ShiftServices {
    @Autowired
    private ShiftRepository shiftRepository;

    @Override
    public Shift getShift(Integer locationId, int shiftId) {
        return shiftRepository.findByIdAndLocationId(shiftId, locationId);
    }

    @Override
    public Shift getShift(int shiftId) {
        return shiftRepository.findById(shiftId);
    }

    @Override
    public List<Shift> getAllShift(Integer locationId) {
        if (locationId == null) {
            return null;
        }
        return shiftRepository.findShiftsByLocationId(locationId);
    }

    @Override
    public Shift saveShift(Shift shift) {
        return shiftRepository.save(shift);
    }

    @Override
    public boolean deleteShift(int id) {
        try {
            shiftRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void saveAllShift(List<Shift> shifts) {
        shiftRepository.saveAll(shifts);
    }
}
