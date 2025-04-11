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
}
