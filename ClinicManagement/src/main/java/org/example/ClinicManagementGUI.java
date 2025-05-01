package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                    Patient patient = patientDAO.getPatientById(patientId);

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
                // Find patient by id
                String searchId = JOptionPane.showInputDialog("Enter patient id:");
                if (patientDAO.getPatientCache().equals(searchId)) {
                    patientTable.add(patientDAO.getPatientCache())
                    updatePatientTable();
                } else {
                    JOptionPane.showMessageDialog(null, "No patient found.");
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
                String inputDate = JOptionPane.showInputDialog("Enter the new date of the appointment (yyyy-MM-dd):");
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                String time = JOptionPane.showInputDialog("Enter the new time of the appointment (HH:mm):");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                try {
                    Date date = Date.parse(inputDate, dateFormatter.toString());
                    System.out.println("Parsed date: " + inputDate);


                }
                if (date != null && time != null) {
                    appointmentDAO.updateSchedule(date, time);
                }

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
                String id = JOptionPane.showInputDialog("Enter the id of the appointment to cancel:");
                if (id != null) {
                    appointmentDAO.cancelAppointment(id);
                    JOptionPane.showMessageDialog(null, "Appointment canceled.");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please fill out id field");
                }
            }
        });

        findAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog("Enter the id of the appointment to find:");
                if (id != null) {
                    appointmentDAO.getAppointmentById(id);
                    JOptionPane.showMessageDialog(null, "Appointment found");
                    updatePatientTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill out id field");
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

}
