package com.comit.services.areaRestriction.business;

import com.comit.services.areaRestriction.model.entity.AreaEmployeeTime;
import com.comit.services.areaRestriction.model.entity.AreaRestriction;
import com.comit.services.areaRestriction.model.entity.Employee;
import com.comit.services.areaRestriction.model.entity.Location;
import com.comit.services.areaRestriction.service.AreaEmployeeTimeService;
import com.comit.services.areaRestriction.service.AreaRestrictionServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AreaEmployeeTimeBusinessImpl implements AreaEmployeeTimeBusiness {


    @Autowired
    private AreaRestrictionServices areaRestrictionServices;
    @Autowired
    private AreaEmployeeTimeService areaEmployeeTimeService;

    @Override
    public List<AreaEmployeeTime> saveEmployeeAreaRestrictionList(String employeeAreaRestrictionStr, Employee employee) {
        Location location = areaRestrictionServices.getLocationOfCurrentUser();
        JsonArray jsonArray = new JsonParser().parse(employeeAreaRestrictionStr).getAsJsonArray();
        List<AreaEmployeeTime> areaEmployeeTimes = new ArrayList<>();
        jsonArray.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("area_restriction_id") && jsonObject.has("time_start") && jsonObject.has("time_end")) {
                Integer areaRestrictionId = jsonObject.get("area_restriction_id").getAsInt();
                String timeStart = jsonObject.get("time_start").getAsString();
                String timeEnd = jsonObject.get("time_end").getAsString();
                AreaRestriction areaRestriction = areaRestrictionServices.getAreaRestriction(location.getId(), areaRestrictionId);
                if (areaRestriction != null) {
                    AreaEmployeeTime areaEmployeeTime = new AreaEmployeeTime();
                    areaEmployeeTime.setAreaRestriction(areaRestriction);
                    areaEmployeeTime.setTimeStart(timeStart);
                    areaEmployeeTime.setTimeEnd(timeEnd);
                    areaEmployeeTime.setEmployeeId(employee.getId());
                    areaEmployeeTimes.add(areaEmployeeTime);
                }
            }
        });
        return areaEmployeeTimeService.saveAllAreaEmployeeTime(areaEmployeeTimes);
    }

    @Override
    public boolean deleteEmployeeAreaRestrictionList(Employee employee) {
        return areaEmployeeTimeService.deleteAreaEmployeeTime(employee.getId());
    }
}
