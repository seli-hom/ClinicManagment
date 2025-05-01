package org.example;

import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ClinicManagementGUI extends JFrame {
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

    private SystemManager systemManager = new SystemManager();

    private DoctorDAO doctorDAO = new DoctorDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clinic Management System");
        frame.setContentPane(new ClinicManagementGUI().clinicManagementPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public ClinicManagementGUI() {
        setUpPatientTable();
        setUpDoctorTable();
        setUpAppointmentTable();
    }

    private void setUpPatientTable() {
        String[] columnNames = {"ID", "Name", "Age"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        patientTable.setModel(model);
        updatePatientTable();
    }

    private void updatePatientTable() {
        List<Patient> patients = patientDAO.getAllPatients();
        DefaultTableModel model = (DefaultTableModel) patientTable.getModel();
        model.setRowCount(0);
        for (Patient patient : patients) {
            model.addRow(new Object[]{patient.getPatientId(), patient.getFirstName(), patient.getLastName(), patient.getAddress(), patient.getContact(), patient.getDob(), patient.getSex(), patient.getBloodType()});
        }
    }

    private void updatePatientTable(ArrayList<Patient> patients) {
        DefaultTableModel model = (DefaultTableModel) patientTable.getModel();
        model.setRowCount(0);
        for (Patient patient : patients) {
            model.addRow(new Object[]{patient.getPatientId(), patient.getFirstName(), patient.getLastName(), patient.getAddress(), patient.getContact(), patient.getDob(), patient.getSex(), patient.getBloodType()});
        }
    }

    private void setUpDoctorTable() {
        String[] columnNames = {"ID", "Name", "Specialty"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        doctorTable.setModel(model);
        updateDoctorTable();
    }

    private void updateDoctorTable() {
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        DefaultTableModel model = (DefaultTableModel) doctorTable.getModel();
        model.setRowCount(0);
        for (Doctor doctor : doctors) {
            model.addRow(new Object[]{doctor.getDoctorId(), doctor.getFirstName(), doctor.getLastName(), doctor.getContact(), doctor.getSpeciality()});
        }
    }

    private void setUpAppointmentTable() {
        String[] columnNames = {"ID", "Patient ID", "Doctor ID", "Date & Time"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        appointmentTable.setModel(model);
        updateAppointmentTable();
    }

    private void updateAppointmentTable() {
        List<Appointment> appointments = appointmentDAO.getAllAppointments();
        DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
        model.setRowCount(0);
        for (Appointment appointment : appointments) {
            model.addRow(new Object[]{appointment.getAppointmentId(), appointment.getPatientId(), appointment.getDoctorId(), appointment.getDate(), appointment.getTime()});
        }
    }

    private void setUpRecordTable() {
        String[] columnNames = {"Record ID", "Patient ID", "Doctor ID", "Diagnosis"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        recordTable.setModel(model);
        updateRecordTable();
    }

    private void updateRecordTable() {
        DefaultTableModel model = (DefaultTableModel) recordTable.getModel();
        model.setRowCount(0);
    }

    public JButton getAddPatientButton() {
        return addPatientButton;
    }

    public JButton getModifyPatientButton() {
        return modifyPatientButton;
    }

    public JButton getFindPatientButton() {
        return findPatientButton;
    }

    public JButton getBookAppointmentButton() {
        return bookAppointmentButton;
    }

    public JButton getRescheduleAppointmentButton() {
        return rescheduleAppointmentButton;
    }

    public JButton getCancelAppointmentButton() {
        return cancelAppointmentButton;
    }

    public JButton getFindAppointmentButton() {
        return findAppointmentButton;
    }

    public JButton getFindPatientRecordButton() {
        return findPatientRecordButton;
    }
}
