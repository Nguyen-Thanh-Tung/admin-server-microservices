package com.comit.services.history.business;

import com.comit.services.history.client.data.CameraDtoClient;
import com.comit.services.history.client.data.LocationDtoClient;
import com.comit.services.history.controller.request.InOutHistoryRequest;
import com.comit.services.history.middleware.HistoryVerifyRequestServicesImpl;
import com.comit.services.history.model.dto.InOutHistoryDto;
import com.comit.services.history.model.entity.InOutHistory;
import com.comit.services.history.service.HistoryServices;
import com.comit.services.history.service.InOutHistoryServices;
import com.comit.services.history.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InOutHistoryBusinessImpl implements InOutHistoryBusiness {
    @Autowired
    private HistoryBusiness historyBusiness;
    @Autowired
    private InOutHistoryServices inOutHistoryServices;
    @Autowired
    private HistoryServices historyServices;
    @Autowired
    private HistoryVerifyRequestServicesImpl verifyRequestServices;

    @Override
    public Page<InOutHistory> getInOutHistoryPage(String cameraIdStrs, Integer employeeId, String timeStartStr, String timeEndStr, Integer locationId, int page, int size) throws ParseException {
        // Is user and has role manage employee (Ex: Time keeping user)
        LocationDtoClient locationDtoClient;
        if (locationId == null) {
            locationDtoClient = historyServices.getLocationOfCurrentUser();
        } else {
            locationDtoClient = historyServices.getLocation(locationId);
        }

        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);

        if (cameraIdStrs == null && employeeId == null) {
            return inOutHistoryServices.getInOutHistoryPageOfLocation(locationDtoClient.getId(), timeStart, timeEnd, paging);
        } else if (employeeId == null) {
            String[] tmp = cameraIdStrs.split(",");
            List<Integer> cameraIds = new ArrayList<>();
            for (String cameraId : tmp) {
                cameraIds.add(Integer.parseInt(cameraId));
            }
            return inOutHistoryServices.getInOutHistoryPage(cameraIds, timeStart, timeEnd, paging);
        } else if (cameraIdStrs == null) {
            return inOutHistoryServices.getInOutHistoryPage(employeeId, timeStart, timeEnd, paging);
        } else {
            String[] tmp = cameraIdStrs.split(",");
            List<Integer> cameraIds = new ArrayList<>();
            for (String cameraId : tmp) {
                cameraIds.add(Integer.parseInt(cameraId));
            }
            return inOutHistoryServices.getInOutHistoryPage(cameraIds, employeeId, timeStart, timeEnd, paging);
        }
    }

    @Override
    public Page<InOutHistory> getInOutHistoryPage(String cameraIdStrs, String areaRestrictionIdStrs, String timeStartStr, String timeEndStr, Integer locationId, int page, int size) throws ParseException {
        if (locationId == null) {
            LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();
            locationId = locationDtoClient.getId();
        }

        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);

        if (cameraIdStrs == null && areaRestrictionIdStrs == null) {
            return inOutHistoryServices.getInOutHistoryPageOfLocation(locationId, timeStart, timeEnd, paging);
        } else if (cameraIdStrs == null) {
            String[] tmp = areaRestrictionIdStrs.split(",");
            List<Integer> areaRestrictionIds = new ArrayList<>();
            for (String areaRestrictionId : tmp) {
                areaRestrictionIds.add(Integer.parseInt(areaRestrictionId));
            }
            return inOutHistoryServices.getInOutHistoryPageOfAreaRestrictionList(areaRestrictionIds, timeStart, timeEnd, paging);
        } else {
            String[] tmp = cameraIdStrs.split(",");
            List<Integer> cameraIds = new ArrayList<>();
            for (String cameraId : tmp) {
                cameraIds.add(Integer.parseInt(cameraId));
            }
            return inOutHistoryServices.getInOutHistoryPage(cameraIds, timeStart, timeEnd, paging);
        }
    }

    @Override
    public List<InOutHistoryDto> getAllInOutHistory(List<InOutHistory> inOutHistories) {
        List<InOutHistoryDto> inOutHistoryDtos = new ArrayList<>();
        inOutHistories.forEach(inOutHistory -> {
            inOutHistoryDtos.add(historyBusiness.convertInOutHistoryToInOutHistoryDto(inOutHistory));
        });
        return inOutHistoryDtos;
    }

    @Override
    public InOutHistoryDto saveInOutHistory(InOutHistoryRequest request) {
        verifyRequestServices.verifySaveHistory(request);
        InOutHistory inOutHistory = new InOutHistory();
        CameraDtoClient cameraDtoClient = historyServices.getCamera(request.getCameraId());
        inOutHistory.setCameraId(request.getCameraId());
        inOutHistory.setEmployeeId(request.getEmployeeId());
        inOutHistory.setTime(TimeUtil.stringDateToDate(request.getTime()));
        inOutHistory.setType(cameraDtoClient.getType());
        inOutHistory.setImageId(request.getImageId());
        inOutHistory.setLocationId(cameraDtoClient.getLocationId());
        inOutHistory.setAreaRestrictionId(cameraDtoClient.getAreaRestrictionId());
        InOutHistory newInOutHistory = inOutHistoryServices.saveInOutHistory(inOutHistory);
        return historyBusiness.convertInOutHistoryToInOutHistoryDto(newInOutHistory);
    }

    @Override
    public InOutHistoryDto getInOutHistory(Integer inOutHistoryId) {
        InOutHistory inOutHistory = inOutHistoryServices.getInOutHistory(inOutHistoryId);
        if (inOutHistory != null) {
            return historyBusiness.convertInOutHistoryToInOutHistoryDto(inOutHistory);
        }
        return null;
    }


    @Override
    public int getNumberCheckInCurrentDay() {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();

        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");

        return inOutHistoryServices.getNumberCheckInCurrentDay(locationDtoClient.getId(), timeStart, timeEnd);
    }

    @Override
    public int getNumberHistory(Integer locationId, Integer employeeId, String timeStartStr, String timeEndStr) {
        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = TimeUtil.stringDateToDate(timeEndStr);

        return inOutHistoryServices.getNumberHistory(locationId, employeeId, timeStart, timeEnd);
    }
}
