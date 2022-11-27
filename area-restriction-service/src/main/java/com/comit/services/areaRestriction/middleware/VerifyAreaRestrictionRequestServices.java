package com.comit.services.areaRestriction.middleware;

import com.comit.services.areaRestriction.controller.request.AreaRestrictionRequest;

public interface VerifyAreaRestrictionRequestServices {
    void verifyAddOrUpdateAreaRestrictionRequest(AreaRestrictionRequest request);
}
