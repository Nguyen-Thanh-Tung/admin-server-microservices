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
            CameraDtoClient cameraDtoClient = historyServices.getCamera(inOutHistory.getCameraId());
            inOutHistoryDto.setCamera(convertCameraDtoFromClient(cameraDtoClient));
        }

        if (inOutHistory.getEmployeeId() != null) {
            EmployeeDtoClient employeeDtoClient = historyServices.getEmployee(inOutHistory.getEmployeeId());
            inOutHistoryDto.setEmployee(convertEmployeeDtoFromClient(employeeDtoClient));
        }

        if (inOutHistory.getImageId() != null) {
            MetadataDtoClient metadataDtoClient = historyServices.getMetadata(inOutHistory.getImageId());
            inOutHistoryDto.setImage(convertMetadataDtoFromClient(metadataDtoClient));
        }
        return inOutHistoryDto;
    }

    public CameraDto convertCameraDtoFromClient(CameraDtoClient cameraDtoClient) {
        if (cameraDtoClient == null) return null;
        CameraDto cameraDto = new CameraDto();
        cameraDto.setId(cameraDtoClient.getId());
        cameraDto.setName(cameraDtoClient.getName());
        cameraDto.setIpAddress(cameraDtoClient.getIpAddress());
        cameraDto.setTaken(cameraDtoClient.getTaken());
        cameraDto.setType(cameraDtoClient.getType());
        cameraDto.setStatus(cameraDtoClient.getStatus());
        cameraDto.setLocation(convertLocationDtoFromClient(cameraDtoClient.getLocation()));
        cameraDto.setAreaRestriction(convertAreaRestrictionDtoFromClient(cameraDtoClient.getAreaRestriction()));
        return cameraDto;
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

    public AreaRestrictionDto convertAreaRestrictionDtoFromClient(AreaRestrictionDtoClient areaRestrictionDtoClient) {
        if (areaRestrictionDtoClient == null) return null;
        AreaRestrictionDto areaRestrictionDto = new AreaRestrictionDto();
        areaRestrictionDto.setId(areaRestrictionDtoClient.getId());
        areaRestrictionDto.setName(areaRestrictionDtoClient.getName());
        areaRestrictionDto.setCode(areaRestrictionDtoClient.getCode());
        areaRestrictionDto.setTimeStart(areaRestrictionDtoClient.getTimeStart());
        areaRestrictionDto.setTimeEnd(areaRestrictionDtoClient.getTimeEnd());
        return areaRestrictionDto;
    }

    public EmployeeDto convertEmployeeDtoFromClient(EmployeeDtoClient employeeDtoClient) {
        if (employeeDtoClient == null) return null;
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeDtoClient.getId());
        employeeDto.setName(employeeDtoClient.getName());
        employeeDto.setCode(employeeDtoClient.getCode());
        employeeDto.setImage(convertMetadataDtoFromClient(employeeDtoClient.getImage()));
        employeeDto.setEmail(employeeDtoClient.getEmail());
        employeeDto.setStatus(employeeDtoClient.getStatus());
        employeeDto.setManager(convertEmployeeDtoFromClient(employeeDtoClient.getManager()));
        employeeDto.setPhone(employeeDtoClient.getPhone());
        return employeeDto;
    }

    public MetadataDto convertMetadataDtoFromClient(MetadataDtoClient metadataDtoClient) {
        if (metadataDtoClient == null) return null;
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
            EmployeeDtoClient employeeDtoClient = historyServices.getEmployee(notificationHistory.getEmployeeId());
            if (employeeDtoClient != null) {
                notificationHistoryDto.setEmployee(convertEmployeeDtoFromClient(employeeDtoClient));
            }

            CameraDtoClient cameraDtoClient = historyServices.getCamera(notificationHistory.getCameraId());
            if (cameraDtoClient != null) {
                if (cameraDtoClient.getAreaRestriction() != null) {
                    AreaRestrictionDtoClient areaRestrictionDtoClient = cameraDtoClient.getAreaRestriction();
                    cameraDtoClient.setAreaRestriction(areaRestrictionDtoClient);
                    NotificationMethodDtoClient notificationMethodDtoClient = historyServices.getNotificationMethodOfAreaRestriction(areaRestrictionDtoClient.getId());
                    notificationHistoryDto.setNotificationMethod(convertNotificationMethodDtoFromClient(notificationMethodDtoClient));

                }
                notificationHistoryDto.setCamera(convertCameraDtoFromClient(cameraDtoClient));
            }

            MetadataDtoClient metadataDtoClient = historyServices.getMetadata(notificationHistory.getImageId());
            if (metadataDtoClient != null) {
                notificationHistoryDto.setImage(convertMetadataDtoFromClient(metadataDtoClient));
            }

            return notificationHistoryDto;
        } catch (Exception e) {
            return null;
        }
    }
}
