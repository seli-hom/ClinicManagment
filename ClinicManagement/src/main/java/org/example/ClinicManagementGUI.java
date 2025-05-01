package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

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

    private SystemManager systemManager = new SystemManager();

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

        addPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a dialog to add a new patient
                String firstName = JOptionPane.showInputDialog("Enter patient's first name:");
                String lastName = JOptionPane.showInputDialog("Enter patient's last name:");
                String address = JOptionPane.showInputDialog("Enter patient's address:");
                String contact = JOptionPane.showInputDialog("Enter patient's contact information:");
                String dob = JOptionPane.showInputDialog("Enter patient's date of birth:");
                String sex = JOptionPane.showInputDialog("Enter patient's sex:");
                String bloodType = JOptionPane.showInputDialog("Enter patient's blood type:");
                Patient patient = new Patient(firstName, lastName, address, contact, dob, sex, bloodType);

                systemManager.registerPatient(patient);
                updatePatientTable();
            }
        });

        modifyPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Modify patient details
                int selectedRow = patientTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int patientId = (int) patientTable.getValueAt(selectedRow, 0);
                    Patient patient = PatientDAO.getPatientById(patientId);

                    String newName = JOptionPane.showInputDialog("Enter new name:", patient.getName());
                    int newAge = Integer.parseInt(JOptionPane.showInputDialog("Enter new age:", patient.getAge()));
                    patient.setName(newName);
                    patient.setAge(newAge);

                    if (PatientDAO.modifyPatient(patient)) {
                        updatePatientTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to modify patient.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No patient selected.");
                }
            }
        });

        findPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Find patient by name
                String searchName = JOptionPane.showInputDialog("Enter patient name:");
                ArrayList<Patient> patients = PatientDAO.findPatientByName(searchName);
                if (patients.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No patient found.");
                } else {
                    updatePatientTable(patients);
                }
            }
        });

        bookAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Book appointment with a doctor
                int selectedPatientRow = patientTable.getSelectedRow();
                int selectedDoctorRow = doctorTable.getSelectedRow();

                if (selectedPatientRow >= 0 && selectedDoctorRow >= 0) {
                    int patientId = (int) patientTable.getValueAt(selectedPatientRow, 0);
                    int doctorId = (int) doctorTable.getValueAt(selectedDoctorRow, 0);

                    String dateTime = JOptionPane.showInputDialog("Enter appointment date and time (yyyy-MM-dd HH:mm):");
                    AppointmentDAO bookAppt = new AppointmentDAO(patientId, doctorId, dateTime);

                    if (bookAppt.bookAppointment()) {
                        JOptionPane.showMessageDialog(null, "Appointment booked successfully!");
                        updateAppointmentTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to book appointment.");
                    }
                }
            }
        });

        rescheduleAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reschedule appointment
                int selectedRow = appointmentTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int appointmentId = (int) appointmentTable.getValueAt(selectedRow, 0);
                    String newDateTime = JOptionPane.showInputDialog("Enter new appointment date and time (yyyy-MM-dd HH:mm):");

                    if (AppointmentDAO.rescheduleAppointment(appointmentId, newDateTime)) {
                        JOptionPane.showMessageDialog(null, "Appointment rescheduled.");
                        updateAppointmentTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to reschedule appointment.");
                    }
                }
            }
        });

        cancelAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cancel appointment
                int selectedRow = appointmentTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int appointmentId = (int) appointmentTable.getValueAt(selectedRow, 0);

                    if (AppointmentDAO.cancelAppointment(appointmentId)) {
                        JOptionPane.showMessageDialog(null, "Appointment canceled.");
                        updateAppointmentTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to cancel appointment.");
                    }
                }
            }
        });

        findAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Find appointments for a patient
                int selectedRow = patientTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int patientId = (int) patientTable.getValueAt(selectedRow, 0);
                    ArrayList<Appointment> appointments = AppointmentDAO.getAppointmentsByPatientId(patientId);
                    updateAppointmentTable(appointments);
                }
            }
        });
    }

    private void setUpPatientTable() {
        String[] columnNames = {"ID", "Name", "Age"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        patientTable.setModel(model);
        updatePatientTable();
    }

    private void updatePatientTable() {
        ArrayList<Patient> patients = PatientDAO.getAllPatients();
        DefaultTableModel model = (DefaultTableModel) patientTable.getModel();
        model.setRowCount(0);
        for (Patient patient : patients) {
            model.addRow(new Object[]{patient.getId(), patient.getName(), patient.getAge()});
        }
    }

    private void updatePatientTable(ArrayList<Patient> patients) {
        DefaultTableModel model = (DefaultTableModel) patientTable.getModel();
        model.setRowCount(0);
        for (Patient patient : patients) {
            model.addRow(new Object[]{patient.getId(), patient.getName(), patient.getAge()});
        }
    }

    private void setUpDoctorTable() {
        String[] columnNames = {"ID", "Name", "Specialty"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        doctorTable.setModel(model);
        updateDoctorTable();
    }

    private void updateDoctorTable() {
        ArrayList<Doctor> doctors = DoctorDAO.getAllDoctors();
        DefaultTableModel model = (DefaultTableModel) doctorTable.getModel();
        model.setRowCount(0);
        for (Doctor doctor : doctors) {
            model.addRow(new Object[]{doctor.getId(), doctor.getName(), doctor.getSpecialty()});
        }
    }

    private void setUpAppointmentTable() {
        String[] columnNames = {"ID", "Patient ID", "Doctor ID", "Date & Time"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        appointmentTable.setModel(model);
        updateAppointmentTable();
    }

    private void updateAppointmentTable() {
        ArrayList<Appointment> appointments = AppointmentDAO.getAllAppointments();
        DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
        model.setRowCount(0);
        for (Appointment appointment : appointments) {
            model.addRow(new Object[]{appointment.getId(), appointment.getPatientId(), appointment.getDoctorId(), appointment.getDateTime()});
        }
    }

    private void setUpRecordTable() {
        String[] columnNames = {"Record ID", "Patient ID", "Doctor ID", "Diagnosis"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        recordTable.setModel(model);
        updateRecordTable();
    }

    private void updateRecordTable() {
        // Populate the record table (this part can be adjusted based on your logic)
        // Example to simulate records
        DefaultTableModel model = (DefaultTableModel) recordTable.getModel();
        model.setRowCount(0);
    }

}
