package com.comit.services.history.service;

import com.comit.services.history.model.entity.FirstInLastOutHistory;
import com.comit.services.history.model.entity.InOutHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface FirstInLastOutHistoryServices {

    FirstInLastOutHistory saveFirstInLastOutHistory(FirstInLastOutHistory history);

    FirstInLastOutHistory getFistCheckInInDayOfEmployee(Integer employeeId);

    FirstInLastOutHistory getLastCheckOutInDayOfEmployee(Integer employeeId);

    Page<FirstInLastOutHistory> getInOutHistoryPageOfLocation(Integer id, Date timeStart, Date timeEnd, Pageable paging);

    Page<FirstInLastOutHistory> getInOutHistoryPage(List<Integer> cameraIds, Date timeStart, Date timeEnd, Pageable paging);
    Page<FirstInLastOutHistory> getInOutHistoryPage(Integer employeeId, Date timeStart, Date timeEnd, Pageable paging);
    Page<FirstInLastOutHistory> getInOutHistoryPage(List<Integer> cameraIds, Integer employeeId, Date timeStart, Date timeEnd, Pageable paging);
}
