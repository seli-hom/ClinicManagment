package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SystemManager {
    private List<Doctor> doctors;
    private List<Patient> patients;
    private List<Appointment> appointments;

    // Doctor management methods
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void removeDoctor(String id) {
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorId().equals(id)) {
                doctors.remove(id);
                System.out.println("Doctor with id: " + id + " has been successfully removed.");
            }
            else {
                System.out.println("Doctor with id: " + id + " does not exist.");
            }
        }
    }

    public void modifyDoctor(int id, Doctor modified) {

    }

    // Patient management methods
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(String id) {
        for (Patient patient : patients) {
            if (patient.getPatientId().equals(id)) {
                patients.remove(id);
                System.out.println("Patient with id: " + id + " has been successfully removed.");
            }
            else {
                System.out.println("Patient with id: " + id + " does not exist.");
            }
        }
    }

    public void modifyPatient(int id, Patient modified) {

    }

    // Appointment management methods
    public void bookAppointment(Patient patient, Doctor doctor, LocalDate date, LocalTime time) {
        // Create new Appointment object
        Appointment appointment = new Appointment(patient, doctor, date, time);
        // Add the new appointment to appointments list
        appointments.add(appointment);
        // Add the patient to the doctor's patient list
        doctor.getPatients().add(patient);
    }

    public void cancelAppointment(String id) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(id)) {
                appointments.remove(appointment);
                System.out.println("Appointment with id: " + id + " has been successfully removed.");
            }
            else {
                System.out.println("Appointment with id: " + " does not exist.");
            }
        }
    }

    public void modifyAppointment() {

    }
}
