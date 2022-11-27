package com.comit.services.timeKeeping.business;

import com.comit.services.timeKeeping.client.data.LocationDtoClient;
import com.comit.services.timeKeeping.constant.Const;
import com.comit.services.timeKeeping.constant.ShiftErrorCode;
import com.comit.services.timeKeeping.constant.TimeKeepingErrorCode;
import com.comit.services.timeKeeping.controller.request.ShiftRequest;
import com.comit.services.timeKeeping.exception.TimeKeepingCommonException;
import com.comit.services.timeKeeping.middleware.ShiftVerifyRequestServices;
import com.comit.services.timeKeeping.model.dto.ShiftDto;
import com.comit.services.timeKeeping.model.entity.Shift;
import com.comit.services.timeKeeping.service.ShiftServices;
import com.comit.services.timeKeeping.service.TimeKeepingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ShiftBusinessImpl implements ShiftBusiness {
    @Autowired
    private ShiftVerifyRequestServices shiftVerifyRequestServices;
    @Autowired
    private ShiftServices shiftServices;
    @Autowired
    private TimeKeepingServices timeKeepingServices;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Value("${app.internalToken}")
    private String internalToken;

    @Override
    public List<ShiftDto> getAllShift() {
        LocationDtoClient locationDtoClient = timeKeepingServices.getLocationOfCurrentUser();
        List<ShiftDto> shiftDtos = new ArrayList<>();
        if (locationDtoClient != null) {
            List<Shift> shifts = shiftServices.getAllShift(locationDtoClient.getId());

            shifts.forEach(shift -> {
                shiftDtos.add(ShiftDto.convertShiftToShiftDto(shift));
            });
        }
        return shiftDtos;
    }

    @Override
    public ShiftDto updateShift(int id, ShiftRequest request) {
        shiftVerifyRequestServices.verifyAddOrUpdateShiftRequest(request);

        LocationDtoClient locationDtoClient = timeKeepingServices.getLocationOfCurrentUser();
        Shift shift = shiftServices.getShift(locationDtoClient.getId(), id);
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
        if (!isInternalFeature()) throw new TimeKeepingCommonException(TimeKeepingErrorCode.PERMISSION_DENIED);
        Shift shift = shiftServices.getShift(id);
        return ShiftDto.convertShiftToShiftDto(shift);
    }

    public boolean isInternalFeature() {
        return Objects.equals(httpServletRequest.getHeader("token"), internalToken);
    }
}
