package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorDAO {
    private Map<String, Doctor> doctorCache = new HashMap<>();

    /**
     * Insert the following fields into the Doctors table
     * @param id the input id that will be retrieved when a Doctor object is created
     * @param fname the input first name
     * @param lname the input last name
     * @param specialty the input specialty
     * @param contact the input contact information
     */
    public void insertDoctorRecord(String id,String fname,String lname, String specialty, String contact){
        String sql = "INSERT INTO Doctors (id, first_name, last_name, specialty, contact) VALUES(?,?,?,?,?)"; //this is sql query with placeholders(?) instead of inserting raw values directly
        // ? are parameter ,markers they will be safely filled later
        //this helps prevent SQL injection attacks and make code cleaner
        try{
            Connection conn = DBConnection.connect();
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
     * Update a doctor's contact information
     * @param doctorId the input doctor id
     * @param newContact the new input contact information
     */
    public  void updateDoctor(String doctorId, String newContact) {
        String sql = "UPDATE Doctors SET  contact = ? WHERE Id = ?";

        try {
            Connection conn = DBConnection.connect();
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
     * Delete a Doctor from the database
     * @param id the input doctor id
     */
    public  void deleteDoctor(String id) {
        String sql = "DELETE FROM Doctors WHERE Id = ?";

        try {
            Connection conn = DBConnection.connect();
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
     * View all doctors in the database
     * @return an array list of all the doctors
     */
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctorList = new ArrayList<>();
        String sql = "SELECT * FROM Doctors";

        try {
            Connection conn = DBConnection.connect();
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
     * Get a patient by their id
     * @param id the input patient id
     * @return the patient object
     */
    public Doctor getDoctorById(String id) {
        // Check if doctor is already in the cache
        Doctor doctor = doctorCache.get(id);

        if (doctor != null) {
            return doctor; // Return the doctor from cache if it is already there
        }

        // If doctor is not in the cache, get it from the database
        String sql = "SELECT * FROM Doctors WHERE doctor_id = ?";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                new Doctor(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("specialty"),
                        rs.getString("contact")
                );

                // Add the doctor to the cache
                doctorCache.put(id, doctor);
            }
            else {
                // If doctor is not found
                return null;
            }
        }
        catch (SQLException e) {
            System.err.printf(Messages.getMessage("error.sql"), e.getMessage());
        }

        return doctor;
    }
}
