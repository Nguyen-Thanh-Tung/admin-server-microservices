package com.comit.services.history.controller;

import com.comit.services.history.business.InOutHistoryBusiness;
import com.comit.services.history.constant.Const;
import com.comit.services.history.constant.HistoryErrorCode;
import com.comit.services.history.controller.request.InOutHistoryRequest;
import com.comit.services.history.controller.response.*;
import com.comit.services.history.model.dto.InOutHistoryDto;
import com.comit.services.history.model.entity.InOutHistory;
import com.comit.services.history.model.excel.AreaRestrictionExcelExporter;
import com.comit.services.history.model.excel.TimeKeepingExcelExporter;
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
        return new ResponseEntity<>(new InOutHistoryListResponse(HistoryErrorCode.SUCCESS, historyDtos, currentPage, totalItems, totalPages), HttpStatus.OK);
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
        List<InOutHistoryDto> historyDtos = inOutHistoryBusiness.getAllInOutHistory(historyPage.getContent());
        TimeKeepingExcelExporter excelExporter = new TimeKeepingExcelExporter(historyDtos);
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
        List<InOutHistoryDto> historyDtos = inOutHistoryBusiness.getAllInOutHistory(historyPage.getContent());
        AreaRestrictionExcelExporter excelExporter = new AreaRestrictionExcelExporter(historyDtos);
        excelExporter.export(response);
    }

    @GetMapping("/number-history")
    public ResponseEntity<BaseResponse> getNumberHistory(
            @RequestParam("location_id") Integer locationId,
            @RequestParam("employee_id") Integer employeeId,
            @RequestParam("time_start") String timeStart,
            @RequestParam("time_end") String timeEnd) {
        int numberHistory = inOutHistoryBusiness.getNumberHistory(locationId, employeeId, timeStart, timeEnd);
        return new ResponseEntity<>(new CountResponse(HistoryErrorCode.SUCCESS, numberHistory), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse> saveInOutHistory(@RequestBody InOutHistoryRequest inOutHistoryRequest) {
        InOutHistoryDto inOutHistoryDto = inOutHistoryBusiness.saveInOutHistory(inOutHistoryRequest);

        return new ResponseEntity<>(new InOutHistoryResponse(HistoryErrorCode.SUCCESS, inOutHistoryDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getInOutHistory(@PathVariable Integer id) {
        InOutHistoryDto inOutHistoryDto = inOutHistoryBusiness.getInOutHistory(id);
        if (inOutHistoryDto != null) {
            return new ResponseEntity<>(new InOutHistoryResponse(HistoryErrorCode.SUCCESS, inOutHistoryDto), HttpStatus.OK);
        }
        return new ResponseEntity<>(new InOutHistoryResponse(HistoryErrorCode.FAIL, null), HttpStatus.OK);
    }
}
