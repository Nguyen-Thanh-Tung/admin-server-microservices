package com.comit.services.areaRestriction.client;

import com.comit.services.areaRestriction.client.request.AreaEmployeeTimeListRequest;
import com.comit.services.areaRestriction.client.response.AreaEmployeeTimeListResponse;
import com.comit.services.areaRestriction.client.response.AreaRestrictionResponse;
import com.comit.services.areaRestriction.client.response.BaseResponse;
import com.comit.services.areaRestriction.client.response.NotificationMethodResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface AreaRestrictionClient {
    AreaRestrictionResponse getAreaRestriction(String token, Integer areaRestrictionId);

    BaseResponse deleteManagerOnAllAreaRestriction(String token, Integer managerId);

    BaseResponse deleteAreaRestrictionManagerNotificationList(String token, Integer employeeId);

    AreaEmployeeTimeListResponse saveAreaEmployeeTimeList(String token, @RequestBody AreaEmployeeTimeListRequest areaEmployeeTimeListRequest);

    BaseResponse deleteAreaEmployeeTimeList(String token, Integer employeeId);

    AreaEmployeeTimeListResponse getAreaEmployeeTimesOfEmployee(String token, int employeeId);

    NotificationMethodResponse getNotificationMethodOfAreaRestriction(String token, Integer areaRestrictionId);
}
