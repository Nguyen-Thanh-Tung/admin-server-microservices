package com.comit.services.history.model.excel;

import com.comit.services.history.client.data.EmployeeDto;
import com.comit.services.history.model.entity.NotificationHistory;
import com.comit.services.history.service.HistoryServices;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TimeKeepingNotificationExcelExporter {
    private final XSSFWorkbook workbook;
    private final List<NotificationHistory> notificationHistories;
    private XSSFSheet sheet;
    private final HistoryServices historyServices;

    public TimeKeepingNotificationExcelExporter(List<NotificationHistory> notificationHistories, HistoryServices historyServices) {
        this.notificationHistories = notificationHistories;
        this.workbook = new XSSFWorkbook();
        this.historyServices = historyServices;
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Time Keeping Report");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Loại cảnh báo", style);
        createCell(row, 1, "Tên nhân viên", style);
        createCell(row, 2, "Mã nhân viên", style);
        createCell(row, 3, "Quản lý", style);
        createCell(row, 4, "Thời gian cảnh báo", style);
        //Setting Auto Column Width
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (NotificationHistory history : notificationHistories) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            EmployeeDto employeeDto = null;
            EmployeeDto managerDto = null;

            if (history.getEmployeeId() != null) {
                employeeDto = historyServices.getEmployee(history.getEmployeeId());
                if (employeeDto != null) {
                    managerDto = employeeDto.getManager();
                }

            }
            createCell(row, columnCount++, history.getType(), style);
            createCell(row, columnCount++, employeeDto != null ? employeeDto.getName() : "", style);
            createCell(row, columnCount++, employeeDto != null ? employeeDto.getCode() : "", style);
            createCell(row, columnCount++, managerDto != null ? managerDto.getName() : "", style);
            createCell(row, columnCount++, history.getTime().toString(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}
