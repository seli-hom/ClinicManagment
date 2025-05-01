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
    private JTable patientTable;
    private JTable doctorTable;
    private JButton findPatientButton;
    private JButton bookAppointmentButton;
    private JTable appointmentTable;
    private JButton rescheduleAppointmentButton;
    private JButton cancelAppointmentButton;
    private JButton findAppointmentButton;
    private JButton findPatientRecordButton;
    private JButton addDoctorButton;
    private JButton modifyDoctorButton;
    private JButton findDoctorButton;
    private JButton findDoctorBySpecialtyButton;
    private JTable recordTable;
    private JPanel patientTab;
    private JPanel doctorTab;
    private JPanel appointmentTab;
    private JPanel recordTab;

//    private SystemManager systemManager = new SystemManager();

    private DoctorDAO doctorDAO = new DoctorDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    public ClinicManagementGUI() {
        setUpPatientTable();
        setUpDoctorTable();
        setUpAppointmentTable();

        SystemManager model = new SystemManager();
        ClinicController controller = new ClinicController(this, model);
    }

    private void setUpPatientTable() {
        String[] columnNames = {"ID", "Name", "Age"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        patientTable.setModel(model);
        updatePatientTable();
    }

    public void updatePatientTable() {
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

    public void updateDoctorTable() {
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

    public void updateAppointmentTable() {
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

    public JButton getAddDoctorButton() {
        return addDoctorButton;
    }

    public JButton getModifyDoctorButton() {
        return modifyDoctorButton;
    }

    public JButton getFindDoctorButton() {
        return findDoctorButton;
    }

    public JButton getFindDoctorBySpecialtyButton() {
        return findDoctorBySpecialtyButton;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JPanel getClinicManagementPanel() {
        return clinicManagementPanel;
    }

    public JTable getPatientTable() {
        return patientTable;
    }

    public JTable getDoctorTable() {
        return doctorTable;
    }

    public JTable getAppointmentTable() {
        return appointmentTable;
    }

    public JTable getRecordTable() {
        return recordTable;
    }

    public JPanel getPatientTab() {
        return patientTab;
    }

    public JPanel getDoctorTab() {
        return doctorTab;
    }

    public JPanel getAppointmentTab() {
        return appointmentTab;
    }

    public JPanel getRecordTab() {
        return recordTab;
    }

    public DoctorDAO getDoctorDAO() {
        return doctorDAO;
    }

    public PatientDAO getPatientDAO() {
        return patientDAO;
    }

    public AppointmentDAO getAppointmentDAO() {
        return appointmentDAO;
    }
}
