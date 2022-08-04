package com.comit.services.timeKeeping.business;

import com.comit.services.timeKeeping.client.data.LocationDto;
import com.comit.services.timeKeeping.constant.Const;
import com.comit.services.timeKeeping.constant.ShiftErrorCode;
import com.comit.services.timeKeeping.controller.request.ShiftRequest;
import com.comit.services.timeKeeping.exception.TimeKeepingCommonException;
import com.comit.services.timeKeeping.middleware.ShiftVerifyRequestServices;
import com.comit.services.timeKeeping.model.dto.ShiftDto;
import com.comit.services.timeKeeping.model.entity.Shift;
import com.comit.services.timeKeeping.service.ShiftServices;
import com.comit.services.timeKeeping.service.TimeKeepingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShiftBusinessImpl implements ShiftBusiness {
    @Autowired
    private ShiftVerifyRequestServices shiftVerifyRequestServices;
    @Autowired
    private ShiftServices shiftServices;
    @Autowired
    private TimeKeepingServices timeKeepingServices;

    @Override
    public List<ShiftDto> getAllShift() {
        LocationDto locationDto = timeKeepingServices.getLocationOfCurrentUser();
        List<ShiftDto> shiftDtos = new ArrayList<>();
        if (locationDto != null) {
            List<Shift> shifts = shiftServices.getAllShift(locationDto.getId());

            shifts.forEach(shift -> {
                shiftDtos.add(ShiftDto.convertShiftToShiftDto(shift));
            });
        }
        return shiftDtos;
    }

    @Override
    public ShiftDto updateShift(int id, ShiftRequest request) {
        shiftVerifyRequestServices.verifyAddOrUpdateShiftRequest(request);

        LocationDto locationDto = timeKeepingServices.getLocationOfCurrentUser();
        Shift shift = shiftServices.getShift(locationDto.getId(), id);
        if (shift == null) {
            throw new TimeKeepingCommonException(ShiftErrorCode.SHIFT_NOT_EXIST);
        }

        shift.setName(request.getName());
        shift.setTimeStart(request.getTimeStart());
        shift.setTimeEnd(request.getTimeEnd());
        Shift newShift = shiftServices.saveShift(shift);
        return ShiftDto.convertShiftToShiftDto(newShift);
    }

    @Override
    public boolean addShiftsForLocation(Integer locationId) {
        //Add shift for location (time keeping module)
        Const.SHIFTS.forEach(shift -> {
            shift.setLocationId(locationId);
            shiftServices.saveShift(shift);
        });
        return true;
    }

    @Override
    public boolean deleteShiftsOfLocation(Integer locationId) {
        List<Shift> shifts = shiftServices.getAllShift(locationId);
        shifts.forEach(shift -> {
            shiftServices.deleteShift(shift.getId());
        });
        return true;
    }

    @Override
    public ShiftDto getShift(Integer id) {
        Shift shift = shiftServices.getShift(id);
        return ShiftDto.convertShiftToShiftDto(shift);
    }
}
