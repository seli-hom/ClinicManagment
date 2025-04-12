package org.example;

import javax.swing.*;

public class ClinicManagerGUI extends JFrame {
    private JPanel clinicManagementSystemJLabel;
    private JTabbedPane tabbedPane;
    private JTabbedPane tabbedPane2;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField4;

    public ClinicManagerGUI() {
        setTitle("Clinic Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ClinicManagerGUI();
    }
}
