package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ClinicManagementGUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel clinicManagementPanel;
    private JButton addPatientButton;
    private JButton modifyPatientButton;
    private JButton findPatientButton;
    private JButton bookAppointmentButton;
    private JButton rescheduleAppointmentButton;
    private JButton cancelAppointmentButton;
    private JButton findAppointmentButton;
    private JButton findPatientRecordButton;
    private JButton addDoctorButton;
    private JButton modifyDoctorButton;
    private JButton findDoctorButton;
    private JButton findDoctorBySpecialtyButton;
    private JTable patientTable;
    private JTable doctorTable;
    private JTable appointmentTable;
    private JTable recordTable;
    private JPanel patientTab;
    private JPanel doctorTab;
    private JPanel appointmentTab;
    private JPanel recordTab;

    private DoctorDAO doctorDAO = new DoctorDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    public ClinicManagementGUI() {
        initComponents();
        setUpPatientTable();
        setUpDoctorTable();
        setUpAppointmentTable();
        setUpRecordTable();

        SystemManager model = new SystemManager();
        new ClinicController(this, model);
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();
        clinicManagementPanel = new JPanel();
        patientTab = new JPanel();
        doctorTab = new JPanel();
        appointmentTab = new JPanel();
        recordTab = new JPanel();

        addPatientButton = new JButton("Add Patient");
        modifyPatientButton = new JButton("Modify Patient");
        findPatientButton = new JButton("Find Patient");
        bookAppointmentButton = new JButton("Book Appointment");
        rescheduleAppointmentButton = new JButton("Reschedule Appointment");
        cancelAppointmentButton = new JButton("Cancel Appointment");
        findAppointmentButton = new JButton("Find Appointment");
        findPatientRecordButton = new JButton("Find Record");
        addDoctorButton = new JButton("Add Doctor");
        modifyDoctorButton = new JButton("Modify Doctor");
        findDoctorButton = new JButton("Find Doctor");
        findDoctorBySpecialtyButton = new JButton("Find by Specialty");

        patientTable = new JTable();
        doctorTable = new JTable();
        appointmentTable = new JTable();
        recordTable = new JTable();

        // Set layout and add components to patientTab
        patientTab.setLayout(new BoxLayout(patientTab, BoxLayout.Y_AXIS));
        patientTab.add(addPatientButton);
        patientTab.add(modifyPatientButton);
        patientTab.add(findPatientButton);
        patientTab.add(new JScrollPane(patientTable));

        // Set layout and add components to doctorTab
        doctorTab.setLayout(new BoxLayout(doctorTab, BoxLayout.Y_AXIS));
        doctorTab.add(addDoctorButton);
        doctorTab.add(modifyDoctorButton);
        doctorTab.add(findDoctorButton);
        doctorTab.add(findDoctorBySpecialtyButton);
        doctorTab.add(new JScrollPane(doctorTable));

        // Set layout and add components to appointmentTab
        appointmentTab.setLayout(new BoxLayout(appointmentTab, BoxLayout.Y_AXIS));
        appointmentTab.add(bookAppointmentButton);
        appointmentTab.add(rescheduleAppointmentButton);
        appointmentTab.add(cancelAppointmentButton);
        appointmentTab.add(findAppointmentButton);
        appointmentTab.add(new JScrollPane(appointmentTable));

        // Set layout and add components to recordTab
        recordTab.setLayout(new BoxLayout(recordTab, BoxLayout.Y_AXIS));
        recordTab.add(findPatientRecordButton);
        recordTab.add(new JScrollPane(recordTable));

        // Add tabs
        tabbedPane.addTab("Patients", patientTab);
        tabbedPane.addTab("Doctors", doctorTab);
        tabbedPane.addTab("Appointments", appointmentTab);
        tabbedPane.addTab("Records", recordTab);

        clinicManagementPanel.setLayout(new BoxLayout(clinicManagementPanel, BoxLayout.Y_AXIS));
        clinicManagementPanel.add(tabbedPane);
        setContentPane(clinicManagementPanel);
    }

    private void setUpPatientTable() {
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

    private void setUpDoctorTable() {
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

    private void setUpAppointmentTable() {
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

    private void setUpRecordTable() {
        String[] columnNames = {"Record ID", "Patient ID", "Doctor ID", "Diagnosis"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        recordTable.setModel(model);
    }

    // --- Getters for Controller ---
    public JButton getAddPatientButton() { return addPatientButton; }
    public JButton getModifyPatientButton() { return modifyPatientButton; }
    public JButton getFindPatientButton() { return findPatientButton; }
    public JButton getBookAppointmentButton() { return bookAppointmentButton; }
    public JButton getRescheduleAppointmentButton() { return rescheduleAppointmentButton; }
    public JButton getCancelAppointmentButton() { return cancelAppointmentButton; }
    public JButton getFindAppointmentButton() { return findAppointmentButton; }
    public JButton getFindPatientRecordButton() { return findPatientRecordButton; }
    public JButton getAddDoctorButton() { return addDoctorButton; }
    public JButton getModifyDoctorButton() { return modifyDoctorButton; }
    public JButton getFindDoctorButton() { return findDoctorButton; }
    public JButton getFindDoctorBySpecialtyButton() { return findDoctorBySpecialtyButton; }
    public JTable getPatientTable() { return patientTable; }
    public JTable getDoctorTable() { return doctorTable; }
    public JTable getAppointmentTable() { return appointmentTable; }
    public JTable getRecordTable() { return recordTable; }
    public JPanel getClinicManagementPanel() { return clinicManagementPanel; }
    public DoctorDAO getDoctorDAO() { return doctorDAO; }
    public PatientDAO getPatientDAO() { return patientDAO; }
    public AppointmentDAO getAppointmentDAO() { return appointmentDAO; }
}
