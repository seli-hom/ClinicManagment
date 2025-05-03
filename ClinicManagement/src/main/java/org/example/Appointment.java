package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointment {
    private static int count = getStartingCountFromDB();
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private Date date;
    private Time time;

    // Constructor for creating new appointments
    public Appointment(String patientId, String doctorId, Date date, Time time) {
        this.appointmentId = String.format("A%03d", count++);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
    }

    // Constructor for loading existing appointments from the database
    public Appointment(String appointmentId, String patientId, String doctorId, Date date, Time time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
    }

    public static int getStartingCountFromDB() {
        String sql = "SELECT id FROM Appointments ORDER BY id DESC LIMIT 1";
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String lastId = rs.getString("id");
                int num = Integer.parseInt(lastId.substring(3)); // Get rid of the A00
                return num + 1; // start at the next available number
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // the default if there are no appointments yet
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
