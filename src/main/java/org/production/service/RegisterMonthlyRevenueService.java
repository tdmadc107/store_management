package org.production.service;

import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.production.common.StoreManagementUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.production.common.Constant.CURRENT_DATE;
import static org.production.common.Constant.FILE_NAME;

@Service
@Data
public class RegisterMonthlyRevenueService {

    @Autowired
    private final StoreManagementUtils storeManagementUtils;

    public Workbook execute(String sheetName) {

        Workbook workbook = new XSSFWorkbook();
        workbook.createSheet(sheetName);
        workbook.setSheetOrder(sheetName, 0);

        return workbook;
    }
}
