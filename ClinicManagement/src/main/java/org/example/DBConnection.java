package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static DBConnection dObject;

    private DBConnection(){

    }

    //creat epublic static method that allows us to create and access object we created
    //inseide method, we will create a condition tha trestricts us from creating more than one object

    public static DBConnection getInstance(){
        //if objected is not created create new object
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

    public static void createNewPatientsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Patients (
                    id TEXT PRIMARY KEY,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    address TEXT,
                    contact TEXT NOT NULL
                    birthdate DATETIME NOT NULL,
                    sex VARCHAR(10) NOT NULL,
                    familyDoctor VARCHAR(50) FOREIGN KEY REFERENCES Doctors(id),
                    bloodType VARCHAR(10) NOT NULL,
                    height DOUBLE PRECISION NOT NULL,
                    weight DOUBLE PRECISION NOT NULL,
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


    //==========Insert new record to table===================
    public static void insertRecord(String fname,String lname, String address, String contact, Date birthDate, Sex sex, Doctor doctor, Bloodtype blood, double height, double weight){
        String sql = "INSERT INTO patients (fname, lname, address, contact, birthDate, sex, doctor, blood, height, weight) VALUES(?,?,?,?,?,?,?,?,?,?"; //this is sql query with placeholders(?) instead of inserting raw values directly
        // ? are parameter ,markers they will be safely filled later
        //this helps prevent SQL injection attacks and make code cleaner
        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fname); //set student name
            pstmt.setInt(2, lname);
            pstmt.setInt(3, address);
            pstmt.setInt(4, contact);
            pstmt.setInt(5, birthDate);
            pstmt.setInt(6, sex);
            pstmt.setInt(7, doctor);
            pstmt.setInt(8, blood);
            pstmt.setInt(9, height);
            pstmt.setInt(10, weight);
            pstmt.execute();//insert data into tble
            System.out.println("Data inserted successfully");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    //===================Update Student===================
// update an existing student
    public static void updateStudent(int id, String newName, int newAge) {
        String sql = "UPDATE Students SET name = ?, age = ? WHERE id = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newName); // set student name
            pstmt.setInt(2, newAge); // set student age
            pstmt.setInt(3, id);
            int rowsUpdated = pstmt.executeUpdate(); // returns number of rows affected

            if (rowsUpdated > 0) {
                System.out.println("An existing student was updated successfully.");
            }
            else {
                System.out.println("No student with the provided ID exists");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //===================Delete Student===================
    public static void deleteStudent(int id) {
        String sql = "DELETE FROM Students WHERE id = ?";

        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(3, id);
            int rowsDeleted = pstmt.executeUpdate(); // returns number of rows affected

            if (rowsDeleted > 0) {
                System.out.println("The student with id: " + id + " was deleted succesfully");
            }
            else {
                System.out.println("No student with the provided ID exists");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


}
