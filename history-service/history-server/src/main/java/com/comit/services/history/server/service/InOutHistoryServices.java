package com.comit.services.history.server.service;

import com.comit.services.history.server.model.entity.InOutHistory;
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

    boolean hasPermissionManageInOutHistory(String locationType);

    Page<InOutHistory> getInOutHistoryPageOfAreaRestrictionList(List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, Pageable paging);

    int getNumberCheckInCurrentDay(Integer id, Date timeStart, Date timeEnd);
}
