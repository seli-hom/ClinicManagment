package org.example;

import javax.swing.*;

public class ClinicManagementGUI {
    private JTabbedPane tabbedPane;
    private JPanel clinicManagementPanel;
    private JButton addPatientButton;
    private JButton modifyPatientButton;
    private JTable patientTable;
    private JTable doctorTable;
    private JButton findPatientButton;
    private JButton bookAppointmentButton;
    private JTable appointmentTable;
    private JButton rescheduleAppointmentButton;
    private JButton cancelAppointmentButton;
    private JButton findAppointmentButton;
    private JButton findPatientRecordButton;
    private JTable recordTable;
    private JPanel patientTab;
    private JPanel doctorTab;
    private JPanel appointmentTab;
    private JPanel recordTab;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clinic Management System");
        frame.setContentPane(new ClinicManagementGUI().clinicManagementPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
