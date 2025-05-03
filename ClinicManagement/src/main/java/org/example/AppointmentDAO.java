package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentDAO {
    private final Map<String, Appointment> appointmentCache = new HashMap<>();



    /**
     * Insert the following fields into the Appointments table
     * @param id the input id that will be retrieved when an Appointment object is created
     * @param patientId the input patient's id
     * @param doctorId the input doctor's id
     * @param date the input date of the appointment
     * @param time the input time of the appointment
     */
    public  void insertAppointmentRecord(String id, String patientId , String doctorId, Date date, Time time){
        String sql = "INSERT INTO Appointments (id, patient_id, doctor_id, date, time) VALUES(?, ?,?,?,?)"; //this is sql query with placeholders(?) instead of inserting raw values directly
        // ? are parameter ,markers they will be safely filled later
        //this helps prevent SQL injection attacks and make code cleaner
        try{
            Connection conn = DBConnection.getInstance().getConnection();
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
     * Update the date and time of an appointment
     * @param aptID the input appointment id
     * @param newDate the new input date
     * @param newTime the new input time
     */
    public  void updateSchedule(String aptID, Date newDate, Time newTime) {
        String sql = "UPDATE Appointments SET  date = ?, time = ? WHERE Id = ?";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
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
     * Cancel an appointment
     * @param id the input appointment id
     */
    public  void cancelAppointment(String id) {
        String sql = "DELETE FROM Appointments WHERE id = ?";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
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

    /**
     * View all doctors in the database
     * @return an array list of all the doctors
     */
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointmentList = new ArrayList<>();
        String sql = "SELECT * FROM Appointments";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getString("id"),
                        rs.getString("patient_id"),
                        rs.getString("doctor_id"),
                        rs.getDate("date"),
                        rs.getTime("time")
                );

                appointmentList.add(appointment);

                appointmentCache.put(appointment.getAppointmentId(), appointment);
            }
        }
        catch (SQLException e) {
            System.err.printf( e.getMessage());
        }

        return appointmentList;
    }

    /**
     * Get a patient by their id
     * @param id the input patient id
     * @return the patient object
     */
    public Appointment getAppointmentById(String id) {
        // Check if appointment is already in the cache
        Appointment appointment = appointmentCache.get(id);

        if (appointment != null) {
            return appointment; // Return the appointment from cache if it is already there
        }

        // If appointment is not in the cache, get it from the database
        String sql = "SELECT * FROM Appointments WHERE id = ?";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                new Appointment(
                        rs.getString("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("date"),
                        rs.getTime("time")
                );

                // Add the appointment to the cache
                appointmentCache.put(id, appointment);
            }
            else {
                // If appointment is not found
                return null;
            }
        }
        catch (SQLException e) {
            System.err.printf( e.getMessage());
        }

        return appointment;
    }
}
