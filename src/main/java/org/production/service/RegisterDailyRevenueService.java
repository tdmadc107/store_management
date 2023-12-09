package org.production.service;

import lombok.Data;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.production.common.StoreManagementUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@Service
@Data
public class RegisterDailyRevenueService {

    @Autowired
    private final StoreManagementUtils storeManagementUtils;

    public void execute(Workbook workbook, Map<String, String> data, String fileDir, String sheetName) throws IOException {
        int rowIndex = 0;

        Sheet sheet = workbook.getSheetAt(0);
        if (sheet.getSheetName().equalsIgnoreCase(sheetName)) {
            if (sheet.getPhysicalNumberOfRows() == 0) {
                storeManagementUtils.writeHeader(sheet, 0);
            }
            for (Row cells : sheet) {
                rowIndex++;
            }
            Row row = sheet.createRow(rowIndex);
            storeManagementUtils.writeDailyRevenue(data, sheet, row);
            storeManagementUtils.writeRevenue(sheet,rowIndex, row);
            storeManagementUtils.autosizeColumn(sheet);
        } else {
            Sheet sheetNew = workbook.createSheet(sheetName);
            workbook.setSheetOrder(sheetName, 0);
            storeManagementUtils.writeHeader(sheetNew, 0);
            for (Row cells : sheetNew) {
                rowIndex++;
            }
            Row row = sheetNew.createRow(rowIndex);
            storeManagementUtils.writeDailyRevenue(data, sheetNew, row);
            storeManagementUtils.writeRevenue(sheet,rowIndex, row);
            storeManagementUtils.autosizeColumn(sheetNew);
        }
        sheet.protectSheet("admin");
        storeManagementUtils.writeFile(workbook, fileDir);
    }
}
