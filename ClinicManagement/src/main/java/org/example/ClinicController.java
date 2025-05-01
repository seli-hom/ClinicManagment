package org.example;

import javax.swing.*;
import java.awt.event.*;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClinicController {
    private ClinicManagementGUI view;
    private SystemManager model;

    public ClinicController(ClinicManagementGUI view, SystemManager model) {
        this.view = view;
        this.model = model;

        class AddPatientListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fname = JOptionPane.showInputDialog(Messages.getMessage("enter first name"));
                String lname = JOptionPane.showInputDialog(Messages.getMessage("enter first name"));
                String address = JOptionPane.showInputDialog(Messages.getMessage("enter first name"));
                String contact = JOptionPane.showInputDialog(Messages.getMessage("enter first name"));
                JSpinner dateForDob = new JSpinner(new SpinnerDateModel());
                JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
                dateForDob.setEditor(dateEditor);
                Date dob = (Date) dateForDob.getValue();
                JComboBox<String> sex = new JComboBox<>();
                sex.addItem("Male");
                sex.addItem("Female");
                sex.addItem("Other");
                JComboBox<String> bloodType = new JComboBox<>();
                bloodType.addItem("O+");
                bloodType.addItem("A+");
                bloodType.addItem("B+");
                bloodType.addItem("AB+");
                bloodType.addItem("O-");
                bloodType.addItem("A-");
                bloodType.addItem("B-");
                bloodType.addItem("AB-");

                Patient newPatient = new Patient(fname, lname, address, contact, dob, sex.getSelectedItem().toString(), bloodType.getSelectedItem().toString());

                model.registerPatient(newPatient);
            } //action performed method

        } // add Patient class
        this.view.getAddPatientButton().addActionListener(new AddPatientListener())

        //        addPatientButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Open a dialog to add a new patient
//                String firstName = JOptionPane.showInputDialog("Enter patient's first name:");
//                String lastName = JOptionPane.showInputDialog("Enter patient's last name:");
//                String address = JOptionPane.showInputDialog("Enter patient's address:");
//                String contact = JOptionPane.showInputDialog("Enter patient's contact information:");
//                String dob = JOptionPane.showInputDialog("Enter patient's date of birth:");
//                String sex = JOptionPane.showInputDialog("Enter patient's sex:");
//                String bloodType = JOptionPane.showInputDialog("Enter patient's blood type:");
//                Patient patient = new Patient(firstName, lastName, address, contact, dob, sex, bloodType);
//
//                systemManager.registerPatient(patient);
//                updatePatientTable();
//            }
//        });
    class ModifyPatientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Modify patient details
            int selectedRow = view.getPatientTable.getSelectedRow();
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
    }
    this.view.getModifyPatientButton().addActionListener(new ModifyPatientListener())

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
                    java.util.Date date = java.util.Date.parse(inputDate, dateFormatter.toString());
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

    }//end of constructor

}
