package org.example;

import java.util.List;
import java.sql.*;

public class Patient {
    private static int count = 1;
    private String patientId;
    private String firstName;
    private String lastName;
    private String address;
    private String contact;
    private java.sql.Date dob;
    private String sex;
    private String familyDoctor;
    private String bloodType;
    private boolean discharged;

    private List<String> prescriptions;

    public Patient(String firstName, String lastName, String address, String contact, java.sql.Date dob, String sex, String bloodType) {
        this.patientId = String.format("P%03F" + count++);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contact = contact;
        this.dob = dob;
        this.sex = sex;
        this.familyDoctor = null; //at creation patient does not have a doctor in the clinic
        this.bloodType = bloodType;
        this.discharged = false;
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

    public Date getBirthDate() {
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

    public boolean isDischarged() { return discharged; }

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

    public void setDischarged(boolean discharged) {this.discharged = discharged;}
}
