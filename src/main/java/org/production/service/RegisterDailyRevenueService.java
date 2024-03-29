package org.production.service;

import lombok.Data;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.production.common.StoreManagementUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

@Service
@Data
public class RegisterDailyRevenueService {

    @Autowired
    private final StoreManagementUtils storeManagementUtils;

    public void execute(Component component, Workbook workbook, Map<String, String> data, String fileDir,
                        String sheetName) throws IOException {

        Sheet sheet = workbook.getSheetAt(0);
        if (sheet.getSheetName().equalsIgnoreCase(sheetName)) {
            if (sheet.getPhysicalNumberOfRows() == 0) {
                storeManagementUtils.writeHeader(sheet, 0);
            }
            storeManagementUtils.writeFileUtil(sheet, data);
        } else {
            Sheet sheetNew = workbook.createSheet(sheetName);
            workbook.setSheetOrder(sheetName, 0);
            storeManagementUtils.writeHeader(sheetNew, 0);
            storeManagementUtils.writeFileUtil(sheetNew, data);
        }
        sheet.protectSheet("admin");
        storeManagementUtils.writeFile(workbook, fileDir);
        JOptionPane.showMessageDialog(component, "Nhập doanh thu thành công");
    }
}
