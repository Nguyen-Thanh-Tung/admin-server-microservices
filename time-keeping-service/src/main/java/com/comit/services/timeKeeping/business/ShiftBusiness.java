package com.comit.services.timeKeeping.business;

import com.comit.services.timeKeeping.controller.request.ShiftRequest;
import com.comit.services.timeKeeping.model.dto.ShiftDto;

import java.util.List;

public interface ShiftBusiness {
    List<ShiftDto> getAllShift();

    ShiftDto updateShift(int id, ShiftRequest shiftRequest);

    boolean addShiftsForLocation(Integer locationId);

    boolean deleteShiftsOfLocation(Integer locationId);

    ShiftDto getShift(Integer id);
}
