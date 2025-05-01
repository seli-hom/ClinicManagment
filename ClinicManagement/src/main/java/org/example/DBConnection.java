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
                    FOREIGN KEY(patient_id) REFERENCES Patients(id),
                    FOREIGN KEY(doctor_id) REFERENCES Doctors(id)
                );
                """;

        try {
            Connection conn = DBConnection.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);  // execute the create table statement
            System.out.println("Table created successfully.");
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


}
