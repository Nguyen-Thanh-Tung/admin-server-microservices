package com.comit.services.areaRestriction.business;

import com.comit.services.areaRestriction.client.data.EmployeeDtoClient;
import com.comit.services.areaRestriction.client.data.LocationDtoClient;
import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.constant.Const;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionNotificationRequest;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionRequest;
import com.comit.services.areaRestriction.exception.AreaRestrictionCommonException;
import com.comit.services.areaRestriction.loging.model.CommonLogger;
import com.comit.services.areaRestriction.model.dto.*;
import com.comit.services.areaRestriction.model.entity.AreaRestriction;
import com.comit.services.areaRestriction.model.entity.AreaRestrictionManagerNotification;
import com.comit.services.areaRestriction.model.entity.NotificationMethod;
import com.comit.services.areaRestriction.service.AreaEmployeeTimeService;
import com.comit.services.areaRestriction.service.AreaRestrictionServices;
import com.comit.services.areaRestriction.service.NotificationMethodServices;
import com.comit.services.areaRestriction.middleware.VerifyAreaRestrictionRequestServices;
import com.comit.services.areaRestriction.util.TimeUtil;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Value("${app.internalToken}")
    private String internalToken;

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
        // Verify input
        verifyAreaRestrictionRequestServices.verifyAddOrUpdateAreaRestrictionRequest(request);
        // Get location of current user and check permission
        LocationDtoClient locationDtoClient = areaRestrictionService.getLocationOfCurrentUser();
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(locationDtoClient.getId(), request.getCode().trim());
        if (areaRestriction != null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_IS_EXISTED);
        }
        areaRestriction = new AreaRestriction();
        areaRestriction.setName(request.getName());
        areaRestriction.setCode(request.getCode());
        List<Integer> managerIds = request.getManagerIds();
        List<Integer> listManager = new ArrayList<>();
        for (int managerId : managerIds) {
            EmployeeDtoClient employee = areaRestrictionService.getEmployee(managerId);
            if (employee == null || !employee.getStatus().equals(Const.ACTIVE)) {
                throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.MANAGERS_NOT_FOUND);
            }
            // Compare location of employee with location of current user
            if (!Objects.equals(employee.getLocationId(), locationDtoClient.getId())) {
                throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.MANAGERS_IN_VALID);
            }
            listManager.add(managerId);
        }
        areaRestriction.setManagerIds(listManager.stream().map(String::valueOf)
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
        notificationMethodServices.saveNotificationMethod(notificationMethod);

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
        if (!areaRestriction.getCode().equals(request.getCode())) {
            AreaRestriction temp = areaRestrictionService.getAreaRestriction(locationDtoClient.getId(), request.getCode());
            if (temp != null) {
                throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_IS_EXISTED);
            }
        }
        areaRestriction.setName(request.getName());
        areaRestriction.setCode(request.getCode());
        List<Integer> managerIds = request.getManagerIds();
        List<Integer> listManager = new ArrayList<>();
        for (int managerId : managerIds) {
            EmployeeDtoClient employee = areaRestrictionService.getEmployee(managerId);
            if (employee == null || !employee.getStatus().equals(Const.ACTIVE)) {
                throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.MANAGERS_NOT_FOUND);
            }
            if (!Objects.equals(employee.getLocationId(), locationDtoClient.getId())) {
                throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.MANAGERS_IN_VALID);
            }
            listManager.add(managerId);
        }
        areaRestriction.setManagerIds(listManager.stream().map(String::valueOf)
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
        boolean deleteSuccess = areaEmployeeTimeService.deleteAreaEmployeeTimeList(areaRestriction.getId());
        if (deleteSuccess) {
            int numberCameraOfAreaRestriction = areaRestrictionService.getNumberCameraOfAreaRestriction(areaRestriction.getId());
            if (numberCameraOfAreaRestriction > 0) {
                throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.CAN_NOT_DELETE_AREA_RESTRICTION);
            }
            return areaRestrictionService.deleteAreaRestriction(areaRestriction);
        }
        return false;
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
        if (!isInternalFeature()) throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.PERMISSION_DENIED);
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
        throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_NOT_EXIST);
    }

    @Override
    public BaseAreaRestrictionDto getAreaRestrictionBase(Integer id) {
        if (!isInternalFeature()) throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.PERMISSION_DENIED);
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(id);
        if (areaRestriction != null) {
            return convertAreaRestrictionToBaseAreaRestrictionDto(areaRestriction);
        }
        throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_NOT_EXIST);
    }

    @Override
    public AreaRestrictionNotificationDto getAreaRestrictionNotification(Integer areaRestrictionId) {
        LocationDtoClient locationDtoClient = areaRestrictionService.getLocationOfCurrentUser();
        //Get area restriction
        AreaRestriction areaRestriction = areaRestrictionService.getAreaRestriction(locationDtoClient.getId(), areaRestrictionId);
        if (areaRestriction == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.AREA_RESTRICTION_NOT_EXIST);
        }
        //Get notification method
        NotificationMethod notificationMethod = notificationMethodServices.getNotificationMethodOfAreaRestriction(areaRestrictionId);
        //Get list manager and time after to receive notification
        List<AreaRestrictionManagerNotification> areaRestrictionManagerNotifications =
                areaRestrictionManagerNotificationBusiness.getAreaManagerTimeList(areaRestrictionId);
        AreaRestrictionNotificationDto areaRestrictionNotificationDto = new AreaRestrictionNotificationDto();
        List<AreaRestrictionManagerNotificationDto> areaRestrictionManagerNotificationDtos = new ArrayList<>();
        //Convert list manager and time after to response (dto)
        for (AreaRestrictionManagerNotification areaRestrictionManagerNotification : areaRestrictionManagerNotifications) {
            EmployeeDtoClient employeeDtoClient = areaRestrictionService.getEmployee(areaRestrictionManagerNotification.getManagerId());
            if (employeeDtoClient != null && employeeDtoClient.getStatus().equals(Const.ACTIVE)) {
                AreaRestrictionManagerNotificationDto areaRestrictionManagerNotificationDto = new AreaRestrictionManagerNotificationDto();
                areaRestrictionManagerNotificationDto.setManager(convertEmployeeDtoFromClient(employeeDtoClient));
                areaRestrictionManagerNotificationDto.setTimeSkip(areaRestrictionManagerNotification.getTimeSkip());
                areaRestrictionManagerNotificationDtos.add(areaRestrictionManagerNotificationDto);
            }
        }
        // Set attribute for object
        areaRestrictionNotificationDto.setAreaRestrictionDto(convertAreaRestrictionToAreaRestrictionDto(areaRestriction));
        areaRestrictionNotificationDto.setAreaRestrictionManagerNotifications(areaRestrictionManagerNotificationDtos);
        areaRestrictionNotificationDto.setNotificationMethod(NotificationMethodDto.convertNotificationMethodToNotificationMethodDto(notificationMethod));
        return areaRestrictionNotificationDto;
    }

    @Override
    public BaseAreaRestrictionNotificationDto getBaseAreaRestrictionNotification(Integer areaRestrictionId) {
        NotificationMethod notificationMethod = notificationMethodServices.getNotificationMethodOfAreaRestriction(areaRestrictionId);
        List<AreaRestrictionManagerNotification> areaRestrictionManagerNotifications = areaRestrictionManagerNotificationBusiness.getAreaManagerTimeList(areaRestrictionId);
        BaseAreaRestrictionNotificationDto areaRestrictionNotificationDto = new BaseAreaRestrictionNotificationDto();
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
        if (!isInternalFeature()) throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.PERMISSION_DENIED);
        List<AreaRestriction> areaRestrictions = areaRestrictionService.getAllAreaRestrictionOfManager(managerId);
        areaRestrictions.forEach(areaRestriction -> {
            String[] tmp = areaRestriction.getManagerIds().split(",");
            if (tmp.length == 1) {
                throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.FAIL);
            }
            StringBuilder result = new StringBuilder();
            for (String s : tmp) {
                if (!Objects.equals(s, managerId.toString())) {
                    result.append(s).append(",");
                }
            }
            if (result.length() > 0) {
                areaRestriction.setManagerIds(result.substring(0, result.length() - 1));
            } else {
                areaRestriction.setManagerIds("");
            }
            areaRestrictionService.updateAreaRestriction(areaRestriction);
        });
        return true;
    }

    public BaseAreaRestrictionDto convertAreaRestrictionToBaseAreaRestrictionDto(AreaRestriction areaRestriction) {
        if (areaRestriction == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(areaRestriction, BaseAreaRestrictionDto.class);
        } catch (Exception e) {
            CommonLogger.error(e.getMessage(), e);
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
        if (areaRestriction.getManagerIds() != null && !areaRestriction.getManagerIds().trim().equals("")) {
            List<EmployeeDto> employeeDtos = new ArrayList<>();
            String[] tmp = areaRestriction.getManagerIds().split(",");
            for (String managerIdStr : tmp) {
                EmployeeDtoClient employeeDtoClient = areaRestrictionService.getEmployee(Integer.parseInt(managerIdStr.trim()));
                if (employeeDtoClient != null && employeeDtoClient.getStatus().equals(Const.ACTIVE)) {
                    employeeDtos.add(convertEmployeeDtoFromClient(employeeDtoClient));
                }
            }
            areaRestrictionDto.setManagers(employeeDtos);
        }
        return areaRestrictionDto;
    }

    @Override
    public EmployeeDto convertEmployeeDtoFromClient(EmployeeDtoClient employeeDtoClient) {
        if (employeeDtoClient == null) return null;
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

    @Override
    public List<AreaRestrictionDto> getAllAreaRestriction(Integer managerId) {
        if (!isInternalFeature()) throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.PERMISSION_DENIED);
        List<AreaRestriction> areaRestrictions = areaRestrictionService.getAllAreaRestrictionOfManager(managerId);
        List<AreaRestrictionDto> areaRestrictionDtos = new ArrayList<>();
        for (AreaRestriction areaRestriction : areaRestrictions) {
            String[] managerStr = areaRestriction.getManagerIds().split(",");
            if (managerStr.length == 1) {
                areaRestrictionDtos.add(convertAreaRestrictionDtoFromClient(areaRestriction));
            }
        }
        return areaRestrictionDtos;
    }

    @Override
    public AreaRestrictionDto convertAreaRestrictionDtoFromClient(AreaRestriction areaRestriction) {
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
        LocationDtoClient locationDtoClient = areaRestrictionService.getLocationById(areaRestriction.getLocationId());
        areaRestrictionDto.setLocation(convertLocationDtoFromClient(locationDtoClient));
        // Set manager for area restriction
        if (areaRestriction.getManagerIds() != null && !areaRestriction.getManagerIds().trim().equals("")) {
            List<EmployeeDto> employeeDtos = new ArrayList<>();
            String[] tmp = areaRestriction.getManagerIds().split(",");
            for (String managerIdStr : tmp) {
                EmployeeDtoClient employeeDtoClient = areaRestrictionService.getEmployee(Integer.parseInt(managerIdStr));
                if (employeeDtoClient != null && employeeDtoClient.getStatus().equals(Const.ACTIVE)) {
                    employeeDtos.add(convertEmployeeDtoFromClient(employeeDtoClient));
                }
            }
            areaRestrictionDto.setManagers(employeeDtos);
        }
        return areaRestrictionDto;
    }

    public LocationDto convertLocationDtoFromClient(LocationDtoClient locationDtoClient) {
        if (locationDtoClient == null) return null;
        LocationDto locationDto = new LocationDto();
        locationDto.setId(locationDtoClient.getId());
        locationDto.setName(locationDtoClient.getName());
        locationDto.setCode(locationDtoClient.getCode());
        locationDto.setType(locationDtoClient.getType());
        locationDto.setOrganizationId(locationDtoClient.getOrganizationId());
        return locationDto;
    }

    public boolean isInternalFeature() {
        return Objects.equals(httpServletRequest.getHeader("token"), internalToken);
    }
}
