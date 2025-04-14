package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
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

    public static void createNewPatientsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Patients (
                    id TEXT PRIMARY KEY,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    address TEXT,
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

    public static void createNewDoctorsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Doctors (
                    id TEXT PRIMARY KEY,
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

    public static void createNewAppointmentsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Appointments (
                    id TEXT PRIMARY KEY,
                    patient_id FOREIGN KEY,
                    doctor_id FOREIGN KEY,
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
}
