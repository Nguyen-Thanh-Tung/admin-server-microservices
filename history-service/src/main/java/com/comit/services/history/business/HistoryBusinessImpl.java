package com.comit.services.history.business;

import com.comit.services.history.client.data.*;
import com.comit.services.history.model.dto.*;
import com.comit.services.history.model.entity.InOutHistory;
import com.comit.services.history.model.entity.NotificationHistory;
import com.comit.services.history.service.HistoryServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryBusinessImpl implements HistoryBusiness {
    @Autowired
    HistoryServices historyServices;

    @Override
    public InOutHistoryDto convertInOutHistoryToInOutHistoryDto(InOutHistory inOutHistory) {
        if (inOutHistory == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        InOutHistoryDto inOutHistoryDto = modelMapper.map(inOutHistory, InOutHistoryDto.class);
        if (inOutHistory.getCameraId() != null) {
            inOutHistoryDto.setCamera(convertCameraDtoFromClient(inOutHistory.getCameraId()));
        }

        if (inOutHistory.getEmployeeId() != null) {
            inOutHistoryDto.setEmployee(convertEmployeeDtoFromClient(inOutHistory.getEmployeeId()));
        }

        if (inOutHistory.getImageId() != null) {
            inOutHistoryDto.setImage(convertMetadataDtoFromClient(inOutHistory.getImageId()));
        }
        return inOutHistoryDto;
    }

    public CameraDto convertCameraDtoFromClient(Integer cameraId) {
        if (cameraId == null) return null;
        CameraDtoClient cameraDtoClient = historyServices.getCamera(cameraId);
        CameraDto cameraDto = new CameraDto();
        cameraDto.setId(cameraDtoClient.getId());
        cameraDto.setName(cameraDtoClient.getName());
        cameraDto.setIpAddress(cameraDtoClient.getIpAddress());
        cameraDto.setTaken(cameraDtoClient.getTaken());
        cameraDto.setType(cameraDtoClient.getType());
        cameraDto.setStatus(cameraDtoClient.getStatus());
        cameraDto.setLocation(convertLocationDtoFromClient(cameraDtoClient.getLocationId()));
        cameraDto.setAreaRestriction(convertAreaRestrictionDtoFromClient(cameraDtoClient.getLocationId(), cameraDtoClient.getAreaRestrictionId()));
        return cameraDto;
    }

    public LocationDto convertLocationDtoFromClient(Integer locationId) {
        if (locationId == null) return null;
        LocationDtoClient locationDtoClient = historyServices.getLocation(locationId);
        LocationDto locationDto = new LocationDto();
        locationDto.setId(locationDtoClient.getId());
        locationDto.setName(locationDtoClient.getName());
        locationDto.setCode(locationDtoClient.getCode());
        locationDto.setType(locationDtoClient.getType());
        locationDto.setOrganizationId(locationDtoClient.getOrganizationId());
        return locationDto;
    }

    public AreaRestrictionDto convertAreaRestrictionDtoFromClient(Integer locationId, Integer areaRestrictionId) {
        if (locationId == null || areaRestrictionId == null) return null;
        AreaRestrictionDtoClient areaRestrictionDtoClient = historyServices.getAreaRestriction(locationId, areaRestrictionId);
        AreaRestrictionDto areaRestrictionDto = new AreaRestrictionDto();
        areaRestrictionDto.setId(areaRestrictionDtoClient.getId());
        areaRestrictionDto.setName(areaRestrictionDtoClient.getName());
        areaRestrictionDto.setCode(areaRestrictionDtoClient.getCode());
        areaRestrictionDto.setTimeStart(areaRestrictionDtoClient.getTimeStart());
        areaRestrictionDto.setTimeEnd(areaRestrictionDtoClient.getTimeEnd());
        return areaRestrictionDto;
    }

    public EmployeeDto convertEmployeeDtoFromClient(Integer employeeId) {
        if (employeeId == null) return null;
        EmployeeDtoClient employeeDtoClient = historyServices.getEmployee(employeeId);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeDtoClient.getId());
        employeeDto.setName(employeeDtoClient.getName());
        employeeDto.setCode(employeeDtoClient.getCode());
        employeeDto.setImage(convertMetadataDtoFromClient(employeeDtoClient.getImageId()));
        employeeDto.setEmail(employeeDtoClient.getEmail());
        employeeDto.setStatus(employeeDtoClient.getStatus());
        employeeDto.setManager(employeeDtoClient.getManager() == null ? null : convertEmployeeDtoFromClient(employeeDtoClient.getManager().getId()));
        employeeDto.setPhone(employeeDtoClient.getPhone());
        return employeeDto;
    }

    public MetadataDto convertMetadataDtoFromClient(Integer metadataId) {
        if (metadataId == null) return null;
        MetadataDtoClient metadataDtoClient = historyServices.getMetadata(metadataId);
        MetadataDto metadataDto = new MetadataDto();
        metadataDto.setId(metadataDtoClient.getId());
        metadataDto.setPath(metadataDtoClient.getPath());
        metadataDto.setMd5(metadataDtoClient.getMd5());
        return metadataDto;
    }

    public NotificationMethodDto convertNotificationMethodDtoFromClient(NotificationMethodDtoClient notificationMethodDtoClient) {
        NotificationMethodDto notificationMethodDto = new NotificationMethodDto();
        notificationMethodDto.setId(notificationMethodDtoClient.getId());
        notificationMethodDto.setUseEmail(notificationMethodDtoClient.isUseEmail());
        notificationMethodDto.setUseOTT(notificationMethodDtoClient.isUseOTT());
        notificationMethodDto.setUseRing(notificationMethodDtoClient.isUseRing());
        notificationMethodDto.setUseScreen(notificationMethodDtoClient.isUseScreen());
        notificationMethodDto.setAreaRestrictionId(notificationMethodDtoClient.getAreaRestrictionId());
        return notificationMethodDto;
    }

    @Override
    public NotificationHistoryDto convertNotificationHistoryToNotificationHistoryDto(NotificationHistory notificationHistory) {
        if (notificationHistory == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            NotificationHistoryDto notificationHistoryDto = modelMapper.map(notificationHistory, NotificationHistoryDto.class);
            if (notificationHistory.getEmployeeId() != null) {
                notificationHistoryDto.setEmployee(convertEmployeeDtoFromClient(notificationHistory.getEmployeeId()));
            }

            if (notificationHistory.getCameraId() != null) {
                CameraDto cameraDto = convertCameraDtoFromClient(notificationHistory.getCameraId());
                if (cameraDto.getAreaRestriction() != null) {
                    NotificationMethodDtoClient notificationMethodDtoClient = historyServices.getNotificationMethodOfAreaRestriction(cameraDto.getAreaRestriction().getId());
                    notificationHistoryDto.setNotificationMethod(convertNotificationMethodDtoFromClient(notificationMethodDtoClient));
                }
                notificationHistoryDto.setCamera(cameraDto);

            }

            if (notificationHistory.getImageId() != null) {
                notificationHistoryDto.setImage(convertMetadataDtoFromClient(notificationHistory.getImageId()));
            }

            return notificationHistoryDto;
        } catch (Exception e) {
            return null;
        }
    }
}
