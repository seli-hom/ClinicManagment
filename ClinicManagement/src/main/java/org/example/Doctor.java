package org.example;

import java.util.List;

public class Doctor {
    private static int count = 1;
    private String doctorId;
    private String firstName;
    private String lastName;
    private String speciality;
    private String contact;
    private List<Patient> patients;

    public Doctor(String doctorId, String firstName, String lastName, String speciality, String contact, List<Patient> patients) {
        this.doctorId = "D%03d" + count++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;
        this.contact = contact;
        this.patients = patients;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public static int getCount() {
        return count;
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

    public List<Patient> getPatients() {
        return patients;
    }

    public static void setCount(int count) {
        Doctor.count = count;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
