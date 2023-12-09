package org.production.common;

import java.time.LocalDate;

public class Constant {

    public static final String TITLE = "Store Management";
    public static final Integer WIDTH = 805;
    public static final Integer HEIGHT = 430;
    public static final Boolean TRUE = true;
    public static final Boolean FALSE = false;
    public static final String LOCATION_FILE_REVENUE = "D:/Store/Daily_Revenue/";
    public static final LocalDate CURRENT_DATE = LocalDate.now();
    public static final String FILE_NAME = "Daily_Revenue_";
    public static final String XLSX_TYPE = ".xlsx";
    public static final String YYYYMM = "YYYYMM";
    public static final String YYYYMMDD = "YYYYMMdd";
    public static final String YYYYMMDDhhmmss = "YYYYMMddhhmmss";
    public static final int COLUMN_INDEX_ID = 0;
    public static final int COLUMN_INDEX_CUSTOMER = 1;
    public static final int COLUMN_INDEX_PRODUCT_NAME = 2;
    public static final int COLUMN_INDEX_UNIT_PRICE = 3;
    public static final int COLUMN_INDEX_QUANTITY = 4;
    public static final int COLUMN_INDEX_TOTAL = 5;
    public static final int COLUMN_INDEX_PAID = 6;
    public static final int COLUMN_INDEX_DAILY_REVENUE = 7;
    public static final String COLUMN_VALUE_ID = "Id";
    public static final String COLUMN_VALUE_CUSTOMER = "Khách hàng";
    public static final String COLUMN_VALUE_PRODUCT_NAME = "Tên sản phẩm";
    public static final String COLUMN_VALUE_UNIT_PRICE = "Đơn giá";
    public static final String COLUMN_VALUE_QUANTITY = "Số lượng";
    public static final String COLUMN_VALUE_TOTAL = "Thành tiền";
    public static final String COLUMN_VALUE_DAILY_REVENUE = "Doanh thu trong ngày";
    public static final String COLUMN_VALUE_PAID = "Trạng thái thanh toán";
}
