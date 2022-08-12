package com.comit.services.history.server.controller;

import com.comit.services.history.client.dto.InOutHistoryDto;
import com.comit.services.history.client.response.BaseResponse;
import com.comit.services.history.client.response.InOutHistoryListResponse;
import com.comit.services.history.server.business.InOutHistoryBusiness;
import com.comit.services.history.server.constant.Const;
import com.comit.services.history.server.constant.HistoryErrorCode;
import com.comit.services.history.server.model.entity.InOutHistory;
import com.comit.services.history.server.model.excel.AreaRestrictionExcelExporter;
import com.comit.services.history.server.model.excel.TimeKeepingExcelExporter;
import com.comit.services.history.server.service.HistoryServices;
import com.comit.services.history.server.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/in-out-histories")
public class InOutHistoryController {
    @Autowired
    InOutHistoryBusiness inOutHistoryBusiness;
    @Autowired
    HistoryServices historyServices;

    @GetMapping(value = "")
    public ResponseEntity<BaseResponse> getAllInOutHistory(
            @RequestParam(value = "camera_ids", required = false) String cameraIds,
            @RequestParam(value = "employee_id", required = false) Integer employeeId,
            @RequestParam(value = "time_start", required = false) String timeStart,
            @RequestParam(value = "time_end", required = false) String timeEnd,
            @RequestParam(defaultValue = Const.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Const.DEFAULT_SIZE_PAGE) int size
    ) throws ParseException {
        Page<InOutHistory> historyPage = inOutHistoryBusiness.getInOutHistoryPage(cameraIds, employeeId, timeStart, timeEnd, null, page, size);
        List<InOutHistoryDto> historyDtos = new ArrayList<>();
        int currentPage = 0;
        long totalItems = 0;
        int totalPages = 0;
        if (historyPage != null) {
            currentPage = historyPage.getNumber();
            totalItems = historyPage.getTotalElements();
            totalPages = historyPage.getTotalPages();
            historyDtos = inOutHistoryBusiness.getAllInOutHistory(historyPage.getContent());
        }
        return new ResponseEntity<>(new InOutHistoryListResponse(HistoryErrorCode.SUCCESS.getCode(), HistoryErrorCode.SUCCESS.getMessage(), historyDtos, currentPage, totalItems, totalPages), HttpStatus.OK);
    }

    @GetMapping("/report")
    public void downloadReport(
            @RequestParam(value = "camera_ids", required = false) String cameraIds,
            @RequestParam(value = "employee_id", required = false) Integer employeeId,
            @RequestParam(value = "time_start", required = false) String timeStart,
            @RequestParam(value = "time_end", required = false) String timeEnd,
            @RequestParam(value = "location_id") Integer locationId,
            HttpServletResponse response
    ) throws IOException, ParseException {
        Page<InOutHistory> historyPage = inOutHistoryBusiness.getInOutHistoryPage(cameraIds, employeeId, timeStart, timeEnd, locationId, 0, Integer.parseInt(Const.DEFAULT_SIZE_PAGE));
        response.setContentType("application/octet-stream");
        String currentDateTime = TimeUtil.getCurrentDateTimeStr();

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=time_keeping_report_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        TimeKeepingExcelExporter excelExporter = new TimeKeepingExcelExporter(historyPage.getContent(), historyServices);
        excelExporter.export(response);
    }

    @GetMapping("/report-area-restriction")
    public void downloadReportAreaRestriction(
            @RequestParam(value = "camera_ids", required = false) String cameraIds,
            @RequestParam(value = "area_restriction_ids", required = false) String areaRestrictionIds,
            @RequestParam(value = "time_start", required = false) String timeStart,
            @RequestParam(value = "time_end", required = false) String timeEnd,
            @RequestParam(value = "location_id") Integer locationId,
            HttpServletResponse response
    ) throws IOException, ParseException {
        Page<InOutHistory> historyPage = inOutHistoryBusiness.getInOutHistoryPage(cameraIds, areaRestrictionIds, timeStart, timeEnd, locationId, 0, Integer.parseInt(Const.DEFAULT_SIZE_PAGE));
        response.setContentType("application/octet-stream");
        String currentDateTime = TimeUtil.getCurrentDateTimeStr();

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=area_restriction_report_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        AreaRestrictionExcelExporter excelExporter = new AreaRestrictionExcelExporter(historyPage.getContent(), historyServices);
        excelExporter.export(response);
    }
}
