package com.comit.services.history.service;

import com.comit.services.history.constant.Const;
import com.comit.services.history.model.dto.InOutHistoryDto;
import com.comit.services.history.model.entity.InOutHistory;
import com.comit.services.history.repository.InOutHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InOutHistoryServicesImpl implements InOutHistoryServices {

    @Autowired
    private InOutHistoryRepository inOutHistoryRepository;

    @Override
    public Page<InOutHistory> getInOutHistoryPage(List<Integer> cameraIds, Date timeStart, Date timeEnd, Pageable paging) {
        return inOutHistoryRepository.findByCameraIdInAndTimeAfterAndTimeBeforeOrderByTimeDesc(cameraIds, timeStart, timeEnd, paging);
    }

    @Override
    public Page<InOutHistory> getInOutHistoryPage(Integer employeeId, Date timeStart, Date timeEnd, Pageable paging) {
        return inOutHistoryRepository.findByEmployeeIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(employeeId, timeStart, timeEnd, paging);
    }

    @Override
    public Page<InOutHistory> getInOutHistoryPageOfLocation(Integer locationId, Date timeStart, Date timeEnd, Pageable paging) {
        return inOutHistoryRepository.findByLocationIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, timeStart, timeEnd, paging);
    }

    @Override
    public Page<InOutHistory> getInOutHistoryPage(List<Integer> cameraIds, Integer employeeId, Date timeStart, Date timeEnd, Pageable paging) {
        return inOutHistoryRepository.findByCameraIdInAndEmployeeIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(cameraIds, employeeId, timeStart, timeEnd, paging);
    }

    @Override
    public InOutHistory saveInOutHistory(InOutHistory inOutHistory) {
        return inOutHistoryRepository.save(inOutHistory);
    }

    @Override
    public Page<InOutHistory> getInOutHistoryPageOfAreaRestrictionList(List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, Pageable paging) {
        return inOutHistoryRepository.findByAreaRestrictionIdInAndTimeAfterAndTimeBeforeOrderByTimeDesc(areaRestrictionIds, timeStart, timeEnd, paging);
    }

    @Override
    public int getNumberCheckInCurrentDay(Integer locationId, Date timeStart, Date timeEnd) {
        return inOutHistoryRepository.countNumberCheckIn(locationId, timeStart, timeEnd, Const.CHECK_IN);
    }

    @Override
    public int getNumberHistory(Integer locationId, Integer employeeId, Date timeStart, Date timeEnd) {
        return inOutHistoryRepository.countByLocationIdAndEmployeeIdAndTimeAfterAndTimeBefore(locationId, employeeId, timeStart, timeEnd);
    }

    @Override
    public InOutHistory getInOutHistory(int inOutHistoryId) {
        return inOutHistoryRepository.findById(inOutHistoryId);
    }
}
