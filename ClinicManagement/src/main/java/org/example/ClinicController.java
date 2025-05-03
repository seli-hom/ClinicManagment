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
            String firstName = JOptionPane.showInputDialog(Messages.getMessage("dialog.firstName"));
            String lastName = JOptionPane.showInputDialog(Messages.getMessage("dialog.lastName"));
            String specialty = JOptionPane.showInputDialog(Messages.getMessage("dialog.specialty"));
            String contact = JOptionPane.showInputDialog(Messages.getMessage("dialog.contact"));

            if (firstName == null || lastName == null || contact == null || specialty == null) {
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.fieldsNotFilled"));
            }

            Doctor doctor = new Doctor(firstName, lastName, specialty, contact);
            model.registerDoctor(doctor);
            view.updateDoctorTable();

            System.out.println(doctor.getDoctorId());
        });

        view.getModifyDoctorButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterId"));
            if (id != null) {
                String newContact = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterNewContact"));
                model.modifyDoctor(id, newContact);
                view.updateDoctorTable();
            } else {
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.noDoctorId"));
            }

        });

        view.getFindDoctorButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterId"));
            Doctor doctor = view.getDoctorDAO().getDoctorById(id);

            if (doctor != null) {
                view.updateDoctorTable(List.of(doctor));
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.doctorFound"));
            } else {
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.noDoctorId"));
            }
        });

        view.getFindDoctorBySpecialtyButton().addActionListener(e -> {
            String specialty = JOptionPane.showInputDialog(Messages.getMessage("dialog.specialty"));
            List<Doctor> doctors;

            if (specialty != null) {
                doctors = model.findDoctorsBySpecialty(specialty);
                view.updateDoctorTable(doctors);
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.foundDoctors"));
            } else {
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.noDoctorId"));
            }
        });

        view.getViewDoctorsButton().addActionListener(e -> {
            view.updateDoctorTable(view.getDoctorDAO().getAllDoctors());
        });
    }

    public void setUpPatientActions() {
        view.getAddPatientButton().addActionListener(e -> {
            String firstName = JOptionPane.showInputDialog(Messages.getMessage("dialog.firstName"));
            String lastName = JOptionPane.showInputDialog(Messages.getMessage("dialog.lastName"));
            String address = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterAddress"));
            String contact = JOptionPane.showInputDialog(Messages.getMessage("dialog.contact"));
            String dobStr = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterDateOfBirth"));
            String sex = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterSex"));
            String bloodType = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterBloodType"));

            if (firstName == null || lastName == null || address == null || contact == null || dobStr == null || sex == null || bloodType == null) {
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.fieldsNotFilled"));
            }

            Date dob = Date.valueOf(dobStr);
            Patient patient = new Patient(firstName, lastName, address, contact, dob, sex, bloodType);
            model.registerPatient(patient);
            view.updatePatientTable();

            System.out.println(patient.getPatientId());
        });

        view.getModifyPatientButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterId"));
            String newAddress = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterNewAddress"));
            String newContact = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterNewContact"));

            if (id != null) {
                if (newAddress != null && newContact != null) {
                    model.updatePatientInfo(id, newAddress, newContact);
                    view.updatePatientTable();
                } else {
                    JOptionPane.showMessageDialog(view, Messages.getMessage("message.fieldsNotFilled"));
                }
            } else {
                JOptionPane.showMessageDialog(view, "No patient with the given ID exists.");
            }
        });

        view.getFindPatientButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterId"));
            Patient patient = view.getPatientDAO().getPatientById(id);
            if (patient != null) {
                view.updatePatientTable(List.of(patient));
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.patientFound"));
            } else {
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.noPatientId"));
            }
        });

        view.getViewPatientsButton().addActionListener(e -> {
            view.updatePatientTable(view.getPatientDAO().getAllPatients());
        });
    }

    public void setUpAppointmentActions() {
        view.getBookAppointmentButton().addActionListener(e -> {
            String patientId = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterPatientId"));
            String doctorId = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterDoctorId"));
            String dateStr = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterDate"));
            String timeStr = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterTime"));

            try {
                Date date = Date.valueOf(dateStr);
                Time time = Time.valueOf(timeStr + ":00");
                Appointment appointment = new Appointment(patientId, doctorId, date, time);
                model.bookAppointment(appointment);
                System.out.println(appointment.getAppointmentId());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.error") + ex.getMessage());
            }
        });

        view.getRescheduleAppointmentButton().addActionListener(e -> {
            String aptId = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterId"));
            String newDateStr = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterNewDate"));
            String newTimeStr = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterNewTime"));

            try {
                Date newDate = Date.valueOf(newDateStr);
                Time newTime = Time.valueOf(newTimeStr + ":00");
                model.rescheduleAppointment(aptId, newDate, newTime);
                view.updateAppointmentTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.error") + ex.getMessage());
            }
        });

        view.getCancelAppointmentButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterId"));
            try {
                if (id != null) {
                    if (model.findAppointmentByID(id) != null) {
                        model.cancelAppointment(id);
                        view.updateAppointmentTable();
                        JOptionPane.showMessageDialog(view, Messages.getMessage("message.appointmentCancelled"));
                    } else {
                        JOptionPane.showMessageDialog(view, Messages.getMessage("message.noAppointmentId"));
                    }
                } else {
                    JOptionPane.showMessageDialog(view, Messages.getMessage("message.fieldsNotFilled"));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.error") + ex.getMessage());
            }
        });

        view.getFindAppointmentButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterId"));
            try {
                if (id != null) {
                    if (model.findAppointmentByID(id) != null) {
                        Appointment appointment = view.getAppointmentDAO().getAppointmentById(id);
                        view.updateAppointmentTable(List.of(appointment));
                        JOptionPane.showMessageDialog(view, Messages.getMessage("message.appointmentFound"));
                    } else {
                        JOptionPane.showMessageDialog(view, Messages.getMessage("message.noAppointmentId"));
                    }
                } else {
                    JOptionPane.showMessageDialog(view, Messages.getMessage("message.fieldsNotFilled"));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.error") + ex.getMessage());
            }
        });

        view.getViewAppointmentsButton().addActionListener(e -> {
            view.updateAppointmentTable(view.getAppointmentDAO().getAllAppointments());
        });
    }

    public void setUpRecordActions() {
        view.getFindPatientRecordButton().addActionListener(e -> {
            String id = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterId"));
            Patient patient = model.findPatient(id);

            try {
                if (id != null) {
                    if (model.findPatient(id) != null) {
                        view.updateRecordTable(List.of(patient));
                    }
                    else {
                        JOptionPane.showMessageDialog(view, Messages.getMessage("message.noPatientId"));
                    }
                }
                else {
                    JOptionPane.showMessageDialog(view, Messages.getMessage("message.fieldsNotFilled"));
                }
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.error") + ex.getMessage());
            }
        });

        view.getAssignDoctorButton().addActionListener(e -> {
            String patientId = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterPatientId"));
            String doctorId = JOptionPane.showInputDialog(Messages.getMessage("dialog.enterDoctorId"));
            Patient patient = model.findPatient(patientId);
            Doctor doctor = model.findDoctor(doctorId);

            if (patientId != null && doctorId != null) {
                if (patient != null && doctor != null) {
                    model.assignDoctor(patientId, doctorId);
                    view.updateRecordTable(List.of(patient));
                    JOptionPane.showMessageDialog(view, Messages.getMessage("message.assignDoctorSuccess"));
                    view.updateRecordTable(List.of(patient));
                }
                else {
                    JOptionPane.showMessageDialog(view, Messages.getMessage("message.noPatientOrDoctor"));
                }
            }
            else {
                JOptionPane.showMessageDialog(view, Messages.getMessage("message.fieldsNotFilled"));
            }
        });

        view.getViewRecordsButton().addActionListener(e -> {
            view.updateRecordTable(view.getPatientDAO().getAllPatients());
        });
    }
}