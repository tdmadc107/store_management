package org.production;

import org.production.layout.HomePage;

import javax.swing.*;

import static org.production.common.Constant.*;

public class StoreApplication {

    public static void main(String[] args) {

        HomePage homePage = new HomePage();

        homePage.setContentPane(homePage.getHomePage());
        homePage.setTitle(TITLE);
        homePage.setSize(WIDTH, HEIGHT);
        homePage.setVisible(TRUE);
        homePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}