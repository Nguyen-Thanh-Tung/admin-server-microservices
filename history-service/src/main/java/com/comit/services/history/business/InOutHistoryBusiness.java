package com.comit.services.history.business;

import com.comit.services.history.controller.request.InOutHistoryRequest;
import com.comit.services.history.model.dto.InOutHistoryDto;
import com.comit.services.history.model.entity.InOutHistory;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface InOutHistoryBusiness {
    Page<InOutHistory> getInOutHistoryPage(String cameraIds, Integer employeeId, String timeStart, String timeEnd, Integer locationId, int page, int size) throws ParseException;

    Page<InOutHistory> getInOutHistoryPage(String cameraIds, String areaRestrictionIds, String timeStart, String timeEnd, Integer locationId, int page, int size) throws ParseException;

    List<InOutHistoryDto> getAllInOutHistory(List<InOutHistory> content);

    InOutHistoryDto saveInOutHistory(InOutHistoryRequest inOutHistoryRequest);

    InOutHistoryDto getInOutHistory(Integer inOutHistoryId);

    int getNumberCheckInCurrentDay();

    int getNumberHistory(Integer locationId, Integer employeeId, String timeStart, String timeEnd);
}
