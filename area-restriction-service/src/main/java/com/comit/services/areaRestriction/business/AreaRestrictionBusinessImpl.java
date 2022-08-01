package com.comit.services.areaRestriction.business;

import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionNotificationRequest;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionRequest;
import com.comit.services.areaRestriction.exception.AreaRestrictionCommonException;
import com.comit.services.areaRestriction.model.dto.AreaRestrictionDto;
import com.comit.services.areaRestriction.model.dto.NotificationMethodDto;
import com.comit.services.areaRestriction.model.entity.AreaRestriction;
import com.comit.services.areaRestriction.model.entity.Location;
import com.comit.services.areaRestriction.model.entity.NotificationMethod;
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

import java.util.*;
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

    @Override
    public Page<AreaRestriction> getAreaRestrictionPage(int page, int size, String search) {
        Pageable paging = PageRequest.of(page, size);
        Location location = areaRestrictionService.getLocationOfCurrentUser();
        return areaRestrictionService.getAreaRestrictionPage(location.getId(), search, paging);
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
        Location location = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(location.getId(), request.getName());

        if (areaRestriction != null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_IS_EXISTED);
        }

        areaRestriction = new AreaRestriction();
        areaRestriction.setName(request.getName());
        areaRestriction.setCode(request.getCode());

        List<Integer> managerIds = request.getManagerIds();

        areaRestriction.setManagerIds(managerIds.stream().map(String::valueOf)
                .collect(Collectors.joining(",")));
        areaRestriction.setLocationId(location.getId());
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
        Location location = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(location.getId(), id);
        if (areaRestriction == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_NOT_EXIST);
        }

        areaRestriction.setName(request.getName());
        areaRestriction.setCode(request.getCode());

        List<Integer> managerIds = request.getManagerIds();

        areaRestriction.setManagerIds(managerIds.stream().map(String::valueOf)
                .collect(Collectors.joining(",")));
        areaRestriction.setLocationId(location.getId());
        areaRestriction.setTimeStart(request.getTimeStart());
        areaRestriction.setTimeEnd(request.getTimeEnd());
        AreaRestriction newAreaRestriction = areaRestrictionService.updateAreaRestriction(areaRestriction);
        return convertAreaRestrictionToAreaRestrictionDto(newAreaRestriction);
    }

    @Override
    public boolean deleteAreaRestriction(Integer id) {
        Location location = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(location.getId(), id);
        if (areaRestriction == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_NOT_EXIST);
        }
        return areaRestrictionService.deleteAreaRestriction(areaRestriction);
    }

    @Override
    public AreaRestrictionDto updateAreaRestrictionNotification(Integer areaRestrictionId, AreaRestrictionNotificationRequest request) {
        Location location = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(location.getId(), areaRestrictionId);
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

    public AreaRestrictionDto convertAreaRestrictionToAreaRestrictionDto(AreaRestriction areaRestriction) {
        if (areaRestriction == null) return null;
        Date startDay = TimeUtil.getDateTimeFromTimeString("00:00:00");
        DateTime now = new DateTime(TimeUtil.asiaHoChiMinh);
        AreaRestrictionDto areaRestrictionDto = new AreaRestrictionDto();
        areaRestrictionDto.setCode(areaRestriction.getCode());
        areaRestrictionDto.setName(areaRestriction.getName());
        areaRestrictionDto.setTimeStart(areaRestriction.getTimeStart());
        areaRestrictionDto.setTimeEnd(areaRestriction.getTimeEnd());
        int numberCamera = areaRestrictionService.getNumberCameraOfAreaRestriction(areaRestriction.getId());
        areaRestrictionDto.setNumberCamera(numberCamera);
        int numberAreaEmployeeTime = areaRestrictionService.getNumberAreaEmployeeTimeOfAreaRestriction(areaRestriction.getId());
        areaRestrictionDto.setNumberEmployeeAllow(numberAreaEmployeeTime);
        int numberHistories = areaRestrictionService.getNumberNotificationOfAreaRestriction(areaRestriction, startDay, now.toDate());
        areaRestrictionDto.setNumberNotification(numberHistories);
        return areaRestrictionDto;
    }
}
