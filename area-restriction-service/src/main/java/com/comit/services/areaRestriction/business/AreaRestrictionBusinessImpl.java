package com.comit.services.areaRestriction.business;

import com.comit.services.areaRestriction.client.data.EmployeeDtoClient;
import com.comit.services.areaRestriction.client.data.LocationDtoClient;
import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionNotificationRequest;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionRequest;
import com.comit.services.areaRestriction.exception.AreaRestrictionCommonException;
import com.comit.services.areaRestriction.model.dto.*;
import com.comit.services.areaRestriction.model.entity.AreaRestriction;
import com.comit.services.areaRestriction.model.entity.AreaRestrictionManagerNotification;
import com.comit.services.areaRestriction.model.entity.NotificationMethod;
import com.comit.services.areaRestriction.service.AreaEmployeeTimeService;
import com.comit.services.areaRestriction.service.AreaRestrictionServices;
import com.comit.services.areaRestriction.service.NotificationMethodServices;
import com.comit.services.areaRestriction.service.VerifyAreaRestrictionRequestServices;
import com.comit.services.areaRestriction.util.TimeUtil;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AreaRestrictionBusinessImpl implements AreaRestrictionBusiness {
    @Autowired
    private AreaRestrictionServices areaRestrictionService;
    @Autowired
    private NotificationMethodServices notificationMethodServices;

    @Autowired
    private VerifyAreaRestrictionRequestServices verifyAreaRestrictionRequestServices;

    @Autowired
    private AreaRestrictionManagerNotificationBusiness areaRestrictionManagerNotificationBusiness;
    @Autowired
    private AreaEmployeeTimeService areaEmployeeTimeService;

    @Override
    public Page<AreaRestriction> getAreaRestrictionPage(int page, int size, String search) {
        Pageable paging = PageRequest.of(page, size);
        LocationDtoClient locationDtoClient = areaRestrictionService.getLocationOfCurrentUser();
        return areaRestrictionService.getAreaRestrictionPage(locationDtoClient.getId(), search, paging);
    }

    @Override
    public List<AreaRestrictionDto> getAllAreaRestriction(List<AreaRestriction> areaRestrictions) {
        List<AreaRestrictionDto> areaRestrictionDtos = new ArrayList<>();
        areaRestrictions.forEach(areaRestriction -> {
            AreaRestrictionDto areaRestrictionDto = convertAreaRestrictionToAreaRestrictionDto(areaRestriction);
            areaRestrictionDtos.add(areaRestrictionDto);
        });
        return areaRestrictionDtos;
    }

    @Override
    public AreaRestrictionDto addAreaRestriction(AreaRestrictionRequest request) {
        verifyAreaRestrictionRequestServices.verifyAddOrUpdateAreaRestrictionRequest(request);
        LocationDtoClient locationDtoClient = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(locationDtoClient.getId(), request.getName());

        if (areaRestriction != null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_IS_EXISTED);
        }

        areaRestriction = new AreaRestriction();
        areaRestriction.setName(request.getName());
        areaRestriction.setCode(request.getCode());

        List<Integer> managerIds = request.getManagerIds();

        areaRestriction.setManagerIds(managerIds.stream().map(String::valueOf)
                .collect(Collectors.joining(",")));
        areaRestriction.setLocationId(locationDtoClient.getId());
        areaRestriction.setTimeStart(request.getTimeStart());
        areaRestriction.setTimeEnd(request.getTimeEnd());
        AreaRestriction newAreaRestriction = areaRestrictionService.updateAreaRestriction(areaRestriction);

        //Add notification method for area restriction
        NotificationMethod notificationMethod = new NotificationMethod();
        notificationMethod.setUseEmail(true);
        notificationMethod.setUseScreen(true);
        notificationMethod.setUseRing(true);
        notificationMethod.setAreaRestrictionId(areaRestriction.getId());
        NotificationMethod newNotificationMethod = notificationMethodServices.saveNotificationMethod(notificationMethod);

        return convertAreaRestrictionToAreaRestrictionDto(newAreaRestriction);
    }

    @Override
    public AreaRestrictionDto updateAreaRestriction(Integer id, AreaRestrictionRequest request) {
        verifyAreaRestrictionRequestServices.verifyAddOrUpdateAreaRestrictionRequest(request);
        LocationDtoClient locationDtoClient = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(locationDtoClient.getId(), id);
        if (areaRestriction == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_NOT_EXIST);
        }

        areaRestriction.setName(request.getName());
        areaRestriction.setCode(request.getCode());

        List<Integer> managerIds = request.getManagerIds();

        areaRestriction.setManagerIds(managerIds.stream().map(String::valueOf)
                .collect(Collectors.joining(",")));
        areaRestriction.setLocationId(locationDtoClient.getId());
        areaRestriction.setTimeStart(request.getTimeStart());
        areaRestriction.setTimeEnd(request.getTimeEnd());
        AreaRestriction newAreaRestriction = areaRestrictionService.updateAreaRestriction(areaRestriction);
        return convertAreaRestrictionToAreaRestrictionDto(newAreaRestriction);
    }

    @Override
    public boolean deleteAreaRestriction(Integer id) {
        LocationDtoClient locationDtoClient = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(locationDtoClient.getId(), id);
        if (areaRestriction == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_NOT_EXIST);
        }
        return areaRestrictionService.deleteAreaRestriction(areaRestriction);
    }

    @Override
    public AreaRestrictionDto updateAreaRestrictionNotification(Integer areaRestrictionId, AreaRestrictionNotificationRequest request) {
        LocationDtoClient locationDtoClient = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(locationDtoClient.getId(), areaRestrictionId);
        if (areaRestriction == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_NOT_EXIST);
        }

        NotificationMethod notificationMethod = notificationMethodServices.getNotificationMethodOfAreaRestriction(areaRestriction.getId());
        notificationMethod.setUseRing(request.getUseRing());
        notificationMethod.setUseEmail(request.getUseEmail());
        notificationMethod.setUseScreen(request.getUseScreen());
        notificationMethod.setUseOTT(request.getUseOTT());
        notificationMethod.setAreaRestrictionId(areaRestrictionId);
        NotificationMethod newNotificationMethod = notificationMethodServices.saveNotificationMethod(notificationMethod);

        if (request.getManagerTimeSkips() != null) {
            areaRestrictionManagerNotificationBusiness.deleteAreaRestrictionManagerNotificationList(areaRestrictionId);
            if (!request.getManagerTimeSkips().isEmpty()) {
                areaRestrictionManagerNotificationBusiness.saveAreaManagerTimeList(request.getManagerTimeSkips(), areaRestrictionId);
            }
        }
        return convertAreaRestrictionToAreaRestrictionDto(areaRestriction);
    }

    @Override
    public NotificationMethodDto getNotificationMethodOfAreaRestriction(Integer id) {
        NotificationMethod notificationMethod = notificationMethodServices.getNotificationMethodOfAreaRestriction(id);
        return NotificationMethodDto.convertNotificationMethodToNotificationMethodDto(notificationMethod);
    }

    @Override
    public AreaRestrictionDto getAreaRestriction(Integer id) {
        LocationDtoClient locationDtoClient = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(locationDtoClient.getId(), id);
        if (areaRestriction != null) {
            return convertAreaRestrictionToAreaRestrictionDto(areaRestriction);
        }
        return null;
    }

    @Override
    public BaseAreaRestrictionDto getAreaRestrictionBase(Integer id) {
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(id);
        if (areaRestriction != null) {
            return convertAreaRestrictionToBaseAreaRestrictionDto(areaRestriction);
        }
        return null;
    }

    @Override
    public AreaRestrictionNotificationDto getAreaRestrictionNotification(Integer areaRestrictionId) {
        NotificationMethod notificationMethod = notificationMethodServices.getNotificationMethodOfAreaRestriction(areaRestrictionId);
        List<AreaRestrictionManagerNotification> areaRestrictionManagerNotifications = areaRestrictionManagerNotificationBusiness.getAreaManagerTimeList(areaRestrictionId);
        AreaRestrictionNotificationDto areaRestrictionNotificationDto = new AreaRestrictionNotificationDto();
        List<AreaRestrictionManagerNotificationDto> areaRestrictionManagerNotificationDtos = new ArrayList<>();
        for (AreaRestrictionManagerNotification areaRestrictionManagerNotification : areaRestrictionManagerNotifications) {
            EmployeeDtoClient employeeDtoClient = areaRestrictionService.getEmployee(areaRestrictionManagerNotification.getManagerId());
            AreaRestrictionManagerNotificationDto areaRestrictionManagerNotificationDto = new AreaRestrictionManagerNotificationDto();
            areaRestrictionManagerNotificationDto.setManager(convertEmployeeDtoFromClient(employeeDtoClient));
            areaRestrictionManagerNotificationDto.setTimeSkip(areaRestrictionManagerNotification.getTimeSkip());
            areaRestrictionManagerNotificationDtos.add(areaRestrictionManagerNotificationDto);
        }
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(areaRestrictionId);
        areaRestrictionNotificationDto.setAreaRestrictionDto(convertAreaRestrictionToBaseAreaRestrictionDto(areaRestriction));
        areaRestrictionNotificationDto.setAreaRestrictionManagerNotifications(areaRestrictionManagerNotificationDtos);
        areaRestrictionNotificationDto.setNotificationMethod(NotificationMethodDto.convertNotificationMethodToNotificationMethodDto(notificationMethod));
        return areaRestrictionNotificationDto;
    }

    @Override
    public boolean deleteManagerOnAllAreaRestriction(Integer managerId) {
        List<AreaRestriction> areaRestrictions = areaRestrictionService.getAllAreaRestrictionOfManager(managerId);
        areaRestrictions.forEach(areaRestriction -> {
            String[] tmp = areaRestriction.getManagerIds().split(",");
            StringBuilder result = new StringBuilder();
            for (String s : tmp) {
                if (!Objects.equals(s, managerId.toString())) {
                    result.append(s).append(",");
                }
            }
            if (result.length() > 0) {
                areaRestriction.setManagerIds(result.substring(0, result.length() - 1));
                areaRestrictionService.updateAreaRestriction(areaRestriction);
            }
        });

        return true;
    }

    public BaseAreaRestrictionDto convertAreaRestrictionToBaseAreaRestrictionDto(AreaRestriction areaRestriction) {
        if (areaRestriction == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(areaRestriction, BaseAreaRestrictionDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    public AreaRestrictionDto convertAreaRestrictionToAreaRestrictionDto(AreaRestriction areaRestriction) {
        if (areaRestriction == null) return null;
        Date startDay = TimeUtil.getDateTimeFromTimeString("00:00:00");
        DateTime now = new DateTime(TimeUtil.asiaHoChiMinh);
        ModelMapper modelMapper = new ModelMapper();
        AreaRestrictionDto areaRestrictionDto = modelMapper.map(areaRestriction, AreaRestrictionDto.class);
        int numberCamera = areaRestrictionService.getNumberCameraOfAreaRestriction(areaRestriction.getId());
        areaRestrictionDto.setNumberCamera(numberCamera);
        int numberAreaEmployeeTime = areaEmployeeTimeService.getNumberAreaEmployeeTimeOfAreaRestriction(areaRestriction.getId());
        areaRestrictionDto.setNumberEmployeeAllow(numberAreaEmployeeTime);
        int numberHistories = areaRestrictionService.getNumberNotificationOfAreaRestriction(areaRestriction, startDay, now.toDate());
        areaRestrictionDto.setNumberNotification(numberHistories);
        LocationDtoClient locationDtoClient = areaRestrictionService.getLocationOfCurrentUser();
        areaRestrictionDto.setLocation(convertLocationDtoFromClient(locationDtoClient));
        // Set manager for area restriction
        if (areaRestriction.getManagerIds() != null) {
            List<EmployeeDto> employeeDtos = new ArrayList<>();
            String[] tmp = areaRestriction.getManagerIds().split(",");
            for (String managerIdStr : tmp) {
                EmployeeDtoClient employeeDtoClient = areaRestrictionService.getEmployee(Integer.parseInt(managerIdStr));
                if (employeeDtoClient != null) {
                    employeeDtos.add(convertEmployeeDtoFromClient(employeeDtoClient));
                }
            }
            areaRestrictionDto.setManagers(employeeDtos);
        }
        return areaRestrictionDto;
    }

    @Override
    public EmployeeDto convertEmployeeDtoFromClient(EmployeeDtoClient employeeDtoClient) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeDtoClient.getId());
        employeeDto.setCode(employeeDtoClient.getCode());
        employeeDto.setEmail(employeeDtoClient.getEmail());
        employeeDto.setName(employeeDtoClient.getName());
        employeeDto.setEmbeddingId(employeeDtoClient.getEmbeddingId());
        employeeDto.setPhone(employeeDtoClient.getPhone());
        employeeDto.setLocationId(employeeDtoClient.getLocationId());
        employeeDto.setStatus(employeeDtoClient.getStatus());
        return employeeDto;
    }

    public LocationDto convertLocationDtoFromClient(LocationDtoClient locationDtoClient) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(locationDtoClient.getId());
        locationDto.setName(locationDtoClient.getName());
        locationDto.setCode(locationDtoClient.getCode());
        locationDto.setType(locationDtoClient.getType());
        locationDto.setOrganizationId(locationDtoClient.getOrganizationId());
        return locationDto;
    }
}
