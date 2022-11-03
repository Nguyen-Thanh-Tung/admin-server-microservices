package com.comit.services.history.model.excel;

import com.comit.services.history.model.dto.AreaRestrictionDto;
import com.comit.services.history.model.dto.CameraDto;
import com.comit.services.history.model.dto.EmployeeDto;
import com.comit.services.history.model.dto.InOutHistoryDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AreaRestrictionExcelExporter {
    private final XSSFWorkbook workbook;
    private final List<InOutHistoryDto> inOutHistories;
    private XSSFSheet sheet;

    public AreaRestrictionExcelExporter(List<InOutHistoryDto> inOutHistories) {
        this.inOutHistories = inOutHistories;
        this.workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Area Restriction Report");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        createCell(row, 0, "Camera", style);
        createCell(row, 1, "Khu vực hạn chế", style);
        createCell(row, 2, "Vào/Ra", style);
        createCell(row, 3, "Tên nhân viên", style);
        createCell(row, 4, "Mã nhân viên", style);
        createCell(row, 5, "Quản lý", style);
        createCell(row, 6, "Thời gian", style);

        //Setting Auto Column Width
        for (int i = 0; i < 7; i++) {
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

        for (InOutHistoryDto history : inOutHistories) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            CameraDto cameraDto = history.getCamera();
            AreaRestrictionDto areaRestrictionDto = history.getCamera() == null ? null : history.getCamera().getAreaRestriction();
            EmployeeDto employeeDto = history.getEmployee();
            EmployeeDto managerDto = history.getEmployee() == null ? null : history.getEmployee().getManager();

            createCell(row, columnCount++, cameraDto != null ? cameraDto.getName() : "", style);
            createCell(row, columnCount++, areaRestrictionDto != null ? areaRestrictionDto.getName() : "", style);
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
