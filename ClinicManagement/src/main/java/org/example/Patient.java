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
    private java.sql.Date birthDate;
    private String sex;
    private Doctor familyDoctor;
    private String type;
    private boolean discharged;

    private List<String> prescriptions;

    public Patient(String patientId, String firstName, String lastName, String address, String contact, java.sql.Date birthDate, String sex, String  type) {
        this.patientId = "P%03F" + count ++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contact = contact;
        this.birthDate = birthDate;
        this.sex = sex;
        this.familyDoctor = null; //at creation patient does not have a doctor in the clinic
        this.type = type;
        this.discharged = false;

//        this.prescriptions = prescriptions;
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
        return birthDate;
    }

    public String getSex() {
        return sex;
    }

    public Doctor getFamilyDoctor() {
        return familyDoctor;
    }

    public String getType() {
        return type;
    }

    public boolean isDischarged() { return discharged; }


    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


//    public void setBirthDate(Date birthDate) {
//        this.birthDate = birthDate;
//    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setFamilyDoctor(Doctor familyDoctor) {
        this.familyDoctor = familyDoctor;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDischarged(boolean discharged) {this.discharged = discharged;}


//    public void setPrescriptions(List<String> prescriptions) {
//        this.prescriptions = prescriptions;
//    }
}
