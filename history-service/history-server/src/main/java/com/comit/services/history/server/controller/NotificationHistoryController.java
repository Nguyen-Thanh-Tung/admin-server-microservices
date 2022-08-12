package com.comit.services.history.server.controller;

import com.comit.services.history.client.dto.NotificationHistoryDto;
import com.comit.services.history.client.response.BaseResponse;
import com.comit.services.history.client.response.CountNotificationResponse;
import com.comit.services.history.client.response.NotificationHistoryListResponse;
import com.comit.services.history.server.business.NotificationHistoryBusiness;
import com.comit.services.history.server.constant.Const;
import com.comit.services.history.server.constant.HistoryErrorCode;
import com.comit.services.history.server.model.entity.NotificationHistory;
import com.comit.services.history.server.model.excel.AreaRestrictionNotificationExcelExporter;
import com.comit.services.history.server.model.excel.TimeKeepingNotificationExcelExporter;
import com.comit.services.history.server.service.HistoryServices;
import com.comit.services.history.server.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notification-histories")
public class NotificationHistoryController {
    @Autowired
    NotificationHistoryBusiness notificationHistoryBusiness;
    @Autowired
    HistoryServices historyServices;

    @GetMapping(value = "")
    public ResponseEntity<BaseResponse> getAllNotificationHistory(
            @RequestParam(value = "types", required = false) String typeStrs,
            @RequestParam(value = "employee_ids", required = false) String employeeIds,
            @RequestParam(value = "area_restriction_ids", required = false) String areaRestrictionIds,
            @RequestParam(value = "time_start", required = false) String timeStart,
            @RequestParam(value = "time_end", required = false) String timeEnd,
            @RequestParam(value = "has_employee", required = false) Boolean hasEmployee,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(defaultValue = Const.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Const.DEFAULT_SIZE_PAGE) int size
    ) throws ParseException {
        Page<NotificationHistory> historyPage;
        if (areaRestrictionIds != null && status != null) {
            historyPage = notificationHistoryBusiness.getNotificationHistoryPage(areaRestrictionIds, status, page, size);
        } else if (areaRestrictionIds != null) {
            historyPage = notificationHistoryBusiness.getNotificationHistoryPage(typeStrs, timeStart, timeEnd, null, areaRestrictionIds, page, size);
        } else if (hasEmployee != null) {
            historyPage = notificationHistoryBusiness.getNotificationHistoryPage(hasEmployee, timeStart, timeEnd, null, page, size);
        } else if (status != null) {
            historyPage = notificationHistoryBusiness.getNotificationHistoryPage(status, page, size);
        } else {
            historyPage = notificationHistoryBusiness.getNotificationHistoryPage(typeStrs, employeeIds, timeStart, timeEnd, null, page, size);
        }

        List<NotificationHistoryDto> historyDtos = new ArrayList<>();
        int currentPage = 0;
        long totalItems = 0;
        int totalPages = 0;
        if (historyPage != null) {
            currentPage = historyPage.getNumber();
            totalItems = historyPage.getTotalElements();
            totalPages = historyPage.getTotalPages();
            historyDtos = notificationHistoryBusiness.getAllNotificationHistory(historyPage.getContent());
        }
        return new ResponseEntity<>(new NotificationHistoryListResponse(HistoryErrorCode.SUCCESS.getCode(), HistoryErrorCode.SUCCESS.getMessage(), historyDtos, currentPage, totalItems, totalPages), HttpStatus.OK);
    }

    @GetMapping("/report")
    public void downloadReport(
            @RequestParam(value = "types", required = false) String typeStrs,
            @RequestParam(value = "employee_ids", required = false) String employeeIds,
            @RequestParam(value = "time_start", required = false) String timeStart,
            @RequestParam(value = "time_end", required = false) String timeEnd,
            @RequestParam(value = "location_id") Integer locationId,
            HttpServletResponse response
    ) throws IOException, ParseException {
        Page<NotificationHistory> historyPage = notificationHistoryBusiness.getNotificationHistoryPage(typeStrs, employeeIds, timeStart, timeEnd, locationId, 0, 1000000);
        response.setContentType("application/octet-stream");
        String currentDateTime = TimeUtil.getCurrentDateTimeStr();

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=time_keeping_notification_report_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        TimeKeepingNotificationExcelExporter excelExporter = new TimeKeepingNotificationExcelExporter(historyPage.getContent(), historyServices);
        excelExporter.export(response);
    }

    @GetMapping("/report-area-restriction")
    public void downloadReportAreaRestriction(
            @RequestParam(value = "types", required = false) String typeStrs,
            @RequestParam(value = "area_restriction_ids", required = false) String areaRestrictionIds,
            @RequestParam(value = "time_start", required = false) String timeStart,
            @RequestParam(value = "time_end", required = false) String timeEnd,
            @RequestParam(value = "location_id") Integer locationId,
            HttpServletResponse response
    ) throws IOException, ParseException {
        Page<NotificationHistory> historyPage = notificationHistoryBusiness.getNotificationHistoryPage(typeStrs, timeStart, timeEnd, locationId, areaRestrictionIds, 0, 1000000);
        response.setContentType("application/octet-stream");
        String currentDateTime = TimeUtil.getCurrentDateTimeStr();

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=area_restriction_notification_report_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        AreaRestrictionNotificationExcelExporter excelExporter = new AreaRestrictionNotificationExcelExporter(historyPage.getContent(), historyServices);
        excelExporter.export(response);
    }

    @PutMapping("/{id}/update-status")
    public ResponseEntity<BaseResponse> updateStatusNotificationHistory(@PathVariable Integer id) {
        boolean updateSuccess = notificationHistoryBusiness.updateStatusNotificationHistory(id);
        if (updateSuccess) {
            return new ResponseEntity<>(new BaseResponse(HistoryErrorCode.SUCCESS.getCode(), HistoryErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(HistoryErrorCode.FAIL.getCode(), HistoryErrorCode.FAIL.getMessage()), HttpStatus.OK);
    }

    @GetMapping("/area-restriction/{areaRestrictionId}")
    public ResponseEntity<BaseResponse> getNumberNotificationOfAreaRestriction(@PathVariable Integer areaRestrictionId) {
        int numberNotificationOfAreaRestriction = notificationHistoryBusiness.getNumberNotificationOfAreaRestriction(areaRestrictionId);
        return new ResponseEntity<>(new CountNotificationResponse(HistoryErrorCode.SUCCESS.getCode(), HistoryErrorCode.SUCCESS.getMessage(), numberNotificationOfAreaRestriction), HttpStatus.OK);
    }
}
