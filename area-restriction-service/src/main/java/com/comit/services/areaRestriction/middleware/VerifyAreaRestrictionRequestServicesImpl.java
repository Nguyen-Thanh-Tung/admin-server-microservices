package com.comit.services.areaRestriction.middleware;

import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionRequest;
import com.comit.services.areaRestriction.exception.AreaRestrictionCommonException;
import com.comit.services.areaRestriction.util.ValidateField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerifyAreaRestrictionRequestServicesImpl implements VerifyAreaRestrictionRequestServices {
    @Autowired
    private ValidateField validateField;

    @Override
    public void verifyAddOrUpdateAreaRestrictionRequest(AreaRestrictionRequest request) {
        String name = request.getName();
        String code = request.getCode();
        List<Integer> managerIds = request.getManagerIds();
        String timeStart = request.getTimeStart();
        String timeEnd = request.getTimeEnd();

        if (name == null || name.trim().isEmpty()) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.MISSING_AREA_RESTRICTION_NAME_FIELD);
        }

        if (code == null || code.trim().isEmpty()) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.MISSING_AREA_RESTRICTION_CODE_FIELD);
        }

        if (managerIds == null || managerIds.size() == 0) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.MISSING_AREA_RESTRICTION_MANAGER_IDS_FIELD);
        }

        if (timeStart == null || timeStart.trim().isEmpty()) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.MISSING_AREA_RESTRICTION_TIME_START_FIELD);
        }

        if (timeEnd == null || timeEnd.trim().isEmpty()) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.MISSING_AREA_RESTRICTION_TIME_END_FIELD);
        }

        if (!validateField.validTimeStamp(timeStart)) {
            throw new AreaRestrictionCommonException((AreaRestrictionErrorCode.AREA_RESTRICTION_TIME_START_IN_VALID));
        }
        if (!validateField.validTimeStamp(timeEnd)) {
            throw new AreaRestrictionCommonException((AreaRestrictionErrorCode.AREA_RESTRICTION_TIME_END_IN_VALID));
        }
    }
}
