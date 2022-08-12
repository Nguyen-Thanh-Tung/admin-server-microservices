package com.comit.services.areaRestriction.server.business;

import com.comit.services.areaRestriction.client.dto.AreaEmployeeTimeDto;
import com.comit.services.areaRestriction.client.request.AreaEmployeeTimeListRequest;
import com.comit.services.areaRestriction.server.model.AreaEmployeeTime;
import com.comit.services.areaRestriction.server.model.AreaRestriction;
import com.comit.services.areaRestriction.server.service.AreaEmployeeTimeService;
import com.comit.services.areaRestriction.server.service.AreaRestrictionServices;
import com.comit.services.employee.client.dto.EmployeeDto;
import com.comit.services.location.client.dto.LocationDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.modelmapper.ModelMapper;
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
    public List<AreaEmployeeTimeDto> saveAreaEmployeeTimeList(AreaEmployeeTimeListRequest areaEmployeeTimeListRequest) {
        LocationDto locationDto = areaRestrictionServices.getLocationOfCurrentUser();
        JsonArray jsonArray = new JsonParser().parse(areaEmployeeTimeListRequest.getAreaEmployees()).getAsJsonArray();
        List<AreaEmployeeTime> areaEmployeeTimes = new ArrayList<>();
        jsonArray.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("area_restriction_id") && jsonObject.has("time_start") && jsonObject.has("time_end")) {
                Integer areaRestrictionId = jsonObject.get("area_restriction_id").getAsInt();
                String timeStart = jsonObject.get("time_start").getAsString();
                String timeEnd = jsonObject.get("time_end").getAsString();
                AreaRestriction areaRestriction = areaRestrictionServices.getAreaRestriction(locationDto.getId(), areaRestrictionId);
                if (areaRestriction != null) {
                    AreaEmployeeTime areaEmployeeTime = new AreaEmployeeTime();
                    areaEmployeeTime.setAreaRestriction(areaRestriction);
                    areaEmployeeTime.setTimeStart(timeStart);
                    areaEmployeeTime.setTimeEnd(timeEnd);
                    areaEmployeeTime.setEmployeeId(areaEmployeeTimeListRequest.getEmployeeId());
                    areaEmployeeTimes.add(areaEmployeeTime);
                }
            }
        });
        List<AreaEmployeeTime> newAreaEmployeeTimes = areaEmployeeTimeService.saveAllAreaEmployeeTime(areaEmployeeTimes);
        List<AreaEmployeeTimeDto> areaEmployeeTimeDtos = new ArrayList<>();
        newAreaEmployeeTimes.forEach(areaEmployeeTime -> {
            areaEmployeeTimeDtos.add(convertAreaEmployeeTimeToAreaEmployeeTimeDto(areaEmployeeTime));
        });
        return areaEmployeeTimeDtos;
    }

    @Override
    public List<AreaEmployeeTimeDto> getAreaEmployeeTimeListOfEmployee(Integer employeeId) {
        List<AreaEmployeeTime> areaEmployeeTimes = areaEmployeeTimeService.getAreaEmployeeTimeListOfEmployee(employeeId);
        List<AreaEmployeeTimeDto> areaEmployeeTimeDtos = new ArrayList<>();
        areaEmployeeTimes.forEach(areaEmployeeTime -> {
            areaEmployeeTimeDtos.add(convertAreaEmployeeTimeToAreaEmployeeTimeDto(areaEmployeeTime));
        });
        return areaEmployeeTimeDtos;
    }

    @Override
    public List<AreaEmployeeTimeDto> getAreaEmployeeTimeListOfAreaRestriction(Integer areaRestrictionId) {
        List<AreaEmployeeTime> areaEmployeeTimes = areaEmployeeTimeService.getAreaEmployeeTimeListOfAreaRestriction(areaRestrictionId);
        List<AreaEmployeeTimeDto> areaEmployeeTimeDtos = new ArrayList<>();
        areaEmployeeTimes.forEach(areaEmployeeTime -> {
            areaEmployeeTimeDtos.add(convertAreaEmployeeTimeToAreaEmployeeTimeDto(areaEmployeeTime));
        });
        return areaEmployeeTimeDtos;
    }

    @Override
    public boolean deleteEmployeeAreaRestrictionList(Integer employeeId) {
        return areaEmployeeTimeService.deleteAreaEmployeeTime(employeeId);
    }

    public AreaEmployeeTimeDto convertAreaEmployeeTimeToAreaEmployeeTimeDto(AreaEmployeeTime areaEmployeeTime) {
        if (areaEmployeeTime == null) return null;
        LocationDto locationDto = areaRestrictionServices.getLocationOfCurrentUser();
        try {
            ModelMapper modelMapper = new ModelMapper();
            AreaEmployeeTimeDto areaEmployeeTimeDto = modelMapper.map(areaEmployeeTime, AreaEmployeeTimeDto.class);
            EmployeeDto employeeDto = areaRestrictionServices.getEmployee(areaEmployeeTime.getEmployeeId(), locationDto.getId());
            areaEmployeeTimeDto.setEmployee(employeeDto);
            return areaEmployeeTimeDto;
        } catch (Exception e) {
            return null;
        }
    }
}
