package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class SystemManager {
    private List<Doctor> doctors;
    private List<Patient> patients;
    private List<Appointment> appointments;

    private final DoctorDAO doctorDAO = new DoctorDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    public SystemManager() {
        this.doctors = new ArrayList<>();
        this.patients = new ArrayList<>();
        this.appointments = new ArrayList<>();

        this.doctors = doctorDAO.getAllDoctors();
        this.patients = patientDAO.getAllPatients();
        this.appointments = appointmentDAO.getAllAppointments();
    }
    /**
     * Assigns a family doctor to a registered patient
     * @param patientId patient who will be assigned a doctor
     * @param doctorId doctor to be assigned to th epatient
     */
    public void assignDoctor(String patientId, String doctorId) {
        Patient patient = findPatient(patientId);
        Doctor doctor = findDoctor(doctorId);

        if (doctor == null || patient == null) {
            throw new NoSuchElementException("Invalid doctor or patient id");
        }

        if (patient.getFamilyDoctor() != null) { //make sure that the patient doesn't already have a family doctor
            throw new IllegalArgumentException("Patient already as a family doctor assigned.");
        }
        patient.setFamilyDoctor(doctorId);
        patientDAO.updateFamilyDoctor(patientId, doctorId);
    }

    /**
     * Registers new doctor to the list of doctors in the system
     *  then updates the information on tables through the DB connection class,
     * @param doctor Doctor
     */
    public void registerDoctor(Doctor doctor){
        doctors.add(doctor);
        doctorDAO.insertDoctorRecord(doctor.getDoctorId(), doctor.getFirstName(), doctor.getLastName(), doctor.getSpeciality(), doctor.getContact());
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
              DoctorDAO doctorDAO = new DoctorDAO();
              doctorDAO.getDoctorById(doctor.getDoctorId()); //TODO very confused here
                return doctor;
            }
        }
        System.out.println("Doctor with id: " + id + " was not found.");
        return null;
    }

//    /**
//     * Removes doctor with the found id from the list of doctors in the clinics management system
//     *  then updates the information on tables through the DB connection class
//     * @param id
//     */
//    public void removeDoctor(String id) {
//        Doctor doctor = findDoctor(id);
//        if (doctor == null) {
//            System.out.println("Doctor with id: " + id + " does not exist.");
//        }
//        for (Patient pat : doctor.getPatients()) {
//           pat.setFamilyDoctor(null); // make sure to update the patients family doctor so that it can be reassigned
//        };
//        doctors.remove(doctor);
////        DBConnection databse = DBConnection.getInstance();
////        databse.connect();
////        databse.deleteDoctor(id);
////        System.out.println("Doctor with id: " + id + " has been successfully removed.");
//    }

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
        DBConnection.getInstance().getConnection();

        doctorDAO.updateDoctor(id, modifiedContact);
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
        DBConnection.getInstance().getConnection();
        patientDAO.insertPatientRecord(p.getPatientId(), p.getFirstName(), p.getLastName(), p.getAddress(), p.getContact(),p.getDob(),
                p.getSex(),p.getFamilyDoctor(),p.getBloodType());
        System.out.println("Patient registered successfully");
    }

    /**
     * Returns patient with matching id, if no patient matches the id given null object will be returned
     * @param id
     * @return
     */
    public Patient findPatient(String id) {
        for (Patient patient : patients) {
                return patientDAO.getPatientById(id);

        }
        System.out.println("Patient with id: " + id + " was not found.");
        return null;
    }

    /**
     * Modifies the contact information of the patient
     * then updates the information on tables through the DB connection class,
     * if no patient with matching id is found an exception will be thrown
     * @param id id of patient one wishes to modify the information of
     * @param newAddress new patients address
     * @param newContact new contact information of patient
     */
    public void updatePatientInfo(String id, String newAddress, String newContact) {
        Patient modifiedPatient = findPatient(id);
        if (modifiedPatient == null) {
            throw new NoSuchElementException("Patient with id: " + id + " was not found.");
        }
        modifiedPatient.setContact(newContact);
        modifiedPatient.setAddress(newAddress);
        System.out.println("Patient's contact info has been successfully updated.");

        patientDAO.updatePatient(id, newAddress, newContact);
    }

    //================================================== Appointment management methods =========================================

    /**
     * Create an appointment and add it to the appointment list,
     * then updates the information on tables through the DB connection class.
     * @param appointment the input appointment object
     */
    public void bookAppointment(Appointment appointment) {
        if (findPatient(appointment.getPatientId()) != null && findDoctor(appointment.getDoctorId()) != null) {
            appointments.add(appointment);
            appointmentDAO.insertAppointmentRecord(
                    appointment.getAppointmentId(),
                    appointment.getPatientId(),
                    appointment.getDoctorId(),
                    appointment.getDate(),
                    appointment.getTime()
            );
        }
        else{
            throw new NoSuchElementException("Invalid patient or doctor id.");
        }
    }

    /**
     * Finds the appointment based on th id of the doctor and the patien making sure that the patient and doctor
     * match that of the appointment through the findDoctor and findPatient methods
     * @param patientID
     * @param doctorID
     * @return
     */
    public Appointment findAppointment(String patientID, String doctorID) {
        for (Appointment appointment : appointments) {
            if (appointment.getPatientId().equals(findPatient(patientID)) &&
                    appointment.getDoctorId().equals(findDoctor(doctorID))){
                return appointment;
            }
        }
        System.out.println("Appointment for " + findPatient(patientID).toString() + " was not found.");
        return null;
    }

    /**
     * Returns appointment matching aptID (helper method for cancelling appointments
     * @param aptID
     * @return
     */
    public Appointment findAppointmentByID(String aptID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(aptID)) {
                return appointment;
            }
        }
        System.out.println("Appointment was not found.");
        return null;
    }

    /**
     * Removes appointment from appointment list,then updates the information on tables through the DB connection class.
     * @param aptID
     */
    public void cancelAppointment(String aptID) {
       Appointment appointment = findAppointmentByID(aptID);
       if (appointment != null) {
           appointments.remove(appointment);
       }
       else {
           throw new NoSuchElementException("Appointment with id: " + " does not exist.");
       }

       appointmentDAO.cancelAppointment(appointment.getAppointmentId());
    }


    /**
     * Changes the date and/or time of the appointment found through the findAppintment method call,
     * then updates the information on tables through the DB connection class.
     * @param aptID
     * @param newDate
     * @param newTime
     */
    public void rescheduleAppointment(String aptID , Date newDate, Time newTime) {
        Appointment appointment = findAppointmentByID(aptID);
        if (appointment == null) {
            System.out.println("Appointment with id: " +aptID + " does not exist.");
            throw new NoSuchElementException("Appointment does not exist.");
        }

        appointment.setDate(newDate);
        appointment.setTime(newTime);

        appointmentDAO.updateSchedule(aptID,newDate,newTime);
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Appointment> getAppointments() {
        return appointments;
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
