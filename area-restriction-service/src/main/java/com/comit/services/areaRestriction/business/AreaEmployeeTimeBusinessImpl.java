package com.comit.services.areaRestriction.business;

import com.comit.services.areaRestriction.client.data.EmployeeDtoClient;
import com.comit.services.areaRestriction.client.data.LocationDtoClient;
import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.constant.Const;
import com.comit.services.areaRestriction.controller.request.AreaEmployeeTimeListRequest;
import com.comit.services.areaRestriction.exception.AreaRestrictionCommonException;
import com.comit.services.areaRestriction.middleware.AreaEmployeeTimesVerifyRequestServices;
import com.comit.services.areaRestriction.model.dto.AreaEmployeeTimeDto;
import com.comit.services.areaRestriction.model.dto.EmployeeDto;
import com.comit.services.areaRestriction.model.entity.AreaEmployeeTime;
import com.comit.services.areaRestriction.model.entity.AreaRestriction;
import com.comit.services.areaRestriction.service.AreaEmployeeTimeService;
import com.comit.services.areaRestriction.service.AreaRestrictionServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AreaEmployeeTimeBusinessImpl implements AreaEmployeeTimeBusiness {

    @Autowired
    private AreaRestrictionBusiness areaRestrictionBusiness;
    @Autowired
    private AreaRestrictionServices areaRestrictionServices;
    @Autowired
    private AreaEmployeeTimeService areaEmployeeTimeService;
    @Autowired
    private AreaEmployeeTimesVerifyRequestServices areaEmployeeTimesVerifyRequestServices;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Value("${app.internalToken}")
    private String internalToken;

    @Override
    public List<AreaEmployeeTimeDto> saveAreaEmployeeTimeList(AreaEmployeeTimeListRequest areaEmployeeTimeListRequest) {
        if (!isInternalFeature()) throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.PERMISSION_DENIED);
        areaEmployeeTimesVerifyRequestServices.verifyAddAreaEmployeeTimes(areaEmployeeTimeListRequest);
        JsonArray jsonArray = new JsonParser().parse(areaEmployeeTimeListRequest.getAreaEmployees()).getAsJsonArray();
        List<AreaEmployeeTime> areaEmployeeTimes = new ArrayList<>();
        jsonArray.forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("area_restriction_id") && jsonObject.has("time_start") && jsonObject.has("time_end")) {
                int areaRestrictionId = jsonObject.get("area_restriction_id").getAsInt();
                String timeStart = jsonObject.get("time_start").getAsString();
                String timeEnd = jsonObject.get("time_end").getAsString();
                AreaRestriction areaRestriction = areaRestrictionServices.getAreaRestriction(areaRestrictionId);
                if (areaRestriction == null) {
                    throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_NOT_EXIST);
                }
                AreaEmployeeTime areaEmployeeTime = new AreaEmployeeTime();
                areaEmployeeTime.setAreaRestriction(areaRestriction);
                areaEmployeeTime.setTimeStart(timeStart);
                areaEmployeeTime.setTimeEnd(timeEnd);
                EmployeeDtoClient employee = areaRestrictionServices.getEmployee(areaEmployeeTimeListRequest.getEmployeeId());
                if (employee == null || employee.getStatus().equals(Const.DELETE)) {
                    throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.EMPLOYEE_NOT_EXIST);
                }
                areaEmployeeTime.setEmployeeId(areaEmployeeTimeListRequest.getEmployeeId());
                areaEmployeeTimes.add(areaEmployeeTime);
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
        if (!isInternalFeature()) throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.PERMISSION_DENIED);
        List<AreaEmployeeTime> areaEmployeeTimes = areaEmployeeTimeService.getAreaEmployeeTimeListOfEmployee(employeeId);
        List<AreaEmployeeTimeDto> areaEmployeeTimeDtos = new ArrayList<>();
        areaEmployeeTimes.forEach(areaEmployeeTime -> {
            areaEmployeeTimeDtos.add(convertAreaEmployeeTimeToAreaEmployeeTimeDto(areaEmployeeTime));
        });
        return areaEmployeeTimeDtos;
    }

    @Override
    public List<AreaEmployeeTimeDto> getAreaEmployeeTimeListOfAreaRestriction(Integer areaRestrictionId) {
        LocationDtoClient locationDtoClient = areaRestrictionServices.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionServices.getAreaRestriction(locationDtoClient.getId(), areaRestrictionId);
        if (areaRestriction == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_NOT_EXIST);
        }
        List<AreaEmployeeTime> areaEmployeeTimes = areaEmployeeTimeService.getAreaEmployeeTimeListOfAreaRestriction(areaRestrictionId);
        List<AreaEmployeeTimeDto> areaEmployeeTimeDtos = new ArrayList<>();
        areaEmployeeTimes.forEach(areaEmployeeTime -> {
            areaEmployeeTimeDtos.add(convertAreaEmployeeTimeToAreaEmployeeTimeDto(areaEmployeeTime));
        });
        return areaEmployeeTimeDtos;
    }

    @Override
    public boolean deleteEmployeeAreaRestrictionList(Integer employeeId) {
        if (!isInternalFeature()) throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.PERMISSION_DENIED);
        return areaEmployeeTimeService.deleteAreaEmployeeTime(employeeId);
    }

    public AreaEmployeeTimeDto convertAreaEmployeeTimeToAreaEmployeeTimeDto(AreaEmployeeTime areaEmployeeTime) {
        if (areaEmployeeTime == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            AreaEmployeeTimeDto areaEmployeeTimeDto = modelMapper.map(areaEmployeeTime, AreaEmployeeTimeDto.class);
            EmployeeDtoClient employeeDtoClient = areaRestrictionServices.getEmployee(areaEmployeeTime.getEmployeeId());
            EmployeeDto employeeDto = areaRestrictionBusiness.convertEmployeeDtoFromClient(employeeDtoClient);
            areaEmployeeTimeDto.setEmployee(employeeDto);
            return areaEmployeeTimeDto;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isInternalFeature() {
        return Objects.equals(httpServletRequest.getHeader("token"), internalToken);
    }
}
