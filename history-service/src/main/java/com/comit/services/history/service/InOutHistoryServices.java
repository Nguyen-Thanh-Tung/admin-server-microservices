package com.comit.services.history.service;

import com.comit.services.history.model.dto.InOutHistoryDto;
import com.comit.services.history.model.entity.InOutHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface InOutHistoryServices {
    Page<InOutHistory> getInOutHistoryPage(List<Integer> cameraIds, Date timeStart, Date timeEnd, Pageable paging);

    Page<InOutHistory> getInOutHistoryPage(Integer employeeId, Date timeStart, Date timeEnd, Pageable paging);

    Page<InOutHistory> getInOutHistoryPageOfLocation(Integer locationId, Date timeStart, Date timeEnd, Pageable paging);

    Page<InOutHistory> getInOutHistoryPage(List<Integer> cameraIds, Integer employeeId, Date timeStart, Date timeEnd, Pageable paging);

    InOutHistory saveInOutHistory(InOutHistory inOutHistory);

    Page<InOutHistory> getInOutHistoryPageOfAreaRestrictionList(List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, Pageable paging);

    int getNumberCheckInCurrentDay(Integer id, Date timeStart, Date timeEnd);

    int getNumberHistory(Integer locationId, Integer employeeId, Date timeStart, Date timeEnd);

    InOutHistory getInOutHistory(int inOutHistoryId);
}
