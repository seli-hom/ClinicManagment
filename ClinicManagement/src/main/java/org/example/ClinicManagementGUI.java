package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ClinicManagementGUI extends JFrame {
    private JPanel clinicManagementPanel;
    private JTabbedPane tabbedPane;
    private JPanel patientTab;
    private JPanel doctorTab;
    private JPanel appointmentTab;
    private JPanel recordTab;

    private JButton addPatientButton;
    private JButton modifyPatientButton;
    private JButton findPatientButton;
    private JButton viewPatientsButton;
    private JButton findPatientRecordButton;

    private JButton bookAppointmentButton;
    private JButton rescheduleAppointmentButton;
    private JButton cancelAppointmentButton;
    private JButton findAppointmentButton;
    private JButton viewAppointmentsButton;

    private JButton addDoctorButton;
    private JButton modifyDoctorButton;
    private JButton findDoctorButton;
    private JButton findDoctorBySpecialtyButton;
    private JButton viewDoctorsButton;

    private JTable patientTable;
    private JTable doctorTable;
    private JTable appointmentTable;
    private JTable recordTable;

    private DoctorDAO doctorDAO = new DoctorDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    public ClinicManagementGUI() {
        setTitle("Clinic Management System");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel
        clinicManagementPanel = new JPanel(new BorderLayout());
        setContentPane(clinicManagementPanel);

        // Tabs
        tabbedPane = new JTabbedPane();

        setUpPatientTab();
        setUpDoctorTab();
        setUpAppointmentTab();
        setUpRecordTab();

        clinicManagementPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    public void setUpPatientTab() {
        patientTab = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        addPatientButton = new JButton("Add Patient");
        modifyPatientButton = new JButton("Modify Patient");
        findPatientButton = new JButton("Find Patient");
        buttonPanel.add(addPatientButton);
        buttonPanel.add(modifyPatientButton);
        buttonPanel.add(findPatientButton);

        patientTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(patientTable);

        patientTab.add(buttonPanel, BorderLayout.NORTH);
        patientTab.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Patients", patientTab);
    }

    public void setUpDoctorTab() {
        doctorTab = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        addDoctorButton = new JButton("Add Doctor");
        modifyDoctorButton = new JButton("Modify Doctor");
        findDoctorButton = new JButton("Find Doctor By ID");
        findDoctorBySpecialtyButton = new JButton("Find Doctor By Specialty");
        buttonPanel.add(addDoctorButton);
        buttonPanel.add(modifyDoctorButton);
        buttonPanel.add(findDoctorButton);
        buttonPanel.add(findDoctorBySpecialtyButton);

        doctorTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(doctorTable);

        doctorTab.add(buttonPanel, BorderLayout.NORTH);
        doctorTab.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Doctors", doctorTab);
    }

    public void setUpAppointmentTab() {
        appointmentTab = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        bookAppointmentButton = new JButton("Book Appointment");
        rescheduleAppointmentButton = new JButton("Reschedule Appointment");
        cancelAppointmentButton = new JButton("Cancel Appointment");
        findAppointmentButton = new JButton("Find Appointment");
        buttonPanel.add(bookAppointmentButton);
        buttonPanel.add(rescheduleAppointmentButton);
        buttonPanel.add(cancelAppointmentButton);
        buttonPanel.add(findAppointmentButton);

        appointmentTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(appointmentTable);

        appointmentTab.add(buttonPanel, BorderLayout.NORTH);
        appointmentTab.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Appointments", appointmentTab);
    }

    public void setUpRecordTab() {
        recordTab = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        findPatientRecordButton = new JButton("Find Record");
        buttonPanel.add(findPatientRecordButton);

        recordTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(patientTable);

        recordTab.add(buttonPanel, BorderLayout.NORTH);
        patientTab.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Records", recordTab);
    }

    public void setUpPatientTable() {
        String[] columnNames = {"ID", "First Name", "Last Name", "Address", "Contact", "DOB", "Sex", "Blood Type"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        patientTable.setModel(model);
        updatePatientTable();
    }

    public void updatePatientTable() {
        List<Patient> patients = patientDAO.getAllPatients();
        DefaultTableModel model = (DefaultTableModel) patientTable.getModel();
        model.setRowCount(0);
        for (Patient patient : patients) {
            model.addRow(new Object[]{
                    patient.getPatientId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getAddress(),
                    patient.getContact(),
                    patient.getDob(),
                    patient.getSex(),
                    patient.getBloodType()
            });
        }
    }

    public void setUpDoctorTable() {
        String[] columnNames = {"ID", "First Name", "Last Name", "Contact", "Specialty"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        doctorTable.setModel(model);
        updateDoctorTable();
    }

    public void updateDoctorTable() {
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        DefaultTableModel model = (DefaultTableModel) doctorTable.getModel();
        model.setRowCount(0);
        for (Doctor doctor : doctors) {
            model.addRow(new Object[]{
                    doctor.getDoctorId(),
                    doctor.getFirstName(),
                    doctor.getLastName(),
                    doctor.getContact(),
                    doctor.getSpeciality()
            });
        }
    }

    public void setUpAppointmentTable() {
        String[] columnNames = {"ID", "Patient ID", "Doctor ID", "Date", "Time"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        appointmentTable.setModel(model);
        updateAppointmentTable();
    }

    public void updateAppointmentTable() {
        List<Appointment> appointments = appointmentDAO.getAllAppointments();
        DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
        model.setRowCount(0);
        for (Appointment appointment : appointments) {
            model.addRow(new Object[]{
                    appointment.getAppointmentId(),
                    appointment.getPatientId(),
                    appointment.getDoctorId(),
                    appointment.getDate(),
                    appointment.getTime()
            });
        }
    }

    public void setUpRecordTable() {
        String[] columnNames = {"Record ID", "Patient ID", "Doctor ID", "Diagnosis"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        recordTable.setModel(model);
    }

    public void updateRecordTable() {
        List<Appointment> appointments = appointmentDAO.getAllAppointments();
        DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
        model.setRowCount(0);
        for (Appointment appointment : appointments) {
            model.addRow(new Object[]{
                    appointment.getAppointmentId(),
                    appointment.getPatientId(),
                    appointment.getDoctorId(),
                    appointment.getDate(),
                    appointment.getTime()
            });
        }
    }

    // Getters for all GUI elements
    public JButton getAddPatientButton() { return addPatientButton; }
    public JButton getModifyPatientButton() { return modifyPatientButton; }
    public JButton getFindPatientButton() { return findPatientButton; }
    public JButton getViewPatientsButton() { return viewPatientsButton; }
    public JButton getBookAppointmentButton() { return bookAppointmentButton; }
    public JButton getRescheduleAppointmentButton() { return rescheduleAppointmentButton; }
    public JButton getCancelAppointmentButton() { return cancelAppointmentButton; }
    public JButton getFindAppointmentButton() { return findAppointmentButton; }
    public JButton getViewAppointmentsButton() { return viewAppointmentsButton; }
    public JButton getFindPatientRecordButton() { return findPatientRecordButton; }
    public JButton getAddDoctorButton() { return addDoctorButton; }
    public JButton getModifyDoctorButton() { return modifyDoctorButton; }
    public JButton getFindDoctorButton() { return findDoctorButton; }
    public JButton getFindDoctorBySpecialtyButton() { return findDoctorBySpecialtyButton; }
    public JButton getViewDoctorsButton() { return viewDoctorsButton; }
    public JTable getPatientTable() { return patientTable; }
    public JTable getDoctorTable() { return doctorTable; }
    public JTable getAppointmentTable() { return appointmentTable; }
    public JTable getRecordTable() { return recordTable; }
    public JPanel getClinicManagementPanel() { return clinicManagementPanel; }
    public DoctorDAO getDoctorDAO() { return doctorDAO; }
    public PatientDAO getPatientDAO() { return patientDAO; }
    public AppointmentDAO getAppointmentDAO() { return appointmentDAO; }
}
