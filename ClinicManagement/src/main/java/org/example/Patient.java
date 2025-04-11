package org.example;

import java.util.Date;

public class Patient {
    private static int id = 1;
    private String firstName;
    private String lastName;
    private String address;
    private String contact;
    private Record record;

    public Patient(String firstName, String lastName, String address, String contact, Record record) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contact = contact;
        this.record = record;
    }
}
