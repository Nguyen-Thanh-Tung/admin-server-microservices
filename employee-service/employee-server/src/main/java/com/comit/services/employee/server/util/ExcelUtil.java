package com.comit.services.employee.server.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    public Map<Integer, List<String>> readExcel(MultipartFile file) throws IOException {

        Map<Integer, List<String>> data = new HashMap<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        int i = 0;
        for (Row row : sheet) {
            data.put(i, new ArrayList<String>());
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        data.get(i)
                                .add(cell.getRichStringCellValue()
                                        .getString());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            data.get(i)
                                    .add(cell.getDateCellValue() + "");
                        } else {
                            data.get(i)
                                    .add((int) cell.getNumericCellValue() + "");
                        }
                        break;
                    case BOOLEAN:
                        data.get(i)
                                .add(cell.getBooleanCellValue() + "");
                        break;
                    case FORMULA:
                        data.get(i)
                                .add(cell.getCellFormula() + "");
                        break;
                    default:
                        data.get(i)
                                .add(" ");
                }
            }
            i++;
        }
        workbook.close();
        return data;
    }

}
