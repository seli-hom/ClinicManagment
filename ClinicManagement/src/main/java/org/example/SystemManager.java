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
      Patient patient = findPatient(patientId);

        if (doctor != null || patient != null) {
            if (patient.getFamilyDoctor() == null) { //make sure that the patient doesn't already have a family doctor
                patient.setFamilyDoctor(doctor);
                doctor.getPatients().add(patient);
            }
        }
      else{
          throw new IllegalArgumentException("Doctor already assigned!");
      }
      //i dont think we have to do anything in database because we did not create the foreign keys but do let me know
    }

    /**
     * Registers new doctor to the list of doctors in the system
     *  then updates the information on tables through the DB connection class,
     * @param doctor Doctor
     */
    public void registerDoctor(Doctor doctor){
        doctors.add(doctor);
        DBConnection database = DBConnection.getInstance();
        database.connect();
        database.insertDoctorRecord(doctor.getFirstName(), doctor.getLastName(), doctor.getSpeciality(), doctor.getContact());
        System.out.println("Doctor inserted successfully!");
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
     *  then updates the information on tables through the DB connection class
     * @param id
     */
    public void removeDoctor(String id) {

        Doctor doctor = findDoctor(id);
        if (doctor == null) {
            System.out.println("Doctor with id: " + id + " does not exist.");
        }
        for (Patient pat : doctor.getPatients()) {
           pat.setFamilyDoctor(null); // make sure to update the patients family doctor so that it can be reassigned
        };
        doctors.remove(doctor);
        DBConnection databse = DBConnection.getInstance();
        databse.connect();
        databse.deleteDoctor(id);
        System.out.println("Doctor with id: " + id + " has been successfully removed.");
    }

    /**
     * Modifies the contact information of the doctor with the given id
     * then updates the information on tables through the DB connection class
     * @param id
     * @param modifiedContact
     */
    public void modifyDoctor(String id, String modifiedContact) {
        Doctor modifiedDoctor = findDoctor(id);
        if (modifiedDoctor == null) {
            throw new NoSuchElementException("Doctor with id: " + id + " was not found.");
        }
        modifiedDoctor.setContact(modifiedContact);
        DBConnection database = DBConnection.getInstance();
        database.connect();
        database.updateDoctor(id, modifiedContact);
        System.out.println("Doctor info has been successfully updated.");
    }

    //======================================== Patient management methods ======================================================

    /**
     * Registers new patients to the clinics management system
     * then updates the information on tables through the DB connection class
     * @param p
     */
    public void registerPatient(Patient p) {
        patients.add(p);
        DBConnection database = DBConnection.getInstance();
        database.connect();
        database.insertPatientRecord(p.getFirstName(), p.getLastName(), p.getAddress(), p.getContact(),p.getBirthDate(),
                p.getSex().toString(),p.getFamilyDoctor().toString(),p.getType().toString(),p.getHeight(),p.getWeight());
        System.out.println("Patient registered successfully");
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
     * Removes patient from list of patients in the management system
     * then updates the information on tables through the DB connection class,
     * if no patient matches the id given an exception will be thrown
     * @param id
     */
    public void dischargePatient(String id) {
        Patient patient = findPatient(id);
        if (patient != null) {
            patient.getFamilyDoctor().getPatients().remove(patient); // make sure the patient is no longer in the list of the doctors patients
            patients.remove(patient);
            System.out.println("Patient with id: " + id + " has been successfully removed.");

            DBConnection database = DBConnection.getInstance();
            database.connect();
            database.dischargePatient(id);
        }
        else {
            throw new NoSuchElementException("Patient with id: " + id + " does not exist.");
        }
    }

    /**
     * Modifies the contact information of the patient
     * then updates the information on tables through the DB connection class,
     * if no patient with matching id is found an exception will be thrown
     * @param id id of patient one wishes to mdify the information of
     * @param newAdress new patients address
     * @param newContact new contact information of patient
     */
    public void updatePatientInfo(String id, String newAdress, String newContact) {
        Patient modifiedPatient = findPatient(id);
        if (modifiedPatient == null) {
            throw new NoSuchElementException("Patient with id: " + id + " was not found.");
        }
        modifiedPatient.setContact(newContact);
        modifiedPatient.setAddress(newAdress);
        System.out.println("Patient's contact info has been successfully updated.");

        DBConnection database = DBConnection.getInstance();
        database.connect();
        database.updatePatient(id, newAdress, newContact);
    }

    //================================================== Appointment management methods =========================================

    /**
     * Create an appointment and add it to the appointment list,
     * then updates the information on tables through the DB connection class.
     * @param patientID
     * @param doctorID
     * @param date Date that the user wishes to book the appointment on
     * @param time Time that the user wishes to book the appointment on
     */
    public void bookAppointment(String patientID, String doctorID, java.sql.Date date, Time time) {
        // Create new Appointment object
        Patient patient = findPatient(patientID);
        Doctor doctor = findDoctor(doctorID);
        if (patient != null && doctor != null) {
            Appointment appointment = new Appointment(patient, doctor, date, time);
            // Add the new appointment to appointments list
            appointments.add(appointment);
//            // Add the patient to the doctor's patient list
//            doctor.getPatients().add(patient); (i dont think we should add this here cause the patient might not be
//            followed by the same doctor after a singular appointment it is handled in assignDoctor instead)

            DBConnection database = DBConnection.getInstance();
            database.connect();
            database.insertAppointmentRecord(patientID,doctorID,date,time);
        }
        else{
            throw new NoSuchElementException("Please make sure that you have input the correct IDs for both the patient and the doctor");
        }

    }

    /**
     * Finds the appointment based on th id of the doctor and the patien making sure that the patient and doctor
     * match that of the appointment through the findDoctor and findPate=ient methods
     * @param patientID
     * @param doctorID
     * @return
     */
    public Appointment findAppointment(String patientID, String doctorID) {
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().equals(findPatient(patientID)) &&
                    appointment.getDoctor().equals(findDoctor(doctorID))){
                return appointment;
            }
        }
        System.out.println("Appointment for " + findPatient(patientID).toString() + " was not found.");
        return null;
    }

    /**
     * Removes appointment from appointment list,then updates the information on tables through the DB connection class.
     * @param patientID patient whose appointment it was
     * @param doctorID doctor who was scheduled for the appointment with the patient
     */
    public void cancelAppointment(String patientID, String doctorID) {
       Appointment appointment = findAppointment(patientID, doctorID);
       if (appointment != null) {
           appointments.remove(appointment);
       }
       else {
           throw new NoSuchElementException("Appointment with id: " + " does not exist.");
       }
       DBConnection database = DBConnection.getInstance();
       database.connect();
       database.cancelAppointment(appointment.getAppointmentId());
    }


    /**
     * Changes the date and/or time of the appointment found through the findAppintment method call,
     * then updates the information on tables through the DB connection class.
     * @param patientId
     * @param doctorId
     * @param newDate
     * @param newTime
     */
    public void rescheduleAppointment(String patientId, String  doctorId, java.sql.Date newDate, Time newTime) {
        Appointment appointment = findAppointment(patientId, doctorId);
        if (appointment == null) {
            System.out.println("Appointment with id: " + " does not exist.");
            throw new NoSuchElementException("Appointment does not exist.");
        }

        appointment.setDate(newDate);
        appointment.setTime(newTime);
        DBConnection database = DBConnection.getInstance();
        database.connect();

        database.updateSchedule(doctorId,patientId,newDate,newTime);

    }
}

