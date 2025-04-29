package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class DBConnection {

    private static DBConnection dObject;

    private DBConnection(){

    }

    //creat epublic static method that allows us to create and access object we created
    //inseide method, we will create a condition tha trestricts us from creating more than one object

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

    public  void createNewPatientsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Patients (
                    id VARCHAR(5) PRIMARY KEY,
                    first_name VARCHAR(50) NOT NULL,
                    last_name TEXT NOT NULL,
                    address TEXT,
                    contact TEXT NOT NULL
                    dob DATETIME NOT NULL,
                    sex VARCHAR(10) NOT NULL,
                    familyDoctor VARCHAR(50) FOREIGN KEY REFERENCES Doctors(id),
                    bloodType VARCHAR(10) NOT NULL,
                    height DOUBLE PRECISION NOT NULL,
                    weight DOUBLE PRECISION NOT NULL
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


    public  void createNewAppointmentsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Appointments (
                    id VARCHAR(5) PRIMARY KEY,
                    patient_id FOREIGN KEY REFERENCES patients(id),
                    doctor_id FOREIGN KEY REFERENCES doctors(id),
                    date DATE NOT NULL,
                    time TIME NOT NULL
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


    //==========Insert new record to table===================
    public  void insertPatientRecord(String fname,String lname, String address, String contact, Date dob, String sex, String doctorId, String bloodType, double height, double weight){
        String sql = "INSERT INTO patients (fname, lname, address, contact, dob, sex, doctorId, bloodType, height, weight) VALUES(?,?,?,?,?,?,?,?,?,?)"; //this is sql query with placeholders(?) instead of inserting raw values directly
        // ? are parameter ,markers they will be safely filled later
        //this helps prevent SQL injection attacks and make code cleaner
        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fname); //set student name
            pstmt.setString(2, lname);
            pstmt.setString(3, address);
            pstmt.setString(4, contact);
            pstmt.setDate(5, dob);
            pstmt.setString(6, sex);
            pstmt.setString(7, doctorId);
            pstmt.setString(8, bloodType);
            pstmt.setDouble(9, height);
            pstmt.setDouble(10, weight);
            pstmt.execute();//insert data into tble
            System.out.println("Data inserted successfully");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public  void insertDoctorRecord(String fname,String lname, String specialty, String contact){
        String sql = "INSERT INTO doctors (fname, lname, specialty, contact) VALUES(?,?,?,?)"; //this is sql query with placeholders(?) instead of inserting raw values directly
        // ? are parameter ,markers they will be safely filled later
        //this helps prevent SQL injection attacks and make code cleaner
        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fname); //set student name
            pstmt.setString(2, lname);
            pstmt.setString(3, specialty);
            pstmt.setString(4, contact);;
            pstmt.execute();//insert data into tble
            System.out.println("Data inserted successfully");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public  void insertAppointmentRecord(String appointment, String patientId , String doctorId, Date date, Time time){
        String sql = "INSERT INTO patients (appointment, patient, doctor, date, time) VALUES(?,?,?,?,?)"; //this is sql query with placeholders(?) instead of inserting raw values directly
        // ? are parameter ,markers they will be safely filled later
        //this helps prevent SQL injection attacks and make code cleaner
        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, appointment); //set student name
            pstmt.setString(2, patientId);
            pstmt.setString(3, doctorId);
            pstmt.setDate(4, date);
            pstmt.setTime(5, time);

            pstmt.execute();//insert data into tble
            System.out.println("Data inserted successfully");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    //===================Update Pateient===================
// update an existing student
    public  void updatePatient(String patientid, String newAdress, String newContact) {
        String sql = "UPDATE patients SET adress = ?, contact = ? WHERE patientId = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newAdress); // set student name
            pstmt.setString(2, newContact); // set student age
            pstmt.setString(3, patientid);
            int rowsUpdated = pstmt.executeUpdate(); // returns number of rows affected

            if (rowsUpdated > 0) {
                System.out.println("Patient's information was updated successfully.");
            }
            else {
                System.out.println("No patient with the provided ID exists");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //===========Update Doctors contact
    public  void updateDoctor(String doctorId, String newContact) {
        String sql = "UPDATE doctors SET  contact = ? WHERE doctorId = ?";

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
    //===============Reschedule Appointment
    public  void updateSchedule(String doctorId, String patientId, Date newDate, Time newTime) {
        String sql = "UPDATE appointements SET  date = ?, time = ? WHERE doctorId = ? AND patientId = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, newDate); // set doctor contact
            pstmt.setTime(2, newTime); // set doctor contact
            pstmt.setString(3, doctorId);
            pstmt.setString(4, patientId);
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


    //===================Delete Patient===================
    public  void dischargePatient(String id) {
        String sql = "DELETE FROM patients WHERE patientId = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            int rowsDeleted = pstmt.executeUpdate(); // returns number of rows affected

            if (rowsDeleted > 0) {
                System.out.println("Patient with id: " + id + " was discharged succesfully");
            }
            else {
                System.out.println("No patient with the provided ID exists");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //===============Cancel Appointment
    public  void cancelAppointment(String id) {
        String sql = "DELETE FROM appointments WHERE appointmentId = ?";

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
}
