package com.comit.services.history.service;

import com.comit.services.history.constant.Const;
import com.comit.services.history.model.entity.FirstInLastOutHistory;
import com.comit.services.history.repository.FirstInLastOutHistoryRepository;
import com.comit.services.history.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FirstInLastOutHistoryServicesImpl implements FirstInLastOutHistoryServices {

    @Autowired
    FirstInLastOutHistoryRepository firstInLastOutHistoryRepository;

    @Override
    public FirstInLastOutHistory saveFirstInLastOutHistory(FirstInLastOutHistory history) {
        return firstInLastOutHistoryRepository.save(history);
    }

    @Override
    public FirstInLastOutHistory getFistCheckInInDayOfEmployee(Integer employeeId) {
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");
        return firstInLastOutHistoryRepository.findByEmployeeIdAndTypeAndTimeBetween(employeeId, Const.CHECK_IN, timeStart, timeEnd);
    }

    @Override
    public FirstInLastOutHistory getLastCheckOutInDayOfEmployee(Integer employeeId) {
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");
        return firstInLastOutHistoryRepository.findByEmployeeIdAndTypeAndTimeBetween(employeeId, Const.CHECK_OUT, timeStart, timeEnd);
    }

    @Override
    public Page<FirstInLastOutHistory> getInOutHistoryPageOfLocation(Integer locationId, Date timeStart, Date timeEnd, Pageable paging) {
        return firstInLastOutHistoryRepository.findByLocationIdAndTimeBetweenOrderByTimeDesc(locationId, timeStart, timeEnd, paging);
    }

    @Override
    public Page<FirstInLastOutHistory> getInOutHistoryPage(List<Integer> cameraIds, Date timeStart, Date timeEnd, Pageable paging) {
        return firstInLastOutHistoryRepository.findByCameraIdInAndTimeBetweenOrderByTimeDesc(cameraIds, timeStart, timeEnd, paging);
    }

    @Override
    public Page<FirstInLastOutHistory> getInOutHistoryPage(Integer employeeId, Date timeStart, Date timeEnd, Pageable paging) {
        return firstInLastOutHistoryRepository.findByEmployeeIdAndTimeBetweenOrderByTimeDesc(employeeId, timeStart, timeEnd, paging);
    }

    @Override
    public Page<FirstInLastOutHistory> getInOutHistoryPage(List<Integer> cameraIds, Integer employeeId, Date timeStart, Date timeEnd, Pageable paging) {
        return firstInLastOutHistoryRepository.findByCameraIdInAndEmployeeIdAndTimeBetweenOrderByTimeDesc(cameraIds, employeeId, timeStart, timeEnd, paging);
    }
}
