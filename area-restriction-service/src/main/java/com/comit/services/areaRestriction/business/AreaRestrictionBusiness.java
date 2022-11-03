package com.comit.services.areaRestriction.business;

import com.comit.services.areaRestriction.client.data.EmployeeDtoClient;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionNotificationRequest;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionRequest;
import com.comit.services.areaRestriction.model.dto.*;
import com.comit.services.areaRestriction.model.entity.AreaRestriction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AreaRestrictionBusiness {
    Page<AreaRestriction> getAreaRestrictionPage(int page, int size, String search);

    List<AreaRestrictionDto> getAllAreaRestriction(List<AreaRestriction> areaRestrictions);

    AreaRestrictionDto addAreaRestriction(AreaRestrictionRequest request);

    AreaRestrictionDto updateAreaRestriction(Integer id, AreaRestrictionRequest request);

    boolean deleteAreaRestriction(Integer id);

    AreaRestrictionDto updateAreaRestrictionNotification(Integer id, AreaRestrictionNotificationRequest request);

    NotificationMethodDto getNotificationMethodOfAreaRestriction(Integer id);

    AreaRestrictionDto getAreaRestriction(Integer id);

    BaseAreaRestrictionDto getAreaRestrictionBase(Integer id);

    AreaRestrictionNotificationDto getAreaRestrictionNotification(Integer id);

    BaseAreaRestrictionNotificationDto getBaseAreaRestrictionNotification(Integer id);

    boolean deleteManagerOnAllAreaRestriction(Integer managerId);

    EmployeeDto convertEmployeeDtoFromClient(EmployeeDtoClient employeeDtoClient);
}
