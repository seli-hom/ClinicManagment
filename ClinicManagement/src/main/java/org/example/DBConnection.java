package org.example;

import java.sql.*;

public class DBConnection {
    private static DBConnection dObject;
    private Connection connection ;
    private DBConnection(){
        try {
            String DB_Path = "jdbc:sqlite:src/main/resources/databasedate.db";
            this.connection = DriverManager.getConnection(DB_Path);
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }

    // create public static method that allows us to create and access object we created
    // inside method, we will create a condition that restricts us from creating more than one object

    public static DBConnection getInstance(){
    // Singleton Pattern so that there will not be another creation of the database
        if (dObject == null){
            synchronized (DBConnection.class){
                if (dObject == null){
                    dObject = new DBConnection();
                }
            }
        }
        //return singleton object
        return dObject;
    }

    public  Connection getConnection() {
        return connection;
    }
    public void initializeDatabase() {
        createNewAppointmentsTable();
        createNewPatientsTable();
        createNewDoctorsTable();
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
        executeSQL(sql);
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
                    blood_type VARCHAR(10) NOT NULL,
                    family_doctor VARCHAR(50),
                    FOREIGN KEY(family_doctor) REFERENCES Doctors(id)
                );
                """;
        executeSQL(sql);
    }

    /**
     * Create an Appointments table if one does not already exist
     */
    public void createNewAppointmentsTable() {
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
        executeSQL(sql);
    }

    public void dropAllTables() {
        String sql1 = "DROP TABLE IF EXISTS Appointments;";
        String sql2 = "DROP TABLE IF EXISTS Patients;";
        String sql3 = "DROP TABLE IF EXISTS Doctors;";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql1);
            stmt.execute(sql2);
            stmt.execute(sql3);
            System.out.println("All tables dropped.");
        } catch (SQLException e) {
            System.err.println("Error dropping tables: " + e.getMessage());
        }
    }

    private void executeSQL(String sql) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error executing SQL: " + e.getMessage());
        }
    }

}
