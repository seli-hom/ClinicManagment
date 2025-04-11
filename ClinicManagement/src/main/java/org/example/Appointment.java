package org.example;

import java.sql.Time;
import java.util.Date;

public class Appointment {
    private Patient patient;
    private Doctor doctor;
    private Date date;
    private Time time;

    public Appointment(Patient patient, Doctor doctor, Date date, Time time) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }
}
