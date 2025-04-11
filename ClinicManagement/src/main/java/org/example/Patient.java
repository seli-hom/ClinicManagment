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

    public String getPatientId() {
        return patientId;
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

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public Record getRecord() {
        return record;
    }

    public static void setCount(int count) {
        Patient.count = count;
    }

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

    public void setRecord(Record record) {
        this.record = record;
    }
}
