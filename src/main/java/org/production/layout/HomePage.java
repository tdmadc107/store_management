package org.production.layout;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.production.common.StoreManagementUtils;
import org.production.service.RegisterDailyRevenueService;
import org.production.service.RegisterMonthlyRevenueService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import static org.production.common.Constant.*;

@EqualsAndHashCode(callSuper = true)
@Component
@Data
public class HomePage extends JFrame {

    private StoreManagementUtils storeManagementUtils = new StoreManagementUtils();

    private RegisterMonthlyRevenueService registerMonthlyRevenueService = new RegisterMonthlyRevenueService(storeManagementUtils);

    private RegisterDailyRevenueService registerDailyRevenueService = new RegisterDailyRevenueService(storeManagementUtils);

    private JTextField productName;
    private JSpinner unitPrice;
    private JSpinner quantity;
    private JTextField customer;
    private JRadioButton paidRadioButton;
    private JRadioButton unpaidRadioButton;
    private ButtonGroup buttonPaymentGroup;
    private JButton enterOrderButton;
    private JPanel homePage;

    public HomePage() {
        enterOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String fileName = FILE_NAME.concat(storeManagementUtils.formatterYYYYMM(CURRENT_DATE)).concat(XLSX_TYPE);
                    String fileDir =
                            LOCATION_FILE_REVENUE.concat(storeManagementUtils.formatterYYYYMM(CURRENT_DATE)).concat(
                                    "/").concat(fileName);
                    String sheetName = FILE_NAME.concat(storeManagementUtils.formatterYYYYMMDD(CURRENT_DATE));
                    boolean isFileAlready = true;

                    // Get is payment
                    buttonPaymentGroup = new ButtonGroup();
                    buttonPaymentGroup.add(paidRadioButton);
                    buttonPaymentGroup.add(unpaidRadioButton);
                    boolean isPayment = storeManagementUtils.checkPayment(buttonPaymentGroup);

                    // Get data from application
                    Map<String, String> data = storeManagementUtils.getData(getProductName().getText(), getUnitPrice().getValue(),
                            getQuantity().getValue(), getCustomer().getText(), isPayment);

                    try {
                        FileInputStream file = storeManagementUtils.openFile(fileName);
                        Workbook workbookAlready = new XSSFWorkbook(file);
                        registerDailyRevenueService.execute(homePage, workbookAlready, data, fileDir,
                                sheetName);
                    } catch (IOException ex) {
                        isFileAlready = false;
                    }

                    if (!isFileAlready) {
                        Workbook workbookNew = registerMonthlyRevenueService.execute(sheetName);
                        try {
                            registerDailyRevenueService.execute(homePage, workbookNew, data, fileDir,
                                    sheetName);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(homePage, ex.getMessage());
                        }
                    }
                } catch (NullPointerException exception) {
                    JOptionPane.showMessageDialog(homePage, "Vui lòng nhập đầy đủ tất cả các mục !!!");
                }
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        homePage = new JPanel();
        homePage.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        homePage.setAutoscrolls(true);
        final JTabbedPane tabbedPane1 = new JTabbedPane();
        tabbedPane1.setAlignmentX(1.0f);
        tabbedPane1.setAlignmentY(1.0f);
        tabbedPane1.setAutoscrolls(true);
        tabbedPane1.setInheritsPopupMenu(true);
        tabbedPane1.setName("");
        homePage.add(tabbedPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 3, new Insets(0, 50, 40, 50), 5, 5));
        panel1.setInheritsPopupMenu(true);
        panel1.setName("Store Management");
        panel1.setPreferredSize(new Dimension(800, 400));
        tabbedPane1.addTab("Nhập doanh thu", panel1);
        final JLabel label1 = new JLabel();
        label1.setText("Đơn giá");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(150, 60), new Dimension(150, 60), new Dimension(150, 60), 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Số lượng");
        panel1.add(label2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(150, 60), new Dimension(150, 60), new Dimension(150, 60), 0, false));
        productName = new JTextField();
        productName.setColumns(0);
        productName.setHorizontalAlignment(2);
        productName.setMargin(new Insets(2, 6, 2, 6));
        productName.setVerifyInputWhenFocusTarget(true);
        productName.setVisible(true);
        panel1.add(productName, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(525, 30), new Dimension(525, 30), new Dimension(525, 30), 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Tên sản phẩm");
        panel1.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(150, 60), new Dimension(150, 60), new Dimension(150, 60), 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Khách hàng");
        panel1.add(label4, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(150, 60), new Dimension(150, 60), new Dimension(150, 60), 0, false));
        customer = new JTextField();
        customer.setHorizontalAlignment(2);
        panel1.add(customer, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(525, 30), new Dimension(525, 30), new Dimension(525, 30), 0, false));
        paidRadioButton = new JRadioButton();
        paidRadioButton.setActionCommand("paid");
        paidRadioButton.setAutoscrolls(true);
        paidRadioButton.setHorizontalAlignment(2);
        paidRadioButton.setHorizontalTextPosition(11);
        paidRadioButton.setName("payment");
        paidRadioButton.setText("Đã thanh toán");
        panel1.add(paidRadioButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(124, 60), new Dimension(124, 60), new Dimension(124, 60), 0, false));
        unpaidRadioButton = new JRadioButton();
        unpaidRadioButton.setActionCommand("unpaid");
        unpaidRadioButton.setAutoscrolls(true);
        unpaidRadioButton.setHorizontalAlignment(2);
        unpaidRadioButton.setName("payment");
        unpaidRadioButton.setSelected(false);
        unpaidRadioButton.setText("Chưa thanh toán");
        panel1.add(unpaidRadioButton, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(124, 60), new Dimension(353, 60), new Dimension(124, 60), 0, false));
        unitPrice = new JSpinner();
        unitPrice.setDoubleBuffered(false);
        panel1.add(unitPrice, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(525, 30), new Dimension(525, 30), new Dimension(525, 30), 0, false));
        quantity = new JSpinner();
        panel1.add(quantity, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(525, 30), new Dimension(525, 30), new Dimension(525, 30), 0, false));
        enterOrderButton = new JButton();
        enterOrderButton.setText("Nhập doanh thu");
        panel1.add(enterOrderButton, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Nhập hàng", panel2);
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(paidRadioButton);
        buttonGroup.add(unpaidRadioButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return homePage;
    }
}
