package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class SystemManager {
    private List<Doctor> doctors;
    private List<Patient> patients;
    private List<Appointment> appointments;

    // Doctor management methods

    /**
     *
     * @param doctor
     */
    public void assignDoctor(String patientId, String doctorId) {
      Doctor doctor = findDoctorByID(doctorId);
      Patient patient = findPatientByID(patientId);

      patient.setFamilyDoctor(doctor);

    }
    public void registerDoctor(Doctor doctor){
        doctors.add(doctor);
    }
    public Doctor findDoctorByID(String docId){
        for (Doctor doc : doctors){
            if (doc.getDoctorId == docId){
                return doc;
            }
            throw new NoArgsException("No doctor with provided id was found in the system");
        }
    }

    public Doctor findPatientByID(String patientID){
        for (Patient pat : patients){
            if (pat.getPatientID == patientID){
                return pat;
            }
            throw new NoArgsException("No patient with provided id is registered in the system");
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public Doctor findDoctor(String id) {
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorId().equals(id)) {
                return doctor;
            }
        }
        System.out.println("Doctor with id: " + id + " was not found.");
        return null;
    }

    /**
     *
     * @param id
     */
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

    /**
     *
     * @param id
     * @param modified
     */
    public void modifyDoctor(int id, Doctor modified) {

    }

    // Patient management methods

    /**
     *
     * @param patient
     */
    public void registerPatient(Patient patient) {

        patients.add(patient);
//        DBconnection.getInstance();
    }

    /**
     *
     * @param id
     * @return
     */
    public Patient findPatient(String id) {
        for (Patient patient : patients) {
            if (patient.getPatientId().equals(id)) {
                return patient;
            }
        }
        System.out.println("Patient with id: " + id + " was not found.");
        return null;
    }

    /**
     *
     * @param id
     */
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

    /**
     *
     * @param id
     * @param modified
     */
    public void modifyPatient(int id, Patient modified) {

    }

    // Appointment management methods

    /**
     *
     * @param patient
     * @param doctor
     * @param date
     * @param time
     */
    public void bookAppointment(Patient patient, Doctor doctor, Date date, Time time) {
        // Create new Appointment object
        Appointment appointment = new Appointment(patient, doctor, date, time);
        // Add the new appointment to appointments list
        appointments.add(appointment);
        // Add the patient to the doctor's patient list
        doctor.getPatients().add(patient);
    }

    /**
     *
     * @param id
     * @return
     */
    public Appointment findAppointment(String id) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(id)) {
                return appointment;
            }
        }
        System.out.println("Appointment with id: " + id + " was not found.");
        return null;
    }

    /**
     *
     * @param id
     */
    public void cancelAppointment(String id) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(id)) {
                appointments.remove(appointment);
                System.out.println("Appointment with id: " + appointmentId + " has been successfully removed.");
            }
            else {
                System.out.println("Appointment with id: " + " does not exist.");
            }
        }
    }

    /**
     *
     * @param patientId
     * @param doctorId
     * @param newDate
     * @param newTime
     */
    public void rescheduleAppointment(int patientId, int doctorId, java.sql.Date newDate, Time newTime) {
        DBConnection database = DBConnection.getInstance();
        database.connect();
        database.updateSchedule(doctorId,patientId,newDate,newTime);

    }
}

