package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private static int count = getStartingCountFromDB();
    private final String doctorId;
    private final String firstName;
    private final String lastName;
    private final String speciality;
    private String contact;

    // Constructor for creating new doctors
    public Doctor(String firstName, String lastName, String speciality, String contact) {
        this.doctorId = String.format("D%03d", count++);
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;
        this.contact = contact;
    }

    // Constructor for loading existing doctors from the database
    public Doctor(String doctorId, String firstName, String lastName, String speciality, String contact) {
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;
        this.contact = contact;
    }

    public static int getStartingCountFromDB() {
        String sql = "SELECT id FROM Doctors ORDER BY id DESC LIMIT 1";
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String lastId = rs.getString("id");
                int num = Integer.parseInt(lastId.substring(1)); // Get rid of the D
                return num + 1; // start at the next available number
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // the default if there are no doctors yet
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getContact() {
        return contact;
    }

//    public List<Patient> getPatients() {
//        return patients;
//    }

    public void setContact(String contact) {
        this.contact = contact;
    }

//    public void setPatients(List<Patient> patients) {
//        this.patients = patients;
//    }
}
