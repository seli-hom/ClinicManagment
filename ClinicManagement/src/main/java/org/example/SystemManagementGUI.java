package org.example;

import javax.swing.*;

public class SystemManagementGUI {
    private JTabbedPane TabbedPane;
    private JPanel panel1;
    private JTable patientDisplayTable;
    private JTextField patientFirstNameTextField;
    private JTextField patientLastNameTextField;
    private JTextField patientAddressTextField;
    private JTextField patientContactTextField;
    private JPanel patientTab;
    private JPanel doctorTab;
    private JPanel appointmentTab;
    private JPanel recordTab;
    private JLabel patientFirstNameLabel;
    private JLabel patientLastNameLabel;
    private JLabel patientAddressLabel;
    private JLabel patientContactLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clinic Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);
    }
}
