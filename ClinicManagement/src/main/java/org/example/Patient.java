package org.example;

import java.util.List;
import java.sql.*;

public class Patient {
    private static int count = getStartingCountFromDB();
    private final String patientId;
    private final String firstName;
    private final String lastName;
    private String address;
    private String contact;
    private final java.sql.Date dob;
    private final String sex;
    private String familyDoctor;
    private String bloodType;

    private List<String> prescriptions;

    // Constructor for creating new patients
    public Patient(String firstName, String lastName, String address, String contact, java.sql.Date dob, String sex, String bloodType) {
        this.patientId = String.format("P%03d", count++);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contact = contact;
        this.dob = dob;
        this.sex = sex;
        this.familyDoctor = null; //at creation patient does not have a doctor in the clinic
        this.bloodType = bloodType;
    }

    // Constructor for loading existing patients from the database
    public Patient(String patientId, String firstName, String lastName, String address, String contact, Date dob, String sex, String bloodType) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contact = contact;
        this.dob = dob;
        this.sex = sex;
        this.familyDoctor = null;
        this.bloodType = bloodType;
    }

    public static int getStartingCountFromDB() {
        String sql = "SELECT id FROM Patients ORDER BY id DESC LIMIT 1";
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String lastId = rs.getString("id");
                int num = Integer.parseInt(lastId.substring(1)); // Get rid of the P00
                return num + 1; // start at the next available number
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // the default if there are no patients yet
    }

    public String getPatientId() {
        return patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public Date getDob() {
        return dob;
    }

    public String getSex() {
        return sex;
    }

    public String getFamilyDoctor() {
        return familyDoctor;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setFamilyDoctor(String familyDoctor) {
        this.familyDoctor = familyDoctor;
    }

    public void BloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
