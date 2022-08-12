package com.comit.services.timeKeeping.server.business;

import com.comit.services.timeKeeping.client.dto.ShiftDto;
import com.comit.services.timeKeeping.client.request.ShiftRequest;

import java.util.List;

public interface ShiftBusiness {
    List<ShiftDto> getAllShift();

    ShiftDto updateShift(int id, ShiftRequest shiftRequest);

    boolean addShiftsForLocation(Integer locationId);

    boolean deleteShiftsOfLocation(Integer locationId);

    ShiftDto getShift(Integer id);
}
