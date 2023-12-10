package org.production.common;

import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.production.common.Constant.*;

@Component
@Data
public class StoreManagementUtils {

    public FileInputStream openFile(String fileName) throws FileNotFoundException {

        String fileDir = LOCATION_FILE_REVENUE.concat(formatterYYYYMM(CURRENT_DATE)).concat("/");
        File filePath = new File(fileDir);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        return new FileInputStream(fileDir.concat(fileName));
    }

    public String formatterYYYYMM(LocalDate date) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(YYYYMM);
        return dateTimeFormatter.format(date);
    }

    public String formatterYYYYMMDD(LocalDate date) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(YYYYMMDD);
        return dateTimeFormatter.format(date);
    }

    public String formatterDateTime(LocalDateTime dateTime) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(YYYYMMDDhhmmss);
        return dateTimeFormatter.format(dateTime);
    }

    public void writeFile(Workbook workbook, String fileDir) throws IOException {

        FileOutputStream outputStream = new FileOutputStream(fileDir);
        workbook.write(outputStream);
        workbook.close();
    }

    public CellStyle createStyleForHeader(Sheet sheet) {

        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    public CellStyle createStyleForBody(Sheet sheet) {

        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setFontHeightInPoints((short) 13); // font size
        font.setColor(IndexedColors.BLACK.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        DataFormat format = sheet.getWorkbook().createDataFormat();
        cellStyle.setDataFormat(format.getFormat("#,###"));
        return cellStyle;
    }

    public void writeRevenue(Sheet sheet, int rowIndex, Row row) {

        String colTotal = CellReference.convertNumToColString(COLUMN_INDEX_TOTAL);

        Cell cell = sheet.getRow(1).getCell(COLUMN_INDEX_DAILY_REVENUE);

        if (cell == null) {
            cell = row.createCell(COLUMN_INDEX_DAILY_REVENUE, CellType.FORMULA);
        }

        cell.setCellFormula("SUM(" + colTotal + "2:" + colTotal + ++rowIndex + ")");

        CellStyle cellStyle = createStyleForHeader(sheet);
        cellStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderRight(BorderStyle.NONE);
        DataFormat format = sheet.getWorkbook().createDataFormat();
        cellStyle.setDataFormat(format.getFormat("#,###"));
        cell.setCellStyle(cellStyle);

        if (sheet.getMergedRegions() != null) {
            sheet.removeMergedRegion(sheet.getNumMergedRegions());
        }

        if (--rowIndex > 1) {
            sheet.addMergedRegion(new CellRangeAddress(1, rowIndex, COLUMN_INDEX_DAILY_REVENUE,
                    COLUMN_INDEX_DAILY_REVENUE));
        }
    }

    public void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(COLUMN_VALUE_ID);

        cell = row.createCell(COLUMN_INDEX_CUSTOMER);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(COLUMN_VALUE_CUSTOMER);

        cell = row.createCell(COLUMN_INDEX_PRODUCT_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(COLUMN_VALUE_PRODUCT_NAME);

        cell = row.createCell(COLUMN_INDEX_UNIT_PRICE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(COLUMN_VALUE_UNIT_PRICE);

        cell = row.createCell(COLUMN_INDEX_QUANTITY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(COLUMN_VALUE_QUANTITY);

        cell = row.createCell(COLUMN_INDEX_TOTAL);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(COLUMN_VALUE_TOTAL);

        cell = row.createCell(COLUMN_INDEX_PAID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(COLUMN_VALUE_PAID);

        cell = row.createCell(COLUMN_INDEX_DAILY_REVENUE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(COLUMN_VALUE_DAILY_REVENUE);
    }

    // Auto resize column width
    public void autosizeColumn(Sheet sheet) {
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        for (int columnIndex = 0; columnIndex < numberOfColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    //Collect data input
    public Map<String, String> getData(String productName, Object unitPrice, Object quantity, String customer, boolean isPaid) {
        Integer price = (Integer) unitPrice;
        Integer amount = (Integer) quantity;

        if (productName.isEmpty() || customer.isEmpty()) {
            throw new NullPointerException();
        }

        LocalDateTime dateTime = LocalDateTime.now();
        Map<String, String> data = new HashMap<>();
        data.put(COLUMN_VALUE_ID, formatterDateTime(dateTime));
        data.put(COLUMN_VALUE_PRODUCT_NAME, productName);
        data.put(COLUMN_VALUE_UNIT_PRICE, String.valueOf(unitPrice));
        data.put(COLUMN_VALUE_QUANTITY, String.valueOf(quantity));
        data.put(COLUMN_VALUE_CUSTOMER, customer);
        data.put(COLUMN_VALUE_PAID, isPaid ? "Done" : "Not Yet");
        data.put(COLUMN_VALUE_TOTAL, String.valueOf(price * amount));
        return data;
    }

    public boolean checkPayment(ButtonGroup btnGroup) {
        return "paid".equalsIgnoreCase(btnGroup.getSelection().getActionCommand());
    }

    // Write data
    public void writeDailyRevenue(Map<String, String> data, Sheet sheet, Row row) {
        CellStyle cellStyle = createStyleForBody(sheet);

        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.get(COLUMN_VALUE_ID));

        cell = row.createCell(COLUMN_INDEX_CUSTOMER);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.get(COLUMN_VALUE_CUSTOMER));

        cell = row.createCell(COLUMN_INDEX_PRODUCT_NAME);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.get(COLUMN_VALUE_PRODUCT_NAME));

        cell = row.createCell(COLUMN_INDEX_UNIT_PRICE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(Integer.parseInt(data.get(COLUMN_VALUE_UNIT_PRICE)));

        cell = row.createCell(COLUMN_INDEX_QUANTITY);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.get(COLUMN_VALUE_QUANTITY));

        cell = row.createCell(COLUMN_INDEX_TOTAL);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(Integer.parseInt(data.get(COLUMN_VALUE_TOTAL)));

        cell = row.createCell(COLUMN_INDEX_PAID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(data.get(COLUMN_VALUE_PAID));
    }
}

