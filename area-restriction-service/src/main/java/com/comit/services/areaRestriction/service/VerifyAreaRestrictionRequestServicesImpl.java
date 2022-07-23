package com.comit.services.areaRestriction.service;

import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionRequest;
import com.comit.services.areaRestriction.exception.AreaRestrictionCommonException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerifyAreaRestrictionRequestServicesImpl implements VerifyAreaRestrictionRequestServices {

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
    }
}
