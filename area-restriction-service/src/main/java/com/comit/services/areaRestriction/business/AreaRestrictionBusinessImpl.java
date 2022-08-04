package com.comit.services.areaRestriction.business;

import com.comit.services.areaRestriction.client.data.EmployeeDto;
import com.comit.services.areaRestriction.client.data.LocationDto;
import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionNotificationRequest;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionRequest;
import com.comit.services.areaRestriction.exception.AreaRestrictionCommonException;
import com.comit.services.areaRestriction.model.dto.AreaRestrictionDto;
import com.comit.services.areaRestriction.model.dto.AreaRestrictionManagerNotificationDto;
import com.comit.services.areaRestriction.model.dto.AreaRestrictionNotificationDto;
import com.comit.services.areaRestriction.model.dto.NotificationMethodDto;
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
        LocationDto locationDto = areaRestrictionService.getLocationOfCurrentUser();
        return areaRestrictionService.getAreaRestrictionPage(locationDto.getId(), search, paging);
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
        LocationDto locationDto = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(locationDto.getId(), request.getName());

        if (areaRestriction != null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_IS_EXISTED);
        }

        areaRestriction = new AreaRestriction();
        areaRestriction.setName(request.getName());
        areaRestriction.setCode(request.getCode());

        List<Integer> managerIds = request.getManagerIds();

        areaRestriction.setManagerIds(managerIds.stream().map(String::valueOf)
                .collect(Collectors.joining(",")));
        areaRestriction.setLocationId(locationDto.getId());
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
        LocationDto locationDto = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(locationDto.getId(), id);
        if (areaRestriction == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_NOT_EXIST);
        }

        areaRestriction.setName(request.getName());
        areaRestriction.setCode(request.getCode());

        List<Integer> managerIds = request.getManagerIds();

        areaRestriction.setManagerIds(managerIds.stream().map(String::valueOf)
                .collect(Collectors.joining(",")));
        areaRestriction.setLocationId(locationDto.getId());
        areaRestriction.setTimeStart(request.getTimeStart());
        areaRestriction.setTimeEnd(request.getTimeEnd());
        AreaRestriction newAreaRestriction = areaRestrictionService.updateAreaRestriction(areaRestriction);
        return convertAreaRestrictionToAreaRestrictionDto(newAreaRestriction);
    }

    @Override
    public boolean deleteAreaRestriction(Integer id) {
        LocationDto locationDto = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(locationDto.getId(), id);
        if (areaRestriction == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_NOT_EXIST);
        }
        return areaRestrictionService.deleteAreaRestriction(areaRestriction);
    }

    @Override
    public AreaRestrictionDto updateAreaRestrictionNotification(Integer areaRestrictionId, AreaRestrictionNotificationRequest request) {
        LocationDto locationDto = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(locationDto.getId(), areaRestrictionId);
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
        LocationDto locationDto = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(locationDto.getId(), id);
        if (areaRestriction != null) {
            return convertAreaRestrictionToAreaRestrictionDto(areaRestriction);
        }
        return null;
    }

    @Override
    public AreaRestrictionNotificationDto getAreaRestrictionNotification(Integer areaRestrictionId) {
        LocationDto locationDto = areaRestrictionService.getLocationOfCurrentUser();
        NotificationMethod notificationMethod = notificationMethodServices.getNotificationMethodOfAreaRestriction(areaRestrictionId);
        List<AreaRestrictionManagerNotification> areaRestrictionManagerNotifications = areaRestrictionManagerNotificationBusiness.getAreaManagerTimeList(areaRestrictionId);
        AreaRestrictionNotificationDto areaRestrictionNotificationDto = new AreaRestrictionNotificationDto();
        List<AreaRestrictionManagerNotificationDto> areaRestrictionManagerNotificationDtos = new ArrayList<>();
        for (AreaRestrictionManagerNotification areaRestrictionManagerNotification : areaRestrictionManagerNotifications) {
            EmployeeDto employeeDto = areaRestrictionService.getEmployee(areaRestrictionManagerNotification.getManagerId(), locationDto.getId());
            AreaRestrictionManagerNotificationDto areaRestrictionManagerNotificationDto = new AreaRestrictionManagerNotificationDto();
            areaRestrictionManagerNotificationDto.setManager(employeeDto);
            areaRestrictionManagerNotificationDto.setTimeSkip(areaRestrictionManagerNotification.getTimeSkip());
            areaRestrictionManagerNotificationDtos.add(areaRestrictionManagerNotificationDto);
        }
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(locationDto.getId(), areaRestrictionId);
        areaRestrictionNotificationDto.setAreaRestrictionDto(convertAreaRestrictionToAreaRestrictionDto(areaRestriction));
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
        LocationDto locationDto = areaRestrictionService.getLocationOfCurrentUser();
        // Set manager for area restriction
        if (areaRestriction.getManagerIds() != null) {
            List<EmployeeDto> employeeDtos = new ArrayList<>();
            String[] tmp = areaRestriction.getManagerIds().split(",");
            for (String managerIdStr : tmp) {
                EmployeeDto employeeDto = areaRestrictionService.getEmployee(Integer.parseInt(managerIdStr), locationDto.getId());
                if (employeeDto != null) {
                    employeeDtos.add(employeeDto);
                }
            }
            areaRestrictionDto.setManagers(employeeDtos);
        }
        return areaRestrictionDto;
    }
}
