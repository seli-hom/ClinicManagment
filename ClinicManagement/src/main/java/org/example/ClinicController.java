package org.example;

import javax.swing.*;
import java.awt.event.*;

import java.sql.Date;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;

public class ClinicController {
    private ClinicManagementGUI view;
    private SystemManager model;

    public ClinicController(ClinicManagementGUI view, SystemManager model) {
        this.view = view;
        this.model = model;

        setUpDoctorActions();
        setUpPatientActions();
        setUpAppointmentActions();
        setUpRecordActions();
    }

    public void setUpDoctorActions() {
        view.getAddDoctorButton().addActionListener(e -> {
            String firstName = JOptionPane.showInputDialog(("First Name:"));
            String lastName = JOptionPane.showInputDialog(("Last Name:"));
            String specialty = JOptionPane.showInputDialog(("Specialty:"));
            String contact = JOptionPane.showInputDialog(("Contact:"));

            if (firstName == null || lastName == null || contact == null || specialty == null) {
                JOptionPane.showMessageDialog(view, "Make sure all fields are filled out.");
            }

            Doctor doctor = new Doctor(firstName, lastName, specialty, contact);
            model.registerDoctor(doctor);
            view.updateDoctorTable();

            System.out.println(doctor.getDoctorId());
        });

        view.getModifyDoctorButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter the ID of the doctor to modify:");
            if (id != null) {
                String newContact = JOptionPane.showInputDialog("Enter new contact:");
                model.modifyDoctor(id, newContact);
                view.updateDoctorTable();
            } else {
                JOptionPane.showMessageDialog(view, "No doctor with the given ID exists.");
            }

        });

        view.getFindDoctorButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter the ID of the doctor to find:");
            Doctor doctor = model.findDoctor(id);

            if (doctor != null) {
                view.updateDoctorTable(List.of(doctor));
                JOptionPane.showMessageDialog(view, "Doctor found: " + doctor.getFirstName() + " " + doctor.getLastName());
            } else {
                JOptionPane.showMessageDialog(view, "No doctor with the given ID exists.");
            }
        });

        view.getFindDoctorBySpecialtyButton().addActionListener(e -> {
            String specialty = JOptionPane.showInputDialog("Specialty:");
            if (specialty != null) {
                List<Doctor> docs = model.findDoctorsBySpecialty(specialty);
                JOptionPane.showMessageDialog(view, "Found " + docs.size() + " doctors(s).");
            } else {
                JOptionPane.showMessageDialog(view, "No doctor with the given ID exists.");
            }
        });

        view.getViewDoctorsButton().addActionListener(e -> {
            view.updateDoctorTable(view.getDoctorDAO().getAllDoctors());
        });
    }

    public void setUpPatientActions() {
        view.getAddPatientButton().addActionListener(e -> {
            String firstName = JOptionPane.showInputDialog("First Name:");
            String lastName = JOptionPane.showInputDialog("Last Name:");
            String address = JOptionPane.showInputDialog("Address:");
            String contact = JOptionPane.showInputDialog("Contact:");
            String dobStr = JOptionPane.showInputDialog("Date of Birth (yyyy-mm-dd):");
            String sex = JOptionPane.showInputDialog("Sex (M/F/O):");
            String bloodType = JOptionPane.showInputDialog("Blood Type:");

            if (firstName == null || lastName == null || address == null || contact == null || dobStr == null || sex == null || bloodType == null) {
                JOptionPane.showMessageDialog(view, "Make sure all fields are filled out.");
            }

            Date dob = Date.valueOf(dobStr);
            Patient patient = new Patient(firstName, lastName, address, contact, dob, sex, bloodType);
            model.registerPatient(patient);
            view.updatePatientTable();

            System.out.println(patient.getPatientId());
        });

        view.getModifyPatientButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter the ID of the patient to modify:");
            String newAddress = JOptionPane.showInputDialog("New Address:");
            String newContact = JOptionPane.showInputDialog("New Contact:");

            if (id != null) {
                if (newAddress != null || newContact != null) {
                    model.updatePatientInfo(id, newAddress, newContact);
                    view.updatePatientTable();
                } else {
                    JOptionPane.showMessageDialog(view, "Make sure all fields are filled out.");
                }
            } else {
                JOptionPane.showMessageDialog(view, "No patient with the given ID exists.");
            }
        });

        view.getFindPatientButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Patient ID:");
            Patient patient = model.findPatient(id);
            if (patient != null) {
                view.updatePatientTable(List.of(patient));
                JOptionPane.showMessageDialog(view, "Patient found: " + patient.getFirstName() + " " + patient.getLastName());
            } else {
                JOptionPane.showMessageDialog(view, "No patient with the given ID exists.");
            }
        });

        view.getViewPatientsButton().addActionListener(e -> {
            view.updatePatientTable(view.getPatientDAO().getAllPatients());
        });
    }

    public void setUpAppointmentActions() {
        view.getBookAppointmentButton().addActionListener(e -> {
            String patientId = JOptionPane.showInputDialog("Patient ID:");
            String doctorId = JOptionPane.showInputDialog("Doctor ID:");
            String dateStr = JOptionPane.showInputDialog("Date (yyyy-mm-dd):");
            String timeStr = JOptionPane.showInputDialog("Time (HH:mm):");

            try {
                Date date = Date.valueOf(dateStr);
                Time time = Time.valueOf(timeStr + ":00");
                Appointment appointment = new Appointment(patientId, doctorId, date, time);
                model.bookAppointment(appointment);
                System.out.println(appointment.getAppointmentId());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            }
        });

        view.getRescheduleAppointmentButton().addActionListener(e -> {
            String aptId = JOptionPane.showInputDialog("Appointment ID:");
            String newDateStr = JOptionPane.showInputDialog("New Date (yyyy-mm-dd):");
            String newTimeStr = JOptionPane.showInputDialog("New Time (HH:mm):");

            try {
                Date newDate = Date.valueOf(newDateStr);
                Time newTime = Time.valueOf(newTimeStr + ":00");
                model.rescheduleAppointment(aptId, newDate, newTime);
                view.updateAppointmentTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            }
        });

        view.getCancelAppointmentButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Appointment ID:");
            try {
                if (id != null) {
                    if (model.findAppointmentByID(id) != null) {
                        model.cancelAppointment(id);
                        view.updateAppointmentTable();
                        JOptionPane.showMessageDialog(view, "Appointment with ID " + id + " successfully cancelled");
                    } else {
                        JOptionPane.showMessageDialog(view, "No appointment with the given ID exists.");
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Make sure all fields are filled out.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            }
        });

        view.getFindAppointmentButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Appointment ID:");
            try {
                if (id != null) {
                    if (model.findAppointmentByID(id) != null) {
                        Appointment appointment = model.findAppointmentByID(id);
                        view.updateAppointmentTable(List.of(appointment));
                        JOptionPane.showMessageDialog(view, "Appointment " + appointment.getAppointmentId() + " found");
                    } else {
                        JOptionPane.showMessageDialog(view, "No appointment with the given ID exists.");
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Make sure all fields are filled out.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            }
        });

        view.getViewAppointmentsButton().addActionListener(e -> {
            view.updateAppointmentTable(view.getAppointmentDAO().getAllAppointments());
        });
    }

    public void setUpRecordActions() {
        view.getFindPatientRecordButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Patient ID:");
            Patient patient = model.findPatient(id);

            try {
                if (id != null) {
                    if (model.findPatient(id) != null) {
                        view.updateRecordTable(List.of(patient));
                    }
                    else {
                        JOptionPane.showMessageDialog(view, "No records with the given patient ID exists.");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(view, "Make sure all fields are filled out.");
                }
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
            }
        });

        view.getViewRecordsButton().addActionListener(e -> {
            view.updateRecordTable(view.getPatientDAO().getAllPatients());
        });
    }
}