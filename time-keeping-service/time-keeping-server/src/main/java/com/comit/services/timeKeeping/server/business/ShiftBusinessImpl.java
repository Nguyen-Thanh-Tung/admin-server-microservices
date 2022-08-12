package com.comit.services.timeKeeping.server.business;

import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.timeKeeping.client.dto.ShiftDto;
import com.comit.services.timeKeeping.client.request.ShiftRequest;
import com.comit.services.timeKeeping.server.constant.Const;
import com.comit.services.timeKeeping.server.constant.ShiftErrorCode;
import com.comit.services.timeKeeping.server.exception.TimeKeepingCommonException;
import com.comit.services.timeKeeping.server.middleware.ShiftVerifyRequestServices;
import com.comit.services.timeKeeping.server.model.Shift;
import com.comit.services.timeKeeping.server.service.ShiftServices;
import com.comit.services.timeKeeping.server.service.TimeKeepingServices;
import org.modelmapper.ModelMapper;
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
                shiftDtos.add(convertShiftToShiftDto(shift));
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
        return convertShiftToShiftDto(newShift);
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
        return convertShiftToShiftDto(shift);
    }

    private ShiftDto convertShiftToShiftDto(Shift newShift) {
        if (newShift == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(newShift, ShiftDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}