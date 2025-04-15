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
    private JTextField patientNameOrIdTextField;
    private JLabel doctorFirstNameLabel;
    private JLabel doctorLastNameLabel;
    private JLabel doctorSpecialtyLabel;
    private JLabel doctorContactLabel;
    private JTextField doctorFirstNameTextField;
    private JTextField doctorLastNameTextField;
    private JTextField doctorSpecialtyTextField;
    private JTextField doctorContactTextField;
    private JLabel patientIdLabel;
    private JLabel doctorIdLabel;
    private JLabel dateLabel;
    private JLabel timeLabel;
    private JTextField patientIdTextField;
    private JTextField doctorIdTextField;
    private JTextField dateTextField;
    private JTextField timeTextField;
    private JButton recordSearchButton;
    private JLabel patientNameOrIdLabel;
    private JTable table1;
    private JButton addPatientButton;
    private JButton dischargePatientButton;
    private JButton modifyPatientButton;
    private JButton addDoctorButton;
    private JButton modifyDoctorButton;
    private JButton bookAppointmentButton;
    private JButton rescheduleAppointmentButton;
    private JButton cancelAppointmentButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clinic Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
