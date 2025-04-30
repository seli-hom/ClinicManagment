package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBConnection {
    private static DBConnection dObject;
    private DBConnection(){}
    private Map<String, Patient> patientCache = new HashMap<>();
    private Map<String, Doctor> doctorCache = new HashMap<>();
    private Map<String, Appointment> appointmentCache = new HashMap<>();

    // create public static method that allows us to create and access object we created
    // inside method, we will create a condition that restricts us from creating more than one object

    public static DBConnection getInstance(){
    // Singleton Pattern so that there will not be another creation of the database
        if (dObject == null){
            dObject = new DBConnection();
        }
        //return singleton object
        return dObject;
    }

    public static Connection connect() {
        String Base_Path = "jdbc:sqlite:src/main/resources/database";
        String DB_Path = Base_Path + "data.db";


        Connection connection;
        try {
            connection = DriverManager.getConnection(DB_Path);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    /**
     * Create a Doctors table if one does not already exist
     */
    public  void createNewDoctorsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Doctors (
                    id VARCHAR(5) PRIMARY KEY,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    specialty TEXT,
                    contact TEXT NOT NULL
                );
                """;

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);  // execute the create table statement
            System.out.println("Table created successfully.");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Create a Patients table if one does not already exist
     */
    public  void createNewPatientsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Patients (
                    id VARCHAR(5) PRIMARY KEY,
                    first_name VARCHAR(50) NOT NULL,
                    last_name TEXT NOT NULL,
                    address TEXT,
                    contact TEXT NOT NULL,
                    dob DATE NOT NULL,
                    sex VARCHAR(10) NOT NULL,
                    family_doctor VARCHAR(50),
                    FOREIGN KEY(family_doctor) REFERENCES Doctors(id),
                    blood_type VARCHAR(10) NOT NULL,
                    patient_discharged BOOLEAN NOT NULL
                );
                """;

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);  // execute the create table statement
            System.out.println("Table created successfully.");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Create an Appointments table if one does not already exist
     */
    public  void createNewAppointmentsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Appointments (
                    id VARCHAR(5) PRIMARY KEY,
                    patient_id VARCHAR(5),
                    doctor_id VARCHAR(5),
                    date DATE NOT NULL,
                    time TIME NOT NULL,
                    FOREIGN KEY REFERENCES Patients(id),
                    FOREIGN KEY REFERENCES Doctors(id)
                );
                """;

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);  // execute the create table statement
            System.out.println("Table created successfully.");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Insert the following fields into the Patients table
     * @param id the input id that will be retrieved when a Patient object is created
     * @param fName the input first name
     * @param lName the input last name
     * @param address the input address
     * @param contact the input contact information
     * @param dob the input date of birth
     * @param sex the input sex
     * @param doctorId the input family doctor's id
     * @param bloodType the input blood type
     * @param discharged true if patient has been discharged, false otherwise
     */
    public  void insertPatientRecord(String id,String fName,String lName, String address, String contact, Date dob, String sex, String doctorId, String bloodType, boolean discharged){
        String sql = "INSERT INTO Patients (id,first_name, last_name, address, contact, dob, sex, family_doctor, blood_type, patient_discharged) VALUES(?,?,?,?,?,?,?,?,?,?)"; //this is sql query with placeholders(?) instead of inserting raw values directly
        // ? are parameter ,markers they will be safely filled later
        //this helps prevent SQL injection attacks and make code cleaner
        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, fName); //set student name
            pstmt.setString(3, lName);
            pstmt.setString(4, address);
            pstmt.setString(5, contact);
            pstmt.setDate(6, dob);
            pstmt.setString(7, sex);
            pstmt.setString(8, doctorId);
            pstmt.setString(9, bloodType);
            pstmt.setBoolean(10, discharged);

            pstmt.execute();//insert data into tble
            System.out.println("Data inserted successfully");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert the following fields into the Doctors table
     * @param id the input id that will be retrieved when a Doctor object is created
     * @param fname the input first name
     * @param lname the input last name
     * @param specialty the input specialty
     * @param contact the input contact information
     */
    public  void insertDoctorRecord(String id,String fname,String lname, String specialty, String contact){
        String sql = "INSERT INTO Doctors (id, first_name, last_name, specialty, contact) VALUES(?,?,?,?,?)"; //this is sql query with placeholders(?) instead of inserting raw values directly
        // ? are parameter ,markers they will be safely filled later
        //this helps prevent SQL injection attacks and make code cleaner
        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, fname);
            pstmt.setString(3, lname);
            pstmt.setString(4, specialty);
            pstmt.setString(5, contact);;
            pstmt.execute();//insert data into tble
            System.out.println("Data inserted successfully");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert the following fields into the Appointments table
     * @param id the input id that will be retrieved when an Appointment object is created
     * @param patientId the input patient's id
     * @param doctorId the input doctor's id
     * @param date the input date of the appointment
     * @param time the input time of the appointment
     */
    public  void insertAppointmentRecord( String id, String patientId , String doctorId, Date date, Time time){
        String sql = "INSERT INTO Appointments (id, patient_id, doctor_id, date, time) VALUES(?, ?,?,?,?)"; //this is sql query with placeholders(?) instead of inserting raw values directly
        // ? are parameter ,markers they will be safely filled later
        //this helps prevent SQL injection attacks and make code cleaner
        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, patientId);
            pstmt.setString(3, doctorId);
            pstmt.setDate(4,date);
            pstmt.setTime(5, time);

            pstmt.execute();//insert data into tble
            System.out.println("Data inserted successfully");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Update a patient's address
     * @param patientId the input patient id
     * @param newAdress the new input address
     */
    public  void updatePatientAddress(String patientId, String newAdress) {
        String sql = "UPDATE Patients SET address = ? WHERE Id = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newAdress); // set student name
            pstmt.setString(2, patientId);
            int rowsUpdated = pstmt.executeUpdate(); // returns number of rows affected

            if (rowsUpdated > 0) {
                System.out.println("Patient's address was updated successfully.");
            }
            else {
                System.out.println("No patient with the provided ID exists");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Update a patient's contact information
     * @param patientId the input patient id
     * @param newContact the new input contact information
     */
    public  void updatePatientContact(String patientId, String newContact) {
        String sql = "UPDATE Patients SET contact = ? WHERE Id = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newContact); // set student age
            pstmt.setString(2, patientId);
            int rowsUpdated = pstmt.executeUpdate(); // returns number of rows affected

            if (rowsUpdated > 0) {
                System.out.println("Patient's contact was updated successfully.");
            }
            else {
                System.out.println("No patient with the provided ID exists");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Update a doctor's contact information
     * @param doctorId the input doctor id
     * @param newContact the new input contact information
     */
    public  void updateDoctorContact(String doctorId, String newContact) {
        String sql = "UPDATE Doctors SET  contact = ? WHERE Id = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newContact); // set doctor contact
            pstmt.setString(2, doctorId);
            int rowsUpdated = pstmt.executeUpdate(); // returns number of rows affected

            if (rowsUpdated > 0) {
                System.out.println("Contact for doctor was updated successfully.");
            }
            else {
                System.out.println("No doctor with the provided ID exists");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Update the date and time of an appointment
     * @param aptID the input appointment id
     * @param newDate the new input date
     * @param newTime the new input time
     */
    public  void updateSchedule(String aptID, Date newDate, Time newTime) {
        String sql = "UPDATE Appointments SET  date = ?, time = ? WHERE Id = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, newDate); // set doctor contact
            pstmt.setTime(2, newTime); // set doctor contact
            pstmt.setString(3, aptID);
            int rowsUpdated = pstmt.executeUpdate(); // returns number of rows affected

            if (rowsUpdated > 0) {
                System.out.println("The appointment was rescheduled successfully.");
            }
            else {
                System.out.println("There is no appointment for the provided patient and doctor");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Change a patient's status to discharged = true
     * @param id the input patient id
     */
    public  void dischargePatient(String id) {
        String sql = "UPDATE Patients SET patient_discharged = ? WHERE Id = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, true);
            pstmt.setString(2, id);
            int rowsDeleted = pstmt.executeUpdate(); // returns number of rows affected

            if (rowsDeleted > 0) {
                System.out.println("Patient was discharged succesfully");
            }
            else {
                System.out.println("No patient with the provided ID exists");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Delete a Doctor from the database
     * @param id the input doctor id
     */
    public  void deleteDoctor(String id) {
        String sql = "DELETE FROM Doctors WHERE Id = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            int rowsDeleted = pstmt.executeUpdate(); // returns number of rows affected

            if (rowsDeleted > 0) {
                System.out.println("Doctor with id: " + id + " was discharged succesfully");
            }
            else {
                System.out.println("No doctor with the provided ID exists");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Cancel an appointment
     * @param id the input appointment id
     */
    public  void cancelAppointment(String id) {
        String sql = "DELETE FROM Appointments WHERE id = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            int rowsDeleted = pstmt.executeUpdate(); // returns number of rows affected

            if (rowsDeleted > 0) {
                System.out.println("Appointment with id: " + id + " was cancelled succesfully");
            }
            else {
                System.out.println("No appointment with the provided ID exists");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //=============Find patient =============
//    public void findPatients(String table, String id) {
//        String tableName = patients;
//        String sql = "SELECT * FROM " + tableName +" WHERE id = ?";
//
//        try{
//            Connection conn = connect();
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1,id);
//            int rowsDeleted = pstmt.executeUpdate(); // returns number of rows affected
//
//            if (rowsDeleted > 0) {
//                System.out.println("Appointment with id: " + id + " was cancelled succesfully");
//            }
//            else {
//                System.out.println("No patient with the provided ID exists");
//            }
//        }
//        catch (SQLException e) {
//            System.err.println(e.getMessage());
//        }
//    }

    /**
     * View all patients in the database
     * @return an array list of all the patients
     */
    public List<Patient> getAllPatients() {
       List<Patient> patientList = new ArrayList<>();
       String sql = "SELECT * FROM Patients";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Patient patient = new Patient(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address"),
                    rs.getString("contact"),
                    rs.getDate("dob"),
                    rs.getString("sex"),
                    rs.getString("blood_type")
                );

                patientList.add(patient);

                // Add the patient to the HashMap cache
                patientCache.put(patient.getPatientId(), patient);
            }
        }
        catch (SQLException e) {
            System.err.printf(Messages.getMessage("error.sql"), e.getMessage());
        }

        return patientList;
    }

    /**
     * View all doctors in the database
     * @return an array list of all the doctors
     */
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctorList = new ArrayList<>();
        String sql = "SELECT * FROM Doctors";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Doctor doctor = new Doctor(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("specialty"),
                    rs.getString("contact")
                );

                doctorList.add(doctor);
                doctorCache.put(doctor.getDoctorId(), doctor);
            }
        }
        catch (SQLException e) {
            System.err.printf(Messages.getMessage("error.sql"), e.getMessage());
        }

        return doctorList;
    }

    /**
     * View all doctors in the database
     * @return an array list of all the doctors
     */
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointmentList = new ArrayList<>();
        String sql = "SELECT * FROM Appointments";

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                appointmentList.add(new Appointment(
                        rs.getString("patient_id"),
                        rs.getString("doctor_id"),
                        rs.getDate("date"),
                        rs.getTime("time")
                ));

            }
        }
        catch (SQLException e) {
            System.err.printf(Messages.getMessage("error.sql"), e.getMessage());
        }

        return appointmentList;
    }

    public Patient getPatientById(String id) {
        // Check if patient is already in the cache
        Patient patient = patientCache.get(id);

        if (patient != null) {
            return patient; // Return the patient from cache if it is already there
        }

        // If patient is not in the cache, get it from the database
        String sql = "SELECT * FROM Patients WHERE patient_id = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                new Patient(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address"),
                    rs.getString("contact"),
                    rs.getDate("dob"),
                    rs.getString("sex"),
                    rs.getString("blood_type")
                );

                // Add the patient to the cache
                patientCache.put(id, patient);
            }
            else {
                // If patient is not found
                return null;
            }
        }
        catch (SQLException e) {
            System.err.printf(Messages.getMessage("error.sql"), e.getMessage());
        }

        return patient;
    }

}
