package com.comit.services.areaRestriction.server.business;

import com.comit.services.areaRestriction.client.dto.AreaRestrictionDto;
import com.comit.services.areaRestriction.client.dto.AreaRestrictionNotificationDto;
import com.comit.services.areaRestriction.client.dto.NotificationMethodDto;
import com.comit.services.areaRestriction.client.request.AreaRestrictionNotificationRequest;
import com.comit.services.areaRestriction.client.request.AreaRestrictionRequest;
import com.comit.services.areaRestriction.server.model.AreaRestriction;
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

    AreaRestrictionNotificationDto getAreaRestrictionNotification(Integer id);

    boolean deleteManagerOnAllAreaRestriction(Integer managerId);
}
