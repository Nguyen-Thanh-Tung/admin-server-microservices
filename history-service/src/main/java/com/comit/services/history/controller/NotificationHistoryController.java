package com.comit.services.history.controller;

import com.comit.services.history.business.NotificationHistoryBusiness;
import com.comit.services.history.constant.Const;
import com.comit.services.history.constant.HistoryErrorCode;
import com.comit.services.history.controller.request.InOutHistoryRequest;
import com.comit.services.history.controller.request.NotificationHistoryRequest;
import com.comit.services.history.controller.response.*;
import com.comit.services.history.model.dto.InOutHistoryDto;
import com.comit.services.history.model.dto.NotificationHistoryDto;
import com.comit.services.history.model.entity.NotificationHistory;
import com.comit.services.history.model.excel.AreaRestrictionNotificationExcelExporter;
import com.comit.services.history.model.excel.TimeKeepingNotificationExcelExporter;
import com.comit.services.history.service.HistoryServices;
import com.comit.services.history.util.TimeUtil;
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
        return new ResponseEntity<>(new NotificationHistoryListResponse(HistoryErrorCode.SUCCESS, historyDtos, currentPage, totalItems, totalPages), HttpStatus.OK);
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
        List<NotificationHistoryDto> historyDtos = notificationHistoryBusiness.getAllNotificationHistory(historyPage.getContent());
        TimeKeepingNotificationExcelExporter excelExporter = new TimeKeepingNotificationExcelExporter(historyDtos);
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
        List<NotificationHistoryDto> historyDtos = notificationHistoryBusiness.getAllNotificationHistory(historyPage.getContent());
        AreaRestrictionNotificationExcelExporter excelExporter = new AreaRestrictionNotificationExcelExporter(historyDtos);
        excelExporter.export(response);
    }

    @PutMapping("/{id}/update-status")
    public ResponseEntity<BaseResponse> updateStatusNotificationHistory(@PathVariable Integer id) {
        boolean updateSuccess = notificationHistoryBusiness.updateStatusNotificationHistory(id);
        return new ResponseEntity<>(new BaseResponse(updateSuccess ? HistoryErrorCode.SUCCESS : HistoryErrorCode.FAIL), HttpStatus.OK);
    }

    @GetMapping("/area-restriction/{areaRestrictionId}/number-notify")
    public ResponseEntity<BaseResponse> getNumberNotificationOfAreaRestriction(@PathVariable Integer areaRestrictionId, @RequestParam(required = false) String status) {
        int numberNotificationOfAreaRestriction = notificationHistoryBusiness.getNumberNotificationOfAreaRestriction(areaRestrictionId, status);
        return new ResponseEntity<>(new CountResponse(HistoryErrorCode.SUCCESS, numberNotificationOfAreaRestriction), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse> saveNotificationHistory(@RequestBody NotificationHistoryRequest notificationHistoryRequest) {
        NotificationHistoryDto notificationHistoryDto = notificationHistoryBusiness.saveNotificationHistory(notificationHistoryRequest);

        return new ResponseEntity<>(new NotificationHistoryResponse(HistoryErrorCode.SUCCESS, notificationHistoryDto), HttpStatus.OK);
    }

    @GetMapping("/number-history")
    public ResponseEntity<BaseResponse> getNumberNotificationHistory(
            @RequestParam("location_id") Integer locationId,
            @RequestParam("employee_id") Integer employeeId,
            @RequestParam("time_start") String timeStart,
            @RequestParam("time_end") String timeEnd
    ) {
        int numberHistory = notificationHistoryBusiness.getNumberNotification(locationId, employeeId, timeStart, timeEnd);
        return new ResponseEntity<>(new CountResponse(HistoryErrorCode.SUCCESS, numberHistory), HttpStatus.OK);
    }
}
