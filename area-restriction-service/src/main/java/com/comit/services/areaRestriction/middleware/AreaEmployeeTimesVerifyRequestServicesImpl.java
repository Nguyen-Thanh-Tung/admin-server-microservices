package com.comit.services.areaRestriction.middleware;

import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.controller.request.AreaEmployeeTimeListRequest;
import com.comit.services.areaRestriction.exception.AreaRestrictionCommonException;
import com.comit.services.areaRestriction.util.ValidateField;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaEmployeeTimesVerifyRequestServicesImpl implements AreaEmployeeTimesVerifyRequestServices {
    @Autowired
    private ValidateField validateField;

    @Override
    public void verifyAddAreaEmployeeTimes(AreaEmployeeTimeListRequest request) {
        JsonArray jsonArray = new JsonParser().parse(request.getAreaEmployees()).getAsJsonArray();
        Integer employeeId = request.getEmployeeId();
        if (employeeId == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.MISSING_EMPLOYEE_ID_FIELD);
        }
        if (employeeId <= 0) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.EMPLOYEE_ID_IN_VALID);
        }
        jsonArray.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("area_restriction_id") && jsonObject.has("time_start") && jsonObject.has("time_end")) {
                Integer areaRestrictionId = jsonObject.get("area_restriction_id").getAsInt();
                String timeStart = jsonObject.get("time_start").getAsString();
                String timeEnd = jsonObject.get("time_end").getAsString();

                if (areaRestrictionId == null || timeStart == null || timeStart.trim().isEmpty() || timeEnd == null || timeEnd.trim().isEmpty()
                        || areaRestrictionId <= 0 || !validateField.validTimeStamp(timeStart) || !validateField.validTimeStamp(timeEnd)) {
                    throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_EMPLOYEE_TIMES_IN_VALID);
                }
            }
        });
    }
}