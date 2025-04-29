package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class SystemManager {
    private List<Doctor> doctors;
    private List<Patient> patients;
    private List<Appointment> appointments;

    // Doctor management methods

    /**
     * Assigns a family doctor to a registered patient
     * @param patientId patient who will be assigned a doctor
     * @param doctorId doctor to be assigned to th epatient
     */
    public void assignDoctor(String patientId, String doctorId) {
      Doctor doctor = findDoctor(doctorId);
      Patient patient = findPatientByID(patientId);

      if (patient.getFamilyDoctor() == null) {
          patient.setFamilyDoctor(doctor);
      }
      else{
          throw new IllegalArgumentException("Doctor already assigned!");
      }
    }

    /**
     * Registers new doctor to the list of doctors in the system
     * @param doctor Doctor
     */
    public void registerDoctor(Doctor doctor){
        doctors.add(doctor);
    }

    /**
     * Finds Doctors that practice the specialty desired (e.g. Ophtamologist returns list of doctors that have specialized in the ophtamology field)
     * @param specialty field of specialization of the doctors we wish to find
     * @return List with doctors that have specialized in said specialty field
     */
    public List<Doctor> findDoctorsBySpecialty(String specialty){

        List<Doctor> specialtyDoctors = new ArrayList<Doctor>();
        for (Doctor doc : doctors){
            if (doc.getSpeciality().equalsIgnoreCase(specialty)){
                 specialtyDoctors.add(doc);
            }
        }
        if (specialtyDoctors.isEmpty()){
            throw new IllegalArgumentException("No Doctor found in " + specialty + " field");
        }
        return specialtyDoctors;
    }

    /**
     * Finds the doctor and their information through their id
     * @param id of doctor you wish to find
     * @return Doctor object, if not found a message will display stating that the doctor is not in the system.
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
     * Removes doctor with the found id from the list of doctors in the clinics management system
     * @param id
     */
    public void removeDoctor(String id) {

        Doctor doctor = findDoctor(id);
            if (doctor != null) {
                doctors.remove(doctor);
                System.out.println("Doctor with id: " + id + " has been successfully removed.");
            }
            else {
                System.out.println("Doctor with id: " + id + " does not exist.");
            }
    }

    /**
     * Modifies the contact information of the doctor with the given id
     * @param id
     * @param modifiedContact
     */
    public void modifyDoctor(String id, String modifiedContact) {
        Doctor modifiedDoctor = findDoctor(id);
        if (modifiedDoctor != null) {
            throw new NoSuchElementException("Doctor with id: " + id + " was not found.");
        }
        modifiedDoctor.setContact(modifiedContact);
    }

    // Patient management methods

    /**
     * Registers new patients to the clinics management system
     * @param patient
     */
    public void registerPatient(Patient patient) {
        patients.add(patient);
    }

    /**
     * Returns patient with matching id, if no patient matches the id given null object will be returned
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
     * Removes patient from list of patients in the management system, if no patient matches the id given an exception will be thrown
     * @param id
     */
    public void dischargePatient(String id) {
        for (Patient patient : patients) {
            if (patient.getPatientId().equals(id)) {
                patients.remove(id);
                System.out.println("Patient with id: " + id + " has been successfully removed.");
            }
            else {
                throw new NoSuchElementException("Patient with id: " + id + " does not exist.");
            }
        }
    }

    /**
     * Modifies the contact information of the patient, if no patient with matching id is found an exception will be thrown
     * @param id id of patient one wishes to mdify the information of
     * @param newAdress new patients address
     * @param newContact new contact information of patient
     */
    public void updatePatientInfo(String id, String newAdress, String newContact) {
        Patient modifiedPatient = findPatient(id);
        if (modifiedPatient != null) {
            throw new NoSuchElementException("Patient with id: " + id + " was not found.");
        }
        modifiedPatient.setContact(newContact);
        modifiedPatient.setAddress(newAdress);
        System.out.println("Patient's contact info has been successfully updated.");
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

