package com.comit.services.timeKeeping.service;

import com.comit.services.timeKeeping.model.entity.Shift;

import java.util.List;

public interface ShiftServices {
    Shift getShift(Integer locationId, int shiftId);

    List<Shift> getAllShift(Integer locationId);

    Shift saveShift(Shift shift);

    boolean deleteShift(int id);

    void saveAllShift(List<Shift> shifts);
}
