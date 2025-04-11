package org.example;

import java.util.Date;

public class Patient {
    private static int count = 1;
    private String patientId;
    private String firstName;
    private String lastName;
    private String address;
    private String contact;
    private Record record;

    public Patient(String patientId, String firstName, String lastName, String address, String contact, Record record) {
        this.patientId = "P%03d" + count++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contact = contact;
        this.record = record;
    }
}
