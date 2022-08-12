package com.comit.services.timeKeeping.client;

import com.comit.services.timeKeeping.client.response.BaseResponse;
import com.comit.services.timeKeeping.client.response.ShiftResponse;

public interface TimeKeepingClient {
    ShiftResponse getShift(String token, int shiftId);

    BaseResponse addShiftsForLocation(String token, Integer locationId);

    BaseResponse deleteShiftsOfLocation(String token, Integer locationId);

    BaseResponse addTimeKeepingNotification(String token, Integer locationId);

    BaseResponse deleteTimeKeepingNotification(String token, Integer locationId);
}
