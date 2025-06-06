package org.example;

import org.sqlite.core.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientDAO {
    private final Map<String, Patient> patientCache = new HashMap<>();

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
     */
    public  void insertPatientRecord(String id, String fName, String lName, String address, String contact, Date dob, String sex, String doctorId, String bloodType){
        String sql = "INSERT INTO Patients VALUES(?,?,?,?,?,?,?,?,?)"; //this is sql query with placeholders(?) instead of inserting raw values directly
        // ? are parameter ,markers they will be safely filled later
        //this helps prevent SQL injection attacks and make code cleaner
        try{
            Connection conn = DBConnection.getInstance().getConnection();
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

            pstmt.execute(); //insert data into table
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
     * @param newContact the new input address
     */
    public  void updatePatient(String patientId, String newAdress, String newContact) {
        String sql = "UPDATE Patients SET address = ?, contact = ? WHERE id = ?";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newAdress); // set student name
            pstmt.setString(2, newContact);
            pstmt.setString(3, patientId);
            int rowsUpdated = pstmt.executeUpdate(); // returns number of rows affected

            if (rowsUpdated > 0) {
                System.out.println("Patients was updated successfully.");
            }
            else {
                System.out.println("No patient with the provided ID exists");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateFamilyDoctor(String patientId, String doctorId) {
        String sql = "UPDATE Patients SET family_doctor = ? WHERE id = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctorId);
            pstmt.setString(2, patientId);
            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("No patient with ID " + patientId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to assign family doctor", e);
        }
    }

    /**
     * View all patients in the database
     * @return an array list of all the patients
     */
    public List<Patient> getAllPatients() {
        List<Patient> patientList = new ArrayList<>();
        String sql = "SELECT * FROM Patients";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getString("id"),
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
            System.err.printf(e.getMessage());
        }

        return patientList;
    }

    /**
     * Get a patient by their id
     * @param id the input patient id
     * @return the patient object
     */
    public Patient getPatientById(String id) {
        // Check if patient is already in the cache
        Patient patient = patientCache.get(id);

        if (patient != null) {
            return patient; // Return the patient from cache if it is already there
        }

        // If patient is not in the cache, get it from the database
        String sql = "SELECT * FROM Patients WHERE patient_id = ?";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                new Patient(
                        rs.getString("id"),
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
            System.err.printf( e.getMessage());
        }

        return patient;
    }

    public Map<String, Patient> getPatientCache() {
        return patientCache;
    }
}
