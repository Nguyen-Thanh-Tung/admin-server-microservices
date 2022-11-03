package com.comit.services.timeKeeping.middleware;

import com.comit.services.timeKeeping.constant.ShiftErrorCode;
import com.comit.services.timeKeeping.controller.request.ShiftRequest;
import com.comit.services.timeKeeping.exception.TimeKeepingCommonException;
import com.comit.services.timeKeeping.util.ValidateField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiftVerifyRequestServicesImpl implements ShiftVerifyRequestServices {
    @Autowired
    private ValidateField validateField;

    @Override
    public void verifyAddOrUpdateShiftRequest(ShiftRequest request) {
        String name = request.getName();
        String timeStart = request.getTimeStart();
        String timeEnd = request.getTimeEnd();
        if (name == null || name.trim().isEmpty()) {
            throw new TimeKeepingCommonException(ShiftErrorCode.MISSING_NAME_FIELD);
        }
        if (timeStart == null || timeStart.trim().isEmpty()) {
            throw new TimeKeepingCommonException(ShiftErrorCode.MISSING_TIME_START_FIELD);
        }
        if (timeEnd == null || timeEnd.trim().isEmpty()) {
            throw new TimeKeepingCommonException(ShiftErrorCode.MISSING_TIME_END_FIELD);
        }
        if (!validateField.validTime(timeStart)) {
            throw new TimeKeepingCommonException(ShiftErrorCode.TIME_START_IS_INVALID);
        }
        if (!validateField.validTime(timeEnd)) {
            throw new TimeKeepingCommonException(ShiftErrorCode.TIME_END_IS_INVALID);
        }
    }
}
